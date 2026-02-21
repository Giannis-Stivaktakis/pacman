package pacman;

import java.awt.event.KeyEvent;

public class keyPressed {

    public static void keyPressed(model m, KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_E) {
            System.exit(0);
        }

        /* =========================
           1. PAUSE / RESUME (P)
           ========================= */
        if (key == KeyEvent.VK_P) {
            if (m.state == model.GameState.RUNNING) {
                m.state = model.GameState.PAUSED;
            } else if (m.state == model.GameState.PAUSED) {
                m.state = model.GameState.RUNNING;
            }
            m.repaint();
            return;
        }

        /* =========================
           2. ESC -> MENU (χωρίς reset)
           ========================= */
        if (key == KeyEvent.VK_ESCAPE) {
            m.state = model.GameState.INTRO;
            m.repaint();
            return;
        }

        /* =========================
           3. INTRO SCREEN CONTROLS
           ========================= */
        if (m.state == model.GameState.INTRO) {

            if (key == KeyEvent.VK_1) { m.difficulty = model.Difficulty.EASY;   m.difficultyChanged = true; m.repaint(); }
            if (key == KeyEvent.VK_2) { m.difficulty = model.Difficulty.NORMAL; m.difficultyChanged = true; m.repaint(); }
            if (key == KeyEvent.VK_3) { m.difficulty = model.Difficulty.HARD;   m.difficultyChanged = true; m.repaint(); }

            // SPACE = Continue / Start
            if (key == KeyEvent.VK_SPACE) {

                // Αν δεν έχει ξεκινήσει ποτέ ή αν άλλαξες difficulty -> ξεκινά νέο game
                if (!m.hasStarted || m.difficultyChanged) {
                    m.restartGame(); // κάνει reset + initGame + state RUNNING
                    m.difficultyChanged = false;
                    return;
                }

                // Αλλιώς είναι Continue
                m.state = model.GameState.RUNNING;
                m.repaint();
                return;
            }

            // R = Restart (πάντα reset)
            if (key == KeyEvent.VK_R) {
                m.restartGame();
                return;
            }

            // E = Exit
            if (key == KeyEvent.VK_E) {
                System.exit(0);
            }
            return;
        }

        /* =========================
           4. GLOBAL RESTART (μέσα στο παιχνίδι)
           ========================= */
        if (key == KeyEvent.VK_R) {
            m.restartGame();
            return;
        }

        /* =========================
           5. MOVEMENT (μόνο όταν RUNNING)
           ========================= */
        if (m.state != model.GameState.RUNNING) {
            return;
        }

        switch (key) {
            case KeyEvent.VK_LEFT:
                m.req_dx = -1;
                m.req_dy = 0;
                break;
            case KeyEvent.VK_RIGHT:
                m.req_dx = 1;
                m.req_dy = 0;
                break;
            case KeyEvent.VK_UP:
                m.req_dx = 0;
                m.req_dy = -1;
                break;
            case KeyEvent.VK_DOWN:
                m.req_dx = 0;
                m.req_dy = 1;
                break;
            default:
                break;
        }
    }
}
