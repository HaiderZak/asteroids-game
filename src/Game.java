//Zak Haider

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;


public class Game extends Canvas implements Runnable{
	
	private static final long serialVersionUID = 1L;
	private Thread thread;
	private boolean running;
	private Handler handler;
	KeyInput ki;
	Player player;
	private int round = 0;
	boolean roundFinished = true;
	private int numEnemies = 0;
	private double acc = 0;
	
	public enum STATE{
		PLAYING,
		GAMEOVER
	}
	
	public STATE gameState = STATE.PLAYING;
	
	public int getGameState() {
		if(gameState == STATE.PLAYING) {
			return 1;
		}
		else {
			return 0;
		}
	}
	
	public Game() {
		new Main(this);
		handler = new Handler();
		ki = new KeyInput(this, handler);
		this.addKeyListener(ki);
		
		if(gameState == STATE.PLAYING) {
			generateStars();
			generateStarSizes();
			player = new Player(this,handler,ki,574,350,ID.Player);
			handler.addObject(player);	
		}
	}
	
	public void initStart() {
		this.round = 0;
		this.acc = 0;
		this.roundFinished = true;
		handler = new Handler();
		this.addKeyListener(ki);
		player = new Player(this,handler,ki,574,350,ID.Player);
		player.setLives(3);
		player.setScore(0);
		player.setX(574);
		player.setY(350);
		player.setRotation(0);
		handler.object.clear();
		handler.addObject(player);
	}
	
	public int getRound() {
		return round;
	}
	
	public void setNumEnemies(int numEnemies) {
		this.numEnemies = numEnemies;
	}
	
	public int getNumEnemies() {
		return numEnemies;
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		final double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			if(delta >= 1) {
				tick();
				delta--;
			}
			render();
	
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
			}
			
		}
		stop();			
	}
	
	public void tick() {
		if(gameState == STATE.PLAYING) {
			handler.tick();
			if(player != null && player.getLives() <= 0) {
				gameState = STATE.GAMEOVER;
			}
			
			if(roundFinished == true) {
				round++;
				acc+=0.2;
				roundFinished = false;
				spawnEnemies();
			}
			
	        if(numEnemies <= 0) {
	        	roundFinished=true;
	        }	
		}
	}

	/* Spawn enemies by generating a random integer between 1 and 4 (these numbers represent different sides of the screen e.g., top, left, right, bottom).
	 * The enemies vector direction is determined using simple trig functions to point it to center of the screen.
	*/
	public void spawnEnemies() {
		numEnemies = 0;
		while(numEnemies < round+10) {
			int spawnInt = 1 + (int) (Math.random() * (4));
			int x1 = 0;
			int y1 = 0;
			if(spawnInt == 1) {
				x1 = top()[0];
				y1 = top()[1];
			}
			if(spawnInt == 2) {
				x1 = left()[0];
				y1 = left()[1];
			}
			if(spawnInt == 3) {
				x1 = right()[0];
				y1 = right()[1];
			}
			if(spawnInt == 4) {
				x1 = bottom()[0];
				y1 = bottom()[1];
			}
			int x2 = 574; // middle X
			int y2 = 350; // middle Y
			Enemy enemy = new Enemy(this,handler,x1,y1,ID.Enemy);
			// set direction and speed of enemy towards the center of the screen using
			// the slope between the spawn position of the enemy and the position of the center of the screen.
			int adj = x2 - x1;
			int opp = y2 - y1;
			double angle = Math.atan2(opp,adj);
			if(spawnInt == 1 && x1 < 600) {
				enemy.setDX(1+getRandomDouble(acc,acc+0.4) * Math.cos(angle));
				enemy.setDY(1+getRandomDouble(acc,acc+0.4) * Math.sin(angle));	
			}
			if(spawnInt == 1 && x1 >= 600) {
				enemy.setDX(-1+getRandomDouble(acc,acc+0.4) * Math.cos(angle));
				enemy.setDY(1+getRandomDouble(acc,acc+0.4) * Math.sin(angle));	
			}
			if(spawnInt == 2 && y1 < 450) {
				enemy.setDX(1+getRandomDouble(acc,acc+0.4) * Math.cos(angle));
				enemy.setDY(1+getRandomDouble(acc,acc+0.4) * Math.sin(angle));	
			}
			if(spawnInt == 2 && y1 >= 450) {
				enemy.setDX(1+getRandomDouble(acc,acc+0.4) * Math.cos(angle));
				enemy.setDY(-1+getRandomDouble(acc,acc+0.4) * Math.sin(angle));	
			}
			if(spawnInt == 3 && y1 < 450) {
				enemy.setDX(-1+getRandomDouble(acc,acc+0.4) * Math.cos(angle));
				enemy.setDY(1+getRandomDouble(acc,acc+0.4) * Math.sin(angle));	
			}
			if(spawnInt == 3 && y1 >= 450) {
				enemy.setDX(-1+getRandomDouble(acc,acc+0.4) * Math.cos(angle));
				enemy.setDY(-1+getRandomDouble(acc,acc+0.4) * Math.sin(angle));	
			}
			if(spawnInt == 4 && x1 < 600) {
				enemy.setDX(1+getRandomDouble(acc,acc+0.4) * Math.cos(angle));
				enemy.setDY(-1+getRandomDouble(acc,acc+0.4) * Math.sin(angle));	
			}
			if(spawnInt == 4 && x1 >= 600) {
				enemy.setDX(-1+getRandomDouble(acc,acc+0.4) * Math.cos(angle));
				enemy.setDY(-1+getRandomDouble(acc,acc+0.4) * Math.sin(angle));	
			}
			handler.addObject(enemy);
			numEnemies++;
		}
	}
	
	public int getRandomNumber(int min, int max) {
	    return (int) ((Math.random() * (max - min)) + min);
	}
	
	public double getRandomDouble(double min, double max) {
	    return ((Math.random() * (max - min)) + min);
	}
	
	/* These 4 methods are used to spawn the asteroids randomly on either side of the screen. 
	 * This data is processed inside of spawnEnemies() method. */
	public int[] top() {
		int x1 = getRandomNumber(0,1200);
		int y1 = getRandomNumber(-75,-50);
		return new int[] {x1,y1};
	}
	
	public int[] left() {
		int x1 = getRandomNumber(-75,-50);
		int y1 = getRandomNumber(0,900);
		return new int[] {x1,y1};
	}
	
	public int[] right() {
		int x1 = getRandomNumber(1250,1275);
		int y1 = getRandomNumber(0,900);
		return new int[] {x1,y1};
	}
	
	public int[] bottom() {
		int x1 = getRandomNumber(0,1200);
		int y1 = getRandomNumber(950,975);
		return new int[] {x1,y1};
	}
	
	//Generate (x,y) coordinate pair inside of a 2D array for star location (0 <= x <= 1200, 0 <= y <= 900)
	int[][] arr = new int[100][2];
	public int[][] generateStars() {
		for(int i=0; i<arr.length; i++) {
			int x = getRandomNumber(0,1200);
			int y = getRandomNumber(0,900);	
			arr[i][0] = x;
			arr[i][1] = y;
		}
		return arr;
	}
	// Generate the different sizes for each star (1 <= r <= 7)
	int[] arr2 = new int[100];
	public int[] generateStarSizes() {
		for(int i=0; i<arr2.length; i++) {
			int a = getRandomNumber(1,7);	
			arr2[i] = a;
		}
		return arr2;
	}
	
	private long blinkTimer;
	private long breakTimer;
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		//Draw background
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1200, 900);
		g.setColor(Color.WHITE);

		for(int i=0; i<arr.length; i++) {
			g.fillOval(arr[i][0], arr[i][1], arr2[i], arr2[i]);
		}

		if(gameState == STATE.PLAYING) {
			g.drawLine(0, 700, 1200, 700);
			handler.render(g);	
		}
		else {
			g.setFont(new Font("Arial", Font.PLAIN, 35)); 
			g.setColor(Color.RED);
			g.drawString("GAME OVER!", 480, 150);
			g.setColor(Color.WHITE);
			long elapsed = (System.nanoTime() - blinkTimer) / 1000000;
		    if(elapsed < 500){
				g.drawString("PRESS SPACE TO PLAY AGAIN", 341, 600);
		        breakTimer = System.nanoTime();
		    }

		    long breakElapsed = (System.nanoTime() - breakTimer) / 1000000;
		    if(breakElapsed > 500){
		        blinkTimer = System.nanoTime();
		    }
			g.drawString("Your Stats:", 505, 275);
			g.setFont(new Font("Arial", Font.PLAIN, 25)); 
			g.drawString("Round: " + getRound(), 533, 375);
			g.drawString("Kills: " + player.getScore(), 533, 425);
			if(ki.getPlayAgain() == true) {
				gameState = STATE.PLAYING;
				initStart();
				ki.setPlayAgain(false);
			}
		}

		g.dispose();
		bs.show();
	}
	
	public static void main(String[] args) {
		new Game();
	}
}
