package asteroidpackage;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

/**
 * This class will display the game information
 */
public class DashPanel extends JPanel {
	
	private GamePanel gp;
	
	public DashPanel(Game game) {
        this.gp = game;
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(800, 50));
    }
	
	/*
	 * this will display the current game info
	 */
	@Override
    public void paint(Graphics g) {
        super.paint(g);
        
        // Set font and color for text
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.setColor(Color.WHITE);
        
        // Draw score
        g.drawString("Score: " + gp.getPoints(), 20, 30);
        
        // Draw ships left
        g.drawString("Ships: " + gp.getShipCounter(), 150, 30);
        
        // Draw game state if game is over
        if (gp.isGameOver()) {
            String message = gp.playerWins() ? "YOU WIN!" : "GAME OVER";
            g.setFont(new Font("Arial", Font.BOLD, 24));
            g.setColor(Color.RED);
            g.drawString(message, 300, 30);
        }
    }

}
