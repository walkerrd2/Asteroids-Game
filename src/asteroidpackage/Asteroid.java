package asteroidpackage;

import java.awt.Color;
import java.awt.Polygon;
import java.util.Random;

/**
 * This class will create the space rocks 
 * that the ship will destory. When it is initially hit
 * it breaks into smaller rocks each time
 */
public class Asteroid extends PolygonSprite {

	protected double dAngle; //rotation speed
	private static final Random random = new Random();
	
	public Asteroid(int left, int right, int top, int bottom, int size) {
		super(left,right,top,bottom);
		setSize(size);
		myColor = new Color(150,150,150); // set a grey color
		dAngle = (random.nextDouble()*6.0)-3.0; // random angular velo between -3 and 3 degrees
		generatePolygonShape(); // will generate a random shape
		setRandomVelocity(); // will set a random initial velo
	}
	
	/*
	 * This will generate the polygon
	 * shape for the asteroids
	 */
	private void generatePolygonShape() {
		myPoly = new Polygon();
		int sides = random.nextInt(6) + 5; //number of sides between 5 and 10
		
		// this will generate points around a circle randomly
		for(int i = 0; i < sides; i++) {
			double angle = 2 * Math.PI * i/sides;
			double radius = size * (0.9 + 0.2 * random.nextDouble()); // random radius
			int px = (int) Math.round(radius * Math.cos(angle));
			int py = (int) Math.round(radius * Math.sin(angle));
			myPoly.addPoint(px, py);
		}
		myColor = Color.WHITE;
	}
	
	/*
	 * Set a random velo for the asteroids
	 */
	private void setRandomVelocity() {
		double speed = 1.0 + random.nextDouble() * 2.0; // random speed between 1 and 3
		double andgle = random.nextDouble() * 2 * Math.PI; // random direction
		dx = speed * Math.cos(andgle);
		dy = speed * Math.sin(andgle);
	}
	
	/*
	 * Moves the asteroid around
	 */
	@Override
	public void move() {
		super.move();
		angle += dAngle;
	}

	/*
	 * update the asteroid state
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
	
	/*
	 * This will split the asteroid into smaller asteroids
	 */
	public Asteroid split() {
		// if the asteroid is too small 
		// to split, return null
		if(size <= 15) {
			return null;
		}
		
		//create new asteroid half the size
		int newSize = size /2;
		setSize(newSize);
		
		//create the asteroid at same location
		Asteroid newAsteroid = new Asteroid(minx,maxx,miny,maxy,newSize);
		newAsteroid.setLocation((int)x, (int)y);
		
		// new asteroid will have different velo
		double newAngle = random.nextDouble() * 2 * Math.PI;
		double newSpeed = 1.0 + random.nextDouble() * 3.0;
		newAsteroid.setVelocity(newSpeed * Math.cos(newAngle), newSpeed * Math.sin(newAngle));
		
		// create new shape for both the new asteroids
		generatePolygonShape();
		newAsteroid.generatePolygonShape();
		
		return newAsteroid;
	}
	
	// Getters
	public double getDeltaAngle() {
        return dAngle;
    }
    
    public void setDeltaAngle(double dAngle) {
        this.dAngle = dAngle;
    }
}
