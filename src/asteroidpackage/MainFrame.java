package asteroidpackage;

import java.awt.BorderLayout;

import javax.swing.JFrame;

/**
 * This class will create our game window 
 * and connect all the components of the game
 */

public class MainFrame extends JFrame {
	
	// The game components
	private GamePanel game;
	private Canvas canvas;
	private DashPanel dp;
	private GameController gc;
	
	// constructor to initiate our game
	public MainFrame() {
		super("Asteroids");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		// Game instances
		game = new Game(0, Canvas.WIDTH, 0, Canvas.HEIGHT);
		canvas = new Canvas(game);
		dp = new DashPanel(game);
		
		// Adding components to the frame
		add(canvas, BorderLayout.CENTER);
		add(dp, BorderLayout.SOUTH);
		
		// Will be the controller and connect everything
		gc = new GameController(game, canvas, dp);
		
		// Set up the frame window
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		
	}

}
