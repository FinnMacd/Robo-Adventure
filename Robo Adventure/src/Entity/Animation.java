package Entity;

import java.awt.image.BufferedImage;

public class Animation {
	
	private BufferedImage[] images;
	
	private int currentFrame;
	
	private long start,delay;
	
	public boolean playedOnce;
	
	public Animation(){
		playedOnce = false;
	}
	
	public void setFrames(BufferedImage[] images){
		
		this.images = images;
		currentFrame = 0;
		start = System.nanoTime();
		playedOnce = false;
		
	}
	
	public void setDelay(long d){delay = d;}
	public void setFrame(int f){currentFrame = f;}
	
	public void update(){
		
		if(delay == -1)return;
		
		long elapsed = (System.nanoTime()-start)/1000000;
		
		if(elapsed >= delay){
			currentFrame++;
			start = System.nanoTime();
		}
		
		if(currentFrame == images.length){
			currentFrame = 0;
			playedOnce = true;
		}
		
	}
	
	public int getFrame(){return currentFrame;}
	public BufferedImage getImage(){return images[currentFrame];}
	public boolean playedOnce(){return playedOnce;}
	
}














