package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddAdminDialog extends JDialog {
    private JTextField nomField;
    private JTextField prenomField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private boolean confirmed = false;

    public AddAdminDialog(JFrame parent) {
        super(parent, "Ajouter un nouvel administrateur", true);
        setSize(400, 350);
        setLocationRelativeTo(parent);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Informations administrateur"));

        nomField = new JTextField();
        prenomField = new JTextField();
        emailField = new JTextField();
        passwordField = new JPasswordField();

        formPanel.add(new JLabel("Nom:"));
        formPanel.add(nomField);
        formPanel.add(new JLabel("Prénom:"));
        formPanel.add(prenomField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Mot de passe:"));
        formPanel.add(passwordField);

        // Panel pour les boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Annuler");

        okButton.addActionListener(e -> {
            if (validateFields()) {
                confirmed = true;
                dispose();
            }
        });

        cancelButton.addActionListener(e -> {
            confirmed = false;
            dispose();
        });

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private boolean validateFields() {
        if (nomField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Le nom est obligatoire", "Erreur de validation", JOptionPane.ERROR_MESSAGE);
            nomField.requestFocus();
            return false;
        }

        if (prenomField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Le prénom est obligatoire", "Erreur de validation", JOptionPane.ERROR_MESSAGE);
            prenomField.requestFocus();
            return false;
        }

        if (emailField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "L'email est obligatoire", "Erreur de validation", JOptionPane.ERROR_MESSAGE);
            emailField.requestFocus();
            return false;
        } else if (!emailField.getText().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            JOptionPane.showMessageDialog(this, "Format d'email invalide", "Erreur de validation", JOptionPane.ERROR_MESSAGE);
            emailField.requestFocus();
            return false;
        }

        if (passwordField.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this, "Le mot de passe est obligatoire", "Erreur de validation", JOptionPane.ERROR_MESSAGE);
            passwordField.requestFocus();
            return false;
        } else if (passwordField.getPassword().length < 6) {
            JOptionPane.showMessageDialog(this, "Le mot de passe doit contenir au moins 6 caractères", "Erreur de validation", JOptionPane.ERROR_MESSAGE);
            passwordField.requestFocus();
            return false;
        }

        return true;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public String getNom() {
        return nomField.getText();
    }

    public String getPrenom() {
        return prenomField.getText();
    }

    public String getEmail() {
        return emailField.getText();
    }


    public String getMotDePasse() {
        return new String(passwordField.getPassword());
    }

    public static void main(String[] args) {
        JFrame testFrame = new JFrame("Test AddAdminDialog");
        testFrame.setSize(100, 100);

        AddAdminDialog dialog = new AddAdminDialog(testFrame);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            System.out.println("\nDonnées de l'administrateur:");
            System.out.println("Nom: " + dialog.getNom());
            System.out.println("Prénom: " + dialog.getPrenom());
            System.out.println("Email: " + dialog.getEmail());
            System.out.println("Mot de passe: " + dialog.getMotDePasse().replaceAll(".", "*"));
        } else {
            System.out.println("Ajout d'administrateur annulé.");
        }

        System.exit(0);
    }
}