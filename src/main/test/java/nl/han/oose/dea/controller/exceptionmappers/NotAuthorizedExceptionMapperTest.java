package nl.han.oose.dea.controller.exceptionmappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.NotAuthorizedException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NotAuthorizedExceptionMapperTest {

    private NotAuthorizedExceptionMapper sut;
    private NotAuthorizedException exception;

    @BeforeEach
    public void setup(){
        sut = new NotAuthorizedExceptionMapper();
    }

    @Test
    void toResponse() {
        //Act
        var result = sut.toResponse(exception);

        //Assert
        assertEquals(401, result.getStatus());
    }
}
