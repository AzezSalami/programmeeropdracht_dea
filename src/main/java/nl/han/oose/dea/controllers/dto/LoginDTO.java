package nl.han.oose.dea.controllers.dto;

public class LoginDTO {
    private String user;
    private String password;

    public LoginDTO() {
    }

    public LoginDTO(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
