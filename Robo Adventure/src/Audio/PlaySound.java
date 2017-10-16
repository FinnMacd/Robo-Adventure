package Audio;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class PlaySound{

	private Clip clip;

	public PlaySound(String s) {
		
		init(s);
		
	}
	
	public void update(){
		if(clip.isRunning())return;
		clip.setFramePosition(0);
		clip.start();
	}

	private void init(String s) {
		try {
			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(getClass().getResource(s));
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	public void playSound() {

		if (clip.isRunning())
			clip.stop();
		clip.setFramePosition(0);
		clip.start();

	}
	
	public void stop(){
		if (clip.isRunning())
			clip.stop();
	}

}