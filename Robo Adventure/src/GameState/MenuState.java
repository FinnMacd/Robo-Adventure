package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Entity.Animation;
import Main.GamePanel;
import TileMap.Background;

public class MenuState extends GameState{
	
	private Background bg;
	
	private Color titleColor;
	private Font titleFont,font;
	
	private String[] options = {"Start","Help","Credits","Quit"};
	
	private int currentChoice;
	
	private BufferedImage[] player;
	
	private Animation animation;
	
	private boolean catchKeys;
	
	public MenuState(GameStateManager gsm){
		this.gsm = gsm;
		init();
	}

	public void init() {
		bg = new Background("/Backgrounds/MenuBackground.png",0);
		bg.setVector(-0.5, 0);
		
		titleColor = new Color(128,0,0);
		titleFont = new Font("Times New Roman",Font.BOLD,70);
		font = new Font("Times New Roman",Font.PLAIN,48);
		
		animation = new Animation();
		
		BufferedImage sheet;
		player = new BufferedImage[2];
		
		try {
			sheet = ImageIO.read(getClass().getResourceAsStream("/Player/Player.png"));
			for(int i = 0; i < 2;i++){
				player[i] = sheet.getSubimage(i*32, 64, 32, 32);
			}
			animation.setFrames(player);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		animation.setDelay(100);
		
	}

	public void update() {
		bg.update();
		animation.update();
	}

	public void draw(Graphics2D g) {
		bg.draw(g);
		
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("Robo Adventure", 40, 70);
		g.setFont(font);
		for(int i = 0; i < options.length;i++){
			if(i == currentChoice){
				g.setColor(Color.black);
			}else{
				g.setColor(Color.red);
			}
			FontMetrics meterics = g.getFontMetrics(font);
			g.drawString(options[i], GamePanel.WIDTH/2-meterics.stringWidth(options[i])/2, 175+i*70);
		}
		
		g.drawImage(animation.getImage(), 32, 12*32, null);
		
	}

	public void select(){
		if(currentChoice == 0){
			gsm.changeState(GameStateManager.LEVELSTATE);
		}else if(currentChoice == 1){
			gsm.changeState(GameStateManager.HELPSTATE);
		}else if(currentChoice == 2){
			gsm.changeState(GameStateManager.CREDITSTATE);
		}else{
			System.exit(0);
		}
		catchKeys = false;
	}
	
	public void keyPressed(int e) {
		catchKeys = true;
		
	}

	public void keyReleased(int e) {
		if(e == KeyEvent.VK_DOWN&&!catchKeys){
			currentChoice++;
			if(currentChoice > options.length-1)currentChoice = 0;
		}
		if(e == KeyEvent.VK_UP&&!catchKeys){
			currentChoice--;
			if(currentChoice < 0)currentChoice = options.length-1;
		}
		if(e == KeyEvent.VK_SPACE||e == KeyEvent.VK_ENTER){
			select();
		}
		catchKeys = false;
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
