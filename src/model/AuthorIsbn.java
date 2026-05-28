package model;

public class AuthorIsbn {
	
	private	int	authorId;
	private String	isbn;

	public	AuthorIsbn() {};
	public	AuthorIsbn( int	authorId, String isbn ) {
		this.setAuthorId(authorId);
		this.setIsbn(isbn);
	};

	public void	setAuthorId( int id ) {
		this.authorId = id;
	}

	public void	setIsbn( String isbn ) {
		this.isbn = isbn;
	}

	public int	getAuthorId() {
		return this.authorId;
	}

	public String	getIsbn() {
		return this.isbn;
	}
}
