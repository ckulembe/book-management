package repository;

import java.util.List;
import java.util.Optional;

import exception.DatabaseException;
import model.Author;

public interface AuthorRepository {

	void	save( Author author )	throws DatabaseException;
	Optional<Author> findById(int id)	throws DatabaseException;
	List<Author>	findAll()	throws DatabaseException;
	List<Author>	findByName( String firstname, String lastname )	throws DatabaseException;
	void	update( Author author )	throws	DatabaseException;
	void	delete( int id )	throws	DatabaseException;

}
