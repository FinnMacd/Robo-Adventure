package Entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import Main.GamePanel;
import TileMap.*;

public abstract class MapObject {
	
	//tile stuff
	protected TileMap tileMap;
	protected int tileSize;
	protected double xmap,ymap;
	
	//position 
	protected double x,y,dx,dy; 
	
	protected double xdest,ydest,xtemp,ytemp;
	
	protected double moveSpeed,jumpStart,fallSpeed,maxFallSpeed,stopSpeed,slideSpeed;
	
	protected Animation animation;
	
	protected int height,width;
	
	protected int cheight,cwidth;
	
	protected boolean tr,tl,br,bl;
	protected boolean left,right,jumping,falling,down,djumping,candjump;
	
	protected boolean facingRight = true,dead = false;
	
	public MapObject(TileMap tileMap){
		this.tileMap = tileMap;
		tileSize = tileMap.getTileSize();
	}
	
	public boolean collision(MapObject o){
		
		Rectangle r1 = getRect();
		Rectangle r2 = o.getRect();
		
		return r1.intersects(r2);
		
	}
	
	public Rectangle getRect(){
		return new Rectangle((int)x-cwidth/2,(int)y-cheight/2,cwidth,cheight);
	}
	
	public void checkCorners(double x, double y,int offset){
		
		int leftcol = (int)(x-cwidth/2)/tileSize;
		int rightcol = (int)(x+cwidth/2)/tileSize;
		int toprow = (int)(y-cheight/2+offset)/tileSize;
		int botrow = (int)(y+cheight/2-offset)/tileSize;
		
		tl = tileMap.checkState(toprow, leftcol) == Tile.SOLID;
		tr = tileMap.checkState(toprow, rightcol) == Tile.SOLID;
		bl = tileMap.checkState(botrow, leftcol) == Tile.SOLID;
		br = tileMap.checkState(botrow, rightcol) == Tile.SOLID;
		
	}
	
	public void checkTileMapCollision(){
		
		xdest = x+dx;
		ydest = y+dy;
		
		xtemp = x;
		ytemp = y;
		
		checkCorners(x,ydest,0);
		if(dy<0){
			if(tl||tr){
				dy = 0;
			}else{
				ytemp+=dy;
			}
		}else if(dy>0){
			if(bl||br){
				dy = 0;
				falling = false;
			}else{
				ytemp+=dy;
			}
		}
		checkCorners(xdest,y,4);
		if(dx < 0){
			if(tl||bl){
				dx = 0;
			}else{
				xtemp+=dx;
			}
		}else if(dx > 0){
			if(tr||br){
				dx = 0;
			}else{
				xtemp+=dx;
			}
		}
		
		if(!falling){
			
			checkCorners(x,ydest+1,0);
			if(!br&&!bl){
				falling = true;
			}
			
		}
		
	}
	
	public void setPos(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public void setMapPosition(){
		xmap = tileMap.getX();
		ymap = tileMap.getY();
	}
	
	public void draw(Graphics2D g){
		
		if(facingRight){
			g.drawImage(animation.getImage(), (int)(x+xmap-width/2), (int)(y + ymap-height/2), null);
		}else{
			g.drawImage(animation.getImage(), (int)(x+xmap-width/2)+width, (int)(y + ymap-height/2),-width,height, null);
		}
		
	}
	
	public void setLeft(boolean b){left = b;}
	public void setRight(boolean b){right = b;}
	public void setDown(boolean b){down = b;}
	public void setJumping(boolean b){jumping = b;}
	
	public double getX(){return x;}
	public double getY(){return y;}
	public boolean getFacingRight(){return facingRight;}
	public boolean isDead(){return dead;}
	
	public boolean onScreen(){
		if(x == 0||xmap == 0||width==0||y==0||ymap==0||height==0){{
			return true;
		}
			
		}
		if(x+tileMap.getX()+(width/2)+16>0&&x+tileMap.getX()-(width/2)-16<GamePanel.WIDTH&&y + tileMap.getY()+(height/2)+16>0&&y + tileMap.getY()-(height/2)-16<GamePanel.HEIGHT){
			return true;
		}
		return false;
	}
	
}












