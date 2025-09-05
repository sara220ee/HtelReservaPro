package model.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    @Test
    void testConstructeurAvecParametres() {
        Client client = new Client(2, "Doe", "Jane", "jane.doe@example.com");

        assertEquals(2, client.getId());
        assertEquals("Doe", client.getNom());
        assertEquals("Jane", client.getPrenom());
        assertEquals("jane.doe@example.com", client.getEmail());
    }

    @Test
    void testConstructeurSansParametre() {
        Client client = new Client();

        assertEquals(0, client.getId()); // car tu fais setId(0) dans super() => à corriger si tu veux éviter l'exception
        assertEquals("sara", client.getNom());
        assertEquals("Rahmouni", client.getPrenom());
        assertEquals("sara@gmail.com", client.getEmail());
    }

    @Test
    void testSettersEtGetters() {
        Client client = new Client(3, "Test", "Client", "test@client.com");

        client.setNom("NouveauNom");
        client.setPrenom("NouveauPrenom");
        client.setEmail("new@mail.com");

        assertEquals("NouveauNom", client.getNom());
        assertEquals("NouveauPrenom", client.getPrenom());
        assertEquals("new@mail.com", client.getEmail());
    }

    @Test
    void testEmailInvalide() {
        Client client = new Client(4, "Nom", "Prenom", "mail@valide.com");

        assertThrows(ValeurInvalideException.class, () -> client.setEmail("invalid-email"));
    }

    @Test
    void testToString() {
        Client client = new Client(5, "Nom", "Prenom", "mail@valide.com");
        String expected = "Nom Prenom (ID: 5)";
        assertEquals(expected, client.toString());
    }
}
