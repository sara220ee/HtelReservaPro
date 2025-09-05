package model.entity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {

    private Client client;
    private Chambre chambre;
    private LocalDate debut;
    private LocalDate fin;

    @BeforeEach
    void setUp() {
        client = new Client(1, "Dupont", "Jean" ,"sara.rahmouni@gmail.com");
        chambre = new Chambre(101, 1,80.0, TypeChambre.DOUBLE,"chambre.jpe");
        debut = LocalDate.of(2025, 4, 5);
        fin = LocalDate.of(2025, 4, 10);
    }

    @Test
    void testCreationReservationValide() {
        Reservation reservation = new Reservation(1, client, chambre, debut, fin);
        assertEquals(1, reservation.getId());
        assertEquals(client, reservation.getClient());
        assertEquals(chambre, reservation.getChambre());
        assertEquals(debut, reservation.getDateDebut());
        assertEquals(fin, reservation.getDateFin());
    }

    @Test
    void testSetClientNullDevraitLancerException() {
        Reservation r = new Reservation();
        assertThrows(ValeurInvalideException.class, () -> r.setClient(null));
    }

    @Test
    void testSetChambreNullDevraitLancerException() {
        Reservation r = new Reservation();
        assertThrows(ValeurInvalideException.class, () -> r.setChambre(null));
    }

    @Test
    void testSetDateDebutApresDateFin() {
        Reservation r = new Reservation();
        r.setDateFin(LocalDate.of(2025, 4, 5));
        assertThrows(ValeurInvalideException.class, () -> r.setDateDebut(LocalDate.of(2025, 4, 6)));
    }

    @Test
    void testSetDateFinAvantDateDebut() {
        Reservation r = new Reservation();
        r.setDateDebut(LocalDate.of(2025, 4, 10));
        assertThrows(ValeurInvalideException.class, () -> r.setDateFin(LocalDate.of(2025, 4, 5)));
    }

    @Test
    void testSetDateDebutEtFinValides() {
        Reservation r = new Reservation();
        r.setDateFin(fin); // d'abord la fin
        r.setDateDebut(debut); // puis le début, cohérent
        assertEquals(debut, r.getDateDebut());
        assertEquals(fin, r.getDateFin());
    }

    @Test
    void testConstructeurSansParametres() {
        Reservation r = new Reservation();
        assertEquals(0, r.getId());
        assertNull(r.getClient());
        assertNull(r.getChambre());
        assertNull(r.getDateDebut());
        assertNull(r.getDateFin());
    }
}
