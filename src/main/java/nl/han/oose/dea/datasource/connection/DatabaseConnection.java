package nl.han.oose.dea.datasource.connection;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private DatabaseProperties databaseProperties;

    public DatabaseConnection() throws SQLException {
    }

    @Inject
    public void setDatabaseProperties(DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseProperties.connectionString());
    }

}
