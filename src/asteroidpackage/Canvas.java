package asteroidpackage;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Canvas extends JPanel {
	
	public static final int WIDTH = 800;
    public static final int HEIGHT = 800;
    public static final int LEFT = 0;
    public static final int RIGHT = WIDTH;
    public static final int TOP = 0;
    public static final int BOTTOM = HEIGHT;
    
    
    private GamePanel gp;
    
    
    private BufferedImage offscreenBuffer;
    private Graphics2D offscreenGraphics;
    
    public Canvas(GamePanel game) {
    	this.gp = game;
    	setBackground(Color.BLACK);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        offscreenBuffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        offscreenGraphics = offscreenBuffer.createGraphics();
    }
    
    /*
     * this will create the game onto the canvas
     */
    @Override
    public void paint(Graphics g) {
        // Clear the offscreen buffer
        offscreenGraphics.setColor(Color.BLACK);
        offscreenGraphics.fillRect(0, 0, WIDTH, HEIGHT);
        
        if (gp.isGameOver()) {
            // Draw game over message
            offscreenGraphics.setColor(Color.WHITE);
            offscreenGraphics.setFont(new Font("Arial", Font.BOLD, 36));
            String message = gp.playerWins() ? "YOU WIN! THE UNIVERSE IS SAFE." : "GAME OVER";
            
            // Center the text
            int messageWidth = offscreenGraphics.getFontMetrics().stringWidth(message);
            offscreenGraphics.drawString(message, (WIDTH - messageWidth) / 2, HEIGHT / 2);
            
            // Draw score
            String scoreText = "Final Score: " + gp.getPoints();
            int scoreWidth = offscreenGraphics.getFontMetrics().stringWidth(scoreText);
            offscreenGraphics.setFont(new Font("Arial", Font.BOLD, 24));
            offscreenGraphics.drawString(scoreText, (WIDTH - scoreWidth) / 2, HEIGHT / 2 + 50);
        } else {
            // Draw all game objects
            gp.drawAll(offscreenGraphics);
        }
        
        // Draw the offscreen buffer to the screen
        g.drawImage(offscreenBuffer, 0, 0, this);
    } 
   
}
