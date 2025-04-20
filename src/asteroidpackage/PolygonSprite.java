package asteroidpackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;

/**
 * This abstract class represents sprites as Polygons
 */
public abstract class PolygonSprite extends Sprite {
	
	protected Polygon myPoly; // shape of sprite
	protected Color myColor; // color of sprite
	protected double angle; // rotation angle in degrees
	
	public PolygonSprite(int left, int right, int top, int bottom) {
		super(left, right, top, bottom);
		this.angle = 0.0;
		this.myPoly = new Polygon();
		this.myColor = Color.WHITE;
	}
	
	/*
	 * draw the polygon sprite.
	 * Will use affine transforms to handle
	 * rotation and translation
	 */
	@Override
	public void drawOn(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(myColor);
		AffineTransform savedTransform = g2d.getTransform();
		g2d.translate(x, y);
		g2d.rotate(Math.toRadians(angle));
		g2d.fillPolygon(myPoly);
		g2d.setTransform(savedTransform);
	}
	
	/*
	 * rotate the sprite by any angle
	 */
	public void rotate(double theta) {
		angle += theta;
		
		// angle between 0 and 360
		while(angle >= 360.0) {
			angle -= 360.0;
		}
		while(angle < 0.0) {
			angle += 360.0;
		}
	}
	
	// Getters and Setters

	public Color getMyColor() {
		return myColor;
	}

	public void setMyColor(Color myColor) {
		this.myColor = myColor;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public Polygon getMyPoly() {
		return myPoly;
	}
	
} 
