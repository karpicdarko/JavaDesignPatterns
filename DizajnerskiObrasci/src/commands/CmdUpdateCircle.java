package commands;

import geometrics.Circle;

public class CmdUpdateCircle implements Command{
	private Circle oldState;
	private Circle newState;
	private Circle original = new Circle();

	public CmdUpdateCircle(Circle oldState, Circle newState) {
		this.oldState = oldState;
		this.newState = newState;
	}

	@Override
	public void execute() {
		original = oldState.clone();
		oldState.setR(newState.getR());
		oldState.setBorderColor(newState.getBorderColor());
		//oldState.setCenter(newState.getCenter());
		oldState.getCenter().setX(newState.getCenter().getX());
		oldState.getCenter().setY(newState.getCenter().getY());
		oldState.setFillColor(newState.getFillColor());
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
		oldState.setSelected(original.isSelected());
	}
	public String toString() {
		return "Modified Circle " + "[" + oldState + "]" + " to [" + newState + "]";
	}
	
}
