package Entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import Main.GamePanel;

public class DisplayText{
	
	private String[] textA;
	private String text;
	
	private long start,elapsed;
	
	private Font font;
	
	private boolean done,ary,started;
	
	private float alpha;
	
	private int delay;
	
	public void init(){
		
		font = new Font("TimesNewRoman",Font.BOLD,28);
		ary = true;
		alpha = 1.0f;
		
	}
	
	public void setText(String[] text,int delay){
		this.textA = text;
		done = false;
		started = true;
		ary = true;
		alpha = 1.0f;
		this.delay = delay;
	}
	
	public void setText(String text){
		this.text = text;
		done = false;
		started = true;
		ary = false;
		alpha = 1.0f;
		delay = 6;
	}
	
	public void update(){
		
		if(started){
			started = false;
			start = System.nanoTime();
		}
		
		if(done)return;
		
		elapsed = System.nanoTime()-start;
		
		if(elapsed/1000000000 >= delay){
			
			alpha -= 0.009f;
			
		}
		
		if(alpha <= 0)done = true;
		
	}
	
	public void draw(Graphics2D g){
		
		if(done)return;
		
		Composite c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
	    g.setComposite(c);
		
	    FontMetrics metrics = g.getFontMetrics(font);
	    
	    g.setFont(font);
	    g.setColor(Color.white);
	    
	    if(ary){
	    	
	    	int y = metrics.getHeight();
	    	
	    	int x = metrics.stringWidth(textA[0]);
	    	g.drawString(textA[0], GamePanel.WIDTH/2-x/2, GamePanel.HEIGHT/2-y);
	    	
	    	x = metrics.stringWidth(textA[1]);
	    	g.drawString(textA[1], GamePanel.WIDTH/2-x/2, GamePanel.HEIGHT/2+y);
	    	
	    }else{
	    	
	    	int y = metrics.getHeight();
	    	
	    	int x = metrics.stringWidth(text);
	    	g.drawString(text, GamePanel.WIDTH/2-x/2, GamePanel.HEIGHT/2-y/2);
	    	
	    }
	    
	    c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
	    g.setComposite(c);
	    
	}
	
}
