 package GameState;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class GameStateManager {
	
	private ArrayList<GameState> gamestates;
	private int currentstate;
	
	public static final int MENUSTATE = 0,HELPSTATE = 1,LEVELSTATE = 2,PAUSESTATE= 3,CREDITSTATE = 4, ENDSTATE = 5;
	
	public static String ends = "";
	public static String endm = "";
	
	public GameStateManager(){
		init();
	}
	
	public void init(){
		
		gamestates = new ArrayList<GameState>();
		
		currentstate = MENUSTATE;
		
		gamestates.add(new MenuState(this));
		gamestates.add(new HelpState(this));
		gamestates.add(new PlayState(this));
		gamestates.add(new PauseState(this));
		gamestates.add(new CreditState(this));
		gamestates.add(new EndState(this));
		
	}
	
	public void changeState(int state){
		currentstate = state;
		if(state == PAUSESTATE||state == HELPSTATE||state == CREDITSTATE){
			gamestates.get(currentstate).init();
		}
	}
	
	public void update(){
		gamestates.get(currentstate).update();
	}
	
	public void draw(Graphics2D g){
		gamestates.get(currentstate).draw(g);
	}
	
	public void keyPressed(int e){
		gamestates.get(currentstate).keyPressed(e);
	}
	
	public void keyReleased(int e){
		gamestates.get(currentstate).keyReleased(e);
	}

	public void focusLost() {
		gamestates.get(currentstate).focusLost();
	}
	
	public void focusGained() {
		gamestates.get(currentstate).focusGained();
	}
	
	public void setEnd(String m,String s){
		ends = s;
		endm = m;
	}
	
}
