package commands;

import geometrics.Point;
import mvc.DrawingModel;

public class CmdUpdatePoint implements Command{
	
	private Point oldState;
	private Point newState;
	private Point original;
	
	public CmdUpdatePoint (Point oldState, Point newState) {
		this.oldState = oldState;
		this.newState = newState;
	}
	

	@Override
	public void execute() {
		original = oldState.clone();
		oldState.setX(newState.getX());
		oldState.setY(newState.getY());
		oldState.setBorderColor(newState.getBorderColor());
		oldState.setSelected(newState.isSelected());
		
		
	}

	@Override
	public void unexecute() {
		oldState.setX(original.getX());
		oldState.setY(original.getY());
		oldState.setBorderColor(original.getBorderColor());
		oldState.setSelected(original.isSelected());
	}
	
	public String toString() {
		return "Modified Point " + "[" + oldState + "]" + " to [" + newState + "]";
	}
	


}
