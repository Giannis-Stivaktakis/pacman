package pacman;

import java.awt.Color;
import java.awt.Graphics2D;

public class drawGhost {

    public static void drawGhost(model m, Graphics2D g2d, int x, int y, int i) {

        if (m.powerTicks > 0) {

            Color body = (m.powerTicks < 40 && ((m.powerTicks / 4) % 2 != 0))
                    ? Color.WHITE
                    : Color.BLUE;

            int w = m.BLOCK_SIZE;
            int h = m.BLOCK_SIZE;

            // σώμα (κεφάλι)
            g2d.setColor(body);
            g2d.fillOval(x, y, w, h - 6);

            // κάτω μέρος
            g2d.fillRect(x, y + (h / 2), w, (h / 2) - 2);

            // κυματάκια
            int baseY = y + h - 2;
            int step = w / 4;
            g2d.fillOval(x + 0 * step, baseY - 6, step + 2, 8);
            g2d.fillOval(x + 1 * step, baseY - 6, step + 2, 8);
            g2d.fillOval(x + 2 * step, baseY - 6, step + 2, 8);
            g2d.fillOval(x + 3 * step, baseY - 6, step + 2, 8);

            // μάτια
            g2d.setColor(Color.WHITE);
            g2d.fillOval(x + 6, y + 8, 6, 6);
            g2d.fillOval(x + 14, y + 8, 6, 6);

            g2d.setColor(new Color(0, 0, 120));
            g2d.fillOval(x + 8, y + 10, 3, 3);
            g2d.fillOval(x + 16, y + 10, 3, 3);

            return;
        }

        // Κανονικό ghost
        g2d.drawImage(m.ghost, x, y, m); // αντί για this
    }
}