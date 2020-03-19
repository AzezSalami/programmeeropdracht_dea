package nl.han.oose.dea.controllers.dto;

public class LoginRespondeDTO {
    private String token;
    private String user;

    public LoginRespondeDTO() {
    }

    public LoginRespondeDTO(String token, String user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

}
