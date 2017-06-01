
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
    
     boolean downPressed;
    boolean upPressed;
    boolean rightPressed;
    boolean leftPressed;   
    int gravity = 1;
    boolean inAir = false;
    boolean jump = false;
    boolean right = false;
    boolean left = false;
    Color greenish = new Color(144, 212, 144);
    Color shell = new Color(98, 181, 103);
    
    
    int jumpVelocity = -15;
    int maxYVelocity = 20;
    int maxXVelocity = 6;
    
    
    int dy = 0;
    int dx = 0;
    //shell coordinates
    int shellX = 10;
    int shellY = 500;
    //head coordinates
    int headX = 80;
    int headY = 510;
    //leg 1 coordinates
    int legOneX = 15;
    int legY = 525;
    //leg 2 coordinates
    int legTwoX = 25;
    //leg 3 coordinates
    int legThreeX = 60;
    //leg 4 coordinates
    int legFourX = 70;

    
    //creating a score keeper
    int player = 0;
    Font myFront = new Font("Ariel", Font.BOLD, 75);
    
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
        
        g.setColor(greenish);
       // g.setColor(shell);
        //shell
        g.fillArc(shellX, shellY, 80, 60, 0, 180);
        
        //legs
        g.fillRoundRect(legOneX, legY, 10, 20, WIDTH, HEIGHT);
        g.fillRoundRect(legTwoX, legY, 10, 20, WIDTH, HEIGHT);
        g.fillRoundRect(legThreeX, legY, 10, 20, WIDTH, HEIGHT);
        g.fillRoundRect(legFourX, legY, 10, 20, WIDTH, HEIGHT);

        //face
        g.fillOval(headX, headY, 25, 20);
        
        g.setColor(Color.CYAN);
        g.drawRect(0, 500, 500, 100);
    
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
            dy = dy + gravity;
            if (dy > maxYVelocity){
            dy = maxYVelocity;}
            
            else if (dy < - maxYVelocity){
            dy = -maxYVelocity;}           
            
            if (right){
            dx = dx + 1;}
            if (dx >maxXVelocity){
                dx = maxXVelocity;
            }
            else if (left)
            if (dx < - maxXVelocity){ 
            dx = -maxXVelocity;}

            //movements for down key
            if (downPressed) {
                shellY = shellY + 3;
                headX = headX - 4;
                headY = headY + 3;
            } 
            //movements for up key
             if (upPressed) {
                shellY = shellY - 3;
                headX = headX + 4;
                headY = headY - 3;
            } 
             //movements for right key
             if (rightPressed) {
                 shellX = shellX + 5;
                headX = headX + 5;
                legOneX = legOneX + 5; 
                legTwoX = legTwoX + 5;
                legThreeX = legThreeX + 5;
                legFourX = legFourX + 5;}
             
              //movements for left key
                 if (leftPressed) {
                 shellX = shellX - 5;
                headX = headX - 5;
                legOneX = legOneX - 5; 
                legTwoX = legTwoX - 5;
                legThreeX = legThreeX - 5;
                legFourX = legFourX - 5;}
                 
                 
                 //getting the turtle to jump
             if (jump && !inAir){
             inAir = false;
             dy = jumpVelocity;}
             
             shellY = shellY + dx;
             shellY = shellY + dy;
             legY = legY + dy;
              legY = legY + dx;
             headY = headY + dy;
             headY = headY + dx;


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
            int key = e.getKeyCode();
             if (key == KeyEvent.VK_DOWN) {
                downPressed = true;
        }else
                 //if the up key is pressed
             if (key == KeyEvent.VK_UP) {
                upPressed = true;
        }else
                 //if the right key is pressed
             if (key == KeyEvent.VK_RIGHT) {
                rightPressed = true;
        } else 
                 //if the left key is pressed
              if (key == KeyEvent.VK_LEFT) {
                leftPressed = true;
        } else 
                  //if the space key is pressed
              if (key == KeyEvent.VK_SPACE){
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
            }else
             if (key == KeyEvent.VK_UP) {
                upPressed = false;
             }else
             if (key == KeyEvent.VK_RIGHT) {
                rightPressed = false;
        } else
              if (key == KeyEvent.VK_LEFT) {
                leftPressed = false;
        } else
              if (key == KeyEvent.VK_SPACE){
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
}
