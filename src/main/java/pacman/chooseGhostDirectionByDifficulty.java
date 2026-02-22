package pacman;

public class chooseGhostDirectionByDifficulty {

    public static void chooseGhostDirectionByDifficulty(model m,int i,int count,int[] candDx,int[] candDy) {
        double randomFactor;

        //  smart
        if (null == m.difficulty) {
            randomFactor = 0.30;   
        } else switch (m.difficulty) {
            case EASY:
                randomFactor = 0.60;   // EASY 60% random
                break;
            case NORMAL:
                randomFactor = 0.45;   // NORMAL 45% random
                break;
            default:
                randomFactor = 0.30;   // HARD 30% random
                break;
        }
        if (Math.random() < randomFactor) {
            int r = (int) (Math.random() * count);
            m.ghost_dx[i] = candDx[r];
            m.ghost_dy[i] = candDy[r];
            return;
        }
        
        m.chooseGhostDirectionSmart(i, count, candDx, candDy);
    }
}