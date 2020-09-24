package commands;

import geometrics.HexagonAdapter;
import geometrics.Line;
import geometrics.Point;
import geometrics.Shape;
import mvc.DrawingModel;

public class CmdAddShape implements Command{
	
	private DrawingModel model;
	private Shape shape;
	
	
	public CmdAddShape (DrawingModel model, Shape shape) {
		this.model = model;
		this.shape = shape;
	}
	
	@Override
	public void execute() {
		model.add(shape);
		
	}

	@Override
	public void unexecute() {
		model.remove(shape);
		
	}
	
	public String toString() {
		if(shape instanceof HexagonAdapter) {
			return "Add Hexagon "  + shape.toString();
		} else 
			return "Add " + shape.getClass().getSimpleName() + " " + shape.toString();
		
	}
}
