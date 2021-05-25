//Libraries Imported
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class GamePanel extends JPanel implements ActionListener {

    //attributes

    static final int SCREEN_WIDTH = 600; //Width of the screen
    static final int SCREEN_HEIGHT = 600; //Length of the screen
    static final int UNIT_SIZE = 25; // Size of each unit on screen (imagine a of size 25x25)
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE; //Number of units possible on the screen
    static final int DELAY = 75; //Determines how fast the game is running
    static boolean paused = false; //set paused state to false
    final int[] x = new int[GAME_UNITS]; //hold x coordinates of all body parts of the snake
    final int[] y = new int[GAME_UNITS]; //hold y coordinates of all body parts of the snake
    int bodyParts = 6; //initial length of snake
    int applesEaten = 0; //keeps count of score
    int apple_X; //X coordinate for the apple spawned
    int apple_Y; // Y coordinate for the apple spawned
    char direction = 'R'; //initial direction of the snake
    boolean running = false; //initially set game state to not running
    Timer timer;
    Random random;

    //constructor template
    GamePanel() {

        random = new Random(); //creates an instance of type Random
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT)); //Sets preferred dimension to width x height
        this.setBackground(Color.BLACK); //Sets Background of game to Black
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter()); //Allows user to interact with Snake game using keys
        startGame();


    }
    //First method called needed to run the game
    public void startGame() {
        spawnApple(); //Spawns first apple on random coordinate
        running = true; //Indicates that the game is running
        timer = new Timer(DELAY, this);
        timer.start();
    }
    //Method called when pausing game
    public void pause() {

        paused = true; //Indicates that the game is paused
        running = false; //Indicates that the game is not running when paused
        timer = new Timer(DELAY, null);
        timer.stop();
    }
    //Method called when resuming game
    public void resume() {
        paused = false; //Indicates that the game is no longer in the paused state
        running = true; //Indicates that the game in now currently running
        timer = new Timer(DELAY, null);
        timer.start();
    }

    public void paintComponent(Graphics graph) {

        super.paintComponent(graph); //allows to paint the game units on the screen
        try {
            draw(graph); //this method draws the apples and snake inside the screen
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics graph) throws IOException {

        //All graphics are drawn game is running
        if (running) {

            graph.setColor(Color.red); //sets apple color to red
            graph.fillOval(apple_X, apple_Y, UNIT_SIZE, UNIT_SIZE);

            //Iterates through every body part of the snake and colors it green
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    graph.setColor(Color.green);
                    graph.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    graph.setColor(new Color(45, 180, 0));
                    graph.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);

                }
                // Sets the current score at the top middle in the color red
                graph.setColor(Color.red);
                graph.setFont(new Font("Ink Free", Font.BOLD, 35));
                FontMetrics metrics = getFontMetrics(graph.getFont());
                graph.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2, graph.getFont().getSize());
            }
          // Checks to see if the game is paused
        } else if (paused == true) {

            GamePaused(graph); //calls GamePaused method if the key "SPACE" was pressed
        } else {
            gameOver(graph); //Shows the Game Over screen if game is not paused or running

        }
    }
    //Spawns an apple at a random coordinate on the screen
    public void spawnApple() {
        apple_X = random.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
        apple_Y = random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;

    }
    //Allows the snake to move by setting positions at the end to the previous positions of the head of the snake
    public void move() {
        //Sets the position of the newer body part to the previous position of an older body part
        for (int i = bodyParts; i > 0; i--) {

            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        //Sets the direction of the snake head depending on the keys pressed
        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;

            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;

            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;

            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;

        }
    }
    //This method spawns a new apple only if the snake ate the apple
    // Also increases the length of the snake
    public void checkApple() {
        if ((x[0] == apple_X) && (y[0] == apple_Y)) {
            bodyParts++;
            applesEaten++;
            spawnApple();
        }
    }
    //Checks if the snake head is colliding with body
    public void checkCollisions() {
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;

            }
        }
        //checks if the snake head is colliding with left border
        if (x[0] < 0) {
            running = false;
        }
        //checks if the snake is colliding with right border
        if (x[0] > SCREEN_WIDTH) {
            running = false;
        }

        //checks if the snake is colliding with the top border
        if (y[0] < 0) {
            running = false;
        }
        //checks to see if the snake is colliding with the bottom border

        if (y[0] > SCREEN_HEIGHT) {
            running = false;
        }
        if (!running) {
            timer.stop();
        }


    }

    public void GamePaused(Graphics graph) {
        //paused text

        graph.setColor(Color.red);
        graph.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics1 = getFontMetrics(graph.getFont());
        graph.drawString("Paused", (SCREEN_WIDTH - metrics1.stringWidth("Paused")) / 2, SCREEN_HEIGHT / 2);

    }

    public void gameOver(Graphics graph) throws IOException {

        //Keeps track of high score

        File highscore = new File("C:\\Users\\William\\Documents\\JavaProjects\\snakeApp\\src\\HighScoreHolder.txt");
        Scanner myReader = new Scanner(highscore);
        int temp_hs = Integer.parseInt(myReader.nextLine());
        //updates the high score inside text file

        //Overwrites high score inside of text file if current score is larger
        if (applesEaten > temp_hs) {
            try {

                FileWriter myWriter = new FileWriter("C:\\Users\\William\\Documents\\JavaProjects\\snakeApp\\src\\HighScoreHolder.txt");
                myWriter.write(Integer.toString(applesEaten));
                myWriter.close();
                System.out.println("Successfully wrote to the file.");
            }
            //Catches error when not converting an integer to a string
            catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }


        }

        //Score graphics displayed

        graph.setColor(Color.red);
        graph.setFont(new Font("Ink Free", Font.BOLD, 25));
        FontMetrics metrics2 = getFontMetrics(graph.getFont());
        graph.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics2.stringWidth("Score: " + applesEaten)) / 2, graph.getFont().getSize());


        //High score graphics displayed
        File highscore_final = new File("C:\\Users\\William\\Documents\\JavaProjects\\snakeApp\\src\\HighScoreHolder.txt");
        Scanner myReader2 = new Scanner(highscore_final);
        int final_hs = Integer.parseInt(myReader2.nextLine());
        graph.setColor(Color.red);
        graph.setFont(new Font("Ink Free", Font.BOLD, 25));
        FontMetrics metrics3 = getFontMetrics(graph.getFont());
        graph.drawString("High Score: " + final_hs, (SCREEN_WIDTH - metrics3.stringWidth("Score: " + final_hs)) / 2, graph.getFont().getSize() + 25);

        //Game Over graphics displayed

        graph.setColor(Color.red);
        graph.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics4 = getFontMetrics(graph.getFont());
        graph.drawString("Game Over", (SCREEN_WIDTH - metrics4.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);


    }
    //This method allows the snake to constantly move if the Snake App is running
    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }
    //New class that allows user to control the Snake App while pressing certain keys
    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {

            switch (e.getKeyCode()) {
                //Changes direction of snake head to LEFT when pressed "LEFT ARROW KEY"
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';

                    }
                    break;
                //Changes direction of snake head to RIGHT when pressed "RIGHT ARROW KEY"
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                //Changes direction of snake head to UP when pressed "UP ARROW KEY"
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';

                    }
                    break;
                //Changes direction of snake head to DOWN when pressed "DOWN ARROW KEY"
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';

                    }
                    break;
                //Set the Snake App to Paused or Resume depending on the state of the game
                case KeyEvent.VK_SPACE:
                    if (GamePanel.paused) {
                        resume();
                    } else {
                        pause();

                    }
                    break;
            }

        }

    }

}
