package pacman;

public class initLevel {

    public static void initLevel(model m) {

        //template level
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

        //working copy (ώστε να μην πειράζω μόνιμα το template)
        m.levelDataWork = baseTemplate.clone();

        //POWER pellets for this level
        m.applyPowerPellets(m.levelDataWork, powerCells);

        // load screenData from working copy
        System.arraycopy(m.levelDataWork, 0, m.screenData, 0, m.N_BLOCKS * m.N_BLOCKS);

        // reset power mode to new level
        m.powerTicks = 0;
        m.ghostsEatenInPower = 0;

        //scaling level (ghost count + speed)
        m.applyLevelScaling();

        //Reset bonus life state per level
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