import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600, SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 75;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel() {
        this.random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        this.startGame();

    }

    public void startGame() {
        this.newApple();
        this.running = true;
        this.timer = new Timer(DELAY, this);
        this.timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.draw(g);
    }

    public void draw(Graphics g) {
        for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
            g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
            g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
        }
        g.setColor(Color.red);
        g.fillOval(this.appleX, this.appleY, UNIT_SIZE, UNIT_SIZE);

        for (int i = 0; i < this.bodyParts; i++) {
            if (i == 0) {
                g.setColor(Color.green);
                g.fillRect(this.x[i], this.y[i], UNIT_SIZE, UNIT_SIZE);
            } else {
                g.setColor(new Color(45, 180, 0));
                g.fillRect(this.x[i], this.y[i], UNIT_SIZE, UNIT_SIZE);
            }
        }
    }

    public void newApple() {
        this.appleX = this.random.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
        this.appleY = this.random.nextInt(SCREEN_HEIGHT / UNIT_SIZE)
                * UNIT_SIZE;
    }

    public void move() {
        for (int i = this.bodyParts; i > 0; i--) {
            this.x[i] = this.x[i - 1];
            this.y[i] = this.y[i - 1];

            switch (this.direction) {
                case 'U':
                    this.y[0] = this.y[0] - UNIT_SIZE;
                    break;
                case 'D':
                    this.y[0] = this.y[0] + UNIT_SIZE;
                    break;
                case 'L':
                    this.x[0] = this.x[0] - UNIT_SIZE;
                    break;
                case 'R':
                    this.x[0] = this.x[0] + UNIT_SIZE;
                    break;

            }
        }
    }

    public void checkApple() {

    }

    public void checkCollisions() {

    }

    public void gameOver(Graphics g) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {

        }
    }

}
