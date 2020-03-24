package controllers;

import nl.han.oose.dea.controllers.LoginController;
import nl.han.oose.dea.controllers.dto.LoginDTO;
import nl.han.oose.dea.controllers.dto.LoginRespondeDTO;
import nl.han.oose.dea.datasource.dao.LoginDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

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
        LoginDTO loginDTO = new LoginDTO("azezsalami", "password");
        LoginRespondeDTO loginRespondeDTO = new LoginRespondeDTO("1234567", "azezsalami");
        when(mockedLoginDAO.findUser("azezsalami")).thenReturn(loginDTO);
        when(mockedLoginDAO.findData(loginDTO.getUser())).thenReturn(loginRespondeDTO);
        // Act
        Response response = sut.login(loginDTO);
        System.out.println(response.getStatus());
        System.out.println(loginRespondeDTO.getUser());
        // Assert
        assertEquals(loginRespondeDTO, response.getEntity());
        assertEquals(HTTP_OK, response.getStatus());
    }

    @Test
    public void inlogWithIncorrectAccount() {
        // Arrange
        LoginDTO loginDTO = new LoginDTO("azezsalami", "pass");
        LoginRespondeDTO loginRespondeDTO = new LoginRespondeDTO("1234567", "azezsalami");
        when(mockedLoginDAO.findUser("azezsalami")).thenReturn(loginDTO);
        when(mockedLoginDAO.findData(loginDTO.getUser())).thenReturn(loginRespondeDTO);
        // Act
        // Assert
        Response response = sut.login(loginDTO);
        assertEquals(401, response.getStatus());

    }

}
