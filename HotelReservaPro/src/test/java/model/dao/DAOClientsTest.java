package model.dao;

import model.entity.Client;
import model.entity.ValeurInvalideException;
import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DAOClientsTest {
    private static final String TEST_FILE = "test_clients.dat";
    private DAOClients dao;

    @BeforeEach
    void setUp() {
        File file = new File(TEST_FILE);
        if (file.exists()) file.delete();
        dao = new DAOClients(TEST_FILE);
    }

    @AfterEach
    void tearDown() {
        File file = new File(TEST_FILE);
        if (file.exists()) file.delete();
    }

    @Test
    void testAddAndGetClient() {
        int id = dao.addClient(new Client(0, "Nom1", "Prenom1", "test1@mail.com"));
        Client c = dao.getClientById(id);
        assertNotNull(c);
        assertEquals("Nom1", c.getNom());
    }

    @Test
    void testUpdateClient() {
        int id = dao.addClient(new Client(0, "Nom2", "Prenom2", "test2@mail.com"));
        Client updated = new Client(id, "Nom2Mod", "Prenom2", "test2@mail.com");
        boolean success = dao.updateClient(updated);
        assertTrue(success);
        Client c = dao.getClientById(id);
        assertEquals("Nom2Mod", c.getNom());
    }

    @Test
    void testDeleteClientById() {
        int id = dao.addClient(new Client(0, "Nom3", "Prenom3", "test3@mail.com"));
        boolean removed = dao.deleteClient(id);
        assertTrue(removed);
        assertNull(dao.getClientById(id));
    }

    @Test
    void testDeleteClientByObject() {
        Client c = new Client(0, "Nom4", "Prenom4", "test4@mail.com");
        dao.addClient(c);
        boolean removed = dao.deleteClient(c);
        assertTrue(removed);
    }

    @Test
    void testGetList() {
        dao.addClient(new Client(0, "Nom5", "Prenom5", "test5@mail.com"));
        dao.addClient(new Client(0, "Nom6", "Prenom6", "test6@mail.com"));
        ArrayList<Client> list = dao.getAllClients();
        assertEquals(2, list.size());
    }

    @Test
    void testSaveAndLoad() {
        int id = dao.addClient(new Client(0, "Nom7", "Prenom7", "test7@mail.com"));
        dao.save();

        // Nouveau DAO → doit charger les données du fichier
        DAOClients loadedDao = new DAOClients(TEST_FILE);
        Client c = loadedDao.getClientById(id);
        assertNotNull(c);
        assertEquals("Nom7", c.getNom());
    }

    @Test
    void testValeurInvalideExceptionOnBadFile() {
        // Création d'un fichier texte invalide
        try (java.io.FileWriter writer = new java.io.FileWriter(TEST_FILE)) {
            writer.write("ceci n'est pas un fichier valide");
        } catch (Exception ignored) {}

        assertThrows(ValeurInvalideException.class, () -> {
            new DAOClients(TEST_FILE); // Le chargement doit échouer
        });
    }
    @Test
    void testImporterDepuisCSV() throws Exception {
        String csvFile = "clients_import_test.csv";

        // Créer un fichier CSV temporaire
        try (java.io.FileWriter writer = new java.io.FileWriter(csvFile)) {
            writer.write("Dupont,Jean,jean.dupont@mail.com\n");
            writer.write("Martin,Claire,claire.martin@mail.com\n");
        }

        dao.importerClientsDepuisCSV(csvFile);
        ArrayList<Client> clients = dao.getAllClients();

        assertEquals(2, clients.size());
        assertEquals("Dupont", clients.get(0).getNom());
        assertEquals("Martin", clients.get(1).getNom());

        // Nettoyage
        new File(csvFile).delete();
    }

    @Test
    void testExporterVersCSV() throws Exception {
        String csvFile = "clients_export_test.csv";

        dao.addClient(new Client(0, "Durand", "Alice", "alice.durand@mail.com"));
        dao.addClient(new Client(0, "Petit", "Lucas", "lucas.petit@mail.com"));

        dao.exporterClientsVersCSV(csvFile);

        File f = new File(csvFile);
        assertTrue(f.exists());

        // Vérifier le contenu
        ArrayList<String> lignes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                lignes.add(ligne);
            }
        }

        assertEquals(2, lignes.size());
        assertTrue(lignes.get(0).contains("Durand"));
        assertTrue(lignes.get(1).contains("Petit"));

        // Nettoyage
        f.delete();
    }

}
