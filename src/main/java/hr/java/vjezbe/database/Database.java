package hr.java.vjezbe.database;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    private static final String DATABASE_PROPERTIES = "database.properties";
    static Connection connectToDatabase() throws SQLException, IOException {
        Properties settings = new Properties();
        settings.load(new FileReader(DATABASE_PROPERTIES));

        String databaseUrl = settings.getProperty("DATABASE_URL");
        String user = settings.getProperty("DATABASE_USER");
        String password = settings.getProperty("DATABASE_PASSWORD");

        return DriverManager.getConnection(databaseUrl,user,password);
    }
}
