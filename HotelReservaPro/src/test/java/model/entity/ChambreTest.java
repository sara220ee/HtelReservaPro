package model.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ChambreTest {

    @Test
    void testConstructeurAvecParametres() {
        Chambre c = new Chambre(1, 101, 150.0, TypeChambre.SUITE,"chambre.gpe");

        assertEquals(1, c.getId());
        assertEquals(101, c.getNumero());
        assertEquals(150.0, c.getPrix());
        assertEquals(TypeChambre.SUITE, c.getType());
    }

    @Test
    void testSetNumeroValide() {
        Chambre c = new Chambre();
        c.setNumero(202);
        assertEquals(202, c.getNumero());
    }

    @Test
    void testSetNumeroInvalide() {
        Chambre c = new Chambre();
        assertThrows(ValeurInvalideException.class, () -> c.setNumero(-1));
        assertThrows(ValeurInvalideException.class, () -> c.setNumero(-5));
    }

    @Test
    void testSetPrixValide() {
        Chambre c = new Chambre();
        c.setPrix(99.99);
        assertEquals(99.99, c.getPrix());
    }

    @Test
    void testSetPrixInvalide() {
        Chambre c = new Chambre();
        assertThrows(ValeurInvalideException.class, () -> c.setPrix(0));
        assertThrows(ValeurInvalideException.class, () -> c.setPrix(-25));
    }

    @Test
    void testSetTypeValide() {
        Chambre c = new Chambre();
        c.setType(TypeChambre.DOUBLE);
        assertEquals(TypeChambre.DOUBLE, c.getType());
    }

    @Test
    void testSetTypeInvalide() {
        Chambre c = new Chambre();
        assertThrows(ValeurInvalideException.class, () -> c.setType(null));
    }

    @Test
    void testEqualsMemeValeurs() {
        Chambre c1 = new Chambre(1, 101, 150.0, TypeChambre.SINGLE, "chambre.gpe");
        Chambre c2 = new Chambre(1, 101, 150.0, TypeChambre.SINGLE, "chambre.gpe");
        assertEquals(c1, c2);
    }

    @Test
    void testEqualsValeursDifferentes() {
        Chambre c1 = new Chambre(1, 101, 150.0, TypeChambre.SINGLE, "chambre.gpe");
        Chambre c2 = new Chambre(2, 102, 200.0, TypeChambre.SUITE,"chambre.gpe");
        assertNotEquals(c1, c2);
    }

    @Test
    void testEqualsAvecNull() {
        Chambre c1 = new Chambre(1, 101, 150.0, TypeChambre.SINGLE , "chambre.gpe");
        assertNotEquals(c1, null);
    }

    @Test
    void testEqualsAvecAutreClasse() {
        Chambre c1 = new Chambre(1, 101, 150.0, TypeChambre.SINGLE , "chambre.gpe");
        String autreObjet = "pas une chambre";
        assertNotEquals(c1, autreObjet);
    }
}
