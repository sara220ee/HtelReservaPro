package view;

import controller.ControllerHotel;
import model.ReservationDateFormatter;
import model.entity.Reservation;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ManageReservationsPanel extends JPanel {
    private JTable reservationTable;
    private JTextField filterField;
    private DefaultTableModel reservationsModel;
    private JButton resetButton ;
    private JButton filterButton ;

    public ManageReservationsPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(10, 10, 10, 10),
                new TitledBorder("Gérer les réservations")
        ));
        setOpaque(false);

        String[] reservationsColumns = {"ID", "Client", "Chambre", "Date début", "Date fin", "Action"};
        reservationsModel = new DefaultTableModel(reservationsColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5;
            }
        };

        reservationTable = new JTable(reservationsModel);
        styleTable(reservationTable);
        reservationTable.getColumn("Action").setCellRenderer(new ButtonRenderer());
        reservationTable.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox(), reservationTable));

        JScrollPane scrollPane = new JScrollPane(reservationTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel filtrePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        filtrePanel.setOpaque(false);

        JLabel filterLabel = new JLabel("Filtrer par id chambre:");
        filterLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        filtrePanel.add(filterLabel);

        filterField = new JTextField(10);
        filtrePanel.add(filterField);

        filterButton = new JButton("Filtrer");
        styleButton(filterButton);
        filtrePanel.add(filterButton);
        filterButton.setActionCommand("filter");


        resetButton = new JButton("Réinitialiser");
        resetButton.setActionCommand("reset");
        styleButton(resetButton);
        filtrePanel.add(resetButton);
        add(filtrePanel, BorderLayout.SOUTH);

    }
    public void setController(ControllerHotel controller) {
        filterButton.addActionListener(controller);
        resetButton.addActionListener(controller);
    }

    public void updateTable(ArrayList<Reservation> reservations) {
        reservationsModel.setRowCount(0);
        for (Reservation r : reservations) {
            reservationsModel.addRow(new Object[]{
                    r.getId(),
                    r.getClient().toString(),
                    r.getChambre().toString(),
                    ReservationDateFormatter.format(r.getDateDebut()),
                    ReservationDateFormatter.format(r.getDateFin()),
                    "Supprimer"
            });

        }
    }
    public void clearReservationsTable() {
        updateTable(new ArrayList<>());
    }

    public JTable getTable() {
        return reservationTable;
    }
    public String getFilterText() {
        return filterField.getText().trim();
    }

    public void clearFilterField() {
        filterField.setText("");
    }


    private void styleTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(70, 130, 180));
        table.getTableHeader().setForeground(Color.DARK_GRAY);
        table.setRowHeight(25);
        table.setGridColor(new Color(220, 220, 220));
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.DARK_GRAY);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setFocusPainted(false);
    }
}
