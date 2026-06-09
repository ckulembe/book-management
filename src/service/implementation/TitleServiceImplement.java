package service.implementation;

import java.util.List;
import	java.util.Optional;

import exception.DatabaseException;
import exception.NotFoundException;
import model.Title;
import repository.TitleRepository;
import service.TitleService;

public class TitleServiceImplement implements TitleService {
	
	private final	TitleRepository titleRepository;

	public	TitleServiceImplement( TitleRepository titleRepository )
	{
		this.titleRepository = titleRepository;
	}

	public void	addTitle( Title title ) throws DatabaseException {

		if ( title == null || title.getIsbn().isBlank() )
			throw new IllegalArgumentException();
		if (  title.getTitle().isBlank() )
			throw new IllegalArgumentException();
		if ( title.getCopyRight() <= 0 || title.getEditionNumber() <= 0 )
			throw new IllegalArgumentException();

		titleRepository.save( title );
	}

	public Title	getById( String isbn ) throws DatabaseException, NotFoundException {

		if ( isbn == null || isbn.isBlank() )
			throw new IllegalArgumentException();

		Optional<Title>	optional = titleRepository.findById( isbn );
		if ( optional.isEmpty() )
			throw new NotFoundException( "" );
		return optional.get();
	}

	public List<Title>	getAll() throws	DatabaseException {
		return	titleRepository.findAll();
	}

	public List<Title>	getByName( String title ) throws DatabaseException, NotFoundException {

		if ( title == null || title.isBlank() )
			throw new IllegalArgumentException();

		List<Title>	titles = titleRepository.findByName( title );

		if ( titles.isEmpty() )
			throw new NotFoundException( "" );
		return titles;
	}

	public void	updateTitle( Title title ) throws DatabaseException {

		if ( title == null )
			throw new IllegalArgumentException();
		titleRepository.update( title );
	}

	public void	deleteTitle( String isbn ) throws DatabaseException, NotFoundException {

		if ( isbn == null || isbn.isBlank() )
			throw new IllegalArgumentException();
		titleRepository.delete( isbn );
	}
}
