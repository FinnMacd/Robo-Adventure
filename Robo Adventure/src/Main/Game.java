package Main;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Game {
	
	public static final String title = "Robo Adventures";
	
	public static void main(String[] args){
		
		new Game();
		
	}
	
	public Game(){
		JFrame window = new JFrame(title);
		
		try {
			window.setIconImage(ImageIO.read(getClass().getResourceAsStream("/Icon.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		window.add(new GamePanel(window));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
	}
	
}
