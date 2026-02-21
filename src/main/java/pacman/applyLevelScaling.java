package pacman;

public class applyLevelScaling {

    public static void applyLevelScaling(model m) {

        int baseSpeed = m.difficulty.speed;

        int ghosts;
        int speed;

        if (null == m.difficulty) {

            // HARD: 4,5,5 ghosts — speed +1 κάθε level
            switch (m.level) {
                case 1:
                    ghosts = 4;
                    break;
                case 2:
                    ghosts = 5;
                    break;
                default:
                    ghosts = 5;
                    break;
            }

            speed = baseSpeed + (m.level - 1); // HARD: 3,4,5... (θα clamp-άρει)

        } else switch (m.difficulty) {

            case EASY:
                // EASY: 2,3,3 ghosts — speed σταθερό
                switch (m.level) {
                    case 1:
                        ghosts = 2;
                        break;
                    case 2:
                        ghosts = 3;
                        break;
                    default:
                        ghosts = 3;
                        break;
                }
                speed = baseSpeed;
                break;

            case NORMAL:
                // NORMAL: 3,4,4 ghosts — speed +1 μόνο στο level 3+
                switch (m.level) {
                    case 1:
                        ghosts = 3;
                        break;
                    case 2:
                        ghosts = 4;
                        break;
                    default:
                        ghosts = 4;
                        break;
                }
                speed = baseSpeed + (m.level >= 3 ? 1 : 0);
                break;

            default:
                // HARD: 4,5,5 ghosts — speed +1 κάθε level
                switch (m.level) {
                    case 1:
                        ghosts = 4;
                        break;
                    case 2:
                        ghosts = 5;
                        break;
                    default:
                        ghosts = 5;
                        break;
                }
                speed = baseSpeed + (m.level - 1);
                break;
        }

        m.N_GHOSTS = Math.min(model.MAX_GHOSTS, ghosts);
        m.currentSpeed = Math.min(model.MAX_SPEED, speed);
    }
}