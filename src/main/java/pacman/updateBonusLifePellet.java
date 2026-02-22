package pacman;

public class updateBonusLifePellet {

    public static void updateBonusLifePellet(model m) {

        if (m.level != 2 && m.level != 3) return; // only level 2,3

        if (m.bonusSpawnedThisLevel) {

            
            if (m.bonusActiveTicks > 0) {
                m.bonusActiveTicks--;

                if (m.bonusActiveTicks == 0 && m.bonusPos >= 0) {

                    //Fix DOT
                    if ( (m.screenData[m.bonusPos] & model.BONUS_LIFE) != 0 ) {
                        m.screenData[m.bonusPos] =
                                (short) ((m.screenData[m.bonusPos] & ~model.BONUS_LIFE) | model.DOT);
                    }

                    // clear 
                    m.bonusPos = -1;
                }
            }
            return;
        }

        // not spawn yet
        if (m.bonusSpawnDelayTicks > 0) {
            m.bonusSpawnDelayTicks--;
            return;
        }

        // time to spawn, choose random cell without DOT
        for (int tries = 0; tries < 200; tries++) {

            int pos = (int) (Math.random() * (m.N_BLOCKS * m.N_BLOCKS));
            short ch = m.screenData[pos];

            boolean hasDot = (ch & model.DOT) != 0;
            boolean hasPower = (ch & model.POWER) != 0;
            boolean hasBonusAlready = (ch & model.BONUS_LIFE) != 0;

            if (hasDot && !hasPower && !hasBonusAlready) {

                // remove DOT
                m.screenData[pos] = (short) ((ch & ~model.DOT) | model.BONUS_LIFE);

                m.bonusPos = pos;
                m.bonusSpawnedThisLevel = true;
                m.bonusActiveTicks = model.BONUS_LIFETIME;
                break;
            }
        }
    }
}