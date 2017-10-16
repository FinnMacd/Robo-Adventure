package Enemy;

import java.awt.Graphics2D;

import TileMap.TileMap;
import Entity.MapObject;

public abstract class Enemy extends MapObject{
	
	public int health;
	
	public final static int HURT = 0, NOTHURT = 1;
	
	protected boolean getShot;
	
	protected int hurtState;
	
	public Enemy(TileMap tileMap) {
		super(tileMap);
		hurtState = HURT;
	}
	
	public void hit(int damage){
		health -= damage;
		if(health <= 0){
			dead = true;
		}
	}
	
	public void update(){}
	
	public void draw(Graphics2D g){
		setMapPosition();
		super.draw(g);
	}
	
	public void changeDir(){
		facingRight = !facingRight;
		dx = -dx;
	}
	
	public int getHurtState(){
		return hurtState;
	}
	
	public boolean getShot(){
		return getShot;
	}

}
