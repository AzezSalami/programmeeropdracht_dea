package nl.han.oose.dea.datasource.dao;

import nl.han.oose.dea.controllers.LoginController;
import nl.han.oose.dea.controllers.dto.LoginDTO;
import nl.han.oose.dea.controllers.dto.LoginRespondeDTO;
import nl.han.oose.dea.datasource.connection.DatabaseConnection;
import nl.han.oose.dea.datasource.datamapper.LoginDataMapper;
import nl.han.oose.dea.datasource.datamapper.UserDataMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LoginDAOTest {
    private LoginDAO sut;
    private DatabaseConnection mockedDatabaseConnection;
    private LoginDataMapper mockedLoginDataMapper;
    private UserDataMapper mockedUserDataMapper;
    private Connection connection;

    private Connection getH2Connection(){
        try {
            Class.forName("org.h2.Driver");
            return DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @BeforeEach
    public void setup() throws SQLException {
        sut = new LoginDAO();

        mockedDatabaseConnection = mock(DatabaseConnection.class);
        sut.setDatabaseConnection(mockedDatabaseConnection);

        mockedLoginDataMapper = mock(LoginDataMapper.class);
        sut.setLoginDataMapper(mockedLoginDataMapper);

        mockedUserDataMapper = mock(UserDataMapper.class);
        sut.setUserDataMapper(mockedUserDataMapper);
        try {
            connection = getH2Connection();
            String SQL = "DROP TABLE IF EXISTS `users`;" +
                    "\n" +
                    "CREATE TABLE users (" +
                    "  login_name   varchar(45) NOT NULL," +
                    "  password     varchar(45) NOT NULL," +
                    "  token        varchar(45) NOT NULL," +
                    "  username     varchar(45) NOT NULL," +
                    "  PRIMARY KEY (`login_name`)" +
                    ");";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.executeUpdate();


            when(mockedDatabaseConnection.getConnection()).thenReturn(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    @Test
//    public void testLoginDAOfindUserOpensConnection() throws SQLException {
//        // Arrange
//        // Act
//        sut.findUser("azezsalami");
//        // Assert
//        verify(mockedDatabaseConnection).getConnection();
//    }
//
//    @Test
//    public void testLoginDAOfindUserUsesMapper() throws SQLException {
//        // Arrange
//        String username = "azezsalami";
//        String password = "password";
//        LoginDTO loginDTO = new LoginDTO(username, password);
//        PreparedStatement statement = connection.prepareStatement("select * from users where username =?");
//        statement.setString(1, username);
//        ResultSet resultSet = statement.executeQuery();
//        when(mockedLoginDataMapper.toDTO(resultSet)).thenReturn(loginDTO);
//        // Act
//        LoginDTO result = sut.findUser(username);
//        // Assert
//        verify(mockedLoginDataMapper).toDTO(any(ResultSet.class));
//        assertEquals(result,loginDTO);
//    }

//    @Test
//    public void findUserTest() throws SQLException {
//        // Arrange
//        String username = "azezsalami";
//        String password = "password";
//        LoginDTO loginDTO = new LoginDTO(username, password);
//        when(mockedDatabaseConnection.getConnection()).thenReturn(connection);
//        PreparedStatement statement = connection.prepareStatement("select * from users where username =?");
//        statement.setString(1, username);
//        ResultSet resultSet = statement.executeQuery();
//        when(mockedLoginDataMapper.toDTO(resultSet)).thenReturn(loginDTO);
//        // Act
//        LoginDTO result = sut.findUser(username);
//        // Assert
//        assertEquals(result,loginDTO);
//    }

}
