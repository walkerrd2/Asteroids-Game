package asteroidpackage;

import java.awt.Graphics;

/**
 * this class is the base abstract class for 
 * all the objects of the game (asteroids, ship, bullets).
 * It manages the position, velocity, size, and boundaries 
 * for any sprite
 */
public abstract class Sprite {
	
	protected double x, y; //current position of sprite
	protected double dx, dy; //velocity vector
	protected int size; // radius of bounding circle
	protected int minx, miny, maxx, maxy; //the game boundaries
	
	public Sprite(int left, int right, int top, int bottom) {
		this.minx = left;
		this.maxx = right;
		this.miny = top;
		this.maxy = bottom;
		
		// setting initial position to the middle
		this.x = (left + right) / 2.0;
		this.y = (top + bottom / 2.0);
		
		// initial velocity is 0
		this.dx = 0.0;
		this.dy = 0.0;
		
		// default size
		this.size =10; 
		
	}
	
	/*
	 *  this method checks is a sprite 
	 *  collided with another sprite
	 */
	public boolean isCollided(Sprite other) {
		// calculate euclidean distance between
		// centers of sprites
		double distance = Math.sqrt(Math.pow(this.x - other.x, 2)+Math.pow(this.y - other.y, 2));
		
		return distance < (this.size + other.size);
	}
	
	/*
	 * location of sprite
	 */
	public void setLocation(int nx, int ny) {
		this.x = nx;
		this.y = ny;
	}
	
	/*
	 * velocity of sprite
	 */
	public void setVelocity(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	/*
	 * sets the sprite's size 
	 */
	public void setSize(int ns) {
		this.size = ns;
	}
	
	/*
	 * draws the sprites
	 */
	public abstract void drawOn(Graphics g);
	
	/*
	 * move the sprite around the game
	 */
	public void move() {
		x += dx;
		y +=dy;
	}
	
	/*
	 * updates the sprite's state
	 */
	public abstract void update();
	
	/*
	 * Checks to see if the sprite is out of bounds
	 */
	public boolean isOutOfBounds() {
		return x < minx || x > maxx || y < miny || y > maxy;
	} 
	
	// Getters and Setters

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getDx() {
		return dx;
	}

	public double getDy() {
		return dy;
	}

	public int getSize() {
		return size;
	}
	
	
	

}
