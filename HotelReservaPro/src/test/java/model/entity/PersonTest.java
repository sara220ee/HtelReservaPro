package model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PersonTest {
    private Person person;

    @BeforeEach
    void setUp() {
        person = new Person(1, "Doe", "John", "john.doe@example.com");
    }

    @Test
    void testConstructeurSansParametre() {
        Person defaultPerson = new Person();
        assertEquals(0, defaultPerson.getId());
        assertEquals("sara", defaultPerson.getNom());
        assertEquals("Rahmouni", defaultPerson.getPrenom());
        assertEquals("sara@gmail.com", defaultPerson.getEmail());
    }

    @Test
    void testConstructeurAvecParametres() {
        assertEquals(1, person.getId());
        assertEquals("Doe", person.getNom());
        assertEquals("John", person.getPrenom());
        assertEquals("john.doe@example.com", person.getEmail());
    }

    @Test
    void testSetEmailValide() {
        person.setEmail("new.email@example.com");
        assertEquals("new.email@example.com", person.getEmail());
    }

    @Test
    void testSetEmailInvalide() {
        assertThrows(ValeurInvalideException.class, () -> person.setEmail("invalid-email"));
        assertThrows(ValeurInvalideException.class, () -> person.setEmail("test@.com"));
        assertThrows(ValeurInvalideException.class, () -> person.setEmail(null));
    }

    @Test
    void testSetIdValide() {
        person.setId(5);
        assertEquals(5, person.getId());
    }

    @Test
    void testSetIdInvalide() {
        assertThrows(ValeurInvalideException.class, () -> person.setId(-1));
    }

    @Test
    void testSetNomValide() {
        person.setNom("Smith");
        assertEquals("Smith", person.getNom());
    }

    @Test
    void testSetNomInvalide() {
        assertThrows(ValeurInvalideException.class, () -> person.setNom(""));
        assertThrows(ValeurInvalideException.class, () -> person.setNom(null));
    }

    @Test
    void testSetPrenomValide() {
        person.setPrenom("Alice");
        assertEquals("Alice", person.getPrenom());
    }

    @Test
    void testSetPrenomInvalide() {
        assertThrows(ValeurInvalideException.class, () -> person.setPrenom(""));
        assertThrows(ValeurInvalideException.class, () -> person.setPrenom(null));
    }

    @Test
    void testEquals() {
        Person samePerson = new Person(1, "Doe", "John", "john.doe@example.com");
        Person differentPerson = new Person(2, "Smith", "Alice", "alice.smith@example.com");

        assertEquals(person, samePerson);
        assertNotEquals(person, differentPerson);
    }

    @Test
    void testToString() {
        assertEquals("Doe John (ID: 1)", person.toString());
    }
}
