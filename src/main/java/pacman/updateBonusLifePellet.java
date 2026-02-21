package pacman;

public class updateBonusLifePellet {

    public static void updateBonusLifePellet(model m) {

        if (m.level != 2 && m.level != 3) return; // μόνο στα level 2,3

        if (m.bonusSpawnedThisLevel) {

            // αν είναι ήδη πάνω στον χάρτη, μέτρα lifetime
            if (m.bonusActiveTicks > 0) {
                m.bonusActiveTicks--;

                if (m.bonusActiveTicks == 0 && m.bonusPos >= 0) {

                    // ✅ FIX: Βάλε DOT πίσω ΜΟΝΟ αν το BONUS_LIFE είναι ακόμα πάνω στο tile
                    if ( (m.screenData[m.bonusPos] & model.BONUS_LIFE) != 0 ) {
                        m.screenData[m.bonusPos] =
                                (short) ((m.screenData[m.bonusPos] & ~model.BONUS_LIFE) | model.DOT);
                    }

                    // καθάρισμα θέσης bonus
                    m.bonusPos = -1;
                }
            }
            return;
        }

        // δεν έχει spawn-άρει ακόμα: μέτρα μέχρι να εμφανιστεί
        if (m.bonusSpawnDelayTicks > 0) {
            m.bonusSpawnDelayTicks--;
            return;
        }

        // ώρα να εμφανιστεί: διάλεξε τυχαία κελί που έχει DOT και δεν έχει POWER
        for (int tries = 0; tries < 200; tries++) {

            int pos = (int) (Math.random() * (m.N_BLOCKS * m.N_BLOCKS));
            short ch = m.screenData[pos];

            boolean hasDot = (ch & model.DOT) != 0;
            boolean hasPower = (ch & model.POWER) != 0;
            boolean hasBonusAlready = (ch & model.BONUS_LIFE) != 0;

            if (hasDot && !hasPower && !hasBonusAlready) {

                // Βγάζουμε το DOT για να μη "ξαναφανεί" όταν φαγωθεί το bonus
                m.screenData[pos] = (short) ((ch & ~model.DOT) | model.BONUS_LIFE);

                m.bonusPos = pos;
                m.bonusSpawnedThisLevel = true;
                m.bonusActiveTicks = model.BONUS_LIFETIME;
                break;
            }
        }
    }
}