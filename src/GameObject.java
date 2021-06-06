//Zak Haider

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject {
	
	protected double x, y;
	protected ID id;
	protected double dx, dy;
	
	public GameObject(double x, double y, ID id) {
		this.x = x;
		this.y = y;
		this.id = id;
	}
	
	public abstract void tick();
	public abstract Rectangle getBounds();
	public abstract void paintComponent(Graphics g);
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public void setX(double x) {
		this.x = x;
	}
	public void setY(double y) {
		this.y = y;
	}
	public ID getID() {
		return id;
	}
	public void setID(ID id) {
		this.id = id;
	}
	public double getDX() {
		return dx;
	}
	public double getDY() {
		return dy;
	}
	public void setDX(double dx) {
		this.dx = dx;
	}
	public void setDY(double dy) {
		this.dy = dy;
	}
}
