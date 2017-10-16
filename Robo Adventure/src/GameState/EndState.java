package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import TileMap.Background;

public class EndState extends GameState{
	
	private Background bg;
	
	public EndState(GameStateManager gsm) {
		this.gsm = gsm;
		bg = new Background("/Backgrounds/Background.png",0);
	}

	public void init() {
		
	}

	public void update() {
		
	}

	public void draw(Graphics2D g) {
		bg.draw(g);
		if(GameStateManager.endm.startsWith("0"))GameStateManager.endm.replace("0", "");
		g.setColor(new Color(128,0,0));
		g.setFont(new Font("Times New Roman",Font.BOLD,100));
		g.drawString("You Won!", 100, 80);
		g.setColor(Color.red);
		g.setFont(new Font("Times New Roman",Font.PLAIN,55));
		g.drawString("It took you "+GameStateManager.endm+
				"mins and", 20, 150);
		g.drawString(GameStateManager.ends+ 
				"seconds to complete", 20, 225);
		g.drawString("the game. For an extra", 20, 300);
		g.drawString("challenge, try and beat the", 20, 375);
		g.drawString("makers time of 1:42", 20, 450);
		
	}

	public void keyPressed(int e) {
		
	}

	public void keyReleased(int e) {
		
	}

	public void focusLost() {
		
	}

	public void focusGained() {
		
	}

}
