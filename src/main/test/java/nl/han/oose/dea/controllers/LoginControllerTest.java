package nl.han.oose.dea.controllers;

import nl.han.oose.dea.controllers.dto.LoginDTO;
import nl.han.oose.dea.controllers.dto.LoginRespondeDTOTest;
import nl.han.oose.dea.datasource.dao.LoginDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LoginControllerTest {
    private LoginController sut;
    private LoginDAO mockedLoginDAO;
    private static final int HTTP_OK = 200;

    @BeforeEach
    public void setup() {
        sut = new LoginController();
        this.mockedLoginDAO = mock(LoginDAO.class);
        this.sut.setLoginDAO(mockedLoginDAO);
    }

    @Test
    public void inlogWithCorrectAccount() {
        // Arrange
        var  user = "azezsalami";
        var  password = "password";
        var token = "1234567";

        var loginDTO = new LoginDTO(user, password);
        var loginRespondeDTO = new LoginRespondeDTOTest(token, user);
        when(mockedLoginDAO.findUser(user)).thenReturn(loginDTO);
        when(mockedLoginDAO.findData(loginDTO.getUser())).thenReturn(loginRespondeDTO);
        // Act
        var response = sut.login(loginDTO);
        // Assert
        assertEquals(loginRespondeDTO, response.getEntity());
        assertEquals(HTTP_OK, response.getStatus());
    }

    @Test
    public void inlogWithIncorrectAccount() {
        // Arrange
        var user = "azezsalami";
        var pass = "pass";
        var loginDTO = new LoginDTO(user, pass);
        when(mockedLoginDAO.findUser(user)).thenReturn(loginDTO);
        // Act
        var response = sut.login(loginDTO);
        // Assert
        assertEquals(401, response.getStatus());

    }

}
