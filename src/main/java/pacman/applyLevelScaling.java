package pacman;

public class applyLevelScaling {

    public static void applyLevelScaling(model m) {

        int baseSpeed = m.difficulty.speed;

        int ghosts;
        int speed;

        if (null == m.difficulty) {

            
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

        } else switch (m.difficulty) {

            case EASY:
                // EASY:ghosts — speed
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
                // NORMAL:ghosts — speed
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
                // HARD:ghosts — speed
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