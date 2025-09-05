package view;

import controller.ControllerHotel;
import model.entity.Client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ClientsPanel extends JPanel {
    private JButton btnModifier;
    private JTable clientsTable;
    private DefaultTableModel model;

    public ClientsPanel() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));

        model = new DefaultTableModel(new String[]{"ID", "Nom", "Prénom", "Email"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        clientsTable = new JTable(model);


        styleTable(clientsTable);
        add(new JScrollPane(clientsTable), BorderLayout.CENTER);
        btnModifier = new JButton("Modifier");
        btnModifier.setActionCommand("Modifier Client");
        JPanel panelBtn = new JPanel();
        panelBtn.add(btnModifier);
        add(panelBtn, BorderLayout.SOUTH);
    }

    public void updateTable(ArrayList<Client> clients) {
        model.setRowCount(0);
        for (Client c : clients) {
            model.addRow(new Object[]{
                    c.getId(),
                    c.getNom(),
                    c.getPrenom(),
                    c.getEmail()
            });
        }
    }
    public JTable getTable() {
        return clientsTable;
    }
    public void setController(ControllerHotel controller) {
        btnModifier.addActionListener(controller);
    }

    private void styleTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(70, 130, 180));
        table.getTableHeader().setForeground(Color.DARK_GRAY);
        table.setRowHeight(25);
        table.setGridColor(new Color(220, 220, 220));
        table.setShowGrid(true);
    }
}
