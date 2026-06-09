package repository.implemetation;

import	java.util.List;
import	java.sql.ResultSet;
import	java.sql.Connection;
import	java.util.LinkedList;
import	java.sql.SQLException;
import	java.sql.PreparedStatement;

import	model.Title;
import	model.Author;
import	exception.DatabaseException;
import	repository.AuthorTitleRepository;

public class AuthorTitleRepositoryImplement implements AuthorTitleRepository {
	
	private	final	Connection	connection;

	public	AuthorTitleRepositoryImplement( Connection connection )
	{
		this.connection = connection;
	}

	public	void	addRelation( int author, String isbn ) throws DatabaseException
	{

		String	sql = "INSERT INTO author_isbn ( authorid, isbn ) VALUES ( ?, ? ) ";

		try ( PreparedStatement preparedStatement = connection.prepareStatement(sql) )
		{
			preparedStatement.setInt( 1, author );
			preparedStatement.setString( 2, isbn );
			preparedStatement.executeUpdate();
		}
		catch( SQLException e )
		{
			throw new DatabaseException( " " );
		}
	}

	public void	removeRelation( int authorId, String isbn ) throws DatabaseException
	{
		String	sql = "DELETE FROM author_isbn WHERE authorid=? AND isbn=? ";

		try ( PreparedStatement preparedStatement = connection.prepareStatement( sql ) )
		{
			preparedStatement.setInt( 1, authorId );
			preparedStatement.setString( 2, isbn );
			preparedStatement.executeUpdate();
		}
		catch( SQLException e )
		{
			throw new DatabaseException( " " );
		}
	}

	public boolean	existsRelation( int authorId, String isbn ) throws DatabaseException
	{
		String	sql = "SELECT * FROM author_isbn WHERE authorid=? AND isbn=?";

		try( PreparedStatement preparedStatement = connection.prepareStatement( sql ) )
		{
			preparedStatement.setInt( 1, authorId );
			preparedStatement.setString( 2, isbn );
			ResultSet	resultSet = preparedStatement.executeQuery();
			boolean	bool = false;
			if ( resultSet.next() )
				bool = true;
			resultSet.close();
			return bool;
		}
		catch( SQLException e )
		{
			throw new DatabaseException( " " );
		}
	}

	public	List<Title>	findTitlesByAuthor( int authorId ) throws DatabaseException
	{
		String	sql = "SELECT t.isbn, t.title, t.edition_number, t.copyright FROM "
			+ " author_isbn ai INNER JOIN titles t " 
			+ " ON ai.isbn = t.isbn "
			+ " WHERE ai.authorid=?";

		try ( PreparedStatement preparedStatement = connection.prepareStatement( sql ) )
		{
			preparedStatement.setInt( 1, authorId );
			ResultSet	resultSet = preparedStatement.executeQuery();
			List<Title>	listOfTitle = new LinkedList<Title>();
			while ( resultSet.next() )
				listOfTitle.add(
					new Title(
						resultSet.getString( "isbn" ),
						resultSet.getString( "title" ),
						resultSet.getInt( "edition_number" ),
						resultSet.getInt( "copyright" )
					)
				);
			return listOfTitle;
		}
		catch ( SQLException e )
		{
			throw new DatabaseException( "" );
		}
	}

	public	List<Author>	findAuthorsByTitle( String isbn ) throws DatabaseException
	{
		String	sql = "SELECT a.id, a.firstname, a.lastname FROM "
			+ " author_isbn ai INNER JOIN authors a " 
			+ " ON ai.authorid=a.id "
			+ " WHERE ai.isbn=?";

		try( PreparedStatement preparedStatement = connection.prepareStatement( sql ) )
		{
			preparedStatement.setString( 1, isbn );
			ResultSet	resultSet = preparedStatement.executeQuery();
			List<Author>	listOfAuthor = new LinkedList<Author>();
			while ( resultSet.next() )
				listOfAuthor.add(
					new Author(
						resultSet.getInt("id"),
						resultSet.getString("firstname"),
						resultSet.getString("lastname")
					)
				);
			resultSet.close();
			return listOfAuthor;
		}
		catch ( SQLException e )
		{
			throw new DatabaseException( "" );
		}
	}
}
