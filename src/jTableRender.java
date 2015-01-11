import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * Définir l'affichage dans un JTable
 *
 */
public class jTableRender extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        /**
         * Fixer la couleur de fond de la première colonne en jaune
         */
        /*
        if (column == 0) {
            Color clr = new Color(255, 255, 240);
            component.setBackground(clr);
        } else {
            Color clr = new Color(255, 255, 255);
            component.setBackground(clr);
        }*/
        /**
         * Colorier les cellules en orange si le montant est négatif
         */
        Object o = table.getValueAt(row, 3);
        if (o != null && component instanceof JLabel) {
            JLabel label = (JLabel) component;
            if (label.getText().contains("S")) {
                Color clr = new Color(250, 0, 0);
                component.setBackground(clr);
            }
            else if (label.getText().contains("F")) {
                Color clr = new Color(0, 0, 250);
                component.setBackground(clr);
            }
            else {
                Color clr = new Color(255, 255, 255);
                component.setBackground(clr);
            }
            /**
             * Center le texte pour la colonne 0 et aligner le texte à droite pour les autres colonnes
             */
            /*if (column == 0) {
                label.setHorizontalAlignment(CENTER);
            } else {
                label.setHorizontalAlignment(RIGHT);
            }*/
        }
        return component;
    }
}