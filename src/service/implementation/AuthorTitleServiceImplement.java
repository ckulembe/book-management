package service.implementation;

import java.sql.Connection;
import java.sql.SQLException;
import	java.util.List;
import java.util.Optional;

import exception.DatabaseException;
import exception.NotFoundException;
import	model.Title;
import	repository.AuthorRepository;
import repository.AuthorTitleRepository;
import repository.TitleRepository;
import	model.Author;
import	service.AuthorTitleService;

public	class	AuthorTitleServiceImplement implements AuthorTitleService
{
	private final	Connection	connection;
	private final	AuthorRepository	authorRepository;
	private final	TitleRepository	titleRepository;
	private final	AuthorTitleRepository	authorTitleRepository;

	public	AuthorTitleServiceImplement( 
		Connection connection,
		AuthorRepository authorRepository,
		TitleRepository titleRepository,
		AuthorTitleRepository	authorTitleRepository )
	{
		this.connection = connection;
		this.authorRepository = authorRepository;
		this.titleRepository = titleRepository;
		this.authorTitleRepository = authorTitleRepository;
	}

	public void	assignAuthorToTitle( int authorId, String isbn ) throws DatabaseException, NotFoundException
	{
		if ( authorId <= 0 )
			throw new IllegalArgumentException();
		if ( isbn == null || isbn.isBlank() )
			throw new IllegalArgumentException();

		Optional<Author> author = authorRepository.findById(authorId);
		if ( author.isEmpty() )
			throw new NotFoundException("author id " + authorId + " not exist");
		Optional<Title>	title = titleRepository.findById(isbn);
		if ( title.isEmpty() )	
			throw new NotFoundException("title isbn " + isbn +" not exist");

		if ( authorTitleRepository.existsRelation(authorId, isbn) )
			throw new IllegalArgumentException("Relation already exists");

		authorTitleRepository.addRelation(authorId, isbn);
	}

	public void	removeAuthorFromTitle( int authorId, String isbn ) throws DatabaseException
	{
		if ( authorId <= 0 )
			throw new IllegalArgumentException();
		if ( isbn == null || isbn.isBlank() )
			throw new IllegalArgumentException();

		authorTitleRepository.removeRelation(authorId, isbn);
	}

	public List<Title>	getTitlesByAuthor( int authorId ) throws DatabaseException, NotFoundException
	{
		if ( authorId <= 0 )
			throw new IllegalArgumentException();

		List<Title>	listOfTitle = authorTitleRepository.findTitlesByAuthor(authorId);
		if ( listOfTitle.isEmpty() )
			throw new NotFoundException( "Titles not found" );

		return listOfTitle;
	}

	public List<Author>	getAuthorsByTitle( String isbn ) throws DatabaseException, NotFoundException
	{
		if ( isbn == null || isbn.isBlank() )
			throw new IllegalArgumentException();

		List<Author>	listOfAuthor = authorTitleRepository.findAuthorsByTitle(isbn);
		if ( listOfAuthor.isEmpty() )
			throw new NotFoundException( "Authors not found" );

		return listOfAuthor;
	}

	public void	createTitleWithAuthors( Title title, List<Integer> authorIds ) throws DatabaseException, NotFoundException
	{
		if ( title == null )
			throw new IllegalArgumentException();
		if ( authorIds == null || authorIds.isEmpty() )
			throw new IllegalArgumentException();

		try
		{
			this.connection.setAutoCommit(false);
			titleRepository.save(title);
			for ( Integer id : authorIds )
			{
				if ( authorRepository.findById( id ).isEmpty() )
					throw new NotFoundException( "Author id {" + id + "} not found" );
				authorTitleRepository.addRelation( id, title.getIsbn() );
			}
			connection.commit();
		}
		catch( DatabaseException e )
		{
			rollbackQuickly();
			throw new DatabaseException(
				e.getMessage()
			);
		}
		catch( NotFoundException e )
		{
			rollbackQuickly();
			throw new NotFoundException(
				e.getMessage()
			);
		}
		catch( SQLException e )
		{
			rollbackQuickly();
			throw new DatabaseException(
				e.getMessage()
			);
		}
		finally
		{
			try {

				this.connection.setAutoCommit(true);
			} catch ( SQLException e )
			{
				e.printStackTrace();
			}
		}
	}

	private	final	void	rollbackQuickly()
	{
		try
		{
			connection.rollback();
		}
		catch ( SQLException rollbackException )
		{
			rollbackException.printStackTrace();
		}
	}
}