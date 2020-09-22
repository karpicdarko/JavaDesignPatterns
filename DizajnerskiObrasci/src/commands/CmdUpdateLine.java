package commands;

import geometrics.Line;

public class CmdUpdateLine implements Command{
	private Line oldState;
	private Line newState;
	private Line original=new Line();
	
	public CmdUpdateLine(Line oldState,Line newState) {
		this.oldState=oldState;
		this.newState=newState;
	}
	
	@Override
	public void execute() {
		original=oldState.clone();
		oldState.setBorderColor(newState.getBorderColor());
		oldState.setStartPoint(newState.getStartPoint());
		oldState.setEndPoint(newState.getEndPoint());
		oldState.setSelected(newState.isSelected());
	}

	@Override
	public void unexecute() {
		oldState.setBorderColor(original.getBorderColor());
		oldState.setStartPoint(original.getStartPoint());
		oldState.setEndPoint(original.getEndPoint());
		oldState.setSelected(original.isSelected());

	}
}
