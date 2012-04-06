package shuf13.tools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import shuf13.control.GuiHandler;
import shuf13.exception.FileErrorException;
import shuf13.exception.InternalPlayerException;
import shuf13.exception.InvalidStartDirException;

public class FileManager {

	private S13Player player;
	private GuiHandler guiH;
	
	private final List<File> songList;
	private int[] history;
	private Random rand;
	private Object sync;
	
	private final String startDir;
	private int songNumber = 0;
	private int position = -1;
	private int regCount = 0;
	private boolean mixed = false;
	
	public FileManager(GuiHandler gh, String startDir) throws InvalidStartDirException, InternalPlayerException {
		guiH = gh;
		this.startDir = startDir; 
		
		sync = new Object();
		songList = new ArrayList<File>();
		init(startDir);
		
		new RandomLoader().run();
		player = new S13Player(this);
	}
	
	
	public File getNext() {
		synchronized(sync) {
			new RandomLoader().start();
			if(position<songNumber-1)
				position++;
			return songList.get(history[position]);
		}
	}
	
	public File getLast() {
		synchronized(sync) {
			if(position>0)
				position--;
			return songList.get(history[position]);
		}
	}
	
	public int getSongNumber() {
		return songNumber;
	}
	
	public S13Player getPlayer() {
		return player;
	}
	
	public String getCurrentSongName() throws FileErrorException {
		File currS = songList.get(history[position]);
		String result;
		try {
			result = currS.getCanonicalPath();
		} catch (IOException e) {
			throw new FileErrorException();
		}
		int index = result.lastIndexOf('\\');
		if(index==-1)
			index = result.lastIndexOf('/');
		if(index==-1)
			index = 0;
		result = result.substring(index+1, result.length()-4);
		if(result.length()>40)
			result = result.substring(0, 40) +" (..)";
		return result;
	}
	
	
	private void analyse(File file) throws IOException {
		if(file==null)
			return;
		if(file.isDirectory()) {
			File[] fa = file.listFiles();
			for(File f: fa)
				analyse(f);
		}
		else if(file.isFile()) {
			boolean isPlayable = S13Player.isFilePlayable(file.getCanonicalPath());
			if(isPlayable)
				addToList(file);
		}
			
			
				
	}
	
	public void init(String startDir) throws InvalidStartDirException {
		try {
			analyse(new File(startDir));
		} catch (IOException e) {
			throw new InvalidStartDirException();
		}
		
		history = new int[songNumber];
		rand = new Random(System.currentTimeMillis());
	}
	
	public GuiHandler getHandler() {
		return guiH;
	}
	
	private int nextRandom() {
		boolean done = false;
		int r = 0;
		while(!done) {
			r = rand.nextInt(songNumber);
			done = true;
			for(int i=0;i<regCount;i++)
				if(history[i]==r) {
					done = false;
					break;
				}
		}
		return r;
	}
	
	private void mix() {
		boolean isIncluded = false;
		for(int i=1; i<songNumber;i++) {
			isIncluded = false;
			for(int k: history) {
				if(i==k) {
					isIncluded = true;
					break;
				}
			}
			if(!isIncluded) {
				regCount++;
				history[regCount-1] = i;
			}
		}
		mixed = true;
		
	}
	
	private void addToList(File file) {
		songNumber++;
		songList.add(file);
	}

	
	class RandomLoader extends Thread {
		public void run() {
			double d = (double)regCount/(double)songNumber;
			System.out.println(d);
			if(d>0.70) {
				if(!mixed)
					mix();
				return;
			}
			synchronized(sync) {
				regCount++;
				history[regCount-1] = nextRandom(); //position+1
			}
		}
	}
}
