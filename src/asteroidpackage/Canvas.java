package asteroidpackage;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.Component;

public class Canvas extends JPanel {
	
	public static final int WIDTH = 800;
    public static final int HEIGHT = 800;
    public static final int LEFT = 0;
    public static final int RIGHT = WIDTH;
    public static final int TOP = 0;
    public static final int BOTTOM = HEIGHT;
    
    
    private GamePanel gp;
    private GameController controller;
    
    
    private BufferedImage offscreenBuffer;
    private Graphics2D offscreenGraphics;
    
    private JButton restartButton;
    
    public Canvas(GamePanel game) {
    	this.gp = game;
    	setBackground(Color.BLACK);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(null);
        offscreenBuffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        offscreenGraphics = offscreenBuffer.createGraphics();
        createRestartButton();
    }
    
    /*
     * this will create the restart button
     */
    private void createRestartButton() {
        restartButton = new JButton("Restart Game");
        restartButton.setFont(new Font("Arial", Font.BOLD, 18));
        restartButton.setBounds((WIDTH - 150) / 2, HEIGHT / 2 + 80, 150, 40);
        
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Restart the game
                gp.restart();
                
                // Remove the button
                remove(restartButton);
                
                // Restart the timer in the controller
                if (controller != null) {
                    controller.restartGame();
                }
                
                // Request focus to capture keyboard events
                requestFocus();
                repaint();
            }
        });
    }
    
    /*
     * this will set the controller to canvas
     */
    public void setController(GameController controller) {
        this.controller = controller;
    }
    
    
    /*
     * this will create the game onto the canvas
     */
    @Override
    public void paintComponent(Graphics g) {
    	
    	super.paintComponent(g);
    	
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
         
            // Add restart button if not already added
            if (!containsRestartButton()) {
                add(restartButton);
                revalidate();
            }
        } else {
            // Remove restart button if game is running
            if (containsRestartButton()) {
                remove(restartButton);
                revalidate();
            }
            
            // Draw all game objects
            gp.drawAll(offscreenGraphics);
        }
        
        // Draw the off-screen buffer to the screen
        g.drawImage(offscreenBuffer, 0, 0, this);
    } 
    
    /*
     * This will check if the restart
     * button is added to the panel
     */
    private boolean containsRestartButton() {
        for (Component comp : getComponents()) {
            if (comp == restartButton) {
                return true;
            }
        }
        return false;
    }
}
