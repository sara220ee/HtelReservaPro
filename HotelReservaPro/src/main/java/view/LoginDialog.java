package view;

import authentication.PropertiesAuthenticator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginDialog extends JDialog {
    private boolean succeeded = false;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginDialog(JFrame parent) {
        super(parent, "Connexion", true);
        setLayout(new BorderLayout());
        setSize(350, 200);
        setLocationRelativeTo(parent);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        JLabel titleLabel = new JLabel("HOTEL MANAGEMENT", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(new Color(70, 130, 180));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 5, 10));
        usernameField = new JTextField();
        passwordField = new JPasswordField();

        JLabel userLabel = new JLabel("Email*:");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JLabel passLabel = new JLabel("Mot de passe*:");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        formPanel.add(userLabel);
        formPanel.add(usernameField);
        formPanel.add(passLabel);
        formPanel.add(passwordField);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JButton loginButton = new JButton("Connexion");
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setForeground(Color.DARK_GRAY);
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = usernameField.getText().trim();
                String pass = new String(passwordField.getPassword()).trim();

                if (user.isEmpty() || pass.isEmpty()) {
                    JOptionPane.showMessageDialog(LoginDialog.this,
                            "Veuillez remplir tous les champs obligatoires",
                            "Champs manquants",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                PropertiesAuthenticator users = new PropertiesAuthenticator("C:\\Users\\sarar\\IdeaProjects\\HotelReservaPro\\src\\main\\resources\\Properties\\sara.properties");
                if (users.authenticate(user, pass)){
                    succeeded = true;
                    dispose();
                }

               else {
                    JOptionPane.showMessageDialog(LoginDialog.this,
                            "Email ou mot de passe incorrect",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                    passwordField.setText("");
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    public boolean isSucceeded() {
        return succeeded;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame testFrame = new JFrame("Test Login Dialog");
                testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                testFrame.setSize(100, 100);

                LoginDialog dialog = new LoginDialog(testFrame);
                dialog.setVisible(true);

                if (dialog.isSucceeded()) {
                    System.out.println("Connexion réussie!");
                } else {
                    System.out.println("Connexion échouée ou annulée.");
                }
            }
        });
    }
}