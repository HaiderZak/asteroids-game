//Zak Haider

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{
	Handler handler;
	boolean rotateRight = false;
	boolean rotateLeft = false;
	boolean up = false;
	boolean shoot = false;
	boolean playAgain = false;
	Game game;
	
	public boolean getRotateRight() {
		return rotateRight;
	}
	
	public boolean getRotateLeft() {
		return rotateLeft;
	}
	
	public boolean getUp() {
		return up;
	}
	
	public boolean shootCheck() {
		return shoot;
	}
	
	public boolean getPlayAgain() {
		return playAgain;
	}
	
	public void setPlayAgain(boolean playAgain) {
		this.playAgain = playAgain;
	}
	
	public KeyInput(Game game, Handler handler) {
		this.handler = handler;
		this.game = game;
	}

	
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
       
        if(key == KeyEvent.VK_RIGHT) {
            rotateRight = true;
        }if(key == KeyEvent.VK_LEFT) {
            rotateLeft = true;
        }if(key == KeyEvent.VK_UP) {
        	up = true;
        	//System.out.println("x,y: " + player.x + "," + player.y + ", Rotation: " + player.rotation + ", DX: " + player.getDX() + ", DY: " + player.getDY());
        }
        if(key == KeyEvent.VK_SPACE && game.getGameState() == 1) {
        	shoot = true;
        }
        if(key == KeyEvent.VK_SPACE && game.getGameState() == 0) {
        	playAgain = true;
        }
    }
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        
        if(key == KeyEvent.VK_RIGHT) {
            rotateRight = false;
        }else if(key == KeyEvent.VK_LEFT) {
            rotateLeft = false;
        }else if(key == KeyEvent.VK_UP) {
        	up = false;
        }
        if(key == KeyEvent.VK_SPACE && game.getGameState() == 1) {
        	shoot = false;
        }
    }
}
