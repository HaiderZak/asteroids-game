import java.awt.*;       // Using AWT's Graphics and Color
import java.awt.event.*; // Using AWT event classes and listener interfaces
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;    // Using Swing's components and containers

public class Menu extends JFrame implements MouseListener {
	private static final long serialVersionUID = -32739589227090039L;
	// Define constants
	   public static final int CANVAS_WIDTH  = 710;
	   public static final int CANVAS_HEIGHT = 580;
	   private BufferedImage image;
	 
	   // Declare an instance of the drawing canvas,
	   // which is an inner class called DrawCanvas extending javax.swing.JPanel.
	   private DrawCanvas canvas;
	 
	   // Constructor to set up the GUI components and event handlers
	   public Menu() {
	        URL resource = getClass().getResource("/resources/menu.png");
	        try {
	            image = ImageIO.read(resource);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        addMouseListener(this);
	      canvas = new DrawCanvas();    // Construct the drawing canvas
	      canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
	 
	      // Set the Drawing JPanel as the JFrame's content-pane
	      Container cp = getContentPane();
	      cp.add(canvas);
	      // or "setContentPane(canvas);"
	 
	      setDefaultCloseOperation(EXIT_ON_CLOSE);   // Handle the CLOSE button
	      pack();              // Either pack() the components; or setSize()
	      setTitle("Asteroids by Zak Haider");  // "super" JFrame sets the title
	      setLocationRelativeTo(null);
	      setVisible(true);    // "super" JFrame show
	   }
	   
	   /**
	    * Define inner class DrawCanvas, which is a JPanel used for custom drawing.
	    */
	   private class DrawCanvas extends JPanel {
	      // Override paintComponent to perform your own painting
	      @Override
	      public void paintComponent(Graphics g) {
	         super.paintComponent(g);     // paint parent's background
	         setBackground(Color.BLACK);  // set background color for this JPanel
	         g.drawImage(image, 0,0, this);
	      }
	   }
	   // The entry main method
	   public static void main(String[] args) {
	      // Run the GUI codes on the Event-Dispatching thread for thread safety
	      SwingUtilities.invokeLater(new Runnable() {
	         @Override
	         public void run() {
	            new Menu(); // Let the constructor do the job
	         }
	      });
	   }
	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if(x >= 245 && x <= 478 && y >= 364 && y <= 411) {
			new Game();
			dispose();
		}
		if(x >= 246 && x <= 481 && y >= 430 && y <= 477) {
			System.exit(0);
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
