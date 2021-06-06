//Zak Haider

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;

public class Bullet extends GameObject {
	private Handler handler;
	Game game;

	public Bullet(Game game, Handler handler, double x, double y, ID id) {
		super(x, y, id);
		this.handler = handler;
		this.game = game;
	}

	@Override
	public void tick() {
		x+=dx;
		y+=dy;
    	if(this.getX() > 1200-10 || this.getY() > 800-10 || this.getX() < 0 || this.getY() < 0) {
    		handler.removeObject(this);
    	}
        Player player = null;
        for(int i=0; i<handler.object.size(); i++) {
        	if(handler.object.get(i).id.equals(ID.Player)) {
        		player = (Player) handler.object.get(i);
        	}
        }
		for(int i=0; i<handler.object.size(); i++) {
			if(handler.object.get(i).getID().equals(ID.Enemy)) {
				if(this.collides(handler.object.get(i).getBounds())) {
					handler.removeObject(handler.object.get(i));
					handler.removeObject(this);
					game.setNumEnemies(game.getNumEnemies()-1);
					player.incScore();
				}
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {
        Player player = null;
        for(int i=0; i<handler.object.size(); i++) {
        	if(handler.object.get(i).id.equals(ID.Player)) {
        		player = (Player) handler.object.get(i);
        	}
        }
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		AffineTransform old = g2d.getTransform();
		g2d.setColor(Color.RED);
		
		// rotating the hero, rotation point is the middle of the square
		g2d.rotate(player.rotation, x + this.getBounds().getWidth() / 2, y + this.getBounds().getHeight() / 2);
		// drawing the square
		g2d.drawRect((int) x, (int) y, (int) this.getBounds().getWidth(), (int) this.getBounds().getHeight());
		//in case you have other things to rotate
		g2d.fillRect((int)x, (int)y, (int) getBounds().getWidth(), (int) getBounds().getHeight());

		g2d.setTransform(old);
		}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y,4,7);
	}
	
	public boolean collides(Rectangle r) {
		if(this.getBounds().intersects(r)) {
			return true;
		}
		return false;
	}
}
