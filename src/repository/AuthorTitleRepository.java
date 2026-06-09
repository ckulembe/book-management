package repository;

import	java.util.List;

import	model.Title; 
import	model.Author;
import	exception.DatabaseException;

public interface AuthorTitleRepository {
	
	void	addRelation( int author, String isbn ) throws DatabaseException;
	void	removeRelation( int authorId, String isbn ) throws DatabaseException;
	boolean	existsRelation( int authorId, String isbn ) throws DatabaseException;
	List<Title>	findTitlesByAuthor( int authorId ) throws DatabaseException;
	List<Author>	findAuthorsByTitle( String isbn ) throws DatabaseException;

}
