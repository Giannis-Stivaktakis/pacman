package pacman;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class drawScore {

    public static void drawScore(model m, Graphics2D g2d) {

        int hudTop = m.SCREEN_SIZE + 3;        // αρχή της HUD ζώνης
        int hudBaseline = m.SCREEN_SIZE + 20;  // γραμμή για το text

        g2d.drawString("Lvl: " + m.level, m.SCREEN_SIZE - 250, m.SCREEN_SIZE + 22);
        g2d.drawString("Diff: " + m.difficulty.name(), m.SCREEN_SIZE - 200, m.SCREEN_SIZE + 22);

        g2d.setFont(new Font("Monospaced", Font.BOLD, 14));
        g2d.setColor(Color.GREEN);

        // Lives (hearts) μέσα στη HUD μπάρα
        for (int i = 0; i < m.lives; i++) {
            g2d.drawImage(m.heart,
                    8 + i * 30,
                    hudTop,
                    m); // <-- αντί για this
        }

        if (m.powerTicks > 0) {

            double secs = m.powerTicks * 0.04;

            if (m.powerTicks < 40) {
                if ((m.powerTicks / 4) % 2 == 0) {
                    g2d.setColor(Color.RED);
                } else {
                    g2d.setColor(Color.WHITE);
                }
            } else {
                g2d.setColor(Color.CYAN);
            }

            g2d.drawString(String.format("POWER: %.1fs", secs), 10, 36);
        }

        // Score μέσα στη HUD μπάρα (δεξιά)
        g2d.drawString("Score: " + m.score, m.SCREEN_SIZE - 110, hudBaseline);
    }
}