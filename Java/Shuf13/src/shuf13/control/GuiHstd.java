package shuf13.control;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import shuf13.exception.FileErrorException;
import shuf13.exception.InternalPlayerException;
import shuf13.exception.InvalidStartDirException;
import shuf13.gui.Guistd;
import shuf13.tools.FileManager;
import shuf13.tools.S13Player;


public class GuiHstd implements GuiHandler {

	private Guistd gui;
	private FileManager fileM;
	private S13Player player;
	
	private boolean playing;
	private boolean paused;
	private boolean initialized;
	
	public GuiHstd() {
		initialized = false;
		playing = false;
		paused = false;
		
		final GuiHstd gh = this;
		java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                gui = new Guistd(gh);
            }
        });
		
	}
	
	public void playHandle(String sdir) throws InvalidStartDirException {
		if(!initialized) {
			if(sdir.isEmpty())
				throw new InvalidStartDirException();
			if(!new File(sdir).isDirectory())
				throw new InvalidStartDirException();
			
			try {
				fileM = new FileManager(this, sdir);
			} catch (InternalPlayerException e) {
				displayText("InternalPlayerException");
				throw new RuntimeException(e);
			}
			player = fileM.getPlayer();
			
			gui.setPathEd(false);
			gui.display("System initialized. " +fileM.getSongNumber() +" Songs added.");
			initialized = true;
			return;
		}
		if(playing)
			pause();
		else
			play();
	}
	
	public void next() {
		try {
			player.playNext();
			gui.display(fileM.getCurrentSongName());
		} catch (InternalPlayerException e) {
			displayText("InternalPlayerException");
			throw new RuntimeException(e);
		} catch (FileErrorException e) {
			gui.display("File Error.");
		}
	}
	
	public void last() {
		try {
			player.playLast();
			gui.display(fileM.getCurrentSongName());
		} catch (InternalPlayerException e) {
			displayText("InternalPlayerException");
			throw new RuntimeException(e);
		} catch (FileErrorException e) {
			gui.display("File Error.");
		}
	}
	
	public String browse() {
		final JFileChooser fc = new JFileChooser();
	    fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fc.showOpenDialog(null);
		
		if(returnVal==JFileChooser.APPROVE_OPTION) {
			File inputFile = fc.getSelectedFile();
			try {
				if(player!=null) {
					player.stop();
					fileM = null;
					player = null;
					initialized = false;
					gui.display(null);
					setPause(true);
					paused = false;
					playing = false;
				}
				return inputFile.getCanonicalPath();
			} catch (IOException e) {
				gui.display("File Error.");
				throw new RuntimeException(e);
			} catch (InternalPlayerException e) {
				displayText("InternalPlayerException");
				throw new RuntimeException(e);
			}
		}
		return null;
		
	}
	
	private void play() {
		try {
			if(paused)
				player.pause(false);
			else
				player.play();
		} catch (InternalPlayerException e) {
			displayText("InternalPlayerException");
			throw new RuntimeException(e);
		}
		
		paused = false;
		playing = true;
		gui.setPlayButton(1);
		try {
			gui.display(fileM.getCurrentSongName());
		} catch (FileErrorException e) {
			gui.display("File Error.");
		}
	}
	
	private void pause() {
		try {
			player.pause(true);
		} catch (InternalPlayerException e) {
			displayText("InternalPlayerException");
			throw new RuntimeException(e);
		}
		
		paused = true;
		playing = false;
		gui.setPlayButton(2);
		gui.display("Paused");
	}
	
	
	public void setPause(boolean on) {
		int mode;
		if(on)
			mode = 2;
		else
			mode = 1;
		gui.setPlayButton(mode);
	}


	public void displayText(String text) {
		gui.display(text);
	}
	
}
