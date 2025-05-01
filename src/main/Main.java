package main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		
		JFrame window = new JFrame();
		GamePanel gp = new GamePanel();
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("Pikimin 5");
		

		window.add(gp);
		
		window.pack(); 
		
		window.setLocationRelativeTo(null);//window will be displayed on the center of the screen
		window.setVisible(true);
		
		gp.startGameThread();
	}

}
