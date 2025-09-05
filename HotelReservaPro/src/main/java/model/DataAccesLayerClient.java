package model;

import model.entity.Client;
import java.util.ArrayList;

public interface DataAccesLayerClient {
    int addClient(Client client);
    boolean updateClient(Client client);
    boolean deleteClient(int id);
    boolean deleteClient(Client client);
    Client getClientById(int id);
    ArrayList<Client> getAllClients();
    void importerClientsDepuisCSV(String cheminFichier);
    void exporterClientsVersCSV(String cheminFichier);

    void save();
    void load();
}