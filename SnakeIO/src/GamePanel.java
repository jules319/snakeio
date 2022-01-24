import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
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
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT)
            / (UNIT_SIZE * UNIT_SIZE);
    static final int DELAY = 505;
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
        
        if (this.running) {
            //Grid Lines (Left commented for later use):
//            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
//                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
//                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
//            }

            g.setColor(Color.red);
            g.fillOval(this.appleX, this.appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < this.bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(this.x[i], this.y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(45, 180, 0));
//                    g.setColor(new Color(this.random.nextInt(255),
//                            this.random.nextInt(255),
//                            this.random.nextInt(255)));
                    g.fillRect(this.x[i], this.y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            //Current Score
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics = this.getFontMetrics(g.getFont());
            g.drawString("Score: " + this.applesEaten,
                    (SCREEN_WIDTH
                            - metrics.stringWidth("Score: " + this.applesEaten))
                            / 2,
                    g.getFont().getSize());
        } else {
            this.gameOver(g);
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
        }

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

    public void checkApple() {
        if ((this.x[0] == this.appleX) && (this.y[0] == this.appleY)) {
            this.bodyParts++;
            this.applesEaten++;
            this.newApple();
        }
    }

    public void checkCollisions() {
        //checks if head collides with body
        for (int i = this.bodyParts; i > 0; i--) {
            if ((this.x[0] == this.x[i]) && (this.y[0] == this.y[i])) {
                this.running = false;
            }
        }
        //check if head touches left border
        if (this.x[0] < 0) {
            this.running = false;

        }
        //check if head touches right border
        if (this.x[0] > SCREEN_WIDTH) {
            this.running = false;
        }
        //check if head touches top border
        if (this.y[0] < 0) {
            this.running = false;
        }
        //check if head touches bottom border
        if (this.y[0] > SCREEN_HEIGHT) {
            this.running = false;
        }

        if (!this.running) {
            this.timer.stop();
        }

    }

    public void gameOver(Graphics g) {
        //Current Score
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics1 = this.getFontMetrics(g.getFont());
        g.drawString("Score: " + this.applesEaten,
                (SCREEN_WIDTH
                        - metrics1.stringWidth("Score: " + this.applesEaten))
                        / 2,
                g.getFont().getSize());
        //Game Over text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics2 = this.getFontMetrics(g.getFont());
        g.drawString("Game Over",
                (SCREEN_WIDTH - metrics2.stringWidth("Game Over")) / 2,
                SCREEN_HEIGHT / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (this.running) {
            this.move();
            this.checkApple();
            this.checkCollisions();
        }
        this.repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (GamePanel.this.direction != 'R') {
                        GamePanel.this.direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (GamePanel.this.direction != 'L') {
                        GamePanel.this.direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (GamePanel.this.direction != 'D') {
                        GamePanel.this.direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (GamePanel.this.direction != 'U') {
                        GamePanel.this.direction = 'D';
                    }
                    break;
            }
        }
    }

}
