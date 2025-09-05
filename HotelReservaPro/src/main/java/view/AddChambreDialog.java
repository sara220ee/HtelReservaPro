package view;

import javax.swing.*;
import java.awt.*;

import model.entity.Chambre;
import model.entity.TypeChambre;

public class AddChambreDialog extends JDialog {
    private JTextField numeroField;
    private JTextField prixField;
    private JTextField imageField;
    private ButtonGroup typeGroup;
    private JRadioButton singleRadio;
    private JRadioButton doubleRadio;
    private JRadioButton suiteRadio;
    private boolean confirmed = false;

    public AddChambreDialog(JFrame parent) {
        super(parent, "Ajouter une nouvelle chambre", true);
        setSize(450, 400);
        setLocationRelativeTo(parent);
        setResizable(false);
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Informations de la chambre"));
        numeroField = new JTextField();
        prixField = new JTextField();
        imageField = new JTextField();
        formPanel.add(new JLabel("Numéro:"));
        formPanel.add(numeroField);
        formPanel.add(new JLabel("Prix:"));
        formPanel.add(prixField);
        formPanel.add(new JLabel("Image:"));
        formPanel.add(imageField);
        JPanel typePanel = new JPanel(new GridLayout(3, 1));
        typePanel.setBorder(BorderFactory.createTitledBorder("Type de chambre"));

        singleRadio = new JRadioButton(TypeChambre.SINGLE.toString());
        doubleRadio = new JRadioButton(TypeChambre.DOUBLE.toString());
        suiteRadio = new JRadioButton(TypeChambre.SUITE.toString());

        typeGroup = new ButtonGroup();
        typeGroup.add(singleRadio);
        typeGroup.add(doubleRadio);
        typeGroup.add(suiteRadio);
        singleRadio.setSelected(true);

        typePanel.add(singleRadio);
        typePanel.add(doubleRadio);
        typePanel.add(suiteRadio);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
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
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(typePanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }
    public AddChambreDialog(JFrame parent, Chambre ch) {
        this(parent);
        numeroField.setText(String.valueOf(ch.getNumero()));
        prixField.setText(String.valueOf(ch.getPrix()));
        imageField.setText(ch.getImage());

        switch (ch.getType()) {
            case SINGLE -> singleRadio.setSelected(true);
            case DOUBLE -> doubleRadio.setSelected(true);
            case SUITE -> suiteRadio.setSelected(true);
        }
    }


    private boolean validateFields() {
        if (numeroField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Le numéro de chambre est obligatoire",
                    "Erreur de validation",
                    JOptionPane.ERROR_MESSAGE);
            numeroField.requestFocus();
            return false;
        }

        try {
            int numero = Integer.parseInt(numeroField.getText());
            if (numero <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Le numéro de chambre doit être un nombre positif",
                        "Erreur de validation",
                        JOptionPane.ERROR_MESSAGE);
                numeroField.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Le numéro de chambre doit être un nombre valide",
                    "Erreur de validation",
                    JOptionPane.ERROR_MESSAGE);
            numeroField.requestFocus();
            return false;
        }
        if (prixField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Le prix est obligatoire",
                    "Erreur de validation",
                    JOptionPane.ERROR_MESSAGE);
            prixField.requestFocus();
            return false;
        }

        try {
            double prix = Double.parseDouble(prixField.getText());
            if (prix <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Le prix doit être un nombre positif",
                        "Erreur de validation",
                        JOptionPane.ERROR_MESSAGE);
                prixField.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Le prix doit être un nombre valide",
                    "Erreur de validation",
                    JOptionPane.ERROR_MESSAGE);
            prixField.requestFocus();
            return false;
        }

        if (imageField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                        "L'image est obligatoire",
                        "Erreur de validation",
                        JOptionPane.ERROR_MESSAGE);
                imageField.requestFocus();
                return false;
            }

        return true;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public int getNumero() {
        return Integer.parseInt(numeroField.getText());
    }

    public double getPrix() {
        return Double.parseDouble(prixField.getText());
    }

    public String getImage() {
        return imageField.getText();
    }

    public TypeChambre getChambreType() {
        if (singleRadio.isSelected()) return TypeChambre.SINGLE;
        if (doubleRadio.isSelected()) return TypeChambre.DOUBLE;
        return TypeChambre.SUITE;
    }
    public static void main(String[] args) {
        JFrame testFrame = new JFrame("Test AddChambreDialog");
        testFrame.setSize(100, 100);

        AddChambreDialog dialog = new AddChambreDialog(testFrame);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            System.out.println("Données de la chambre:");
            System.out.println("Numéro: " + dialog.getNumero());
            System.out.println("Prix: " + dialog.getPrix());
            System.out.println("Image: " + dialog.getImage());
            System.out.println("Type: " + dialog.getChambreType());
        } else {
            System.out.println("Ajout de chambre annulé.");
        }

        System.exit(0);
    }
}