package pacman;

import java.awt.Graphics2D;

public class moveGhosts {

    public static void moveGhosts(model m, Graphics2D g2d) {

        int pos;
        int count;

        for (int i = 0; i < m.N_GHOSTS; i++) {

            // =========================
            // 1) Respawn delay handling
            // =========================
            if (m.ghostRespawnTicks[i] > 0) {
                m.ghostRespawnTicks[i]--;

                // όταν τελειώσει ο χρόνος, κάνε respawn + δώσε grace
                if (m.ghostRespawnTicks[i] == 0) {
                    m.ghost_x[i] = 7 * m.BLOCK_SIZE;
                    m.ghost_y[i] = 5 * m.BLOCK_SIZE;
                    m.ghost_dx[i] = 1;
                    m.ghost_dy[i] = 0;

                    m.ghostGraceTicks[i] = model.GHOST_SPAWN_GRACE_TICKS;
                }

                // όσο είναι "εκτός", δεν κινείται/δεν ζωγραφίζεται/δεν συγκρούεται
                continue;
            }

            // =========================
            // 2) Spawn grace countdown
            // =========================
            if (m.ghostGraceTicks[i] > 0) {
                m.ghostGraceTicks[i]--;
            }

            // =========================
            // 3) Choose direction on grid intersections
            // =========================
            if (m.ghost_x[i] % m.BLOCK_SIZE == 0 && m.ghost_y[i] % m.BLOCK_SIZE == 0) {

                pos = m.ghost_x[i] / m.BLOCK_SIZE + m.N_BLOCKS * (m.ghost_y[i] / m.BLOCK_SIZE);

                count = 0;

                if ((m.screenData[pos] & 1) == 0 && m.ghost_dx[i] != 1) {
                    m.dx[count] = -1;
                    m.dy[count] = 0;
                    count++;
                }

                if ((m.screenData[pos] & 2) == 0 && m.ghost_dy[i] != 1) {
                    m.dx[count] = 0;
                    m.dy[count] = -1;
                    count++;
                }

                if ((m.screenData[pos] & 4) == 0 && m.ghost_dx[i] != -1) {
                    m.dx[count] = 1;
                    m.dy[count] = 0;
                    count++;
                }

                if ((m.screenData[pos] & 8) == 0 && m.ghost_dy[i] != -1) {
                    m.dx[count] = 0;
                    m.dy[count] = 1;
                    count++;
                }

                if (count == 0) {
                    if ((m.screenData[pos] & 15) == 15) {
                        m.ghost_dx[i] = 0;
                        m.ghost_dy[i] = 0;
                    } else {
                        m.ghost_dx[i] = -m.ghost_dx[i];
                        m.ghost_dy[i] = -m.ghost_dy[i];
                    }
                } else {
                    // ✅ SMART choice (replaces random)
                    m.chooseGhostDirectionByDifficulty(i, count, m.dx, m.dy);
                }
            }

            // =========================
            // 4) Move + draw
            // =========================
            m.ghost_x[i] = m.ghost_x[i] + (m.ghost_dx[i] * m.ghostSpeed[i]);
            m.ghost_y[i] = m.ghost_y[i] + (m.ghost_dy[i] * m.ghostSpeed[i]);

            m.drawGhost(g2d, m.ghost_x[i] + 1, m.ghost_y[i] + 1, i);

            // =========================
            // 5) Collision with Pacman
            // =========================
            if (m.pacman_x > (m.ghost_x[i] - 12) && m.pacman_x < (m.ghost_x[i] + 12)
                    && m.pacman_y > (m.ghost_y[i] - 12) && m.pacman_y < (m.ghost_y[i] + 12)
                    && m.state == model.GameState.RUNNING) {

                // ✅ anti spawn-kill + anti exploit
                if (m.ghostGraceTicks[i] > 0) {
                    continue;
                }

                if (m.powerTicks > 0) {
                    int bonus = 200 * (1 << m.ghostsEatenInPower);
                    m.score += bonus;
                    m.ghostsEatenInPower = Math.min(3, m.ghostsEatenInPower + 1);

                    m.ghostRespawnTicks[i] = model.GHOST_RESPAWN_DELAY_TICKS;
                } else {
                    m.dying = true;
                }
            }
        }
    }
}