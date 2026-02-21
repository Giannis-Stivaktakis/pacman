package pacman;

public class chooseGhostDirectionSmart {

    public static void chooseGhostDirectionSmart(model m,
                                                 int i,
                                                 int count,
                                                 int[] candDx,
                                                 int[] candDy) {

        // current tile of ghost
        int gx = m.ghost_x[i] / m.BLOCK_SIZE;
        int gy = m.ghost_y[i] / m.BLOCK_SIZE;

        // pacman tile
        int px = m.pacman_x / m.BLOCK_SIZE;
        int py = m.pacman_y / m.BLOCK_SIZE;

        // pacman direction
        int pdirx = m.pacmand_x;
        int pdiry = m.pacmand_y;

        // default target: pacman
        int tx = px, ty = py;

        if (i == 1) { // ambush 4 tiles ahead
            tx = m.clampTile(px + 4 * pdirx);
            ty = m.clampTile(py + 4 * pdiry);

        } else if (i == 2) { // intercept 8 tiles ahead
            tx = m.clampTile(px + 8 * pdirx);
            ty = m.clampTile(py + 8 * pdiry);

        } else if (i >= 3) { // shy: if close, run to corner, else chase
            int d = m.dist2(gx, gy, px, py);
            if (d < 25) { // within 5 tiles
                tx = 1;
                ty = m.N_BLOCKS - 2;
            }
        }

        // frightened: move away
        boolean frightened = (m.powerTicks > 0);

        int best = 0;
        int bestScore = frightened ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int k = 0; k < count; k++) {

            int ndx = candDx[k], ndy = candDy[k];

            int nx = gx + ndx;
            int ny = gy + ndy;

            int score = m.dist2(nx, ny, tx, ty);

            if (frightened) {
                if (score > bestScore) { bestScore = score; best = k; }
            } else {
                if (score < bestScore) { bestScore = score; best = k; }
            }
        }

        m.ghost_dx[i] = candDx[best];
        m.ghost_dy[i] = candDy[best];
    }
}