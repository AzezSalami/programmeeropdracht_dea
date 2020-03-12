package nl.han.oose.dea;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/login")
public class LoginController {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(LoginDTO loginDTO) {
        if (loginDTO.getUser().equals("user") & (loginDTO.getPassword().equals("password"))){
            LoginRespondeDTO loginRespondeDTO = new LoginRespondeDTO();
            loginRespondeDTO.setToken("123452345");
            loginRespondeDTO.setUser("user achternaam");
            return Response.ok(loginRespondeDTO).build();
        }else{
            return Response.status(401).build();
        }
    }

}
