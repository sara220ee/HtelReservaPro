package model.dao;
import model.DataAccesLayerClient;
import model.entity.Client;
import model.entity.ValeurInvalideException;
import java.util.ArrayList;
import java.io.*;

public class DAOClients implements DataAccesLayerClient {
    private ArrayList<Client> clients;
    private static int idCourant;
    private final String filename;

    public DAOClients(String filename) {
        this.filename = filename;
        clients = new ArrayList<>();
        idCourant = 1;
        try {
            load();
        } catch (Exception e) {
            throw new ValeurInvalideException("Erreur lors du chargement des clients : " + e.getMessage());
        }
    }
    public DAOClients() {
        filename = "C:\\Users\\sarar\\IdeaProjects\\HotelReservaPro\\src\\main\\resources\\Clients\\Clients.dat";
        clients = new ArrayList<>();
        idCourant = 1;
        try {
            load();
        } catch (Exception e) {
            throw new ValeurInvalideException("Erreur lors du chargement des clients : " + e.getMessage());
        }
    }

    @Override
    public int addClient(Client client) {
        if (client == null) throw new ValeurInvalideException("Client invalide (null)");
        client.setId(idCourant++);
        clients.add(client);
        save();
        return client.getId();
    }

    @Override
    public boolean updateClient(Client client) {
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getId() == client.getId()) {
                clients.set(i, client);
                save();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteClient(int id) {
        boolean removed = clients.removeIf(c -> c.getId() == id);
        if (removed) save();
        return removed;
    }

    @Override
    public boolean deleteClient(Client client) {
        boolean removed = clients.remove(client);
        if (removed) save();
        return removed;
    }

    @Override
    public Client getClientById(int id) {
        for (Client client : clients) {
            if (client.getId() == id) {
                return client;
            }
        }
        return null;
    }

    @Override
    public ArrayList<Client> getAllClients() {
        return clients;
    }

    @Override
    public void save() {
        try {
            File file = new File(filename);
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }

            if (!file.exists()) {
                file.createNewFile();
            }

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(clients);
            }

        } catch (IOException e) {
            throw new ValeurInvalideException("Erreur lors de l'enregistrement des clients : " + e.getMessage());
        }
    }

    @Override
    public void load() {
        File file = new File(filename);
        if (!file.exists()) {
            clients = new ArrayList<>();
            idCourant = 1;
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();

            if (obj instanceof ArrayList<?> list) {
                clients = new ArrayList<>();
                for (Object item : list) {
                    if (!(item instanceof Client)) {
                        throw new ValeurInvalideException("Fichier corrompu : élément non-Client détecté.");
                    }
                    clients.add((Client) item);
                }
                int maxId = 0;
                for (Client c : clients) {
                    if (c.getId() > maxId) {
                        maxId = c.getId();
                    }
                }
                idCourant = maxId + 1;
            } else {
                throw new ValeurInvalideException("Fichier corrompu : contenu non reconnu.");
            }

        } catch (IOException | ClassNotFoundException e) {
            throw new ValeurInvalideException("Erreur lors du chargement : " + e.getMessage());
        }
    }
    @Override
    public void importerClientsDepuisCSV(String cheminFichier) {
        File fichier = new File(cheminFichier);
        if (!fichier.exists()) {
            throw new ValeurInvalideException("Fichier CSV introuvable : " + cheminFichier);
        }

        try (BufferedReader br = new BufferedReader(new FileReader(fichier))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] parties = ligne.split(",");
                if (parties.length != 3) {
                    System.out.println("Ligne ignorée (format invalide) : " + ligne);
                    continue;
                }

                String nom = parties[0].trim();
                String prenom = parties[1].trim();
                String email = parties[2].trim();

                Client client = new Client(0, nom, prenom, email);
                addClient(client);
            }
        } catch (IOException e) {
            throw new ValeurInvalideException("Erreur lors de la lecture du fichier CSV : " + e.getMessage());
        }
    }
    @Override
    public void exporterClientsVersCSV(String cheminFichier) {
        File fichier = new File(cheminFichier);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fichier))) {
            for (Client client : getAllClients()) {
                String ligne = String.format("%s,%s,%s", client.getNom(), client.getPrenom(), client.getEmail());
                bw.write(ligne);
                bw.newLine();
            }
        } catch (IOException e) {
            throw new ValeurInvalideException("Erreur lors de l'écriture du fichier CSV : " + e.getMessage());
        }
    }



    @Override
    public String toString() {
        return "DAOClients{" + "clients=" + clients + '}';
    }

    public static void main(String[] args) {
        try {
            DAOClients dao = new DAOClients("clients.dat");

            dao.addClient(new Client(0, "Rahmouni", "Sara1", "sara1.rahmouni@mail.com"));
            dao.addClient(new Client(0, "Seddik", "Sara2", "sara2.seddik@mail.com"));

            Client c = dao.getClientById(1);
            if (c != null) {
                c.setNom("Sara1-Modifié");
                dao.updateClient(c);
            }

            dao.deleteClient(2);
            System.out.println(dao);
        } catch (ValeurInvalideException e) {
            System.out.println("Erreur critique : " + e.getMessage());
        }
    }
}
