import javax.swing.*;
import java.awt.*;

public class Rect extends JComponent {
    public Object[][] donnees;
    private Color red = new Color(255, 0, 0);
    private Color blue = new Color(0, 0, 255);
    private Color green = new Color(0, 255, 0);

    public void Set_Rect(Object[][] o) {
        donnees = o;
    }

    public void paint(Graphics g) {
        int x = 10;
        int y = 10;
        for (Object[] i : donnees) {
            for (Object j : i) {
                if (j == null) {
                    g.setColor(blue);
                } else if (j.toString().equals(" ")) {
                    g.setColor(blue);
                } else if (j.toString().equals("S")) {
                    g.setColor(red);
                } else if (j.toString().equals("T")) {
                    g.setColor(green);
                }

                g.fillRect(x, y, 5, 5);
                x += 6;
            }
            x = 10;
            y += 6;
        }

    }

}
