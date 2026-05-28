package service.implementation;

import java.util.List;
import	java.util.Optional;

import	exception.DatabaseException;
import	exception.NotFoundException;
import	service.AuthorService;
import	model.Author;
import	repository.AuthorRepository;

public class AuthorServiceImplement implements AuthorService {

	private	final	AuthorRepository	repository;

	public	AuthorServiceImplement ( AuthorRepository authorRepository ) {
		this.repository = authorRepository;
	}

	public void	addAuthor(String firstname, String lastname ) throws DatabaseException {
		
		if ( firstname == null || firstname.isBlank() )
			throw new IllegalArgumentException("Nome obrigatório.");
		if ( lastname == null || lastname.isBlank() )
			throw new IllegalArgumentException("Sobrenome obrigatório.");

		List<Author>	authorlist = repository.findByName(firstname, lastname);
		if ( !authorlist.isEmpty() )
			throw new IllegalStateException( "Author já existe." );

		Author	author = new Author();
		author.setFirstName(firstname);
		author.setLastName(lastname);

		this.repository.save(author);
	}

	public Author	getById( int id ) throws DatabaseException, NotFoundException {

		Optional<Author>	optional = repository.findById(id);
		if ( optional.isEmpty() )
			throw new NotFoundException( "Author não encontrado." );
		return optional.get();
	}

	public List<Author>	getAll() throws	DatabaseException {
		return repository.findAll();
	}

	public List<Author>	getByName( String firstname, String lastname ) throws DatabaseException, NotFoundException {

		List<Author>	authorList = repository.findByName(firstname, lastname);
		
		if ( authorList.isEmpty() )
			throw new NotFoundException( "Nenhum author relacionado ao nome " + firstname + " " + lastname + "." );
		return authorList;
	}

	public void	updateAuthor( int id, String firstname, String lastname ) throws DatabaseException, NotFoundException {

		
		if ( firstname == null || firstname.isBlank() )
			throw new IllegalArgumentException("Nome obrigatório.");
		if ( lastname == null || lastname.isBlank() )
			throw new IllegalArgumentException("Sobrenome obrigatório.");

		Optional<Author>	optional = repository.findById(id);
		if ( optional.isEmpty() )
			throw new NotFoundException( "Não pode atualizar, author não encontrado." );
	
		Author	author = optional.get();
		author.setFirstName(firstname);
		author.setLastName(lastname);

		repository.update( author );
	}

	public void	deleteAuthor( int id ) throws DatabaseException, NotFoundException {
		repository.delete( id );
	}
}
