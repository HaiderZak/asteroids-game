//Zak Haider

import javax.swing.JFrame;

public class Main{
	public Main(Game game) {
		JFrame frame = new JFrame("Asteroids by Zak Haider");
        frame.pack();
		frame.setSize(1200,800);
		frame.setLocationRelativeTo(null);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(game);
		frame.setVisible(true);
		game.start();
	}
}
