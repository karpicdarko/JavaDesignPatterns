package commands;

import geometrics.Shape;

public class CmdSelect implements Command {

	private Shape shape;
	
	public CmdSelect(Shape shape) {
		this.shape = shape;
	}
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unexecute() {
		// TODO Auto-generated method stub
		
	}
	
}
