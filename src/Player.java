//Zak Haider

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;

public class Player extends GameObject{
	private KeyInput ki;
	private Handler handler;
    double rotation = 0;
    private int lives = 3;
    private int score = 0;
	public static final long FIRE_RATE = 200000000L;
	public long lastShot = 0;
	public double accel = 1;
    Game game;

	public Player(Game game, Handler handler, KeyInput ki, int x, int y, ID id) {
		super(x, y, id);
		this.ki = ki;
		this.handler = handler;
		this.game = game;
	}
	
	public int getLives() {
		return lives;
	}
	
	public void setLives(int lives) {
		this.lives = lives;
	}
	
	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

	@Override
	public void tick() {
        if(this.x > 1200) {
        	this.x = 0;
        }
        if(this.x < -90) {
        	this.x = 1200;
        }
        if(this.y < -90) {
        	this.y = 800;
        }
        if(this.y > 800) {
        	this.y = 0;
        }
		if(ki.getRotateRight()) {
			rotation+=0.07;
		}
		if(ki.getRotateLeft()) {
			rotation-=0.07;
		}
		//Acceleration of ship
		if(ki.getUp()) {
			if(accel <= 5) {	
		        accel*=1.03;
			}			
			else {
				accel = 5;
			}
	        this.setDY(-accel*Math.cos(rotation));
	        this.setDX(accel*Math.sin(rotation));	
		}
		//Deceleration of ship
		if(!ki.getUp()) {
			if(accel > 1) {
				accel*=0.97;
		        this.setDY(-accel*Math.cos(rotation));
		        this.setDX(accel*Math.sin(rotation));	
			}
			else {
				accel = 1;
		        this.setDY(0);
		        this.setDX(0);
			}
		}
		if(ki.shootCheck()) {
			if(System.nanoTime() - lastShot >= FIRE_RATE) {
			    shootBullet();
			    lastShot = System.nanoTime();
			}
		}
		if(Math.toDegrees(rotation) > 360 || Math.toDegrees(rotation) < -360) { // keep in range of -360 to 360
			rotation = 0;
		}
		for(int i=0; i<handler.object.size(); i++) {
			if(handler.object.get(i).getID().equals(ID.Enemy)) {
				if(this.collides(handler.object.get(i).getBounds())) {
					handler.removeObject(handler.object.get(i));
					lives--;
					game.setNumEnemies(game.getNumEnemies()-1);
				}
			}
		}
        x += dx;
        y += dy;
	}
	
	public void incScore() {
		score++;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public int getScore() {
		return score;
	}
	
	public void shootBullet() {
		
		//Rotation of Matrix around 2D Point(x,y). 
		
		double centerX = x + 20; // center x coordinate of triangle
		double centerY = y - 25; // center y coordinate of triangle
		
		//Rotate the (x,y) values around the center using the 'rotation' angle.
		double rotX = (x + 17 - centerX)*Math.cos(rotation) - (y - 50 - centerY)*Math.sin(rotation);
		double rotY = (x + 17 - centerX)*Math.sin(rotation) + (y - 50 - centerY)*Math.cos(rotation);
		
		//Translate the coordinates back and return the new (x,y) pair.
		double newx = centerX + rotX;
		double newy = centerY + rotY;
		
	    Bullet b = new Bullet(game, handler, (int) newx, (int) newy, ID.Bullet);		
	    handler.addObject(b);
	    b.setDY(-17*Math.cos(rotation));
	    b.setDX(17*Math.sin(rotation));
	}

	@Override
	public void paintComponent(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g;
		//g2d.drawImage(image, 852, 480, null);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		AffineTransform old = g2d.getTransform();

		g2d.setColor(Color.WHITE);

		// rotating the hero, rotation point is the middle of the square
		g2d.rotate(rotation, x + getBounds().getWidth() / 2, y - 50 + getBounds().getHeight() / 2);
		// drawing the square
		g2d.drawPolygon(new int[] {(int) x, (int) x + 20, (int) x + 40}, new int[] {(int) y, (int) y - 50, (int) y}, 3);
				
		//in case you have other things to rotate
		g2d.setTransform(old);

		g2d.setFont(new Font("Arial", Font.PLAIN, 20)); 
		g2d.setColor(Color.GREEN);
		
		//draw lives
		g2d.drawString("Lives: " + getLives(), 1050, 735);
		
		//draw score
		g2d.drawString("Kills: " + getScore(), 575, 735);
		
		//draw round
		g2d.drawString("Round: " + game.getRound(), 50, 735);	
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y - 50, 40, 50);
	}
	
	public boolean collides(Rectangle r) {
		if(this.getBounds().intersects(r)) {
			return true;
		}
		return false;
	}
}
