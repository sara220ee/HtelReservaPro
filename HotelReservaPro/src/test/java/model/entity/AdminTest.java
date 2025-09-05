package model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdminTest {

    private Admin admin;

    @BeforeEach
    void setUp() {
        admin = new Admin(1, "AdminNom", "AdminPrenom", "securePass", "admin@mail.com");
    }

    @Test
    void testConstructeurAvecParametres() {
        assertEquals(1, admin.getId());
        assertEquals("AdminNom", admin.getNom());
        assertEquals("AdminPrenom", admin.getPrenom());
        assertEquals("admin@mail.com", admin.getEmail());

        String expectedHash = admin.hacherMotDePasse("securePass");
        assertEquals(expectedHash, admin.getMotDePasseHache());
    }

    @Test
    void testConstructeurSansParametres() {
        Admin defaultAdmin = new Admin();
        assertEquals("sara", defaultAdmin.getNom());
        assertEquals("Rahmouni", defaultAdmin.getPrenom());
        assertEquals("sara@gmail.com", defaultAdmin.getEmail());

        String expectedHash = defaultAdmin.hacherMotDePasse("123456");
        assertEquals(expectedHash, defaultAdmin.getMotDePasseHache());
    }

    @Test
    void testSetMotDePasseValide() {
        admin.setMotDePasse("abcdef");
        String expectedHash = admin.hacherMotDePasse("abcdef");
        assertEquals(expectedHash, admin.getMotDePasseHache());
    }

    @Test
    void testSetMotDePasseInvalide() {
        assertThrows(ValeurInvalideException.class, () -> admin.setMotDePasse(null));
        assertThrows(ValeurInvalideException.class, () -> admin.setMotDePasse("123"));
    }

    @Test
    void testHacherMotDePasseIdentique() {
        String hash1 = admin.hacherMotDePasse("mypassword");
        String hash2 = admin.hacherMotDePasse("mypassword");
        assertEquals(hash1, hash2);
    }

    @Test
    void testHacherMotDePasseDifferent() {
        String hash1 = admin.hacherMotDePasse("password1");
        String hash2 = admin.hacherMotDePasse("password2");
        assertNotEquals(hash1, hash2);
    }
}
