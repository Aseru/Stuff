package shuf13.main;

import shuf13.control.GuiHandler;
import shuf13.control.GuiHstd;

/** Music player.
 * 
 * @author OutFromTheDeep
 */
public class Shuf13 {

	private GuiHandler guiHandler;

	public Shuf13() {
		guiHandler = new GuiHstd();
		
	}
	

	public static void main(String[] args) {
		new Shuf13();
	}
	
}
