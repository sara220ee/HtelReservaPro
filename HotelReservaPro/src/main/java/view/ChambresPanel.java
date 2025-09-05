package view;

import controller.ControllerHotel;
import model.entity.Chambre;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ChambresPanel extends JPanel {
    private JTable chambresTable;
    private JButton btnModifier;
    private JButton btnVoirImage;


    public ChambresPanel() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));

        String[] chambresColumns = {"ID", "Numéro", "Type", "Prix", "Image"};
        chambresTable = new JTable(new DefaultTableModel(chambresColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });


        styleTable(chambresTable);
        add(new JScrollPane(chambresTable), BorderLayout.CENTER);
        btnModifier = new JButton("Modifier");

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.add(btnModifier);
        btnModifier.setActionCommand("Modifier");
        add(btnPanel, BorderLayout.SOUTH);
        btnVoirImage = new JButton("Voir la chambre");
        btnPanel.add(btnVoirImage);
        btnVoirImage.setActionCommand("Voir Image");
    }
    public void setController(ControllerHotel controller) {
        btnModifier.addActionListener(controller);
        btnVoirImage.addActionListener(controller);
    }

    public void updateTable(ArrayList<Chambre> chambres) {
        DefaultTableModel model = (DefaultTableModel) chambresTable.getModel();
        model.setRowCount(0);
        for (Chambre ch : chambres) {
            model.addRow(new Object[]{
                    ch.getId(),
                    ch.getNumero(),
                    ch.getType(),
                    ch.getPrix(),
                    ch.getImage()
            });
        }
    }
    public JTable getTable() {
        return chambresTable;
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
