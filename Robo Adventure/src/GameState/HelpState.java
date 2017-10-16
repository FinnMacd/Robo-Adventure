package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import TileMap.Background;

public class HelpState extends GameState{
	
	private Background bg;
	
	private boolean once;
	
	public HelpState(GameStateManager gsm){
		this.gsm = gsm;
		init();
		bg = new Background("/Backgrounds/Background.png",0);
	}

	public void init() {
		once = false;
	}

	public void update() {
		
	}

	public void draw(Graphics2D g) {
		bg.draw(g);
		g.setColor(new Color(128,0,0));
		g.setFont(new Font("Times New Roman",Font.BOLD,100));
		g.drawString("Really?", 180, 250);
		if (once) {
			try {
				Thread.sleep(3000);
				gsm.changeState(GameStateManager.MENUSTATE);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		once = true;
	}

	public void keyPressed(int e) {
		
	}

	public void keyReleased(int e) {
		
		
	}

	@Override
	public void focusLost() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusGained() {
		// TODO Auto-generated method stub
		
	}
}
