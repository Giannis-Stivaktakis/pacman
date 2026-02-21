package pacman;

public class chooseGhostDirectionByDifficulty {

    public static void chooseGhostDirectionByDifficulty(model m,int i,int count,int[] candDx,int[] candDy) {
        double randomFactor;

        if (null == m.difficulty) {
            randomFactor = 0.30;   // HARD σχεδόν πάντα smart
        } else switch (m.difficulty) {
            case EASY:
                randomFactor = 0.60;   // 60% random
                break;
            case NORMAL:
                randomFactor = 0.45;   // 50% random
                break;
            default:
                randomFactor = 0.30;   // 30% random
                break;
        }
        if (Math.random() < randomFactor) {
            int r = (int) (Math.random() * count);
            m.ghost_dx[i] = candDx[r];
            m.ghost_dy[i] = candDy[r];
            return;
        }
        // αλλιώς πήγαινε smart
        m.chooseGhostDirectionSmart(i, count, candDx, candDy);
    }
}