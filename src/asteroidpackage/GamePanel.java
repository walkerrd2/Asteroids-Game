package asteroidpackage;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * This class will manage all the game objects
 * and the game state
 */
public class GamePanel {
	
	private static final int MAX_ASTEROIDS = 10;
    private static final int INITIAL_SHIPS = 3;
    private static final int SAFE_RADIUS = 50;  //Safe radius around ship spawn
    
    // Game objects
    private SpaceShip ship;
    private List<Asteroid> asteroids;
    private List<Bullets> bullets;
    
    // Game state
    private int shipCounter; //Remaining ships
    private int points; //Player's score
    private Random random;
    
    // Game boundaries
    private int left, right, top, bottom;
    
    public GamePanel(int left, int right, int top, int bottom) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
        
        random = new Random();
        
        // Initialize game state
        shipCounter = INITIAL_SHIPS;
        points = 0;
        
        // Create collections for game objects
        asteroids = new ArrayList<>();
        bullets = new ArrayList<>();
        
        // Create ship in center of game space
        resetShip();
        
        // Create initial asteroids
        createAsteroids();
    }
    
    /*
     * This will create the ship at the center of the game
     */
    private void resetShip() {
        ship = new SpaceShip(left, right, top, bottom);
        
        // Place ship in center with zero velocity
        ship.setLocation((left + right) / 2, (top + bottom) / 2);
        ship.setVelocity(0, 0);
    }
    
    /*
     * This will create the initial asteroids
     */
    private void createAsteroids() {
        int centerX = (left + right) / 2;
        int centerY = (top + bottom) / 2;
        
        for (int i = 0; i < MAX_ASTEROIDS; i++) {   	
            int size = 15 + random.nextInt(26); //Random size between 15 and 40
            
            // Random position
            int x, y;
            double distance;
            do {
                x = left + random.nextInt(right - left);
                y = top + random.nextInt(bottom - top);
                
                // Distance from center (ship spawn point)
                distance = Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2));
                
                // Keep generating until position is far enough from center
            } while (distance < SAFE_RADIUS);
            
            // Create asteroid and add to list
            Asteroid asteroid = new Asteroid(left, right, top, bottom, size);
            asteroid.setLocation(x, y);
            asteroids.add(asteroid);
        }
    }
    
    /*
     * This will update all game objects for the next frame
     */
    public void updateAll() {
        // Lists to track objects to remove
        List<Asteroid> asteroidsToRemove = new ArrayList<>();
        List<Bullets> bulletsToRemove = new ArrayList<>();
        
        // Update ship if it exists
        if (ship != null) {
            ship.update();
            
            // Check for collisions with asteroids
            for (Asteroid asteroid : asteroids) {
                if (ship.isCollided(asteroid)) {
                	
                    // Ship hit by asteroid
                    asteroidsToRemove.add(asteroid);
                    shipCounter--;
                    
                    // Reset ship if we have ships left
                    if (shipCounter > 0) {
                        resetShip();
                    } else {
                        ship = null;  // Game over
                    }
                    
                    // Add points for destroyed asteroid
                    points += 20;
                    break;  // Ship is destroyed, no need to check other asteroids
                }
            }
        }
        
        // Update asteroids
        for (Asteroid asteroid : asteroids) {
            asteroid.update();
        }
        
        // Update bullets and check for collisions
        for (Iterator<Bullets> it = bullets.iterator(); it.hasNext();) {
            Bullets bullet = it.next();
            bullet.update();
            
            // Remove bullet if it's too old or out of bounds
            if (bullet.isTooOld() || bullet.isOutOfBounds()) {
                bulletsToRemove.add(bullet);
                continue;
            }
            
            // Check for collisions with asteroids
            for (Asteroid asteroid : asteroids) {
                if (bullet.isCollided(asteroid)) {
                	
                    // Bullet hit asteroid
                    bulletsToRemove.add(bullet);
                    
                    // Try to split asteroid
                    Asteroid splitAsteroid = asteroid.split();
                    
                    if (splitAsteroid == null) {
                    	
                        // Asteroid too small to split, remove it
                        asteroidsToRemove.add(asteroid);
                        
                        // Smaller asteroids worth more points
                        points += 100 / asteroid.getSize();
                    } else {
                    	
                        // Add split asteroid to the game
                        asteroids.add(splitAsteroid);
                        
                        // Medium sized asteroids worth fewer points
                        points += 50 / asteroid.getSize();
                    }
                    break;  // Bullet has hit something, stop checking
                }
            }
        }
        //Remove destroyed objects
        asteroids.removeAll(asteroidsToRemove);
        bullets.removeAll(bulletsToRemove);
    }
    
    /*
     * This will draw all game objects
     */
    public void drawAll(Graphics g) {
        // Draw ship if it exists
        if (ship != null) {
            ship.drawOn(g);
        }
        
        // Draw all asteroids
        for (Asteroid asteroid : asteroids) {
            asteroid.drawOn(g);
        }
        
        // Draw all bullets
        for (Bullets bullet : bullets) {
            bullet.drawOn(g);
        }
    }
    
    /*
     * this will check if game is over
     */
    public boolean isGameOver() {
        return shipCounter <= 0 || asteroids.isEmpty();
    }
    
    /*
     * This will check if player won
     */
    public boolean playerWins() {
        return isGameOver() && asteroids.isEmpty();
    }
    
    /*
     * this will tell the ship to shoot
     */
    public void shoot() {
        if (ship != null) {
            Bullets bullet = ship.shoot();
            bullets.add(bullet);
        }
    }
    
    /**
     * This will rotates the ship clockwise.
     */
    public void turnCW() {
        if (ship != null) {
            ship.rotate(5);  // 5 degrees clockwise
        }
    }
    
    /**
     * This will rotate the ship counter-clockwise.
     */
    public void turnCCW() {
        if (ship != null) {
            ship.rotate(-5);  // 5 degrees counter-clockwise
        }
    }
    
    /**
     * This will apply thrust to the ship.
     */
    public void thrust() {
        if (ship != null) {
            ship.thrust();
        }
    }
    
    /**
     * this will apply braking to the ship.
     */
    public void brake() {
        if (ship != null) {
            ship.brake();
        }
    }
    
    // Getters
    
    public int getShipCounter() {
        return shipCounter;
    }
    
    public int getPoints() {
        return points;
    }
    
    
    
    
    
    
    
    
    
    
    
    

}
