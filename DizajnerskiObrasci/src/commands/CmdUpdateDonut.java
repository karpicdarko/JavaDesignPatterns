package commands;

import geometrics.Donut;

public class CmdUpdateDonut implements Command{
	private Donut oldState;
	private Donut newState;
	private Donut original;
	
	public CmdUpdateDonut(Donut oldState,Donut newState) {
		this.oldState=oldState;
		this.newState=newState;
	}
	@Override
	public void execute() {
		original=oldState.clone();
		oldState.setR(newState.getR());
		oldState.setBorderColor(newState.getBorderColor());
		oldState.setCenter(newState.getCenter());
		oldState.getCenter().setX(newState.getCenter().getX());
		oldState.getCenter().setY(newState.getCenter().getY());
		oldState.setFillColor(newState.getFillColor());
		oldState.setInnerRadius(newState.getInnerRadius());
		oldState.setSelected(newState.isSelected());
	}

	@Override
	public void unexecute() {
		oldState.setR(original.getR());
		oldState.setBorderColor(original.getBorderColor());
		//oldState.setCenter(original.getCenter());
		oldState.getCenter().setX(original.getCenter().getX());
		oldState.getCenter().setY(original.getCenter().getY());
		oldState.setFillColor(original.getFillColor());
		oldState.setInnerRadius(original.getInnerRadius());
		oldState.setSelected(original.isSelected());
	}
	
	public String toString() {
		return "Modified Donut " + "[" + oldState + "]" + " to [" + newState + "]";
	}
	
}
