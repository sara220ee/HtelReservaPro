package view;
import model.entity.Chambre;
import model.entity.Client;
import model.entity.Reservation;
import controller.ControllerHotel;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;

public interface VHotel {

    Client promptForNewClient();
    Integer promptForClientId();
    JTable getClientsTable();


    Chambre promptForNewChambre();
    Integer promptForChambreId();
    JTable getChambresTable();

    JTable getReservationsTable();
    int getSelectedClientId();
    int getSelectedChambreId();
    LocalDate getDateDebutReservation();
    LocalDate getDateFinReservation();

    void displayClients(ArrayList<Client> clients);
   void displayChambres(ArrayList<Chambre> chambres);
    void displayReservations(ArrayList<Reservation> reservations);

    String getFilterText();
    void clearFilterField();


    void showMessage(String message);
    void showError(String message);
    void run() ;

    void setController(ControllerHotel c);
    void clearTables();
}