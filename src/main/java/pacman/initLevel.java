package pacman;

public class initLevel {

    public static void initLevel(model m) {

        // 1) διάλεξε template ανάλογα με το level
        short[] baseTemplate;
        int[][] powerCells;

        switch (m.level) {
            case 1:
                baseTemplate = m.level1Data;
                powerCells = m.POWER_CELLS_L1;
                break;
            case 2:
                baseTemplate = m.level2Data;
                powerCells = m.POWER_CELLS_L2;
                break;
            default:
                baseTemplate = m.level3Data;
                powerCells = m.POWER_CELLS_L3;
                break;
        }

        // 2) φτιάξε working copy (ώστε να μην πειράξεις μόνιμα το template)
        m.levelDataWork = baseTemplate.clone();

        // 3) βάλε preset POWER pellets για το συγκεκριμένο level
        m.applyPowerPellets(m.levelDataWork, powerCells);

        // 4) φόρτωσε screenData από το working copy
        System.arraycopy(m.levelDataWork, 0, m.screenData, 0, m.N_BLOCKS * m.N_BLOCKS);

        // 5) reset power mode σε νέο level (προτείνεται)
        m.powerTicks = 0;
        m.ghostsEatenInPower = 0;

        // 6) Εφάρμοσε scaling για το τρέχον level (ghost count + speed)
        m.applyLevelScaling();

        // Reset bonus life state per level
        m.bonusSpawnedThisLevel = false;
        m.bonusPos = -1;
        m.bonusActiveTicks = 0;

        // levl2,3 bonus
        if (m.level == 2 || m.level == 3) {
            m.bonusSpawnDelayTicks = model.BONUS_MIN_DELAY
                    + (int) (Math.random() * (model.BONUS_MAX_DELAY - model.BONUS_MIN_DELAY + 1));
        } else {
            m.bonusSpawnDelayTicks = 0;
        }

        m.continueLevel();
    }
}