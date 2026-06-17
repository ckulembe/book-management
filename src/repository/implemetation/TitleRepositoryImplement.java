package repository.implemetation;

import	java.util.List;
import	java.util.Optional;
import	java.sql.ResultSet;
import	java.util.LinkedList;
import	java.sql.Connection;
import	java.sql.SQLException;
import	java.sql.PreparedStatement;

import	model.Title;
import	repository.TitleRepository;
import	exception.DatabaseException;

public class TitleRepositoryImplement implements TitleRepository {

	private	final	Connection	connection;

	public	TitleRepositoryImplement( Connection connection ) {
		this.connection = connection;
	}

	public void	save( Title title ) throws DatabaseException {
		
		String	sql = "INSERT INTO titles ( isbn, title, edition_number, copyright ) VALUES (?, ?, ?, ?)";

		try ( PreparedStatement	preparedStatement = connection.prepareStatement( sql ) )
		{
			preparedStatement.setString( 1, title.getIsbn() );
			preparedStatement.setString( 2, title.getTitle() );
			preparedStatement.setInt( 3, title.getEditionNumber() );
			preparedStatement.setInt( 4, title.getCopyRight() );

			preparedStatement.executeUpdate();
		}
		catch ( SQLException e )
		{
			throw new DatabaseException( e.getMessage() );
		}
	}

	public Optional<Title> findById( String isbn ) throws DatabaseException {
		
		String	sql = "SELECT * FROM titles WHERE isbn=?";

		try ( PreparedStatement	preparedStatement = connection.prepareStatement( sql ) )
		{
			preparedStatement.setString( 1, isbn );
			ResultSet	resultSet = preparedStatement.executeQuery();
			if ( resultSet.next() )
				return Optional.of(
						new Title(
							resultSet.getString( "isbn" ),
							resultSet.getString( "title" ),
							resultSet.getInt( "edition_number" ),
							resultSet.getInt( "copyright" )
						)
					);
			return Optional.empty();
		}
		catch ( SQLException e )
		{
			throw new DatabaseException( "" );
		}
	}

	public List<Title>	findAll() throws DatabaseException {

		String	sql = "SELECT * FROM titles";

		try ( PreparedStatement	preparedStatement = connection.prepareStatement( sql ) )
		{
			ResultSet	resultSet = preparedStatement.executeQuery();

			List<Title>	titles = new LinkedList<Title>();
			while ( resultSet.next() )
				titles.add(
					new Title(
						resultSet.getString("isbn"),
						resultSet.getString("title"),
						resultSet.getInt("edition_number"),
						resultSet.getInt("copyright")
					)
				);
			return titles;

		}
		catch ( SQLException e )
		{
			throw new DatabaseException( "" );
		}
	}

	public List<Title>	findByName( String isbn )	throws DatabaseException {

		String	sql = "SELECT * FROM titles WHERE title=?";

		try ( PreparedStatement	preparedStatement = connection.prepareStatement( sql ) )
		{
			preparedStatement.setString( 1, isbn );
			ResultSet	resultSet = preparedStatement.executeQuery();
			
			List<Title>	titles = new LinkedList<Title>();
			while ( resultSet.next() )
				titles.add(
					new Title(
						resultSet.getString( "isbn" ),
						resultSet.getString( "title" ),
						resultSet.getInt( "edition_number" ),
						resultSet.getInt( "copyright" )
					)
				);
			return titles;
		}
		catch ( SQLException e )
		{
			throw new DatabaseException("");
		}
	}

	public void	update( Title title )	throws	DatabaseException {

		String	sql = "UPDATE titles SET title=?, edition_number=?, copyright=? WHERE isbn=?";

		try ( PreparedStatement	preparedStatement = connection.prepareStatement( sql ) )
		{	
			preparedStatement.setString( 1, title.getTitle() );
			preparedStatement.setInt( 2, title.getEditionNumber() );
			preparedStatement.setInt( 3, title.getCopyRight() );
			preparedStatement.setString( 4, title.getIsbn() );
			preparedStatement.executeUpdate();
		}
		catch ( SQLException e )
		{
			throw new DatabaseException( "" );
		}
	}

	public void	delete( String isbn ) throws DatabaseException {
		
		String	sql = "DELETE FROM titles WHERE isbn=?";

		try ( PreparedStatement	preparedStatement = connection.prepareStatement( sql ) )
		{	
			preparedStatement.setString( 1, isbn );
			preparedStatement.executeUpdate();
		}
		catch ( SQLException e )
		{
			throw new DatabaseException( "" );
		}
	}
}
