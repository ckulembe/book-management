package service;

import	java.util.List;

import	model.Author;
import	exception.DatabaseException;
import	exception.NotFoundException;

public interface AuthorService {

	void	addAuthor( Author author ) throws DatabaseException;
	Author	getById( int id ) throws DatabaseException, NotFoundException;
	List<Author>	getAll() throws	DatabaseException;
	List<Author>	getByName( String firstname, String lastname ) throws DatabaseException, NotFoundException;
	void	updateAuthor( int id, String firstname, String lastname ) throws DatabaseException, NotFoundException;
	void	deleteAuthor( int id ) throws DatabaseException, NotFoundException;
	
}
