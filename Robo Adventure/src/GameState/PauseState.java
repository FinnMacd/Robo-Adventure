package GameState;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class PauseState extends GameState{
	
	private BufferedImage img;
	
	private boolean drawnOnce,catchPause;
	
	public PauseState(GameStateManager gsm) {
		this.gsm = gsm;
		init();
		try{
			img = ImageIO.read(getClass().getResourceAsStream("/Backgrounds/PauseScreen.png"));
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void init() {
		
		drawnOnce = false;
		
	}
	
	public void update() {
		
	}

	public void draw(Graphics2D g) {

		if (!drawnOnce) {
			g.drawImage(img, 0, 0, null);
			drawnOnce = true;
		}

	}

	public void keyPressed(int e) {
		if(e == KeyEvent.VK_P){
			catchPause = true;
		}
		if(e == KeyEvent.VK_UP){
			PlayState.playsound = true;
		}
		if(e == KeyEvent.VK_DOWN){
			PlayState.playsound = false;
		}
	}

	public void keyReleased(int e) {
		if(e == KeyEvent.VK_P&&catchPause){
			gsm.changeState(GameStateManager.LEVELSTATE);
		}
		catchPause = false;
	}

	public void focusLost() {
		
	}

	public void focusGained() {
		gsm.changeState(GameStateManager.LEVELSTATE);
	}

}
