import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    private final int GRID_SIZE = 20; // Grid size (20x20)
    private final int TILE_SIZE = 25; // Size of each tile
    private final int WIDTH = GRID_SIZE * TILE_SIZE;
    private final int HEIGHT = GRID_SIZE * TILE_SIZE;

    private final int[][] snake = new int[GRID_SIZE * GRID_SIZE][2]; // Snake body positions
    private int snakeLength = 3; // Initial length of snake
    private int foodX, foodY; // Food position
    private char direction = 'R'; // Initial direction (Right)
    private boolean running = false;
    private Timer timer;

    public SnakeGame() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        startGame();
    }

    public void startGame() {
        running = true;
        snakeLength = 3;
        direction = 'R';

        // Initialize snake in the middle
        for (int i = 0; i < snakeLength; i++) {
            snake[i][0] = GRID_SIZE / 2;
            snake[i][1] = (GRID_SIZE / 2) - i;
        }

        placeFood();
        timer = new Timer(100, this); // Speed: 100ms per frame
        timer.start();
    }

    public void placeFood() {
        foodX = (int) (Math.random() * GRID_SIZE);
        foodY = (int) (Math.random() * GRID_SIZE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (running) {
            // Draw food
            g.setColor(Color.RED);
            g.fillOval(foodX * TILE_SIZE, foodY * TILE_SIZE, TILE_SIZE, TILE_SIZE);

            // Draw snake
            for (int i = 0; i < snakeLength; i++) {
                g.setColor(i == 0 ? Color.GREEN : Color.WHITE);
                g.fillRect(snake[i][0] * TILE_SIZE, snake[i][1] * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        } else {
            gameOver(g);
        }
    }

    public void gameOver(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Game Over!", WIDTH / 3, HEIGHT / 2);
    }

    public void move() {
        for (int i = snakeLength - 1; i > 0; i--) {
            snake[i][0] = snake[i - 1][0];
            snake[i][1] = snake[i - 1][1];
        }

        switch (direction) {
            case 'U':
                snake[0][1]--;
                break;
            case 'D':
                snake[0][1]++;
                break;
            case 'L':
                snake[0][0]--;
                break;
            case 'R':
                snake[0][0]++;
                break;
        }

        checkCollision();
        checkFood();
    }

    public void checkFood() {
        if (snake[0][0] == foodX && snake[0][1] == foodY) {
            snakeLength++;
            placeFood();
        }
    }

    public void checkCollision() {
        if (snake[0][0] < 0 || snake[0][0] >= GRID_SIZE || snake[0][1] < 0 || snake[0][1] >= GRID_SIZE) {
            running = false;
        }

        for (int i = 1; i < snakeLength; i++) {
            if (snake[0][0] == snake[i][0] && snake[0][1] == snake[i][1]) {
                running = false;
            }
        }

        if (!running) {
            timer.stop();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                if (direction != 'D')
                    direction = 'U';
                break;
            case KeyEvent.VK_DOWN:
                if (direction != 'U')
                    direction = 'D';
                break;
            case KeyEvent.VK_LEFT:
                if (direction != 'R')
                    direction = 'L';
                break;
            case KeyEvent.VK_RIGHT:
                if (direction != 'L')
                    direction = 'R';
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        SnakeGame game = new SnakeGame();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
