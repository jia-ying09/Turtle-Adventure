
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 *
 * @author linj4653
 */
public class TurtleAdventure extends JComponent {

    // Height and Width of our game
    static final int WIDTH = 800;
    static final int HEIGHT = 600;
    //Title of the window
    String title = "My Game";
    // sets the framerate and delay for our game
    // you just need to select an approproate framerate
    long desiredFPS = 60;
    long desiredTime = (1000) / desiredFPS;
    // YOUR GAME VARIABLES WOULD GO HERE
    //creating booleans for the keys
    boolean downPressed;
    boolean upPressed;
    boolean rightPressed;
    boolean leftPressed;
    //gravity
    int gravity = 1;
    boolean inAir = false;
    boolean jump = false;
    boolean right = false;
    boolean left = false;
    Color greenish = new Color(144, 212, 144);
    int jumpVelocity = -15;
    int maxYVelocity = 20;
    int maxXVelocity = 6;
    int dy = 0;
    int dx = 0;
    //creating new colors
    Color purplish = new Color(163, 177, 217);
    //creating a score keeper
    Font myFront = new Font("Ariel", Font.BOLD, 75);
    //importing the background
    BufferedImage background = loadImage("underwater.png");
    //importing the shells
    BufferedImage shell = loadImage("clam_shell.png");
    int shellOneY = 453;
    int shellOneX = 348;
    
    int shellTwoX = 604;
    int shellTwoY = 329;
     
    int shellThreeX = 150;
    int shellThreeY = 230;
    
     int shellFourX = 348;
    int shellFourY = 201;
  
    int shellFiveX = 680;
    int shellFiveY = 100;
    
    int shellSixX = WIDTH / 2;
    int shellSixY = 30;
    
    int shellSize = 100;
    int shellDirectionOne = 1;
    int shellDirectionTwo = 1;
    int shellDirectionThree = 1;
    int shellDirectionFour = 1;
    int shellDirectionFive = 1;
    int shellDirectionSix = 1;
    int turtleSize = 50;
    //importing the turtle
    BufferedImage turtle = loadImage("turtle.png");
    //the y coordinate of the turtle
    int turtleX = 25;
    //the x coordinate of the turtle
    int turtleY = 550;
    private int position;
    double decay = 0.8;
    Random ranGen = new Random();
    // GAME VARIABLES END HERE   
    // Constructor to create the Frame and place the panel in
    // You will learn more about this in Grade 12 :)

    public TurtleAdventure() {
        // creates a windows to show my game
        JFrame frame = new JFrame(title);

        // sets the size of my game
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // adds the game to the window
        frame.add(this);

        // sets some options and size of the window automatically
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        // shows the window to the user
        frame.setVisible(true);

        // add listeners for keyboard and mouse
        frame.addKeyListener(new Keyboard());
        Mouse m = new Mouse();

        this.addMouseMotionListener(m);
        this.addMouseWheelListener(m);
        this.addMouseListener(m);
    }

    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)
    @Override
    public void paintComponent(Graphics g) {
        // always clear the screen first!
        g.clearRect(0, 0, WIDTH, HEIGHT);

        // GAME DRAWING GOES HERE
        //inputting the bakcground
        g.drawImage(background, 0, 0, WIDTH + 10, HEIGHT + 10, null);

        //imputting the shells
        g.drawImage(shell, shellOneX, shellOneY, shellSize, shellSize, null);
        g.drawImage(shell, shellTwoX, shellTwoY, shellSize, shellSize, null);
        g.drawImage(shell, shellThreeX, shellThreeY, shellSize, shellSize, null);
        g.drawImage(shell, shellFourX, shellFourY, shellSize, shellSize, null);
        g.drawImage(shell, shellFiveX, shellFiveY, shellSize, shellSize, null);
        g.drawImage(shell, shellSixX, shellSixY, shellSize, shellSize, null);

        //imputting the turtle
        g.drawImage(turtle, turtleX, turtleY, turtleSize, turtleSize, null);

        g.setColor(purplish);

        g.setFont(myFront);
        if (turtleY < 0 - turtleSize) {
            g.drawString("YOU WIN", WIDTH / 2 - 200, HEIGHT / 2);
            
        }

    }
    // GAME DRAWING ENDS HERE

    // This method is used to do any pre-setup you might need to do
    // This is run before the game loop begins!
    public void preSetup() {
        // Any of your pre setup before the loop starts should go here
    }

    // The main game loop
    // In here is where all the logic for my game will go
    public void run() {
        // Used to keep track of time used to draw and update the game
        // This is used to limit the framerate later on
        long startTime;
        long deltaTime;

        preSetup();

        // the main game loop section
        // game will end if you set done = false;
        boolean done = false;
        while (!done) {
            // determines when we started so we can keep a framerate
            startTime = System.currentTimeMillis();

            // all your game rules and move is done in here
            // GAME LOGIC STARTS HERE 

            //collision method
            collision();

            //Staying on the shell method 
            shellCollision();


            //making the turtle move to the right when righ key is pressed
            if (rightPressed) {
                turtleX = turtleX + 5;
            }
            //making the turtle move left when left key is pressed
            if (leftPressed) {
                turtleX = turtleX - 5;
            }
            //making the turtle jump when the space key is pressed
           
            else if (jump && inAir == true){
                dy = + 15;

           }
            
            
            dy = dy + gravity;

            if (dy > maxYVelocity) {
                dy = maxYVelocity;
            } else if (dy < -maxYVelocity) {
                dy = -maxYVelocity;
            }

            if (right) {
                dx = dx + 1;

                if (dx > maxXVelocity) {
                    dx = maxXVelocity;
                }
            } else if (left) {
                dx = dx + 1;

                if (dx < -maxXVelocity) {
                    dx = -maxXVelocity;
                }
            } else //getting the turtle to jump
            if (jump && !inAir) {
                inAir = false;
                dy = jumpVelocity;
            } else {

                dx = (int) (dx * decay);
            }

            turtleY = turtleY + dy;
            turtleX = turtleX + dx;

            shellOneMove();
            shellTwoMove();
            shellThreeMove();
            shellFourMove();
            shellFiveMove();
            shellSixMove();


            // GAME LOGIC ENDS HERE 
            // update the drawing (calls paintComponent)
            repaint();

            // SLOWS DOWN THE GAME BASED ON THE FRAMERATE ABOVE
            // USING SOME SIMPLE MATH
            deltaTime = System.currentTimeMillis() - startTime;
            try {
                if (deltaTime > desiredTime) {
                    //took too much time, don't wait
                    Thread.sleep(1);
                } else {
                    // sleep to make up the extra time
                    Thread.sleep(desiredTime - deltaTime);
                }
            } catch (Exception e) {
            };
        }
    }

    // Used to implement any of the Mouse Actions
    private class Mouse extends MouseAdapter {
        // if a mouse button has been pressed down

        @Override
        public void mousePressed(MouseEvent e) {
        }

        // if a mouse button has been released
        @Override
        public void mouseReleased(MouseEvent e) {
        }

        // if the scroll wheel has been moved
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
        }

        // if the mouse has moved positions
        @Override
        public void mouseMoved(MouseEvent e) {
        }
    }

    // Used to implements any of the Keyboard Actions
    private class Keyboard extends KeyAdapter {
        // if a key has been pressed down

        @Override
        public void keyPressed(KeyEvent e) {

            //if the right key is pressed
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                rightPressed = true;
            } else //if the left key is pressed
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                leftPressed = true;
            } else //if the space key is pressed
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                jump = true;
            }
        }

        // if a key has been released
        @Override
        public void keyReleased(KeyEvent e) {

            //if the right key is released
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                rightPressed = false;
            } else //if the left key is released
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                leftPressed = false;
            } else //if the space key is released
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                jump = false;

            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // creates an instance of my game
        TurtleAdventure game = new TurtleAdventure();

        // starts the game loop
        game.run();
    }

    public BufferedImage loadImage(String filename) {

        BufferedImage img = null;

        try {
            // use ImageIO to load in an Image
            // ClassLoader is used to go into a folder in the directory and grab the file
            img = ImageIO.read(ClassLoader.getSystemResourceAsStream(filename));
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

        return img;
    }

    public void collision() {

        //don't fall in the void
        if (turtleY >= 525) {
            turtleY = turtleY - 20;
        }
        //don't past the left side
        if (turtleX < 0) {
            turtleX = turtleX + 5;
        }
        //don't past the right side
        if (turtleX > WIDTH - 50) {
            turtleX = turtleX - 5;
        }

    }

    public void shellCollision() {   
        
        if (!(shellOneX + shellSize < turtleSize || shellOneX > turtleX + turtleSize || shellOneY + shellSize < turtleY - 10 || shellOneY > turtleY  - 10+ turtleSize)) {
                    turtleX = shellOneX + 25;
                    turtleY = shellOneY - 50;
        } 
        else {
        if (!(shellTwoX + shellSize < turtleSize || shellTwoX > turtleX + turtleSize || shellTwoY + shellSize < turtleY  - 10|| shellTwoY > turtleY  - 10+ turtleSize)) {
            turtleX = shellTwoX + 25;
            turtleY = shellTwoY - 50;

        } else {
            if (!(shellThreeX + shellSize < turtleSize || shellThreeX > turtleX + turtleSize || shellThreeY + shellSize < turtleY  - 10|| shellThreeY > turtleY  - 10 + turtleSize)) {
                turtleX = shellThreeX + 25;
                turtleY = shellThreeY - 50;


                } else {
                    if (!(shellFourX + shellSize < turtleSize || shellFourX > turtleX + turtleSize || shellFourY + shellSize < turtleY  - 10|| shellFourY > turtleY  - 10 + turtleSize)) {
                        turtleX = shellFourX + 25;
                        turtleY = shellFourY - 50;

                    } else {
                        if (!(shellFiveX + shellSize < turtleSize || shellFiveX > turtleX + turtleSize || shellFiveY + shellSize < turtleY  - 10|| shellFiveY > turtleY  - 10 + turtleSize)) {
                            turtleX = shellFiveX + 25;
                            turtleY = shellFiveY - 50;

                        } else {
                            if (!(shellSixX + shellSize < turtleSize || shellSixX > turtleX + turtleSize || shellSixY + shellSize < turtleY  - 10|| shellSixY > turtleY  - 10 + turtleSize)) {
                                turtleX = shellSixX + 25;
                                turtleY = shellSixY - 50;
                            }
                        }
                    }
                }
            }
        }
    }

    public void shellOneMove() {


        if (shellOneX > WIDTH - 75) {
            shellDirectionOne = -1;
        }
        if (shellOneX <= 0) {
            shellDirectionOne = 1;
        }
        shellOneX = shellOneX + shellDirectionOne * 2;
    }

    public void shellTwoMove() {


        if (shellTwoX > WIDTH - 75) {
            shellDirectionTwo = -1;
        }
        if (shellTwoX < 0) {
            shellDirectionTwo = 1;
        }

        shellTwoX = shellTwoX + shellDirectionTwo * 3;
    }

    public void shellThreeMove() {


        if (shellThreeX > WIDTH - 75) {
            shellDirectionThree = -1;
        }
        if (shellThreeX < 0) {
            shellDirectionThree = 1;
        }
        shellThreeX = shellThreeX + shellDirectionThree * 2;
    }

    public void shellFourMove() {

        if (shellFourX > WIDTH - 75) {
            shellDirectionFour = -1;
        }

        if (shellFourX < 0) {
            shellDirectionFour = 1;
        }
        shellFourX = shellFourX + shellDirectionFour * 4;
    }

    public void shellFiveMove() {


        if (shellFiveX > WIDTH - 75) {
            shellDirectionFive = -1;
        }
        if (shellFiveX < 0) {
            shellDirectionFive = 1;
        }
        shellFiveX = shellFiveX + shellDirectionFive * 5;
    }

    public void shellSixMove() {


        if (shellSixX > WIDTH - 75) {
            shellDirectionSix = -1;
        }
        if (shellSixX < 0) {
            shellDirectionSix = 1;
        }
        shellSixX = shellSixX + shellDirectionSix * 5;

    }

    public void reset() {
        turtleX = 25;
        turtleY = 225;
    }
}
