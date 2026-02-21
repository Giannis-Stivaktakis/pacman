package pacman;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

public class drawMaze {

    public static void drawMaze(model m, Graphics2D g2d) {

        short i = 0;
        int x, y;

        for (y = 0; y < m.SCREEN_SIZE; y += m.BLOCK_SIZE) {
            for (x = 0; x < m.SCREEN_SIZE; x += m.BLOCK_SIZE) {

                // Background per tile
                g2d.setColor(Color.black);
                g2d.fillRect(x, y, m.BLOCK_SIZE, m.BLOCK_SIZE);

                // Walls from template (stable per level)
                short tileWalls = m.screenData[i];

                g2d.setColor(new Color(100, 0, 0));
                g2d.setStroke(new BasicStroke(5));

                if ((tileWalls & 1) != 0) {
                    g2d.drawLine(x, y, x, y + m.BLOCK_SIZE - 1);
                }

                if ((tileWalls & 2) != 0) {
                    g2d.drawLine(x, y, x + m.BLOCK_SIZE - 1, y);
                }

                if ((tileWalls & 4) != 0) {
                    g2d.drawLine(x + m.BLOCK_SIZE - 1, y,
                            x + m.BLOCK_SIZE - 1, y + m.BLOCK_SIZE - 1);
                }

                if ((tileWalls & 8) != 0) {
                    g2d.drawLine(x, y + m.BLOCK_SIZE - 1,
                            x + m.BLOCK_SIZE - 1, y + m.BLOCK_SIZE - 1);
                }

                // Pellets from screenData (dynamic)
                if ((m.screenData[i] & 16) != 0) {
                    g2d.setColor(Color.white);
                    g2d.fillOval(x + 10, y + 10, 6, 6);
                }

                if ((m.screenData[i] & model.POWER) != 0) {
                    if (m.powerBlink) {
                        g2d.setColor(Color.white);
                        g2d.fillOval(x + 7, y + 7, 12, 12);
                    }
                }

                if ((m.screenData[i] & model.BONUS_LIFE) != 0) {
                    g2d.setColor(Color.green);
                    g2d.fillOval(x + 8, y + 8, 12, 12);
                }

                i++;
            }
        }
    }
}