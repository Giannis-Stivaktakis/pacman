package pacman;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class drawEndScreen {

    public static void drawEndScreen(model m, Graphics2D g2d, String title) {

        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                             RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

        // Overlay
        g2d.setColor(new Color(0, 0, 0, 210));
        g2d.fillRect(0, 0, m.SCREEN_SIZE, m.SCREEN_SIZE);

        int boxWidth  = 380;
        int boxHeight = 240;

        int boxX = (m.SCREEN_SIZE - boxWidth) / 2;
        int boxY = (m.SCREEN_SIZE - boxHeight) / 2;

        // Box background
        g2d.setColor(new Color(10, 10, 10, 235));
        g2d.fillRect(boxX, boxY, boxWidth, boxHeight);

        // Double border
        g2d.setColor(Color.GREEN);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(boxX, boxY, boxWidth, boxHeight);
        g2d.drawRect(boxX + 4, boxY + 4, boxWidth - 8, boxHeight - 8);

        // ===== TITLE =====
        g2d.setFont(new Font("Monospaced", Font.BOLD, 28));
        FontMetrics fmTitle = g2d.getFontMetrics();

        int titleX = boxX + (boxWidth - fmTitle.stringWidth(title)) / 2;
        int titleY = boxY + 55;

        g2d.setColor(new Color(0, 150, 0));
        g2d.drawString(title, titleX - 1, titleY);
        g2d.drawString(title, titleX + 1, titleY);
        g2d.drawString(title, titleX, titleY - 1);
        g2d.drawString(title, titleX, titleY + 1);

        g2d.setColor(Color.GREEN);
        g2d.drawString(title, titleX, titleY);

        // ===== SCORE =====
        g2d.setFont(new Font("Monospaced", Font.BOLD, 16));
        FontMetrics fm = g2d.getFontMetrics();

        String scoreLine = "SCORE: " + m.score;
        int scoreX = boxX + (boxWidth - fm.stringWidth(scoreLine)) / 2;
        int scoreY = boxY + 95;
        g2d.drawString(scoreLine, scoreX, scoreY);

        // ===== BLINKING OPTIONS (ΚΑΘΕΤΑ) =====
        if ((m.blinkCounter / 18) % 2 == 0) {

            int lineSpacing = 28;
            int startY = boxY + 130;

            String line1 = "R - RESTART";
            String line2 = "ESC - MENU";
            String line3 = "E - EXIT";

            int x1 = boxX + (boxWidth - fm.stringWidth(line1)) / 2;
            int x2 = boxX + (boxWidth - fm.stringWidth(line2)) / 2;
            int x3 = boxX + (boxWidth - fm.stringWidth(line3)) / 2;

            g2d.drawString(line1, x1, startY);
            g2d.drawString(line2, x2, startY + lineSpacing);
            g2d.drawString(line3, x3, startY + lineSpacing * 2);
        }

        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    }
}