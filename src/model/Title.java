package model;

public class Title {

	private int	editionNumber;
	private int	copyright;
	private String	isbn;
	private String	title;

	public	Title() {

	};
	public	Title( String isbn, String title, int editionNumber, int copyright ) {
		this.setIsbn(isbn);
		this.setTitle(title);
		this.setEditionNumber(editionNumber);
		this.setCopyRight(copyright);
	}

	public void	setEditionNumber( int editionNumber ) {
		this.editionNumber = editionNumber;
	}

	public void	setCopyRight( int copyright ) {
		this.copyright = copyright;
	}

	public void	setIsbn( String isbn ) {
		this.isbn = isbn;
	}

	public void	setTitle( String title ) {
		this.title = title;
	}

	public int	getEditionNumber() {
		return this.editionNumber;
	}

	public int	getCopyRight() {
		return this.copyright;
	}

	public String	getIsbn() {
		return this.isbn;
	}

	public String	getTitle() {
		return this.title;
	}

	@Override
	public String	toString()
	{
		return String.format("Title{ isbn=%s title=%s edition_number=%d copyright=%d }", this.isbn, this.title, this.editionNumber, this.copyright );
	}

	
}
