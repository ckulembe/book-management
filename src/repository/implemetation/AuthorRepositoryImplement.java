package repository.implemetation;

import	java.sql.Connection;
import	java.sql.PreparedStatement;
import	java.util.LinkedList;
import	java.sql.ResultSet;
import java.sql.SQLException;
import	java.util.List;
import	java.util.Optional;

import exception.DatabaseException;
import repository.AuthorRepository;
import model.Author;

public class AuthorRepositoryImplement implements AuthorRepository {

	private	final	Connection	connection;

	public	AuthorRepositoryImplement( Connection connection ) {
		this.connection = connection;
	}

	public	void	save( Author author ) throws DatabaseException {

		String	sql = "INSERT INTO authors ( firstname, lastname ) VALUES (?, ?)";

		try ( PreparedStatement preparedStatement = connection.prepareStatement( sql ) )
		{
			preparedStatement.setString( 1, author.getFirstName() );
			preparedStatement.setString( 2, author.getLastName() );
			preparedStatement.executeUpdate();
		}
		catch( SQLException exception )
		{
			throw new DatabaseException("Erro na tentativa de salver o author: " + exception.getMessage() );
		}
	}

	public Optional<Author> findById( int id ) throws DatabaseException {

		String	sql = "SELECT * FROM authors WHERE id=?";

		try( PreparedStatement	preparedStatement = this.connection.prepareStatement( sql ))
		{
			preparedStatement.setInt(1, id );
			ResultSet	resultSet = preparedStatement.executeQuery();
		
			if ( resultSet.next() )
				return Optional.of(
					new Author(
						resultSet.getInt( "id" ),
						resultSet.getString( "firstname" ),
						resultSet.getString( "lastname" )
				));

			return Optional.empty();
		}
		catch ( SQLException exception )
		{
			throw	new DatabaseException("Erro ao buscar author: " + exception.getMessage());
		}
	}

	public List<Author>	findAll()	throws DatabaseException {

		String	sql = "SELECT * FROM authors ";

		try ( 
			PreparedStatement preparedStatement = connection.prepareStatement( sql );
			ResultSet	resultSet = preparedStatement.executeQuery();
		)
		{
			List<Author>	authorList = new LinkedList<Author>();
	
			while ( resultSet.next() )
				authorList.add(
					new Author(
						resultSet.getInt("id"),
						resultSet.getString("firstname"),
						resultSet.getString("lastname")
					)
				);
	
			return authorList;
		}
		catch ( SQLException exception )
		{
			throw new DatabaseException("Erro" + exception.getMessage() );
		}
	}

	public List<Author>	findByName( String firstname, String lastname )	throws DatabaseException {

		String	sql = "SELECT * FROM authors WHERE firstname=? AND lastname=?";

		try( PreparedStatement	preparedStatement = connection.prepareStatement( sql ) )
		{
			preparedStatement.setString( 1, firstname );
			preparedStatement.setString( 2, lastname );
			ResultSet	resultSet = preparedStatement.executeQuery();

			List<Author>	authorList = new LinkedList<Author>();
	
			if ( resultSet.wasNull() )
			{
				resultSet.close();
				return null;
			}

			while ( resultSet.next() )
				authorList.add(
					new Author(
						resultSet.getInt( "id" ),
						resultSet.getString( "firstname" ),
						resultSet.getString( "lastname" )
					)
				);
		
		
			resultSet.close();
			return authorList;
		}
		catch ( SQLException exception )
		{
			throw new DatabaseException( "Erro: " + exception.getMessage() );
		}
	}
	
	public void	update( Author author )	throws DatabaseException {

		String	sql = "UPDATE authors SET firstname=?, lastname=? WHERE id=?";

		try ( PreparedStatement	preparedStatement = connection.prepareStatement( sql ) )
		{			
			preparedStatement.setString( 1, author.getFirstName() );
			preparedStatement.setString( 2, author.getLastName() );
			preparedStatement.setInt( 3, author.getId() );
			preparedStatement.executeUpdate();
		}
		catch ( SQLException exception )
		{
			throw new DatabaseException ( "" + exception.getMessage() );
		}
	}

	public void	delete( int id ) throws DatabaseException {

		String	sql = "DELETE FROM authors WHERE id=?";

		try( PreparedStatement	preparedStatement = connection.prepareStatement( sql ) ){
			preparedStatement.setInt(1, id );
			preparedStatement.executeUpdate();
		}
		catch( SQLException exception )
		{
			throw new DatabaseException( "Erro" + exception.getMessage() );
		}
	}
}
