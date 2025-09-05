package model.dao;

import model.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DAOReservationsTest {

    private DAOReservations dao;
    private final String filename = "test_reservations.dat";

    private Client client;
    private Chambre chambre;

    @BeforeEach
    void setUp() {
        // Supprime le fichier de test avant chaque test
        File file = new File(filename);
        if (file.exists()) file.delete();

        dao = new DAOReservations(filename);

        // Données fictives valides
        client = new Client(1, "Jean", "Dupont", "jean.dupont@example.com");
        chambre = new Chambre(1, 101, 100.0, TypeChambre.SINGLE, "chambre.jpg");
    }

    @Test
    void testAddReservation() {
        Reservation res = new Reservation(0, client, chambre, LocalDate.now(), LocalDate.now().plusDays(2));
        int id = dao.addReservation(res);

        assertEquals(1, id);
        assertEquals(1, dao.getList().size());
    }

    @Test
    void testGetReservationById() {
        Reservation res = new Reservation(0, client, chambre, LocalDate.now(), LocalDate.now().plusDays(2));
        int id = dao.addReservation(res);

        Reservation found = dao.getReservationById(id);
        assertNotNull(found);
        assertEquals(client, found.getClient());
    }

    @Test
    void testUpdateReservation() {
        Reservation res = new Reservation(0, client, chambre, LocalDate.now(), LocalDate.now().plusDays(2));
        int id = dao.addReservation(res);

        Reservation toUpdate = dao.getReservationById(id);
        toUpdate.setDateFin(LocalDate.now().plusDays(5));
        boolean updated = dao.updateReservation(toUpdate);

        assertTrue(updated);
        assertEquals(LocalDate.now().plusDays(5), dao.getReservationById(id).getDateFin());
    }

    @Test
    void testDeleteReservationById() {
        Reservation res = new Reservation(0, client, chambre, LocalDate.now(), LocalDate.now().plusDays(2));
        int id = dao.addReservation(res);

        boolean deleted = dao.deleteReservation(id);
        assertTrue(deleted);
        assertNull(dao.getReservationById(id));
    }

    @Test
    void testDeleteReservationByObject() {
        Reservation res = new Reservation(0, client, chambre, LocalDate.now(), LocalDate.now().plusDays(2));
        dao.addReservation(res);

        boolean deleted = dao.deleteReservation(res);
        assertTrue(deleted);
    }

    @Test
    void testGetList() {
        dao.addReservation(new Reservation(0, client, chambre, LocalDate.now(), LocalDate.now().plusDays(1)));
        dao.addReservation(new Reservation(0, client, chambre, LocalDate.now().plusDays(2), LocalDate.now().plusDays(3)));

        List<Reservation> list = dao.getList();
        assertEquals(2, list.size());
    }

    @Test
    void testSaveAndReload() {
        dao.addReservation(new Reservation(0, client, chambre, LocalDate.now(), LocalDate.now().plusDays(1)));
        dao.save();

        DAOReservations newDao = new DAOReservations(filename);
        assertEquals(1, newDao.getList().size());
    }

    @Test
    void testInvalidAddReservation() {
        assertThrows(ValeurInvalideException.class, () -> dao.addReservation(null));
    }

    @Test
    void testDeleteNonExisting() {
        assertFalse(dao.deleteReservation(999));
    }

    // 🔥 Fichier corrompu (test isolé sans BeforeEach)
    @Test
    void testLoadCorruptedFile() {
        String corruptedFile = "corrupted_reservations.dat";

        try {
            // Écrit un texte brut dans un fichier (non lisible en ObjectInputStream)
            try (FileWriter fw = new FileWriter(corruptedFile)) {
                fw.write("ceci est un fichier corrompu");
            }

            // Attendre l’exception lors de la création de DAOReservations
            assertThrows(ValeurInvalideException.class, () -> {
                new DAOReservations(corruptedFile);
            });

        } catch (IOException e) {
            fail("Erreur lors de la création du fichier corrompu : " + e.getMessage());
        } finally {
            // Nettoyage
            File f = new File(corruptedFile);
            if (f.exists()) f.delete();
        }
    }
}
