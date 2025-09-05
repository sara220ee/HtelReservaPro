package controller;

import model.DataAccesLayerClient;
import model.DataAccessLayerChambre;
import model.DataAccessLayerReservation;
import model.ReservationDateFormatter;
import model.entity.Chambre;
import model.entity.Client;
import model.entity.Reservation;
import model.entity.ValeurInvalideException;
import view.*;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public final class ControllerHotel implements ActionListener {

    private final VHotel view;
    private final DataAccesLayerClient clientDAO;
    private final DataAccessLayerChambre chambreDAO;
    private final DataAccessLayerReservation reservationDAO;

    public ControllerHotel(VHotel view, DataAccesLayerClient clientDAO, DataAccessLayerChambre chambreDAO, DataAccessLayerReservation reservationDAO) {
        this.view = view;
        this.clientDAO = clientDAO;
        this.chambreDAO = chambreDAO;
        this.reservationDAO = reservationDAO;
        view.getReservationsTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTable table = view.getReservationsTable();
                int row = table.getSelectedRow();
                int column = table.getSelectedColumn();
                if (column == 5 && row >= 0) {
                    int confirm = JOptionPane.showConfirmDialog(null, "Confirmer la suppression ?", "Confirmation", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        int reservationId = (int) table.getValueAt(row, 0);
                        if (reservationDAO.deleteReservation(reservationId)) {
                            view.showMessage("Réservation supprimée !");
                            view.displayReservations(reservationDAO.getList());
                        } else {
                            view.showError("Erreur lors de la suppression.");
                        }
                    }
                }
            }
        });
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        try {
            switch (cmd) {
                case "Ajouter Client" -> ajouterClient();
                case "Ajouter Chambre" -> ajouterChambre();
                case "Supprimer Client" -> supprimerClient();
                case "Supprimer Chambre" -> supprimerChambre();
                case "Importer Clients" -> importerClients();
                case "Importer Chambres" -> importerChambres();
                case "Exporter Clients" -> exporterClients();
                case "Exporter Chambres" -> exporterChambres();
                case "Déconnecter" -> deconnecter();
                case "Ajouter Réservation" -> ajouterReservation();
                case "Modifier" -> modifierChambre();
                case "Modifier Client" -> modifierClient();
                case "Voir Image" -> afficherImageChambre();
                case "filter" -> filtrerReservations();
                case "reset" -> resetReservations();
                case "Clair" -> changerTheme(true);
                case "Darck" -> changerTheme(false);
                case "Changer Format Date" -> changerFormatDate();


                default -> view.showError("Action inconnue : " + cmd);
            }
        } catch (ValeurInvalideException ex) {
            view.showError("Erreur : " + ex.getMessage());
        }
    }
    private void changerFormatDate() {
        String[] options = {"dd/MM/yyyy", "yyyy-MM-dd", "dd MMM yyyy"};
        String formatChoisi = (String) JOptionPane.showInputDialog(
                null,
                "Choisissez un format de date :",
                "Format de date",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (formatChoisi != null) {
            ReservationDateFormatter.setFormat(formatChoisi);
            view.displayReservations(reservationDAO.getList()); // Rafraîchir
        }
    }
    private void changerTheme(boolean isLightTheme) {
        if (isLightTheme) {
            FlatLightLaf.setup();

            if (view instanceof Component) {
                SwingUtilities.updateComponentTreeUI((Component) view);
                ((Component) view).repaint();
            } else {
                System.err.println("Erreur : la vue n'est pas un composant Swing.");
            }
        } else {
            SwingUtilities.invokeLater(() -> {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

                    if (view instanceof Component) {
                        SwingUtilities.updateComponentTreeUI((Component) view);
                        ((Component) view).repaint();
                    } else {
                        System.err.println("Erreur : la vue n'est pas un composant Swing.");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }


    private void traiterConnexion() {
        LoginDialog login = new LoginDialog((JFrame) view);
        login.setVisible(true);

        if (!login.isSucceeded()) {
            System.exit(0);
        }
    }


    private void ajouterClient() {
        Client client = view.promptForNewClient();
        if (client != null) {
            clientDAO.addClient(client);
            view.showMessage("Client ajouté avec succès !");
            view.displayClients(clientDAO.getAllClients());
        }
    }

    private void ajouterChambre() {
        Chambre chambre = view.promptForNewChambre();
        if (chambre != null) {
            chambreDAO.addChambre(chambre);
            view.showMessage("Chambre ajoutée avec succès !");
            view.displayChambres(chambreDAO.getList());
        }
    }

    private void supprimerClient() {
        Integer id = view.promptForClientId();
        if (id != null && clientDAO.deleteClient(id)) {
            view.showMessage("Client supprimé avec succès !");
            view.displayClients(clientDAO.getAllClients());
        } else {
            view.showError("Client introuvable ou suppression échouée.");
        }
    }

    private void supprimerChambre() {
        Integer id = view.promptForChambreId();
        if (id != null && chambreDAO.deleteChambre(id)) {
            view.showMessage("Chambre supprimée avec succès !");
            view.displayChambres(chambreDAO.getList());
        } else {
            view.showError("Chambre introuvable ou suppression échouée.");
        }
    }

    private void importerClients() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Importer Clients depuis CSV");
        int result = chooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            String path = chooser.getSelectedFile().getAbsolutePath();
            clientDAO.importerClientsDepuisCSV(path);
            view.displayClients(clientDAO.getAllClients());
            view.showMessage("Import des clients terminé !");
        }
    }

    private void importerChambres() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Importer Chambres depuis CSV");
        int result = chooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            String path = chooser.getSelectedFile().getAbsolutePath();
            chambreDAO.importerChambresDepuisCSV(path);
            view.displayChambres(chambreDAO.getList());
            view.showMessage("Import des chambres terminé !");
        }
    }

    private void exporterClients() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Exporter Clients vers CSV");
        int result = chooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            String path = chooser.getSelectedFile().getAbsolutePath();
            clientDAO.exporterClientsVersCSV(path);
            view.showMessage("Export des clients terminé !");
        }
    }

    private void exporterChambres() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Exporter Chambres vers CSV");
        int result = chooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            String path = chooser.getSelectedFile().getAbsolutePath();
            chambreDAO.exporterChambresVersCSV(path);
            view.showMessage("Export des chambres terminé !");
        }
    }

    private void deconnecter() {
        view.showMessage("Déconnexion réussie.");
        view.clearTables();
        traiterConnexion();
        view.displayClients(clientDAO.getAllClients());
        view.displayChambres(chambreDAO.getList());
        view.displayReservations(reservationDAO.getList());
    }
    private void ajouterReservation() {
        try {
            int clientId = view.getSelectedClientId();
            int chambreId = view.getSelectedChambreId();
            LocalDate dateDebut = view.getDateDebutReservation();
            LocalDate dateFin = view.getDateFinReservation();

            Client client = clientDAO.getClientById(clientId);
            Chambre chambre = chambreDAO.getChambreById(chambreId);

            if (client == null || chambre == null) {
                view.showError("Client ou chambre introuvable !");
                return;
            }

            Reservation reservation = new Reservation(0, client, chambre, dateDebut, dateFin);

            if (!reservationDAO.isChambreDisponible(chambreId, dateDebut, dateFin)) {
                view.showError("La chambre est déjà réservée pour cette période !");
                return;
            }

            reservationDAO.addReservation(reservation);
            view.showMessage("Réservation ajoutée avec succès !");
            view.displayReservations(reservationDAO.getList());

        } catch (Exception e) {
            view.showError("Erreur lors de la création de la réservation : " + e.getMessage());
        }
    }

    private void modifierChambre() {
        JTable table = view.getChambresTable();  // Ajoute cette méthode dans l'interface si besoin
        int row = table.getSelectedRow();

        if (row == -1) {
            view.showError("Veuillez sélectionner une chambre à modifier.");
            return;
        }

        int id = (int) table.getValueAt(row, 0);
        Chambre chambre = chambreDAO.getChambreById(id);

        if (chambre == null) {
            view.showError("Chambre introuvable.");
            return;
        }

        AddChambreDialog dialog = new AddChambreDialog((JFrame) view, chambre);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            chambre.setNumero(dialog.getNumero());
            chambre.setPrix(dialog.getPrix());
            chambre.setType(dialog.getChambreType());
            chambre.setImage(dialog.getImage());

            chambreDAO.updateChambre(chambre);
            view.showMessage("Chambre modifiée avec succès.");
            view.displayChambres(chambreDAO.getList());
        }
    }
    private void modifierClient() {
        JTable table = view.getClientsTable();
        int row = table.getSelectedRow();

        if (row == -1) {
            view.showError("Veuillez sélectionner un client à modifier.");
            return;
        }

        int id = (int) table.getValueAt(row, 0);
        Client client = clientDAO.getClientById(id);

        if (client == null) {
            view.showError("Client introuvable.");
            return;
        }

        AddClientDialog dialog = new AddClientDialog((JFrame) view, client);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            client.setNom(dialog.getNom());
            client.setPrenom(dialog.getPrenom());
            client.setEmail(dialog.getEmail());

            clientDAO.updateClient(client);
            view.showMessage("Client modifié avec succès !");
            view.displayClients(clientDAO.getAllClients());
        }
    }
    private void afficherImageChambre() {
        JTable table = view.getChambresTable();
        int row = table.getSelectedRow();
        if (row == -1) {
            view.showError("Veuillez sélectionner une chambre pour voir l'image.");
            return;
        }

        String cheminImage = (String) table.getValueAt(row, 4);  // Colonne image
        if (cheminImage == null || cheminImage.isEmpty()) {
            view.showError("Aucune image spécifiée pour cette chambre.");
            return;
        }

        ImageIcon originalIcon = new ImageIcon(cheminImage);

        Image scaledImage = originalIcon.getImage().getScaledInstance(500, 350, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);

        JLabel label = new JLabel(resizedIcon);
        JOptionPane.showMessageDialog(null, label, "Image de la chambre", JOptionPane.PLAIN_MESSAGE);
    }
    private void filtrerReservations() {
        String filtre = view.getFilterText();
        if (filtre != null && !filtre.isEmpty()) {
            List<Reservation> toutes = reservationDAO.getList();
            List<Reservation> filtrees = new ArrayList<>();
            for (Reservation r : toutes) {
                String numChambre = String.valueOf(r.getChambre().getId());
                if (numChambre.contains(filtre)) {
                    filtrees.add(r);
                }
            }
            view.displayReservations(new ArrayList<>(filtrees));
            view.clearFilterField();
        }
    }

    private void resetReservations() {
        view.displayReservations(reservationDAO.getList());
        view.clearFilterField();
    }

    public void run(){
        traiterConnexion();
        this.view.setController(this);
        view.displayClients(clientDAO.getAllClients());
        view.displayChambres(chambreDAO.getList());
        view.displayReservations(reservationDAO.getList());
        view.run();
    }

}
