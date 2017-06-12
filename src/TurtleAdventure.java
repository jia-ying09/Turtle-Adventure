
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
    Color bluish = new Color(144, 195, 212);
    Color purplish = new Color(163, 177, 217);
    Color sand = new Color(219, 205, 171);
    //creating a score keeper
    int player = 0;
    Font myFront = new Font("Ariel", Font.BOLD, 75);
    //importing the background
    BufferedImage background = loadImage("underwater.png");
    //importing the shells
    BufferedImage shell = loadImage("clam_shell.png");
    int shellOneX = 150;
    int shellOneY = 230;
    int shellTwoX = 400;
    int shellTwoY = 453;
    int shellThreeX = 348;
    int shellThreeY = 201;
    int shellFourX = 604;
    int shellFourY = 329;
    int shellFiveX = WIDTH / 2;
    int shellFiveY = 30;
    int shellSixX = 680;
    int shellSixY = 100;
    //importing the turtle
    BufferedImage turtle = loadImage("turtle.png");
    //the y coordinate of the turtle
    int turtleX = 25;
    //the x coordinate of the turtle
    int turtleY = 525;
    private int position;

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
        g.drawImage(shell, shellOneX, shellOneY, 100, 100, null);
        g.drawImage(shell, shellTwoX, shellTwoY, 100, 100, null);
        g.drawImage(shell, shellThreeX, shellThreeY, 100, 100, null);
        g.drawImage(shell, shellFourX, shellFourY, 100, 100, null);
        g.drawImage(shell, shellFiveX, shellFiveY, 100, 100, null);
        g.drawImage(shell, shellSixX, shellSixY, 100, 100, null);

        //imputting the turtle
        g.drawImage(turtle, turtleX, turtleY, 50, 50, null);

        //create a score board
        g.setColor(purplish);
        g.setFont(myFront);
        g.drawString(" " + player, WIDTH / 2, HEIGHT - HEIGHT);


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


            if (right) {
                dx = dx + 1;
            }
            if (dx > maxXVelocity) {
                dx = maxXVelocity;
            } else if (left) {
                if (dx < -maxXVelocity) {
                    dx = -maxXVelocity;
                }
            }

            //getting the turtle to jump
            if (jump && !inAir) {
                inAir = false;
                dy = jumpVelocity;
            }

            turtleY = turtleY + dy;
            turtleX = turtleX + dx;


            //making the turtle move to the right when righ key is pressed
            if (rightPressed) {
                turtleX = turtleX + 5;
            }
            //making the turtle move left when left key is pressed
            if (leftPressed) {
                turtleX = turtleX - 5;
            }
            //making the turtle jump when the space key is pressed
            if (jump) {
                turtleY = turtleY - 5;
            }

            //collision method
            collision();


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
        //if the down key is pressed
        public void keyPressed(KeyEvent e) {
            //if the player presses the down button
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_DOWN) {
                downPressed = true;
            } else //if the up key is pressed
            if (key == KeyEvent.VK_UP) {
                upPressed = true;
            } else //if the right key is pressed
            if (key == KeyEvent.VK_RIGHT) {
                rightPressed = true;
            } else //if the left key is pressed
            if (key == KeyEvent.VK_LEFT) {
                leftPressed = true;
            } else //if the space key is pressed
            if (key == KeyEvent.VK_SPACE) {
                jump = true;
            }
        }

        // if a key has been released
        @Override
        public void keyReleased(KeyEvent e) {

            int key = e.getKeyCode();
            //if the down key is released
            if (key == KeyEvent.VK_DOWN) {
                downPressed = false;
            } else //if the up key is released
            if (key == KeyEvent.VK_UP) {
                upPressed = false;
            } else //if the right key is released
            if (key == KeyEvent.VK_RIGHT) {
                rightPressed = false;
            } else //if the left key is released
            if (key == KeyEvent.VK_LEFT) {
                leftPressed = false;
            } else //if the space key is released
            if (key == KeyEvent.VK_SPACE) {
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
        /*  //if the turtle I made hits the bottom of the screen
         if (legY >= HEIGHT)
         {
         legY = legY - 5;
         headY = headY - 5;
         shellY = shellY - 5;
         * 
         * 
         }*/

        if (turtleY >= 525) {
            turtleY = turtleY - 20;
        }

        int overlapX = -1;
        if (turtleX <= shellTwoX) {
            overlapX = turtleX - shellTwoX;

        } else {
            overlapX = shellTwoX - turtleX;
        }

        int overlapY = -1;

        if (turtleY <= shellTwoY) {
            overlapY = turtleY + 50 - shellTwoY;
        } else {
            overlapX = shellTwoX - 100 - turtleY;
        }

        if (overlapX < overlapY) {
            if (turtleX <= shellTwoX) {
                turtleX = shellTwoX - 50;

            } else {
                turtleX = shellTwoX - 100;
            }
            dx = 0;
        } else {

            if (turtleY <= shellTwoY) {
                turtleY = shellTwoY - 100;
            } else {
                turtleY = shellTwoY - 100;
            }

        }
    }
}
/*  if (turtleX <= shell.getWidth()){
 turtleX = shell - 50;
 }*/
/*      
 //let the turtle stay on the shell at (453, 400)
 if (turtleX <= shellTwoX && turtleY == shellTwoY){
 turtleX = shellTwoX - 50;
 } else {
 turtleX = shellTwoX + 100;
 } dx = 0;
    
         
 if (turtleX >= shellOneX - 3 && turtleY == shellOneY || turtleX <= shellOneX + 3 && turtleY == shellOneY){
 turtleX = 150;
 turtleY = 230;

 } 
 else
 if (turtleX >= shellThreeX - 3 && turtleY == shellThreeY || turtleX <= shellThreeX + 3 && turtleY == shellThreeY){
 turtleX = 201;
 turtleY = 348;
 } 
 else
 if (turtleX >= shellFourX - 3 && turtleY == shellFourY || turtleX <= shellFourX + 3 && turtleY == shellFourY){
 turtleX = 329;
 turtleY = 204;

 } 
 else
 if (turtleX >= shellFiveX - 3 && turtleY == shellFiveY || turtleX <= shellFiveX + 3 && turtleY == shellFiveY){
 turtleX = WIDTH/2;
 turtleY = 30;
        
 }
 else
 if (turtleX >= shellSixX - 3 && turtleY == shellSixY || turtleX <= shellSixX + 3 && turtleY == shellSixY){
 turtleX = 680;
 turtleY = 100;
        
 }
         
 /*   else
        
 if (turtleX == 150 || turtleX == 150 && turtleY == 230){
 turtleY = turtleY - 20; 
 turtleX = turtleX + (150 - turtleX);
 inAir = false;}*/

/* if (turtleY = 230){
 turtleY = turtleY; */
