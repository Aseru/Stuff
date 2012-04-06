package shuf13.tools;

import java.io.File;
import java.util.Map;

import shuf13.exception.InternalPlayerException;

import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.jlgui.basicplayer.BasicPlayerListener;

public class S13Player {

	private BasicPlayer player;
	private BasicController control;
	
	private FileManager fileM;
	private File currentSong;
	
	public S13Player(FileManager fm) throws InternalPlayerException {
		try {
			initPlayer();
			
			fileM = fm;
			player.addBasicPlayerListener(new S13PlayerListener());
			
			currentSong = fileM.getNext();
			player.open(currentSong);
			
		} catch (BasicPlayerException e) {
			throw new InternalPlayerException();
		}
	}

	public void play() throws InternalPlayerException {
		try {
			player.play();

			control.setGain(0.95);
			control.setPan(0.0);
		}catch(BasicPlayerException e) {
			throw new InternalPlayerException();
		}
	}

	public void pause(boolean pause) throws InternalPlayerException {
		try {
			if(pause)
				player.pause();
			else
				player.resume();
		}catch(BasicPlayerException e) {
			throw new InternalPlayerException();
		}
	}

	public void stop() throws InternalPlayerException {
		try {
			player.stop();
		} catch (BasicPlayerException e) {
			throw new InternalPlayerException();
		}
	}
	
	//Fix button staying on pause when starting playing
	public void playNext() throws InternalPlayerException {
		try {
			player.stop();
			currentSong = fileM.getNext();
			player.open(currentSong);
			play();
		} catch (BasicPlayerException e) {
			throw new InternalPlayerException();
		}
	}
	
	//Fix too
	public void playLast() throws InternalPlayerException {
		try {
			player.stop();
			currentSong = fileM.getLast();
			player.open(currentSong);
			play();
		} catch (BasicPlayerException e) {
			throw new InternalPlayerException();
		}
	}

	private void initPlayer() throws BasicPlayerException {
		player = new BasicPlayer();		// Instantiate BasicPlayer.
		control = (BasicController) player;		// BasicPlayer is a BasicController.
		
	}
	
	public static boolean isFilePlayable(String fileName) {
		if(fileName.endsWith(".mp3"))
			return true;
		else
			return false;
	}
	
	class S13PlayerListener implements BasicPlayerListener {

		public void opened(Object stream, Map properties) {}
		public void progress(int bytesread, long microseconds, byte[] pcmdata, Map properties) {}
		public void setController(BasicController controller) {}

		public void stateUpdated(BasicPlayerEvent event) {
			if(event.getCode()==BasicPlayerEvent.EOM)
				fileM.getHandler().next();
		}
	}
	
}
