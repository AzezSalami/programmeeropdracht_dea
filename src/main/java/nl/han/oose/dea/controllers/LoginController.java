package nl.han.oose.dea.controllers;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.han.oose.dea.datasource.dao.LoginDAO;
import nl.han.oose.dea.controllers.dto.LoginDTO;
import nl.han.oose.dea.controllers.dto.LoginRespondeDTO;

@Path("/login")
public class LoginController {
    private LoginDAO loginDAO;

    public LoginController() {
    }

    @Inject
    public void setLoginDAO(LoginDAO loginDAO){
        this.loginDAO = loginDAO;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(LoginDTO loginDTO) {
        return Response.ok().entity(loginDAO.validateInfo(loginDTO)).build();
    }
}
