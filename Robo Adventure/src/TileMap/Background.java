package TileMap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Main.GamePanel;

public class Background {
	
	private double x,y,dx,dy,movescale;
	
	private BufferedImage image;
	
	private int WIDTH,HEIGHT;
	
	public Background(String file, double movescale){
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(file));
			WIDTH = GamePanel.WIDTH;
			HEIGHT = GamePanel.HEIGHT;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.movescale = movescale;
		
	}
	
	public void setVector(double dx, double dy){
		this.dx = dx;
		this.dy = dy;
	}
	
	public void update(){
		if(x<=-WIDTH)x = 0;
		if(x>=WIDTH)x = 0;
		if(y<=-HEIGHT)y = 0;
		if(y>=HEIGHT)y = 0;
		x += dx;
		y += dy;
	}
	
	public void setPosition(double x,double y){
		this.x = (x*movescale)%WIDTH;
		this.y = (y*movescale)%HEIGHT;
	}
	
	public void draw(Graphics2D g){
		g.drawImage(image, (int)x, (int)y, WIDTH, HEIGHT, null);
		if(x<0)g.drawImage(image, (int)x+WIDTH, (int)y, WIDTH, HEIGHT, null);
		if(x>0)g.drawImage(image, (int)x-WIDTH, (int)y, WIDTH, HEIGHT, null);
		if(y<0)g.drawImage(image, (int)x, (int)y+HEIGHT, WIDTH, HEIGHT, null);
		if(y>0)g.drawImage(image, (int)x, (int)y-HEIGHT, WIDTH, HEIGHT, null);
		if(x<0&&y<0)g.drawImage(image, (int)x+WIDTH, (int)y+HEIGHT, WIDTH, HEIGHT, null);
		if(x>0&&y<0)g.drawImage(image, (int)x-WIDTH, (int)y+HEIGHT, WIDTH, HEIGHT, null);
		if(x<0&&y>0)g.drawImage(image, (int)x+WIDTH, (int)y-HEIGHT, WIDTH, HEIGHT, null);
		if(x<0&&y>0)g.drawImage(image, (int)x-WIDTH, (int)y-HEIGHT, WIDTH, HEIGHT, null);
	}
	
}
