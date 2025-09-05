package authentication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticatorTest {

    private Authenticator authenticator;

    @BeforeEach
    void setUp() {
        authenticator = new Authenticator() {
            @Override
            protected boolean isLoginExists(String username) {
                return "user1".equals(username) || "user2".equals(username);
            }

            @Override
            protected String getPassword(String username) {
                return switch (username) {
                    case "user1" -> "password1";
                    case "user2" -> "password2";
                    default -> null;
                };
            }
        };
    }

    @Test
    void testAuthenticationSuccess() {
        assertTrue(authenticator.authenticate("user1", "password1"));
        assertTrue(authenticator.authenticate("user2", "password2"));
    }

    @Test
    void testAuthenticationWrongPassword() {
        assertFalse(authenticator.authenticate("user1", "wrongpass"));
    }

    @Test
    void testAuthenticationNonExistentUser() {
        assertFalse(authenticator.authenticate("unknown", "password1"));
    }

    @Test
    void testAuthenticationNullPasswordStored() {
        Authenticator customAuthenticator = new Authenticator() {
            @Override
            protected boolean isLoginExists(String username) {
                return "ghost".equals(username);
            }

            @Override
            protected String getPassword(String username) {
                return null;
            }
        };
        assertFalse(customAuthenticator.authenticate("ghost", "any"));
    }
}
