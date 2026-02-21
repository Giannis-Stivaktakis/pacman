package pacman;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class showIntroScreen {

    public static void showIntroScreen(model m, Graphics2D g2d) {

        // Retro text
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                             RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

        // Dark overlay
        g2d.setColor(new Color(0, 0, 0, 200));
        g2d.fillRect(0, 0, m.SCREEN_SIZE, m.SCREEN_SIZE);

        int boxWidth  = 410;
        int boxHeight = 260;
        int boxX = (m.SCREEN_SIZE - boxWidth) / 2;
        int boxY = (m.SCREEN_SIZE - boxHeight) / 2;

        // Box background (σκούρο)
        g2d.setColor(new Color(10, 10, 10, 235));
        g2d.fillRect(boxX, boxY, boxWidth, boxHeight);

        // Retro border (διπλό)
        g2d.setColor(new Color(0, 255, 0));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(boxX, boxY, boxWidth, boxHeight);
        g2d.drawRect(boxX + 4, boxY + 4, boxWidth - 8, boxHeight - 8);

        // Title
        g2d.setFont(new Font("Dialog", Font.BOLD, 44));

        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                             RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

        String title = "PACMAN";
        FontMetrics fmTitle = g2d.getFontMetrics();
        int titleX = boxX + (boxWidth - fmTitle.stringWidth(title)) / 2;
        int titleY = boxY + 55;

        g2d.setColor(new Color(0, 255, 0));
        g2d.drawString(title, titleX, titleY);

        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // difficulty line
        g2d.setFont(new Font("Monospaced", Font.BOLD, 20));
        FontMetrics fmDiff = g2d.getFontMetrics();

        String diffLine = "DIFFICULTY: " + m.difficulty.name();
        int diffX = boxX + (boxWidth - fmDiff.stringWidth(diffLine)) / 2;
        int diffY = boxY + 70;
        g2d.drawString(diffLine, diffX, diffY);

        // Menu text (monospaced για retro alignment)
        Font menuFont = new Font("Monospaced", Font.BOLD, 16);
        g2d.setFont(menuFont);

        int textX = boxX + 40;
        int textY = boxY + 105;
        int line = 26;

        g2d.drawString("ESC    : MENU",               textX, textY);
        g2d.drawString("1/2/3  : SET DIFFICULTY",     textX, textY + line);
        g2d.drawString("ARROWS : MOVE",               textX, textY + line * 2);
        g2d.drawString("P      : PAUSE / RESUME",     textX, textY + line * 3);
        g2d.drawString("R      : RESTART (NEW GAME)", textX, textY + line * 4);
        g2d.drawString("E      : EXIT GAME",          textX, textY + line * 5);

        // Blinking "PRESS SPACE"
        if ((m.blinkCounter / 18) % 2 == 0) {
            g2d.setFont(new Font("Monospaced", Font.BOLD, 16));
            String press = "PRESS SPACE TO START";
            FontMetrics fmPress = g2d.getFontMetrics();
            int pressX = boxX + (boxWidth - fmPress.stringWidth(press)) / 2;
            int pressY = boxY + boxHeight - 10;
            g2d.drawString(press, pressX, pressY);
        }

        // Επαναφορά antialiasing
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    }
}