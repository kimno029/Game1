package OurGame;

import javax.swing.*;

public class Frame {

		public static void main(String[] args)
		{
			JFrame frame = new JFrame("2D Game");
			frame.add(new Board());
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(1215,638); // x margin 15, y margin 38 y Windows default= 638
			frame.setVisible(true);
		}
}
