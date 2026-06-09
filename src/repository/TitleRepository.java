package repository;

import	java.util.List;
import	java.util.Optional;

import	model.Title;
import	exception.DatabaseException;

public interface TitleRepository {
	
	void	save( Title title )	throws	DatabaseException;
	void	delete( String	isbn )	throws	DatabaseException;
	void	update( Title title )	throws DatabaseException;
	List<Title>	findByName( String name )	throws	DatabaseException;
	List<Title>	findAll()	throws	DatabaseException;
	Optional<Title>	findById( String isbn )	throws DatabaseException;

}
