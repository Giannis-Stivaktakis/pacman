package pacman;

public class movePacman {

    public static void movePacman(model m) {

        int pos;
        short ch;

        if (m.pacman_x % m.BLOCK_SIZE == 0 && m.pacman_y % m.BLOCK_SIZE == 0) {
            pos = m.pacman_x / m.BLOCK_SIZE + m.N_BLOCKS * (int) (m.pacman_y / m.BLOCK_SIZE);
            ch = m.screenData[pos];

            if ((ch & model.DOT) != 0) {
                m.screenData[pos] = (short) (ch & ~model.DOT);
                m.score += 10; // πόντοι για απλό pellet
            }

            if ((ch & model.POWER) != 0) {
                m.screenData[pos] = (short) (ch & ~model.POWER);
                m.score += 50;               // πόντοι power pellet
                m.powerTicks = model.POWER_DURATION_TICKS; // ~7 sec
                m.ghostsEatenInPower = 0;    // reset multiplier
            }

            if ((ch & model.BONUS_LIFE) != 0) {
                m.screenData[pos] = (short) (ch & ~(model.BONUS_LIFE | model.DOT));

                if (m.lives < model.MAX_LIVES) {
                    m.lives++;
                }
                m.score += 200; // προαιρετικά bonus πόντοι
                m.bonusPos = -1;
                m.bonusActiveTicks = 0;
            }

            if (m.req_dx != 0 || m.req_dy != 0) {
                if (!((m.req_dx == -1 && m.req_dy == 0 && (ch & 1) != 0)
                        || (m.req_dx == 1 && m.req_dy == 0 && (ch & 4) != 0)
                        || (m.req_dx == 0 && m.req_dy == -1 && (ch & 2) != 0)
                        || (m.req_dx == 0 && m.req_dy == 1 && (ch & 8) != 0))) {
                    m.pacmand_x = m.req_dx;
                    m.pacmand_y = m.req_dy;
                }
            }

            // Check for standstill
            if ((m.pacmand_x == -1 && m.pacmand_y == 0 && (ch & 1) != 0)
                    || (m.pacmand_x == 1 && m.pacmand_y == 0 && (ch & 4) != 0)
                    || (m.pacmand_x == 0 && m.pacmand_y == -1 && (ch & 2) != 0)
                    || (m.pacmand_x == 0 && m.pacmand_y == 1 && (ch & 8) != 0)) {
                m.pacmand_x = 0;
                m.pacmand_y = 0;
            }
        }

        m.pacman_x = m.pacman_x + m.PACMAN_SPEED * m.pacmand_x;
        m.pacman_y = m.pacman_y + m.PACMAN_SPEED * m.pacmand_y;
    }
}