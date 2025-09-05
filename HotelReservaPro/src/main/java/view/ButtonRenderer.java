package view;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ButtonRenderer extends JButton implements TableCellRenderer {

    public ButtonRenderer() {
        setOpaque(true);
        setBackground(new Color(220, 80, 60));
        setForeground(Color.DARK_GRAY);
        setFont(new Font("Segoe UI", Font.BOLD, 12));
        setFocusPainted(false);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        setText((value == null) ? "" : value.toString());
        return this;
    }
}




































/*Permet d’afficher un vrai bouton stylé dans la table, dans la colonne "Action".

Sans ça, Swing afficherait juste du texte brut comme "Supprimer".

Ce qu’il fait :
Partie	Rôle
implements TableCellRenderer	Swing appelle ce renderer pour afficher chaque cellule
getTableCellRendererComponent(...)	C’est ici que tu transformes la cellule en joli bouton
setText(...)	Définit le texte du bouton (ex: "Supprimer")
setBackground(...)	Donne la couleur au fond du bouton
setForeground(...)	Donne la couleur du texte du bouton*/
