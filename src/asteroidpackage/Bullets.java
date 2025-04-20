package asteroidpackage;

import java.awt.Color;
import java.awt.Graphics;

/**
 * This class will create the bullets
 * that will be fired from the ship
 */
public class Bullets extends Sprite {
	
	private static final int INITIAL_AGE = 20;
    private int age;
    
    public Bullets(int left, int right, int top, int bottom) {
    	super(left, right, top, bottom);
    	this.age = INITIAL_AGE;
    	setSize(2);
    }
    
    /*
     * This will draw the bullets 
     */
	@Override
	public void drawOn(Graphics g) {
		g.setColor(Color.YELLOW);
        
        // Calculate the end point of the trail
        int trailLength = 5;
        int endX = (int)(x - (dx * trailLength / 10.0));
        int endY = (int)(y - (dy * trailLength / 10.0));
        
        // Draw a line for the bullet with a small trail
        g.drawLine((int)x, (int)y, endX, endY);
        
        // Also draw a small dot at the bullet position
        g.fillOval((int)x - 1, (int)y - 1, 3, 3);
	}
	
	/*
	 * this will move the bullet and decrease the bullet's age
	 */
	@Override
    public void move() {
        super.move(); //Move by velocity vector
        age--; //Decrease age
    }

	@Override
	public void update() {
		move();
		
	}
	
	/*
	 * this will check if bullet is 
	 * too old. If so it will be removed
	 */
	public boolean isTooOld() {
        return age <= 0;
    }
    
    // Getter
    
    public int getAge() {
        return age;
    }
    

}
