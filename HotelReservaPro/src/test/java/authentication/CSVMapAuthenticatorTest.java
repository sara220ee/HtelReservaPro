package authentication;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CSVMapAuthenticatorTest {

    @Test
    void testDefaultConstructorAuthenticationSuccess() {
        CSVMapAuthenticator auth = new CSVMapAuthenticator();
        assertTrue(auth.authenticate("sara", "sara"));
    }

    @Test
    void testDefaultConstructorAuthenticationWrongPassword() {
        CSVMapAuthenticator auth = new CSVMapAuthenticator();
        assertFalse(auth.authenticate("sara", "wrong"));
    }

    @Test
    void testDefaultConstructorAuthenticationUnknownUser() {
        CSVMapAuthenticator auth = new CSVMapAuthenticator();
        assertFalse(auth.authenticate("unknown", "sara"));
    }

    @Test
    void testSetAndGetAdmins() {
        CSVMapAuthenticator auth = new CSVMapAuthenticator();
        Map<String, String> newAdmins = new HashMap<>();
        newAdmins.put("alice", "secret");
        auth.setAdmins(newAdmins);

        assertEquals("secret", auth.getAdmins().get("alice"));
        assertTrue(auth.authenticate("alice", "secret"));
    }

    @Test
    void testLoadAdminsFromCSV(@TempDir File tempDir) throws IOException {
        // Créer un fichier temporaire
        File csvFile = new File(tempDir, "admins.csv");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
            writer.write("id,nom,prenom,login,password\n");
            writer.write("1,Doe,John,john,pass123\n");
            writer.write("2,Smith,Anna,anna,secret456\n");
        }

        CSVMapAuthenticator auth = new CSVMapAuthenticator(csvFile.getAbsolutePath());

        assertTrue(auth.authenticate("john", "pass123"));
        assertTrue(auth.authenticate("anna", "secret456"));
        assertFalse(auth.authenticate("john", "wrong"));
        assertFalse(auth.authenticate("unknown", "any"));
    }
}
