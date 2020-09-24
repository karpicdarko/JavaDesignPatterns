package commands;

import geometrics.HexagonAdapter;
import geometrics.Line;
import geometrics.Point;
import geometrics.Shape;
import mvc.DrawingModel;

public class CmdDeleteShape implements Command{
	
	private DrawingModel model;
	private Shape shape;
	
	
	public CmdDeleteShape (DrawingModel model, Shape shape) {
		this.model = model;
		this.shape = shape;
	}
	
	@Override
	public void execute() {
		model.remove(shape);
		
	}

	@Override
	public void unexecute() {
		model.add(shape);
		
	}
	
	public String toString() {
		if(shape instanceof HexagonAdapter) {
			return "Delete Hexagon "  + shape.toString();
		} else 
			return "Delete " + shape.getClass().getSimpleName() + " " + shape.toString();
		
	}
}
