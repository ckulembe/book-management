package service;

import	java.util.List;

import	model.Title;
import	exception.DatabaseException;
import	exception.NotFoundException;

public interface TitleService {

	void	addTitle( Title title ) throws DatabaseException;
	Title	getById( String isbn ) throws DatabaseException, NotFoundException;
	List<Title>	getAll() throws	DatabaseException;
	List<Title>	getByName( String title ) throws DatabaseException, NotFoundException;
	void	updateTitle( Title title ) throws DatabaseException, NotFoundException;
	void	deleteTitle( String isbn ) throws DatabaseException, NotFoundException;
	
}
