package commands;

import geometrics.Rectangle;

public class CmdUpdateRectangle implements Command {
	private Rectangle oldState;
	private Rectangle newState;
	private Rectangle original=new Rectangle();
	
	public CmdUpdateRectangle(Rectangle oldState,Rectangle newState) {
		this.oldState=oldState;
		this.newState=newState;
	}
	
	@Override
	public void execute() {
		original=oldState.clone();
		oldState.setBorderColor(newState.getBorderColor());
		oldState.setFillColor(newState.getFillColor());
		oldState.setHeight(newState.getHeight());
		//oldState.setUpperLeftPoint(newState.getUpperLeftPoint());
		oldState.getUpperLeftPoint().setX(newState.getUpperLeftPoint().getX());
		oldState.getUpperLeftPoint().setY(newState.getUpperLeftPoint().getY());
		oldState.setWidth(newState.getWidth());
		oldState.setSelected(newState.isSelected());
	}

	@Override
	public void unexecute() {
		oldState.setBorderColor(original.getBorderColor());
		oldState.setFillColor(original.getFillColor());
		oldState.setHeight(original.getHeight());
		oldState.setUpperLeftPoint(original.getUpperLeftPoint());
		oldState.setWidth(original.getWidth());
		oldState.setSelected(original.isSelected());
	}

}
