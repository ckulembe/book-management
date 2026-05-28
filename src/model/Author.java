package model;

public class Author
{
	private int	id;
	private String	firstname;
	private String	lastName;

	public	Author() {};

	public	Author( int id, String firstname, String lastname )
	{
		this.setId(id);
		this.setFirstName(firstname);
		this.setLastName(lastname);
	}

	public void	setId( int id ) {
		this.id = id;
	}

	public void	setFirstName( String firstname ) {
		this.firstname = firstname;
	}

	public void	setLastName( String lastname ) {
		this.lastName = lastname;
	}

	public String	getFirstName() {
		return this.firstname;
	}

	public String	getLastName() {
		return this.lastName;
	}

	public int	getId() {
		return this.id;
	}

	@Override
	public	String	toString() {
		return String.format("Author{ id=%d, name='%s %s' }", this.getId(), this.getFirstName(), this.getLastName());
	}

	@Override
	public	boolean	equals( Object o ) {
		if ( this == o ) return true;
		if ( !(o instanceof Author) ) return false;
		Author a = ( Author ) o;
		return this.id == a.id;
	}

	@Override
	public	int	hashCode() {
		return Integer.hashCode( id );
	}
}
