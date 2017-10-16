package GameState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Audio.PlaySound;
import Enemy.*;
import Entity.*;
import Main.GamePanel;
import TileMap.Background;
import TileMap.TileMap;

public class PlayState extends GameState{
	
	private Background bg;
	
	private TileMap tileMap;
	
	public Player player;
	
	private ArrayList<Bullet> bullets;
	private ArrayList<Powerup> powerups;
	private ArrayList<PlaySound> sfx;
	
	public static final int BULLET=0,ENEMY=1,PLAYER=2,SHOOT=3,POWERUP=4,SAVE=5,JUMP=6;
	
	public static boolean playsound = true;
	
	private boolean catchPause;
	
	private DisplayText displayText;
	
	private ArrayList<Enemy> enemies;
	
	private ArrayList<SavePoint> savePoints;
	
	private String secs,mins;
	
	private int currentSavePoint;
	
	private boolean isSavePoint, goneOnce = false;
	
	private Point savePoint;
	
	private Portal portal;
	
	private long startTime,time;
		
	public PlayState(GameStateManager gsm){
		this.gsm = gsm;
		init();
	}

	public void init() {
		
		bg = new Background("/Backgrounds/Background.png",1);
		
		tileMap = new TileMap(this,32);
		
		powerups = new ArrayList<Powerup>();
		bullets = new ArrayList<Bullet>();
		enemies = new ArrayList<Enemy>();
		savePoints = new ArrayList<SavePoint>();
		sfx = new ArrayList<PlaySound>();
		
		isSavePoint = false;
		currentSavePoint = -1;
		
		savePoint = new Point();
		
		player = new Player(tileMap,bullets,this);
		player.init();
		
		tileMap.loadTiles("/Tiles/TileSet.png");
		tileMap.loadMap("/Map/Map.map");
		tileMap.setPosition(0, 0);
		tileMap.setSmothness(0.06);
		
		displayText = new DisplayText();
		displayText.init();
		String[] startText = {"Use arrow keys to move. Collect upgrades","to reach the portal and excape!"};
		displayText.setText(startText,10);
		
		sfx.add(new PlaySound("/SFX/BulletHit.wav"));
		sfx.add(new PlaySound("/SFX/EnemyHit.wav"));
		sfx.add(new PlaySound("/SFX/PlayerHit.wav"));
		sfx.add(new PlaySound("/SFX/Shoot.wav"));
		sfx.add(new PlaySound("/SFX/PowerUp.wav"));
		sfx.add(new PlaySound("/SFX/Save.wav"));
		sfx.add(new PlaySound("/SFX/Jump.wav"));
		
		startTime = System.nanoTime();
		
	}
	
	public void update() {
		
		player.update();
		
		tileMap.setPosition(GamePanel.WIDTH/2-player.getX(), GamePanel.HEIGHT/2-player.getY());
		
		bg.setPosition(tileMap.getX(), tileMap.getY());
		
		for(int p = 0;p<bullets.size();p++){
			bullets.get(p).update();
			if(bullets.get(p).isDead()){
				if(bullets.get(p).onScreen())playSound(BULLET);
				bullets.remove(p);
				p--;
			}
		}
		
		for (int i = 0; i < enemies.size(); i++) {
			
				enemies.get(i).update();
				for (int p = 0; p < bullets.size(); p++) {
					if (enemies.get(i).getShot()
							&& enemies.get(i).collision(bullets.get(p))
							&& !bullets.get(p).getHit()) {
						enemies.get(i).hit(1);
						enemies.get(i).changeDir();
						bullets.get(p).setHit();
					}
				}

				if (enemies.get(i).getHurtState() == Enemy.HURT
						&& enemies.get(i).collision(player)) {
					if (isSavePoint) {
						player.setPos(savePoints.get(currentSavePoint).getX(),
								savePoints.get(currentSavePoint).getY() - 1);
					} else {
						player.setPos(savePoint.getX(), savePoint.getY());
					}

					player.setJumping(false);
					player.setDown(false);
					player.setLeft(false);
					player.setRight(false);

					playSound(PLAYER);

				}

				if (enemies.get(i).isDead()) {
					enemies.remove(i);
					i--;
					playSound(ENEMY);
					sfx.get(BULLET).stop();
				}
			
		}
		
		for(int i = 0; i < powerups.size();i++){
			powerups.get(i).update();
			if(powerups.get(i).isDead()){
				powerups.remove(i);
				i--;
				playSound(POWERUP);
			}
		}
		
		portal.update();
		
		if(player.collision(portal)){
			gsm.setEnd(mins, secs);
			gsm.changeState(GameStateManager.ENDSTATE);
		}
		
		for(int i = 0; i < savePoints.size();i++){
			
			if(savePoints.get(i).collision(player)&&currentSavePoint != i){
				currentSavePoint = i;
				for(int j = 0; j < savePoints.size();j++){
					savePoints.get(j).setSaved(false);
				}
				savePoints.get(i).setSaved(true);
				isSavePoint = true;
				displayText.setText("Robot pattern encoded.");
				playSound(SAVE);
			}
			savePoints.get(i).update();
		}
		
		displayText.update();
		
		time = System.nanoTime()-startTime;
		
	}

	public void draw(Graphics2D g) {
		
		bg.draw(g);
		
		for(int i = 0; i < savePoints.size();i++){
			if(savePoints.get(i).onScreen())
			savePoints.get(i).draw(g);
		}
		
		player.draw(g);
		
		tileMap.draw(g);
		
		for(int i = 0; i < powerups.size();i++){
			if(powerups.get(i).onScreen())
			powerups.get(i).draw(g);
		}
		
		for(int i = 0; i < enemies.size();i++){
			if(enemies.get(i).onScreen())
			enemies.get(i).draw(g);
		}
		
		for(int i = 0;i<bullets.size();i++){
			if(bullets.get(i).onScreen())
			bullets.get(i).draw(g);
		}
		
		portal.draw(g);
		
		displayText.draw(g);
		
		g.setColor(Color.red);
		
		
		
		if((time/1000000000)%60<10)secs = "0" + (time/1000000000)%60;
		else secs = ""+(time/1000000000)%60;
		
		if((time/1000000000)/60%60<10)mins = "0" + (time/1000000000)/60%60;
		else mins = ""+(time/1000000000)/60%60;
		
		g.drawString(mins+":"+secs, 10, 40);
		
		if(!goneOnce){
			goneOnce = true;
			startTime = System.nanoTime();
		}
		
	}

	public void keyPressed(int e) {
		if(e == KeyEvent.VK_UP)player.setJumping(true);
		if(e == KeyEvent.VK_DOWN)player.setDown(true);
		if(e == KeyEvent.VK_LEFT)player.setLeft(true);
		if(e == KeyEvent.VK_RIGHT)player.setRight(true);
		if(e == KeyEvent.VK_SPACE)player.setFiring(true);
		if(e == KeyEvent.VK_P){
			catchPause = true;
		}
	}

	public void keyReleased(int e) {
		if(e == KeyEvent.VK_DOWN)player.setDown(false);
		if(e == KeyEvent.VK_LEFT)player.setLeft(false);
		if(e == KeyEvent.VK_RIGHT)player.setRight(false);
		if(e == KeyEvent.VK_SPACE)player.setFiring(false);
		if(e == KeyEvent.VK_P&&catchPause){
			gsm.changeState(GameStateManager.PAUSESTATE);
		}
		catchPause = false;
	}

	public void focusLost() {
		gsm.changeState(GameStateManager.PAUSESTATE);
		System.out.println("focus lost");
	}

	public void focusGained() {
		
	}
	
	public void addPowerup(int type,int x, int y){
		
		powerups.add(new Powerup(tileMap,player,type));
		powerups.get(powerups.size()-1).setPos(x+tileMap.getTileSize()/2, y+tileMap.getTileSize()/2);
		
	}
	
	public void addEnemy(int type, int x, int y){
		int temp = 0;
		if(type == 0){
			temp = 1;
			enemies.add(new Creeper(tileMap));
		}else if(type == 1){
			enemies.add(new Lava(tileMap,type-1));
		}else if(type == 2){
			enemies.add(new Lava(tileMap,type-1));
		}
		enemies.get(enemies.size()-1).setPos(x+16, y+tileMap.getTileSize()/2-temp);
	}
	
	public void setPlayerPos(int x,int y){
		
		player.setPos(x+tileMap.getTileSize()/2, y+tileMap.getTileSize()/2);
		
		savePoint.move(x+tileMap.getTileSize()/2, y+tileMap.getTileSize()/2);
		
	}
	
	public void setPortal(int x, int y){
		portal = new Portal(tileMap,x+tileMap.getTileSize()/2,y+tileMap.getTileSize()/2);
	}
	
	public void setDisplayText(String s){
		displayText.setText(s);
	}
	
	public void addSavePoint(int x,int y){
		savePoints.add(new SavePoint(tileMap, x+tileMap.getTileSize()/2, y+tileMap.getTileSize()/2));
	}
	
	public void playSound(int i) {
		if(!playsound)return;
		try {
			sfx.get(i).playSound();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
