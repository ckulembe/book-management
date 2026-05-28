package db;
import	java.sql.Connection;
import	java.sql.DriverManager;
import	java.sql.SQLException;

import exception.DatabaseException;

public class ConnectionDB {

	private	static	Connection	connection;

	public	ConnectionDB() {}

	public	static	Connection	getConnection() throws DatabaseException {
		try
		{
			if ( connection == null || connection.isClosed() ) {
				connection = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/books",
                    			"postgres",
                    			"1q2w3e"
				);
			}
			return connection;

		} catch ( SQLException e ) {
			throw new DatabaseException( "Erro ao conectar: " + e.getMessage() );
		}
	}
}
