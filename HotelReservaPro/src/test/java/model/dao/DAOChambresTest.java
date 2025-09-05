package model.dao;

import model.entity.Chambre;
import model.entity.ValeurInvalideException;
import model.entity.TypeChambre;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DAOChambresTest {

    private DAOChambres dao;
    private String testFilename;
    private String testCSVFilename;

    @BeforeEach
    void setUp() throws IOException {
        testFilename = "testChambres.dat";
        testCSVFilename = "testChambres.csv";
        cleanUpFiles();
        dao = new DAOChambres(testFilename);
    }

    @AfterEach
    void tearDown() {
        cleanUpFiles();
    }

    private void cleanUpFiles() {
        File[] filesToDelete = {
                new File(testFilename),
                new File(testCSVFilename),
                new File("autre_fichier.dat"),
                new File("vide.dat"),
                new File("chambres_mauvaises.csv")
        };

        for (File file : filesToDelete) {
            if (file.exists()) {
                file.delete();
            }
        }
    }

    @Test
    @DisplayName("Ajout d'une chambre valide")
    void testAddChambre() {
        Chambre chambre = new Chambre(0, 101, 89.99, TypeChambre.SINGLE, "single.jpg");

        int id = dao.addChambre(chambre);

        assertEquals(1, id, "L'ID de la chambre ajoutée doit être 1");
        Chambre retrieved = dao.getChambreById(id);
        assertNotNull(retrieved, "La chambre doit être retrouvable après ajout");
        assertEquals(101, retrieved.getNumero());
        assertEquals(89.99, retrieved.getPrix());
        assertEquals(TypeChambre.SINGLE, retrieved.getType());
        assertEquals("single.jpg", retrieved.getImage());
    }

    @Test
    @DisplayName("Ajout d'une chambre null doit lancer une exception")
    void testAddChambreNull() {
        ValeurInvalideException exception = assertThrows(
                ValeurInvalideException.class,
                () -> dao.addChambre(null),
                "Devrait lancer une exception pour chambre null"
        );
        assertTrue(exception.getMessage().contains("Chambre invalide (null)"));
    }

    @Test
    @DisplayName("Récupération d'une chambre par ID")
    void testGetChambreById() {
        Chambre chambre = new Chambre(0, 101, 89.99, TypeChambre.SINGLE, "single.jpg");
        int id = dao.addChambre(chambre);

        Chambre retrieved = dao.getChambreById(id);

        assertNotNull(retrieved, "La chambre doit être retrouvée par son ID");
        assertEquals(chambre.getNumero(), retrieved.getNumero());
        assertEquals(chambre.getPrix(), retrieved.getPrix());
        assertEquals(chambre.getType(), retrieved.getType());
        assertEquals(chambre.getImage(), retrieved.getImage());
    }

    @Test
    @DisplayName("Récupération d'une chambre avec un ID inexistant")
    void testGetChambreByIdInexistant() {
        assertNull(dao.getChambreById(999), "Devrait retourner null pour un ID inexistant");
    }

    @Test
    @DisplayName("Mise à jour d'une chambre existante")
    void testUpdateChambre() {
        Chambre chambre = new Chambre(0, 101, 89.99, TypeChambre.SINGLE, "single.jpg");
        int id = dao.addChambre(chambre);

        Chambre updated = new Chambre(id, 101, 99.99, TypeChambre.SINGLE, "updated.jpg");
        boolean result = dao.updateChambre(updated);

        assertTrue(result, "La mise à jour devrait réussir");
        Chambre retrieved = dao.getChambreById(id);
        assertEquals(99.99, retrieved.getPrix());
        assertEquals("updated.jpg", retrieved.getImage());
    }

    @Test
    @DisplayName("Mise à jour d'une chambre inexistante")
    void testUpdateChambreInexistante() {
        Chambre chambre = new Chambre(999, 101, 89.99, TypeChambre.SINGLE, "single.jpg");
        boolean result = dao.updateChambre(chambre);
        assertFalse(result, "La mise à jour devrait échouer pour une chambre inexistante");
    }

    @Test
    @DisplayName("Suppression d'une chambre par ID")
    void testDeleteChambreById() {
        Chambre chambre = new Chambre(0, 101, 89.99, TypeChambre.SINGLE, "single.jpg");
        int id = dao.addChambre(chambre);

        boolean result = dao.deleteChambre(id);
        assertTrue(result, "La suppression devrait réussir");
        assertNull(dao.getChambreById(id), "La chambre ne devrait plus exister");
    }

    @Test
    @DisplayName("Suppression d'une chambre par objet")
    void testDeleteChambreByObject() {
        Chambre chambre = new Chambre(0, 101, 89.99, TypeChambre.SINGLE, "single.jpg");
        int id = dao.addChambre(chambre);

        boolean result = dao.deleteChambre(chambre);
        assertTrue(result, "La suppression devrait réussir");
        assertNull(dao.getChambreById(id), "La chambre ne devrait plus exister");
    }

    @Test
    @DisplayName("Suppression d'une chambre inexistante")
    void testDeleteChambreInexistante() {
        assertFalse(dao.deleteChambre(999), "La suppression devrait échouer pour une chambre inexistante");
    }

    @Test
    @DisplayName("Récupération de la liste des chambres")
    void testGetList() {
        Chambre chambre1 = new Chambre(0, 101, 89.99, TypeChambre.SINGLE, "single.jpg");
        Chambre chambre2 = new Chambre(0, 102, 120.00, TypeChambre.DOUBLE, "double.jpg");

        dao.addChambre(chambre1);
        dao.addChambre(chambre2);

        List<Chambre> chambres = dao.getList();
        assertEquals(2, chambres.size(), "La liste devrait contenir 2 chambres");
        assertTrue(chambres.stream().anyMatch(c -> c.getNumero() == 101));
        assertTrue(chambres.stream().anyMatch(c -> c.getNumero() == 102));
    }

    @Test
    @DisplayName("Sauvegarde et chargement des chambres")
    void testSaveAndLoad() {
        Chambre chambre1 = new Chambre(0, 101, 89.99, TypeChambre.SINGLE, "single.jpg");
        Chambre chambre2 = new Chambre(0, 102, 120.00, TypeChambre.DOUBLE, "double.jpg");

        dao.addChambre(chambre1);
        dao.addChambre(chambre2);
        dao.save();

        DAOChambres newDao = new DAOChambres(testFilename);
        List<Chambre> chambres = newDao.getList();

        assertEquals(2, chambres.size(), "Devrait charger 2 chambres");
        assertTrue(chambres.stream().anyMatch(c -> c.getNumero() == 101));
        assertTrue(chambres.stream().anyMatch(c -> c.getNumero() == 102));
    }

    @Test
    @DisplayName("Chargement d'un fichier corrompu")
    void testLoadCorruptFile() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(testFilename))) {
            oos.writeObject("Données corrompues");
        }

        ValeurInvalideException exception = assertThrows(
                ValeurInvalideException.class,
                () -> new DAOChambres(testFilename),
                "Devrait lancer une exception pour fichier corrompu"
        );
        assertTrue(exception.getMessage().contains("Fichier corrompu"));
    }

    @Test
    @DisplayName("Import/Export CSV")
    void testExporterEtImporterCSV() throws ValeurInvalideException {
        // Ajout de chambres
        dao.addChambre(new Chambre(0, 101, 89.99, TypeChambre.SINGLE, "single.jpg"));
        dao.addChambre(new Chambre(0, 102, 120.00, TypeChambre.DOUBLE, "double.jpg"));

        // Export
        dao.exporterChambresVersCSV(testCSVFilename);

        // Vérifie que le fichier a été créé
        assertTrue(new File(testCSVFilename).exists());

        // Import dans un nouveau DAO
        DAOChambres newDao = new DAOChambres("autre_fichier.dat");
        newDao.importerChambresDepuisCSV(testCSVFilename);

        // Vérifications
        List<Chambre> chambres = newDao.getList();
        assertEquals(2, chambres.size(), "Devrait avoir importé 2 chambres");

        // Vérifie le contenu des chambres importées
        assertTrue(chambres.stream().anyMatch(c -> c.getNumero() == 101));
        assertTrue(chambres.stream().anyMatch(c -> c.getNumero() == 102));
    }

    @Test
    @DisplayName("Import CSV avec lignes invalides")
    void testImporterCSVInvalide() throws IOException, ValeurInvalideException {
        // Création d'un CSV avec des lignes invalides
        try (PrintWriter writer = new PrintWriter(new FileWriter(testCSVFilename))) {
            //writer.println("abc;invalid;SINGLE;image.jpg");  // Ligne invalide
            writer.println("103;150.00;SUITE;suite.jpg"); // Ligne valide
        }

        DAOChambres newDao = new DAOChambres("vide.dat");
        newDao.importerChambresDepuisCSV(testCSVFilename);

        List<Chambre> chambres = newDao.getList();
        assertEquals(1, chambres.size(), "Seule la ligne valide devrait être importée");

        Chambre chambre = chambres.get(0);
        assertEquals(103, chambre.getNumero());
        assertEquals(150.00, chambre.getPrix());
        assertEquals(TypeChambre.SUITE, chambre.getType());
        assertEquals("suite.jpg", chambre.getImage());
    }
}