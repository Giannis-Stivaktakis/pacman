package pacman;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.awt.Color;

public class model extends JPanel implements ActionListener {
    enum GameState { INTRO, RUNNING, PAUSED, WIN, GAME_OVER }
    GameState state = GameState.INTRO;
    enum Difficulty {
    EASY(2, 1),    // Level 1: 2 ghosts
    NORMAL(3, 2),  // Level 1: 3 ghosts
    HARD(4, 3);    // Level 1: 4 ghosts

    final int ghostCount;
    final int speed;

    Difficulty(int ghostCount, int speed) {
        this.ghostCount = ghostCount;
        this.speed = speed;
    }
    }
    Difficulty difficulty = Difficulty.NORMAL;
    int level = 1;
    private static final int MAX_LEVELS = 3;      // άλλαξέ το αν θες
    static final int MAX_GHOSTS = 6;      // όριο για να μη ξεφύγει
    static final int MAX_SPEED  = 3;      // όριο για speed
    boolean difficultyChanged = false;
    int lives, score;
    private Dimension d;
    boolean hasStarted = false;
    int blinkCounter = 0;
    static final short DOT = 16;
    static final short POWER = 32;
    int powerTicks = 0;            // πόσα ticks μένουν (Timer=40ms)
    int ghostsEatenInPower = 0;    // για multiplier 200,400,800,1600
    boolean dying = false;
    final int BLOCK_SIZE = 24;
    final int N_BLOCKS = 15;
    final int SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;
    final int PACMAN_SPEED = 4;
    int N_GHOSTS = 6;
    int[] dx, dy;
    int[] ghost_x, ghost_y, ghost_dx, ghost_dy, ghostSpeed;
    Image heart, ghost;
    private Image up, down, left, right;
    int pacman_x, pacman_y, pacmand_x, pacmand_y;
    int req_dx, req_dy;
    boolean powerBlink = true;
    private int blinkTick = 0;
    static final int POWER_DURATION_TICKS = 175; // π.χ. 7s
    static final int GHOST_RESPAWN_DELAY_TICKS = 100; // 75*40ms = 3.0s
    int[] ghostRespawnTicks;
    short[] levelDataWork;
    static final int GHOST_SPAWN_GRACE_TICKS = 40; // 40*40ms = 1.6s
    int[] ghostGraceTicks;
    // Bonus extra-life pellet (red)
    static final short BONUS_LIFE = 64;
    boolean bonusSpawnedThisLevel = false;
    int bonusPos = -1;
    int bonusSpawnDelayTicks = 0;     // πότε θα εμφανιστεί (τυχαία καθυστέρηση)
    int bonusActiveTicks = 0;         // πόσο θα μένει στο χάρτη πριν εξαφανιστεί
    static final int BONUS_MIN_DELAY = 10 * 25;   // ~5 sec (25 ticks/sec με 40ms timer)
    static final int BONUS_MAX_DELAY = 25 * 25;  // ~15 sec
    static final int BONUS_LIFETIME  = 12 * 25;  // ~12 sec στο χάρτη
    static final int MAX_LIVES = 3;
    static final int LEVEL_TRANSITION_TICKS = 40; // 40 * 40ms ≈ 1.6s
    int levelTransitionTicks = 0;

    final int[][] POWER_CELLS_L1 = {
    {3, 1}, {10, 1}, {1, 13}, {13, 13}
    };

    final int[][] POWER_CELLS_L2 = {
    {1, 3}, {13, 3}, {1, 9}, {13, 9}
    };

    final int[][] POWER_CELLS_L3 = {
    {0,0}, {0,0}, {0,0}, {7,7}
    };

    final short level1Data[] = levelData.level1Data;
    final short level2Data[] = levelData.level2Data;
    final short level3Data[] = levelData.level3Data;
    final short allLevels[][] = levelData.allLevels;
    final int validSpeeds[] = {1, 2, 3,4};
    int currentSpeed = 3;
    short[] screenData;
    private Timer timer;
    

    public model() {
        loadImages();
        initVariables();
        addKeyListener(new TAdapter());
        setFocusable(true);
        initGame();
        requestFocusInWindow();
        validateLevels();
        level = 1;
        initLevel();
    }
    
    private void validateLevels() {
    int expected = N_BLOCKS * N_BLOCKS;

    if (level1Data.length != expected) throw new IllegalStateException("level1Data size=" + level1Data.length);
    if (level2Data.length != expected) throw new IllegalStateException("level2Data size=" + level2Data.length);
    if (level3Data.length != expected) throw new IllegalStateException("level3Data size=" + level3Data.length);
    }

    private Image loadImg(String path) {
    java.net.URL url = getClass().getResource(path);
    if (url == null) throw new RuntimeException("Missing resource: " + path);
    return new ImageIcon(url).getImage();
    }

    private void loadImages() {
        down  = loadImg("/pacman/down.gif");
        up    = loadImg("/pacman/up.gif");
        left  = loadImg("/pacman/left.gif");
        right = loadImg("/pacman/right.gif");
        ghost = loadImg("/pacman/ghost.gif");
        heart = loadImg("/pacman/heart.png");
    }

    private void initVariables() {
        screenData = new short[N_BLOCKS * N_BLOCKS];
        d = new Dimension(400, 400);
        ghost_x = new int[MAX_GHOSTS];
        ghostRespawnTicks = new int[MAX_GHOSTS];
        ghost_dx = new int[MAX_GHOSTS];
        ghost_y = new int[MAX_GHOSTS];
        ghost_dy = new int[MAX_GHOSTS];
        ghostSpeed = new int[MAX_GHOSTS];
        ghostGraceTicks = new int[MAX_GHOSTS];
        dx = new int[4];
        dy = new int[4];

        timer = new Timer(40, this);
        timer.start();
    }

    private void playGame(Graphics2D g2d) {

    // Level transition freeze + countdown
    if (levelTransitionTicks > 0) {
        levelTransitionTicks--;

        // draw overlay
        drawLevelTransitionOverlay(g2d);

        // new level
        if (levelTransitionTicks == 0) {
            applyLevelScaling();
            initLevel();
        }
        return; // 
    }

    if (dying) {
        death();
    } else {
        updateBonusLifePellet();
        movePacman();
        drawPacman(g2d);
        moveGhosts(g2d);
        checkMaze();
        if (powerTicks > 0) powerTicks--;
    }

    blinkTick++;
    if (blinkTick % 8 == 0) {
        powerBlink = !powerBlink;
    }
    }

    void showIntroScreen(Graphics2D g2d) {
    showIntroScreen.showIntroScreen(this, g2d);
    }

    private void showWinScreen(Graphics2D g2d) {
    drawEndScreen(g2d, "YOU WIN!");
}

    private void showGameOverScreen(Graphics2D g2d) {
    drawEndScreen(g2d, "GAME OVER");
}

    private void drawPauseOverlay(Graphics2D g2d) {
        g2d.setColor(new Color(0, 0, 0, 180));
        g2d.fillRect(0, 0, SCREEN_SIZE, SCREEN_SIZE);

        g2d.setColor(Color.WHITE);
        g2d.drawString("PAUSED - Press P to Resume", 
        SCREEN_SIZE / 2 - 100, 
        SCREEN_SIZE / 2);
    }
    
    void restartGame() {
        score = 0;
        lives = 3;
        initGame();
        hasStarted = true;
        state = GameState.RUNNING;
        repaint();
    }
    
    private void drawScore(Graphics2D g2d) {
    drawScore.drawScore(this, g2d);
    }
    
    private void checkMaze() {

    for (int i = 0; i < N_BLOCKS * N_BLOCKS; i++) {
        if ((screenData[i] & (DOT | POWER)) != 0) return;
    }

    // Level cleared
    score += 50;

    level++; 

    if (level > MAX_LEVELS) {
        state = GameState.WIN;
        return;
    }

    //transition για να φορτώσει το επόμενο level πιο ομαλά
    levelTransitionTicks = LEVEL_TRANSITION_TICKS;
    }

    private void death() {
    lives--;

    if (lives == 0) {
        state = GameState.GAME_OVER;
        return;
    }
    continueLevel();
}
    
    private void moveGhosts(Graphics2D g2d) {
    moveGhosts.moveGhosts(this, g2d);
}
    
    void chooseGhostDirectionByDifficulty(int i, int count, int[] candDx, int[] candDy) {
    chooseGhostDirectionByDifficulty.chooseGhostDirectionByDifficulty(this, i, count, candDx, candDy);
    }
    
    void drawGhost(Graphics2D g2d, int x, int y, int i) {
    drawGhost.drawGhost(this, g2d, x, y, i);
    }

    private void movePacman() {
    movePacman.movePacman(this);
    }

    private void drawPacman(Graphics2D g2d) {

        if (req_dx == -1) {
            g2d.drawImage(left, pacman_x + 1, pacman_y + 1, this);
        } else if (req_dx == 1) {
            g2d.drawImage(right, pacman_x + 1, pacman_y + 1, this);
        } else if (req_dy == -1) {
            g2d.drawImage(up, pacman_x + 1, pacman_y + 1, this);
        } else {
            g2d.drawImage(down, pacman_x + 1, pacman_y + 1, this);
        }
    }

    private void drawMaze(Graphics2D g2d) {
    drawMaze.drawMaze(this, g2d);
    }
    
    private void initGame() {
        System.out.println("INIT LEVEL -> level=" + level);

        level = 1;
        applyLevelScaling();
        lives = 3;
        score = 0;
        initLevel();
        hasStarted = true;
        System.out.println("TEMPLATE INDEX -> " + (Math.max(1, Math.min(level, allLevels.length)) - 1));
        for (int i = 0; i < N_GHOSTS; i++) {
            ghostRespawnTicks[i] = 0;
        }
    }   

    private void initLevel() {
    initLevel.initLevel(this);
    }

    void continueLevel() {
    continueLevel.continueLevel(this);
    }
    
    void applyLevelScaling() {
    applyLevelScaling.applyLevelScaling(this);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, d.width, d.height);

        drawMaze(g2d);
        drawScore(g2d);

        switch (state) {
    case RUNNING:
        playGame(g2d);
        break;
    case PAUSED:
        drawPauseOverlay(g2d);
        break;
    case WIN:
        showWinScreen(g2d);
        break;
    case GAME_OVER:
        showGameOverScreen(g2d);
        break;
    default:
        showIntroScreen(g2d);
}
        Toolkit.getDefaultToolkit().sync();
    }
      
    //controls
    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            keyPressed.keyPressed(model.this, e);
        }
    }
    
    private void drawEndScreen(Graphics2D g2d, String title) {
    drawEndScreen.drawEndScreen(this, g2d, title);
    }
    
    void applyPowerPellets(short[] template, int[][] cells) {
    for (int[] c : cells) {
        int x = c[0], y = c[1];
        int pos = x + N_BLOCKS * y;

        // μόνο αν είναι διάδρομος (όχι τοίχος)
        if ( (template[pos] & 15) == 0 ) {
            // βγάλε DOT αν υπάρχει και βάλε POWER
            template[pos] = (short)((template[pos] & ~DOT) | POWER);
        }
    }
}
    int dist2(int ax, int ay, int bx, int by) {
    int dx = ax - bx, dy = ay - by;
    return dx * dx + dy * dy;
}

    int clampTile(int t) {
    if (t < 0) return 0;
    if (t > N_BLOCKS - 1) return N_BLOCKS - 1;
    return t;
}

    void chooseGhostDirectionSmart(int i, int count, int[] candDx, int[] candDy) {
    chooseGhostDirectionSmart.chooseGhostDirectionSmart(this, i, count, candDx, candDy);
}
    
    private void drawLevelTransitionOverlay(Graphics2D g2d) {

    g2d.setColor(new Color(0, 0, 0, 180));
    g2d.fillRect(0, 0, SCREEN_SIZE, SCREEN_SIZE);

    g2d.setColor(Color.GREEN);
    g2d.setFont(new Font("Monospaced", Font.BOLD, 28));

    String msg = "LEVEL " + level;
    FontMetrics fm = g2d.getFontMetrics();
    int x = (SCREEN_SIZE - fm.stringWidth(msg)) / 2;
    int y = SCREEN_SIZE / 2;

    g2d.drawString(msg, x, y);
    }

    private void updateBonusLifePellet() {
    updateBonusLifePellet.updateBonusLifePellet(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        if (state == GameState.RUNNING) {
            blinkCounter++;
            
            repaint();
        } else {
            blinkCounter++;
        repaint();
        }
    }
}