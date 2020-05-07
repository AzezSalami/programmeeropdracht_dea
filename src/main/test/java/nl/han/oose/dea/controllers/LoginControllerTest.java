package nl.han.oose.dea.controllers;

import nl.han.oose.dea.controllers.dto.LoginDTO;
import nl.han.oose.dea.controllers.dto.LoginRespondeDTO;
import nl.han.oose.dea.datasource.dao.LoginDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.InternalServerErrorException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LoginControllerTest {
    private LoginController sut;
    private LoginDAO mockedLoginDAO;
    private static final int HTTP_OK = 200;
    private static final int HTTP_Unauthorized = 401;

    @BeforeEach
    public void setup() {
        sut = new LoginController();
        this.mockedLoginDAO = mock(LoginDAO.class);
        this.sut.setLoginDAO(mockedLoginDAO);
    }

    @Test
    public void inlogWithCorrectAccountTestStatus() {
        // Arrange
        var  user = "azezsalami";
        var  password = "password";
        var token = "1234567";

        var loginDTO = new LoginDTO(user, password);
        var loginRespondeDTO = new LoginRespondeDTO(token, user);
        when(mockedLoginDAO.validateInfo(loginDTO)).thenReturn(loginRespondeDTO);
        // Act
        var response = sut.login(loginDTO);
        // Assert
        assertEquals(HTTP_OK, response.getStatus());
    }

    @Test
    public void inlogWithCorrectAccountTestEntity() {
        // Arrange
        var  user = "azezsalami";
        var  password = "password";
        var token = "1234567";

        var loginDTO = new LoginDTO(user, password);
        var loginRespondeDTO = new LoginRespondeDTO(token, user);
        when(mockedLoginDAO.validateInfo(loginDTO)).thenReturn(loginRespondeDTO);
        // Act
        var response = sut.login(loginDTO);
        // Assert
        assertEquals(loginRespondeDTO, response.getEntity());
    }

    @Test
    public void inlogWithIncorrectAccount() {
        // Arrange
        var user = "azezsalami";
        var pass = "pass";
        var loginDTO = new LoginDTO(user, pass);
        doThrow(InternalServerErrorException.class).when(mockedLoginDAO).validateInfo(loginDTO);

        // Assert
        Assertions.assertThrows(InternalServerErrorException.class, () -> sut.login(loginDTO));
    }

}
