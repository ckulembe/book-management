import	java.sql.Connection;

import	db.ConnectionDB;
import	exception.DatabaseException;
import	exception.NotFoundException;
import	repository.AuthorRepository;
import	repository.implemetation.AuthorRepositoryImplement;
import	service.AuthorService;
import	service.implementation.AuthorServiceImplement;


class JDBCMain
{
	public static void main(String[] args)
	{
		try
		{
			Connection connect = ConnectionDB.getConnection();

			AuthorRepository	repository	= new AuthorRepositoryImplement( connect );
			AuthorService		service		= new AuthorServiceImplement( repository ); 

			// service.addAuthor("Tudo", "Todo");

			
			// service.deleteAuthor( 17 );
			// service.deleteAuthor( 18 );
			

			service.updateAuthor(9, "Domingos", "Sabino");
			
			service.getAll().forEach(System.out::println);


		} // Os métodos close dos objectos AutoCloseable são chamados agora
		catch( DatabaseException | NotFoundException  e )
		{
			System.err.println("Erro: " + e.getMessage());
		}
	}
}