package view;

import model.entity.Chambre;
import model.entity.Client;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.Date;
import java.util.ArrayList;

public class ReservationPanel extends JPanel {
    private JComboBox<String> clientsCombo;
    private JComboBox<String> chambresCombo;
    private JSpinner dateDebutSpinner;
    private JSpinner dateFinSpinner;
    private JButton btnAjouter;

    public ReservationPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(10, 10, 10, 10),
                new TitledBorder("Ajouter une réservation")
        ));
        setOpaque(false);
        JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        mainPanel.setOpaque(false);

        JPanel clientPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        clientPanel.setOpaque(false);
        clientPanel.add(new JLabel("Client:"));
        clientsCombo = new JComboBox<>();
        styleComboBox(clientsCombo);
        clientPanel.add(clientsCombo);
        mainPanel.add(clientPanel);

        JPanel chambrePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        chambrePanel.setOpaque(false);
        chambrePanel.add(new JLabel("Chambre:"));
        chambresCombo = new JComboBox<>();
        styleComboBox(chambresCombo);
        chambrePanel.add(chambresCombo);
        mainPanel.add(chambrePanel);

        JPanel dateDebutPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        dateDebutPanel.setOpaque(false);
        dateDebutPanel.add(new JLabel("Date début:"));
        dateDebutSpinner = new JSpinner(new SpinnerDateModel());
        styleSpinner(dateDebutSpinner);
        dateDebutPanel.add(dateDebutSpinner);
        mainPanel.add(dateDebutPanel);

        JPanel dateFinPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        dateFinPanel.setOpaque(false);
        dateFinPanel.add(new JLabel("Date fin:"));
        dateFinSpinner = new JSpinner(new SpinnerDateModel());
        styleSpinner(dateFinSpinner);
        dateFinPanel.add(dateFinSpinner);
        mainPanel.add(dateFinPanel);

        btnAjouter = new JButton("Ajouter");
        btnAjouter.setActionCommand("Ajouter Réservation");

        styleButton(btnAjouter);
        mainPanel.add(btnAjouter);

        add(mainPanel, BorderLayout.CENTER);
    }
    public void setClientIds(ArrayList<Client> clients) {
        clientsCombo.removeAllItems();
        for (Client c : clients) {
            clientsCombo.addItem(String.valueOf(c.getId()));
        }
    }
    public void setChambreIds(ArrayList<Chambre> chambres) {
        chambresCombo.removeAllItems();
        for (Chambre ch : chambres) {
            chambresCombo.addItem(String.valueOf(ch.getId()));
        }
    }


    public JButton getBtnAjouter() {
        return btnAjouter;
    }


    private void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        comboBox.setPreferredSize(new Dimension(150, 28));
    }

    private void styleSpinner(JSpinner spinner) {
        spinner.setEditor(new JSpinner.DateEditor(spinner, "dd/MM/yyyy"));
        spinner.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        spinner.setPreferredSize(new Dimension(100, 28));
    }
    public int getSelectedClientId() {
        return Integer.parseInt((String) clientsCombo.getSelectedItem());
    }

    public int getSelectedChambreId() {
        return Integer.parseInt((String) chambresCombo.getSelectedItem());
    }

    public Date getDateDebut() {
        return (Date) dateDebutSpinner.getValue();
    }

    public Date getDateFin() {
        return (Date) dateFinSpinner.getValue();
    }



    private void styleButton(JButton button) {
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.DARK_GRAY);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(100, 28));
    }
}