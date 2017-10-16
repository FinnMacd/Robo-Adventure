package Main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import Audio.PlaySound;
import GameState.GameStateManager;

@SuppressWarnings("serial")
public class GamePanel extends Canvas implements KeyListener,Runnable,FocusListener{
	
	public static final int WIDTH = 640, HEIGHT = 480, scale = 1;
	
	private Thread thread;
	private boolean running = false;
	
	private BufferedImage image;
	private Graphics2D g;
	
	private GameStateManager gsm;
	private PlaySound music;
	
	private JFrame frame;
	
	public GamePanel(JFrame window){
		setPreferredSize(new Dimension(WIDTH*scale,HEIGHT*scale));
		addKeyListener(this);
		setFocusable(true);
		requestFocus();
		
		frame = window;
		
	}
	
	public void addNotify(){
		super.addNotify();
		if(thread == null){
			
			thread = new Thread(this);
			thread.start();
			running = true;
			
		}
	}
	
	public void init(){
		
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		
		addKeyListener(this);
		
		gsm = new GameStateManager();
		
		music = new PlaySound("/Music/recording.wav");
		music.playSound();
	}
	
	public void run() {
		
		init();
		
		addFocusListener(this);
		
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		//ups
		final double ns = 1000000000.0/50.0;//50
		double delta = 0;
		int frames = 0;
		int updates = 0;
		
		while(running){
			
			long now = System.nanoTime();
			delta += (now-lastTime)/ns;
			lastTime = now;
			while(delta >= 1){
				update();
				updates++;
				delta--;
			}
			
			draw();
			drawToScreen();
			frames++;
			
			if(System.currentTimeMillis()-timer >= 1000){
				timer += 1000;
				frame.setTitle(Game.title+" | "+updates+" ups, "+frames+" fps");
				updates = frames = 0;
			}
			
		}
		
	}
	
	public void update(){
		gsm.update();
	}
	
	public void draw(){
		gsm.draw(g);
	}
	
	public void drawToScreen(){
		
		BufferStrategy bs = getBufferStrategy();
		
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
		
		
		Graphics g2 = bs.getDrawGraphics();
		g2.drawImage(image, 0, 0, WIDTH*scale, HEIGHT*scale, null);
		g2.dispose();
		
		bs.show();
		
	}
	
	public void keyPressed(KeyEvent e) {
		gsm.keyPressed(e.getKeyCode());
	}

	public void keyReleased(KeyEvent e) {
		gsm.keyReleased(e.getKeyCode());
	}

	public void keyTyped(KeyEvent e) {
		
	}

	public void focusGained(FocusEvent arg0) {
		gsm.focusGained();
	}

	public void focusLost(FocusEvent arg0) {
		gsm.focusLost();
	}
	
}
