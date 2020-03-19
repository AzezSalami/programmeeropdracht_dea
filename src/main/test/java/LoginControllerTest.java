import nl.han.oose.dea.controllers.LoginController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoginControllerTest {
    private LoginController loginController;

    @BeforeEach
    public void setup() {
        loginController = new LoginController();
    }

    @Test
    public void inlogWithIncorrectAccount() {
        System.out.println("test");
    }

}
