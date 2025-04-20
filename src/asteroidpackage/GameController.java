package asteroidpackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.Timer;

/**
 * This class will handle the user's input and 
 * the game's timing
 */
public class GameController extends KeyAdapter implements ActionListener{
	
private static final int TIMER_DELAY = 50;  // ~20 frames per second
    
    private Timer time;
    private GamePanel gp;
    private Canvas theCanvas;
    private DashPanel theDash;
    
    public GameController(GamePanel game, Canvas canvas, DashPanel dash) {
        this.gp = game;
        this.theCanvas = canvas;
        this.theDash = dash;
        
        // Create timer for animation
        time = new Timer(TIMER_DELAY, this);
        
        // Add this controller as key listener to the canvas
        theCanvas.addKeyListener(this);
        theCanvas.setFocusable(true);
        
        // Start the game timer
        time.start();
    }
    
    /*
     * This will handle the key press events
     * by the user
     */
    @Override
    public void keyPressed(KeyEvent e) {
    	
        int key = e.getKeyCode();
        
        switch (key) {
            case KeyEvent.VK_UP:
                gp.thrust();
                break;
            case KeyEvent.VK_LEFT:
                gp.turnCCW();
                break;
            case KeyEvent.VK_RIGHT:
                gp.turnCW();
                break;
            case KeyEvent.VK_SPACE:
                gp.shoot();
                break;
            case KeyEvent.VK_DOWN:
                gp.brake();  // Optional: brake when down arrow is pressed
                break;
        }
    }
    
    /*
     * This will handle the time 
     * events for the animations
     */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		gp.updateAll();
        
        // Check if game is over
        if (gp.isGameOver()) {
            time.stop();
        }
        
        // Repaint the views
        theCanvas.repaint();
        theDash.repaint();
    }

}
