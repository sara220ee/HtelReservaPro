package view;

import controller.ControllerHotel;
import model.entity.Chambre;
import model.entity.Client;
import model.entity.Reservation;
import model.entity.TypeChambre;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

public class MainFrame extends JFrame implements VHotel {
    private MenuBarPanel menuBarPanel;
    private TabbedPanel tabbedPanel;
    private ReservationPanel reservationPanel;
    private ManageReservationsPanel manageReservationsPanel;
    public MainFrame() {
        super("Système de Réservation de Chambres");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        menuBarPanel = new MenuBarPanel();
        setJMenuBar(menuBarPanel);

        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 248, 255));


        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BoxLayout(mainContentPanel, BoxLayout.Y_AXIS));
        mainContentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainContentPanel.setOpaque(false);

        tabbedPanel = new TabbedPanel();
        mainContentPanel.add(tabbedPanel);

        reservationPanel = new ReservationPanel();
        mainContentPanel.add(Box.createVerticalStrut(10));
        mainContentPanel.add(reservationPanel);


        mainContentPanel.add(Box.createVerticalStrut(10));
        manageReservationsPanel = new ManageReservationsPanel();
        mainContentPanel.add(manageReservationsPanel);


        add(mainContentPanel, BorderLayout.CENTER);
        this.setVisible(true);
    }

    @Override
    public Client promptForNewClient() {
        AddClientDialog dialog = new AddClientDialog(this);
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            try {
                String nom = dialog.getNom();
                String prenom = dialog.getPrenom();
                String email = dialog.getEmail();

                return new Client(0, nom, prenom, email);
            } catch (Exception e) {
                showError("Erreur lors de la création du client : " + e.getMessage());
            }
        }
        return null;
    }

    @Override
    public Chambre promptForNewChambre() {
        AddChambreDialog dialog = new AddChambreDialog(this);
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            try {
                int numero = dialog.getNumero();
                double prix = dialog.getPrix();
                String image = dialog.getImage();
                TypeChambre type = dialog.getChambreType();

                return new Chambre(0, numero, prix, type, image);
            } catch (Exception e) {
                showError("Erreur lors de la création de la chambre : " + e.getMessage());
            }
        }
        return null;
    }

    @Override
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public Integer promptForClientId() {
        String input = JOptionPane.showInputDialog(
                this,
                "Entrez l'ID du client à supprimer :",
                "Suppression client",
                JOptionPane.QUESTION_MESSAGE
        );

        if (input == null || input.trim().isEmpty()) return null;

        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            showError("ID invalide. Veuillez entrer un nombre.");
            return null;
        }
    }

    @Override
    public Integer promptForChambreId() {
        String input = JOptionPane.showInputDialog(
                this,
                "Entrez l'ID de la chambre à supprimer :",
                "Suppression chambre",
                JOptionPane.QUESTION_MESSAGE
        );

        if (input == null || input.trim().isEmpty()) return null;

        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            showError("ID invalide. Veuillez entrer un nombre.");
            return null;
        }
    }

    @Override
    public void setController(ControllerHotel controller) {
        menuBarPanel.setController(controller);
        reservationPanel.getBtnAjouter().addActionListener(controller);
        manageReservationsPanel.setController(controller);
        tabbedPanel.getChambresPanel().setController(controller);
        tabbedPanel.getClientsPanel().setController(controller);


    }
    @Override
    public JTable getChambresTable() {
        return tabbedPanel.getChambresPanel().getTable();
    }

    @Override
    public void displayClients(ArrayList<Client> clients) {
        tabbedPanel.updateClientsTable(clients);
        reservationPanel.setClientIds(clients);
    }
    @Override
    public void displayChambres(ArrayList<Chambre> chambres) {
        tabbedPanel.updateChambresTable(chambres);
        reservationPanel.setChambreIds(chambres);
    }
    @Override
    public void clearTables() {
        tabbedPanel.clearClientsTable();
        tabbedPanel.clearChambresTable();
        manageReservationsPanel.clearReservationsTable();
    }

    @Override
    public int getSelectedClientId() {
        return reservationPanel.getSelectedClientId();
    }

    @Override
    public int getSelectedChambreId() {
        return reservationPanel.getSelectedChambreId();
    }

    @Override
    public LocalDate getDateDebutReservation() {
        return reservationPanel.getDateDebut().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @Override
    public LocalDate getDateFinReservation() {
        return reservationPanel.getDateFin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
    @Override
    public void displayReservations(ArrayList<Reservation> reservations) {
        manageReservationsPanel.updateTable(reservations);
    }

    @Override
    public JTable getReservationsTable() {
        return manageReservationsPanel.getTable();
    }
    @Override
    public JTable getClientsTable() {
        return tabbedPanel.getClientsPanel().getTable();
    }
    @Override
    public String getFilterText() {
        return manageReservationsPanel.getFilterText();
    }

    @Override
    public void clearFilterField() {
        manageReservationsPanel.clearFilterField();
    }

    @Override
    public void run() {
        setVisible(true);

    }
}
