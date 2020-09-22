package commands;

import geometrics.HexagonAdapter;

public class CmdUpdateHexagon implements Command {
	private HexagonAdapter oldState;
	private HexagonAdapter newState;
	private HexagonAdapter original;
	
	public CmdUpdateHexagon(HexagonAdapter oldState,HexagonAdapter newState) {
		this.oldState=oldState;
		this.newState=newState;
	}
	@Override
	public void execute() {
		original=oldState.clone();
		oldState.getHexagon().setBorderColor(newState.getHexagon().getBorderColor());
		oldState.setBorderColor(newState.getHexagon().getBorderColor());
		oldState.setFillColor(newState.getHexagon().getBorderColor());
		oldState.getHexagon().setAreaColor(newState.getHexagon().getAreaColor());
		oldState.getHexagon().setR(newState.getHexagon().getR());
		oldState.getHexagon().setY(newState.getHexagon().getY());
		oldState.getHexagon().setX(newState.getHexagon().getX());
		oldState.getHexagon().setSelected(newState.getHexagon().isSelected());
		oldState.setSelected(newState.isSelected());
	}

	@Override
	public void unexecute() {
		oldState.getHexagon().setBorderColor(original.getHexagon().getBorderColor());
		oldState.getHexagon().setAreaColor(original.getHexagon().getAreaColor());
		oldState.getHexagon().setR(original.getHexagon().getR());
		oldState.getHexagon().setY(original.getHexagon().getY());
		oldState.getHexagon().setX(original.getHexagon().getX());
		oldState.setSelected(original.isSelected());
		oldState.setBorderColor(original.getBorderColor());
		oldState.setFillColor(original.getFillColor());
		oldState.getHexagon().setSelected(original.isSelected());
		
	}
}
