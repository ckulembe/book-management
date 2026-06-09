package service;

import	java.util.List;

import exception.DatabaseException;
import exception.NotFoundException;
import	model.Title;
import	model.Author;

public interface AuthorTitleService {
	
	void	assignAuthorToTitle( int authorId, String isbn ) throws DatabaseException, NotFoundException;
	void	removeAuthorFromTitle( int authorId, String isbn ) throws DatabaseException;
	List<Title>	getTitlesByAuthor( int authorId ) throws DatabaseException, NotFoundException;
	List<Author>	getAuthorsByTitle( String isbn ) throws DatabaseException, NotFoundException;
}
