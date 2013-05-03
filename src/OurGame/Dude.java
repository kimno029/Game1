package OurGame;

import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.*;

public class Dude {
	int x, dx, y;
	static int xMax = 2;
	Image still;

	public Dude() {
		ImageIcon i = new ImageIcon("src/img/dude.png");
		still = i.getImage();
		x = 10;
		y = 250;
	}

	public void move() {
		x = x + dx;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Image getStill() {
		return still;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT)
			if(dx > -xMax)
				dx -= 1;

		if (key == KeyEvent.VK_RIGHT)
			if(dx < xMax)
				dx += 1;
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT);
			dx = 0;

		if (key == KeyEvent.VK_RIGHT);
			dx = 0;
	}
}
