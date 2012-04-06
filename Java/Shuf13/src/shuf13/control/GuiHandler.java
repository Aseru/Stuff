package shuf13.control;

import shuf13.exception.InvalidStartDirException;

public interface GuiHandler {
	
	public void setPause(boolean on);
	
	public void playHandle(String sdir) throws InvalidStartDirException;
	
	public void next();
	
	public void last();
	
	public String browse();
	
	public void displayText(String text);
}
