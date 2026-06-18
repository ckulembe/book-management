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
					"jdbc:postgresql://ep-nameless-pond-asmg3kg0-pooler.c-4.eu-central-1.aws.neon.tech:5432/neondb?sslmode=require",
    				"neondb_owner",
    				"npg_mQFxMXDqC41f"
				);
			}
			return connection;

		} catch ( SQLException e ) {
			throw new DatabaseException( "Erro ao conectar: " + e.getMessage() );
		}
	}
}
