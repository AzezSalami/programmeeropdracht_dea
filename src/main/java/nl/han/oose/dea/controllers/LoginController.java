package nl.han.oose.dea.controllers;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.han.oose.dea.datasource.dao.LoginDAO;
import nl.han.oose.dea.controllers.dto.LoginDTO;
import nl.han.oose.dea.controllers.dto.LoginRespondeDTO;
import nl.han.oose.dea.datasource.DatabaseProperties;

@Path("/")
public class LoginController {
    LoginDAO loginDAO;


    @GET
    public String hallo() {
        return "hallooooo";
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("login")
    public Response login(LoginDTO loginDTO) {
        DatabaseProperties databaseProperties = new DatabaseProperties();
        LoginDAO loginDAO = new LoginDAO(databaseProperties);

        if (loginDTO.getPassword().equals(loginDAO.findUser(loginDTO.getUser()).getPassword())) {
            LoginRespondeDTO loginRespondeDTO = new LoginRespondeDTO();
            loginRespondeDTO.setToken(loginDAO.findData(loginDTO.getUser()).getToken());
            loginRespondeDTO.setUser(loginDAO.findData(loginDTO.getUser()).getUser());
            return Response.ok(loginRespondeDTO).build();
        } else {
            return Response.status(401).build();
        }
    }


}
