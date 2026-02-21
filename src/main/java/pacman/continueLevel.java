package pacman;

public class continueLevel {

    public static void continueLevel(model m) {

        int dx = 1;
        int random;

        for (int i = 0; i < m.N_GHOSTS; i++) {

            m.ghost_y[i] = 5 * m.BLOCK_SIZE; // start position
            m.ghost_x[i] = 7 * m.BLOCK_SIZE;
            m.ghost_dy[i] = 0;
            m.ghost_dx[i] = dx;
            dx = -dx;

            int maxIndex = Math.min(m.currentSpeed, m.validSpeeds.length - 1);
            random = (int) (Math.random() * (maxIndex + 1));
            m.ghostSpeed[i] = m.validSpeeds[random];
        }

        m.pacman_x = 7 * m.BLOCK_SIZE;  // start position
        m.pacman_y = 11 * m.BLOCK_SIZE;
        m.pacmand_x = 0;    // reset direction move
        m.pacmand_y = 0;
        m.req_dx = 0;        // reset direction controls
        m.req_dy = 0;
        m.dying = false;
    }
}