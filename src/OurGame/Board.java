package OurGame;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;

public class Board extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Player p;
	Solid solid;
	Physics physics;
	Image img;
	Timer time;
	int aniCounter = 0;
	
	Rectangle bounds;

	public Board() {
		solid = new Solid(1200, 600);
		physics = new Physics(solid);
		p = new Player(solid,physics);
		
		setFocusable(true);
		ImageIcon i = new ImageIcon("src/img/bg.png");
		img = i.getImage();
		time = new Timer(15, this);
		time.start();
		
		
		addKeyListener( new AL() );
		System.out.println("Solid px, x 553: "+ solid.solidPx(551, 149) );
		

	}

	public void actionPerformed(ActionEvent e) {
		p.move();
		repaint();
	}

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		
		//g2d.drawImage(img, 0, 0, null);
		for(int i=0; i < solid.getHBlocks(); i++){ // Draws blocks according to bool 2dArray
			for(int j=0; j < solid.getWBlocks(); j++){
				
				if(solid.isSolid(i,j))
					g2d.drawImage(solid.getImg(), solid.getSize()*j, solid.getSize()*i, null);
			
			}
		}
		
		
		p.paint(g2d); // sends g2d to p.draw( Graphics2D g2d)
		
		aniCounter++;
	}
	
	

	private class AL extends KeyAdapter {
		public void keyReleased(KeyEvent e) {
			p.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) {
			p.keyPressed(e);
		}
	}
}
