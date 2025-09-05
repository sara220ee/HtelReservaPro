package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ButtonEditor extends DefaultCellEditor {
    private final JButton button;
    private boolean isPushed;
    private int row;
    private JTable table;

    public ButtonEditor(JCheckBox checkBox, JTable table) {
        super(checkBox);
        this.table = table;
        this.button = new JButton();
        this.button.setOpaque(true);
        this.button.setBackground(new Color(220, 80, 60));
        this.button.setForeground(Color.DARK_GRAY);
        this.button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        this.button.setFocusPainted(false);

        button.addActionListener(e -> fireEditingStopped());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        this.row = row;
        this.button.setText((value == null) ? "Supprimer" : value.toString());
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return button.getText();
    }
}



























































/*Gère ce qui se passe quand on clique sur le bouton "Supprimer" dans la table.

Ce qu’il fait :
Méthode ou composant	Rôle
extends DefaultCellEditor	Permet à Swing de gérer l’édition d’une cellule (bouton ici)
getTableCellEditorComponent(...)	Prépare le bouton quand on clique sur une cellule
getCellEditorValue()	Quand on clique sur le bouton :
→ Affiche une boîte de confirmation (JOptionPane)
→ Si confirmé : supprime la ligne via le DefaultTableModel
fireEditingStopped()	Déclenche getCellEditorValue() après le clic sur le bouton*/
