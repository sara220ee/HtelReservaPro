package authentication;
import authentication.SERMapAuthenticator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SERMapAuthenticatorTest {

    private SERMapAuthenticator authenticator;

    @BeforeEach
    void setUp() {
        authenticator = new SERMapAuthenticator(); // par défaut, contient sara:sara
        Map<String, String> testAdmins = new HashMap<>();
        testAdmins.put("admin1@gmail.com", "pass123");
        testAdmins.put("admin2@gmail.com", "secret");
        authenticator.setAdmins(testAdmins);
    }

    @Test
    void testSuccessfulAuthentication() {
        assertTrue(authenticator.authenticate("admin1@gmail.com", "pass123"));
    }

    @Test
    void testAuthenticationFailsWithWrongPassword() {
        assertFalse(authenticator.authenticate("admin1@gmail.com", "wrongpass"));
    }

    @Test
    void testAuthenticationFailsWithUnknownUser() {
        assertFalse(authenticator.authenticate("unknown@gmail.com", "nopass"));
    }

    @Test
    void testGetAdminsReturnsCorrectMap() {
        Map<String, String> admins = authenticator.getAdmins();
        assertEquals(3, admins.size());
        assertTrue(admins.containsKey("admin1@gmail.com"));
        assertEquals("pass123", admins.get("admin1@gmail.com"));
    }

    @Test
    void testToStringNotNull() {
        assertNotNull(authenticator.toString());
    }
}
