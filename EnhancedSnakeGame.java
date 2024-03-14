// import javax.swing.*;
// import javax.swing.Timer;
// import java.awt.*;
// import java.awt.event.*;
// import java.util.*;

// public class EnhancedSnakeGame extends JPanel implements KeyListener, ActionListener {
//     private final int BOX_SIZE = 20;
//     private final int WIDTH = 20;
//     private final int HEIGHT = 20;
//     private ArrayList<Point> snake;
//     private Point food;
//     private Random random = new Random();
//     private Timer timer;
//     private int snakeDirection;
//     private int score;
//     private int level;
//     private int obstacleDensity; // Number of obstacles
//     private ArrayList<Point> obstacles;
//     private int obstacleSize; // Size of obstacles
//     private boolean levelCompleted;

//     public EnhancedSnakeGame() {
//         setPreferredSize(new Dimension(WIDTH * BOX_SIZE, HEIGHT * BOX_SIZE));
//         setMinimumSize(new Dimension(WIDTH * BOX_SIZE, HEIGHT * BOX_SIZE));
//         setBackground(Color.black);
//         addKeyListener(this);
//         setFocusable(true);
//         snake = new ArrayList<>();
//         snake.add(new Point(10, 10));  
//         obstacles = new ArrayList<>(); // Initialize obstacles ArrayList
//         generateObstacles(); // Generate obstacles
//         spawnFood();
//         timer = new Timer(200, this);
//         timer.start();
//         snakeDirection = KeyEvent.VK_RIGHT;
//         score = 0;
//         level = 1;
//         obstacleDensity = 5; // Initial obstacle density
//         obstacleSize = 10; // Initial obstacle size
//         levelCompleted = false;
//     }
    
//     public void actionPerformed(ActionEvent e) {
//         // Move and update game components...

//         // Check if level completed
//         if (score > 0 && score % 10 == 0 && !levelCompleted) {
//             levelCompleted = true;
//             JOptionPane.showMessageDialog(this, "Congratulations! Level Completed. You can proceed to the next level.", "Level Completed", JOptionPane.INFORMATION_MESSAGE);
//             // Additional actions for level completion (if any)
//         }

//         repaint();
//     }
//     public void paintComponent(Graphics g) {
//         super.paintComponent(g);

//         // Draw obstacles
//         for (Point obstacle : obstacles) {
//             g.setColor(Color.gray);
//             g.fillRect(obstacle.x * BOX_SIZE, obstacle.y * BOX_SIZE, BOX_SIZE, BOX_SIZE);
//         }

//         // Draw snake
//         for (Point segment : snake) {
//             g.setColor(Color.green);
//             g.fillRect(segment.x * BOX_SIZE, segment.y * BOX_SIZE, BOX_SIZE, BOX_SIZE);
//         }

//         // Draw food
//         g.setColor(Color.red);
//         g.fillRect(food.x * BOX_SIZE, food.y * BOX_SIZE, BOX_SIZE, BOX_SIZE);

//         // Draw score and level
//         g.setColor(Color.white);
//         g.drawString("Score: " + score, 10, 20);
//         g.drawString("Level: " + level, 10, 40);
//     }
//     public void keyPressed(KeyEvent e) {
//         int key = e.getKeyCode();
//         if (key == KeyEvent.VK_UP && snakeDirection != KeyEvent.VK_DOWN) {
//             snakeDirection = KeyEvent.VK_UP;
//         } else if (key == KeyEvent.VK_DOWN && snakeDirection != KeyEvent.VK_UP) {
//             snakeDirection = KeyEvent.VK_DOWN;
//         } else if (key == KeyEvent.VK_LEFT && snakeDirection != KeyEvent.VK_RIGHT) {
//             snakeDirection = KeyEvent.VK_LEFT;
//         } else if (key == KeyEvent.VK_RIGHT && snakeDirection != KeyEvent.VK_LEFT) {
//             snakeDirection = KeyEvent.VK_RIGHT;
//         }
//     }

//     public void keyReleased(KeyEvent e) {}
//     public void keyTyped(KeyEvent e) {}

//     public void move() {
//         Point head = snake.get(0);
//         Point newHead = (Point) head.clone();

//         switch (snakeDirection) {
//             case KeyEvent.VK_UP:
//                 newHead.y--;
//                 break;
//             case KeyEvent.VK_DOWN:
//                 newHead.y++;
//                 break;
//             case KeyEvent.VK_LEFT:
//                 newHead.x--;
//                 break;
//             case KeyEvent.VK_RIGHT:
//                 newHead.x++;
//                 break;
//         }

//         snake.add(0, newHead);

//         // Check if snake eats food
//         if (newHead.equals(food)) {
//             score++;
//             spawnFood();

//             // Increase level every 5 food items
//             if (score % 5 == 0) {
//                 level++;
//                 obstacleDensity++; // Increase obstacle density with level
//                 obstacleSize += 5; // Increase obstacle size with level
//                 generateObstacles();
//             }
//         } else {
//             snake.remove(snake.size() - 1);
//         }
//     }

//     public void generateObstacles() {
//         obstacles.clear();
//         for (int i = 0; i < obstacleDensity; i++) {
//             int x = random.nextInt(WIDTH);
//             int y = random.nextInt(HEIGHT);
//             obstacles.add(new Point(x, y));
//         }
//     }

//     public void spawnFood() {
//         int x = random.nextInt(WIDTH);
//         int y = random.nextInt(HEIGHT);

//         // Ensure food does not spawn on the snake's body or obstacles
//         while (snake.contains(new Point(x, y)) || obstacles.contains(new Point(x, y))) {
//             x = random.nextInt(WIDTH);
//             y = random.nextInt(HEIGHT);
//         }

//         food = new Point(x, y);
//     }

//     public void checkCollision() {
//         Point head = snake.get(0);

//         // Check collision with walls
//         if (head.x < 0 || head.x >= WIDTH || head.y < 0 || head.y >= HEIGHT) {
//             gameOver();
//         }

//         // Check collision with itself
//         for (int i = 1; i < snake.size(); i++) {
//             if (head.equals(snake.get(i))) {
//                 gameOver();
//             }
//         }

//         // Check collision with obstacles
//         if (obstacles.contains(head)) {
//             gameOver();
//         }
//     }

//     public void gameOver() {
//         timer.stop();
//         JOptionPane.showMessageDialog(this, "Game Over! Your Score: " + score + " - Level: " + level, "Game Over", JOptionPane.PLAIN_MESSAGE);
//         resetGame();
//     }

//     public void resetGame() {
//         snake.clear();
//         snake.add(new Point(10, 10));
//         spawnFood();
//         score = 0;
//         level = 1;
//         obstacleDensity = 5; // Reset obstacle density
//         obstacleSize = 10; // Reset obstacle size
//         generateObstacles();
//         snakeDirection = KeyEvent.VK_RIGHT;
//         timer.start();
//     }

//     public static void main(String[] args) {
//         JFrame frame = new JFrame("Enhanced Snake Game");
//         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         frame.setResizable(false);
//         frame.add(new EnhancedSnakeGame());
//         frame.pack();
//         frame.setLocationRelativeTo(null);
//         frame.setVisible(true);
//     }
// }
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class EnhancedSnakeGame extends JPanel implements ActionListener {
    private final int BOX_SIZE = 20;
    private final int WIDTH = 20;
    private final int HEIGHT = 20;
    private ArrayList<Point> snake;
    private Point food;
    private Random random = new Random();
    private Timer timer;
    private int snakeDirection;
    private int score;
    private int level;
    private int obstacleDensity; // Number of obstacles
    private ArrayList<Point> obstacles;
    private int obstacleSize; // Size of obstacles
    private boolean levelCompleted;

    public EnhancedSnakeGame() {
        setPreferredSize(new Dimension(WIDTH * BOX_SIZE, HEIGHT * BOX_SIZE));
        setMinimumSize(new Dimension(WIDTH * BOX_SIZE, HEIGHT * BOX_SIZE));
        setBackground(Color.black);
        setFocusable(true);

        // Set up key bindings
        InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "up");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "down");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "left");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "right");

        actionMap.put("up", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                changeDirection(KeyEvent.VK_UP);
            }
        });
        actionMap.put("down", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                changeDirection(KeyEvent.VK_DOWN);
            }
        });
        actionMap.put("left", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                changeDirection(KeyEvent.VK_LEFT);
            }
        });
        actionMap.put("right", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                changeDirection(KeyEvent.VK_RIGHT);
            }
        });

        snake = new ArrayList<>();
        snake.add(new Point(10, 10));
        obstacles = new ArrayList<>(); // Initialize obstacles ArrayList
        generateObstacles(); // Generate obstacles
        spawnFood();

        timer = new Timer(200, this);
        timer.start();

        snakeDirection = KeyEvent.VK_RIGHT;
        score = 0;
        level = 1;
        obstacleDensity = 5; // Initial obstacle density
        obstacleSize = 10; // Initial obstacle size
        levelCompleted = false;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw obstacles
        for (Point obstacle : obstacles) {
            g.setColor(Color.gray);
            g.fillRect(obstacle.x * BOX_SIZE, obstacle.y * BOX_SIZE, BOX_SIZE, BOX_SIZE);
        }

        // Draw snake
        for (Point segment : snake) {
            g.setColor(Color.green);
            g.fillRect(segment.x * BOX_SIZE, segment.y * BOX_SIZE, BOX_SIZE, BOX_SIZE);
        }

        // Draw food
        g.setColor(Color.red);
        g.fillRect(food.x * BOX_SIZE, food.y * BOX_SIZE, BOX_SIZE, BOX_SIZE);

        // Draw score and level
        g.setColor(Color.white);
        g.drawString("Score: " + score, 10, 20);
        g.drawString("Level: " + level, 10, 40);
    }

    public void actionPerformed(ActionEvent e) {
        move();
        checkCollision();
        repaint();
    }

    private void changeDirection(int newDirection) {
        // Prevent snake from turning back on itself
        if ((newDirection == KeyEvent.VK_UP && snakeDirection != KeyEvent.VK_DOWN) ||
            (newDirection == KeyEvent.VK_DOWN && snakeDirection != KeyEvent.VK_UP) ||
            (newDirection == KeyEvent.VK_LEFT && snakeDirection != KeyEvent.VK_RIGHT) ||
            (newDirection == KeyEvent.VK_RIGHT && snakeDirection != KeyEvent.VK_LEFT)) {
            snakeDirection = newDirection;
        }
    }

    public void move() {
        Point head = snake.get(0);
        Point newHead = (Point) head.clone();

        switch (snakeDirection) {
            case KeyEvent.VK_UP:
                newHead.y--;
                break;
            case KeyEvent.VK_DOWN:
                newHead.y++;
                break;
            case KeyEvent.VK_LEFT:
                newHead.x--;
                break;
            case KeyEvent.VK_RIGHT:
                newHead.x++;
                break;
        }

        snake.add(0, newHead);

        // Check if snake eats food
        if (newHead.equals(food)) {
            score++;
            spawnFood();

            // Increase level every 5 food items
            if (score % 5 == 0) {
                level++;
                obstacleDensity++; // Increase obstacle density with level
                obstacleSize += 5; // Increase obstacle size with level
                generateObstacles();
            }
        } else {
            snake.remove(snake.size() - 1);
        }
    }

    public void generateObstacles() {
        obstacles.clear();
        for (int i = 0; i < obstacleDensity; i++) {
            int x = random.nextInt(WIDTH);
            int y = random.nextInt(HEIGHT);
            obstacles.add(new Point(x, y));
        }
    }

    public void spawnFood() {
        int x = random.nextInt(WIDTH);
        int y = random.nextInt(HEIGHT);

        // Ensure food does not spawn on the snake's body or obstacles
        while (snake.contains(new Point(x, y)) || obstacles.contains(new Point(x, y))) {
            x = random.nextInt(WIDTH);
            y = random.nextInt(HEIGHT);
        }

        food = new Point(x, y);
    }

    public void checkCollision() {
        Point head = snake.get(0);

        // Check collision with walls
        if (head.x < 0 || head.x >= WIDTH || head.y < 0 || head.y >= HEIGHT) {
            gameOver();
        }

        // Check collision with itself
        for (int i = 1; i < snake.size(); i++) {
            if (head.equals(snake.get(i))) {
                gameOver();
            }
        }

        // Check collision with obstacles
        if (obstacles.contains(head)) {
            gameOver();
        }
    }

    public void gameOver() {
        timer.stop();
        JOptionPane.showMessageDialog(this, "Game Over! Your Score: " + score + " - Level: " + level, "Game Over", JOptionPane.PLAIN_MESSAGE);
        resetGame();
    }

    public void resetGame() {
        snake.clear();
        snake.add(new Point(10, 10));
        spawnFood();
        score = 0;
        level = 1;
        obstacleDensity = 5; // Reset obstacle density
        obstacleSize = 10; // Reset obstacle size
        generateObstacles();
        snakeDirection = KeyEvent.VK_RIGHT;
        timer.start();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Enhanced Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(new EnhancedSnakeGame());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}


   

