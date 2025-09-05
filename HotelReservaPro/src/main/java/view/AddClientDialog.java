package view;

import model.entity.Client;

import javax.swing.*;
import java.awt.*;

public class AddClientDialog extends JDialog {
    private JTextField nomField;
    private JTextField prenomField;
    private JTextField emailField;
    private boolean confirmed = false;

    public AddClientDialog(JFrame parent) {
        super(parent, "Ajouter un nouveau client", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Informations du client (* champs obligatoires)"));

        nomField = new JTextField();
        prenomField = new JTextField();
        emailField = new JTextField();
        formPanel.add(new JLabel("Nom*:"));
        formPanel.add(nomField);
        formPanel.add(new JLabel("Prénom*:"));
        formPanel.add(prenomField);
        formPanel.add(new JLabel("Email*:"));
        formPanel.add(emailField);

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
    public AddClientDialog(JFrame parent, Client client) {
        this(parent);
        nomField.setText(client.getNom());
        prenomField.setText(client.getPrenom());
        emailField.setText(client.getEmail());
    }

    private boolean validateFields() {
        if (nomField.getText().trim().isEmpty()) {
            showError("Le nom est obligatoire", nomField);
            return false;
        } else if (nomField.getText().length() > 50) {
            showError("Le nom ne doit pas dépasser 50 caractères", nomField);
            return false;
        }

        if (prenomField.getText().trim().isEmpty()) {
            showError("Le prénom est obligatoire", prenomField);
            return false;
        } else if (prenomField.getText().length() > 50) {
            showError("Le prénom ne doit pas dépasser 50 caractères", prenomField);
            return false;
        }

        if (emailField.getText().trim().isEmpty()) {
            showError("L'email est obligatoire", emailField);
            return false;
        } else if (!emailField.getText().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            showError("Format d'email invalide (exemple: exemple@domaine.com)", emailField);
            return false;
        } else if (emailField.getText().length() > 100) {
            showError("L'email ne doit pas dépasser 100 caractères", emailField);
            return false;
        }

        return true;
    }

    private void showError(String message, JComponent field) {
        JOptionPane.showMessageDialog(this,
                message,
                "Erreur de validation",
                JOptionPane.ERROR_MESSAGE);
        field.requestFocus();
        field.setBackground(new Color(255, 200, 200));
        Timer timer = new Timer(2000, e -> field.setBackground(Color.WHITE));
        timer.setRepeats(false);
        timer.start();
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public String getNom() {
        return nomField.getText().trim();
    }

    public String getPrenom() {
        return prenomField.getText().trim();
    }

    public String getEmail() {
        return emailField.getText().trim();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame testFrame = new JFrame("Test AddClientDialog");
            testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            testFrame.setSize(100, 100);
            testFrame.setLocationRelativeTo(null);

            AddClientDialog dialog = new AddClientDialog(testFrame);
            dialog.setVisible(true);

            if (dialog.isConfirmed()) {
                System.out.println("\nDonnées du client:");
                System.out.println("Nom: " + dialog.getNom());
                System.out.println("Prénom: " + dialog.getPrenom());
                System.out.println("Email: " + dialog.getEmail());
            } else {
                System.out.println("Ajout de client annulé.");
            }

            System.exit(0);
        });
    }
}