//Zak Haider

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class Enemy extends GameObject {
	private Handler handler;
	Game game;
	private BufferedImage image;

	public Enemy(Game game, Handler handler, double x, double y, ID id) {
		super(x, y, id);
		this.handler = handler;
		this.game = game;
        URL resource = getClass().getResource("/resources/asteroid.png");
        try {
            image = ImageIO.read(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	@Override
	public void tick() {
		if(getX() < -100 || getX() > 1300 || getY() > 1000 || getY() < -100) {
			handler.removeObject(this);
			game.setNumEnemies(game.getNumEnemies()-1);
		}
		x += dx;
		y += dy;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(image, (int) x, (int) y, game);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) x,(int) y,50,50);
	}
}
