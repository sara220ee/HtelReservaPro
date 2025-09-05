package authentication;

import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class PropertiesAuthenticatorTest {

    private PropertiesAuthenticator defaultAuth;

    @BeforeEach
    void setUp() {
        defaultAuth = new PropertiesAuthenticator();
    }

    @Test
    void testDefaultConstructor_containsSara() {
        assertTrue(defaultAuth.isLoginExists("sara.rahmouni@gmail.com"));
        assertEquals("sara123", defaultAuth.getPassword("sara.rahmouni@gmail.com"));
    }

    @Test
    void testDefaultConstructor_userNotExists() {
        assertFalse(defaultAuth.isLoginExists("non.existe@exemple.com"));
        assertNull(defaultAuth.getPassword("non.existe@exemple.com"));
    }

    @Test
    void testConstructorWithFile_validFile() throws IOException {
        // Créer un fichier temporaire avec des utilisateurs
        Path tempFile = Files.createTempFile("test-users", ".properties");
        try (BufferedWriter writer = Files.newBufferedWriter(tempFile)) {
            writer.write("user1@mail.com=pass1\n");
            writer.write("user2@mail.com=pass2\n");
        }

        PropertiesAuthenticator fileAuth = new PropertiesAuthenticator(tempFile.toString());

        assertTrue(fileAuth.isLoginExists("user1@mail.com"));
        assertEquals("pass1", fileAuth.getPassword("user1@mail.com"));

        assertTrue(fileAuth.isLoginExists("user2@mail.com"));
        assertEquals("pass2", fileAuth.getPassword("user2@mail.com"));

        Files.deleteIfExists(tempFile);
    }

    @Test
    void testConstructorWithFile_fileNotFound() {
        // On teste si le constructeur ne jette pas d'exception
        assertDoesNotThrow(() -> new PropertiesAuthenticator("fichier/inexistant.properties"));
    }

    @Test
    void testToString_notNull() {
        assertNotNull(defaultAuth.toString());
        assertTrue(defaultAuth.toString().contains("sara.rahmouni@gmail.com"));
    }
}
