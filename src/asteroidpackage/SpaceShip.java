package asteroidpackage;

import java.awt.Color;

/**
 * This class will generate the players spaceship
 */
public class SpaceShip extends PolygonSprite {
	
	private static final double THRUST_AMOUNT = 0.5;
	private static final double BULLET_SPEED = 10.0;
	
	
	public SpaceShip(int left, int right, int top, int bottom) {
		super(left,right,top,bottom);
		setSize(10);
		myColor = Color.RED;
		
		// ship will be a triangle
		myPoly.addPoint(15, 0); //nose
		myPoly.addPoint(-5, -8); //back left
		myPoly.addPoint(-5, 8); //back right
	}
	
	/*
	 * This will create and return a
	 * new bullet fired from the ship
	 */
	public Bullets shoot() {
		Bullets bull = new Bullets(minx, maxx, miny, maxy); // will have same boundaris as ship
		bull.setLocation((int)x, (int)y); //bullets will be set to ships position
		
		//Calculate bullets velocity
		// SHip velo + bullet velo in direction of ship's angle
		double bulletDx = dx + BULLET_SPEED * Math.cos(Math.toRadians(angle));
		double bulletDy = dy + BULLET_SPEED * Math.sin(Math.toRadians(angle));
		
		bull.setVelocity(bulletDx, bulletDy);
		
		return bull;
	}
	
	/*
	 * Add thrust to the ship
	 */
	public void thrust() {
		// calculate the thrust in direction ship is pointing
		double thrustDx = THRUST_AMOUNT * Math.cos(Math.toRadians(angle));
        double thrustDy = THRUST_AMOUNT * Math.sin(Math.toRadians(angle));
        
        // add to current velo
        dx += thrustDx;
        dy += thrustDy;
        
        //help limit the max speed
        double speed = Math.sqrt(dx * dx + dy * dy);
        double maxSpeed = 10.0;
        if (speed > maxSpeed) {
            dx = (dx / speed) * maxSpeed;
            dy = (dy / speed) * maxSpeed;
        }
	}
	
	/*
	 * add breaks to slow down the ship
	 */
	public void brake() {
        // Reduce velocity by 10%
        dx *= 0.9;
        dy *= 0.9;
        
        // If very slow, just stop
        if (Math.abs(dx) < 0.1 && Math.abs(dy) < 0.1) {
            dx = 0;
            dy = 0;
        }
    }
	
	/*
	 * update the ship's state
	 */
	@Override
	public void update() {
		move();
		
		// if the asteroid goes off screen
		// this will wrap it back around
		if(x<minx) {
			x = maxx;
		}
		if(x>maxx) {
			x = minx;
		}
		if (y<miny) {
			y = maxy;
		}
		if (y>maxy) {
			y =miny;
		}
		
	}
}
