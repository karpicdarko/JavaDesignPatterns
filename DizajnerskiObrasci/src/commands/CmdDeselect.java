package commands;

import geometrics.Circle;
import geometrics.Donut;
import geometrics.HexagonAdapter;
import geometrics.Line;
import geometrics.Point;
import geometrics.Rectangle;
import geometrics.Shape;
import mvc.DrawingModel;

public class CmdDeselect implements Command {
	private Shape shape;
	private DrawingModel model;
	
	public CmdDeselect(Shape shape, DrawingModel model) {
		this.shape = shape;
		this.model = model;
	}
	@Override
	public void execute() {
		if(shape instanceof Point) {
			Point oldState = (Point)shape;
			Point newState = oldState.clone();
			newState.setSelected(false);
			CmdUpdatePoint cmdUpdate = new CmdUpdatePoint(oldState, newState);
			cmdUpdate.execute();
		}else if(shape instanceof Line) {
			Line oldState = (Line)shape;
			Line newState = oldState.clone();
			newState.setSelected(false);
			CmdUpdateLine cmdUpdate = new CmdUpdateLine(oldState, newState);
			cmdUpdate.execute();
		} else if(shape instanceof Rectangle) {
			Rectangle oldState = (Rectangle) shape;
			Rectangle newState = oldState.clone();
			newState.setSelected(false);
			CmdUpdateRectangle cmdUpdate = new CmdUpdateRectangle(oldState, newState);
			cmdUpdate.execute();
		} else if(shape instanceof Circle) {
			Circle oldState = (Circle) shape;
			Circle newState = oldState.clone();
			newState.setSelected(false);
			CmdUpdateCircle cmdUpdate = new CmdUpdateCircle(oldState, newState);
			cmdUpdate.execute();
		} else if(shape instanceof Donut) { 
			Donut oldState = (Donut) shape;
			Donut newState = oldState.clone();
			newState.setSelected(false);
			CmdUpdateDonut cmdUpdate = new CmdUpdateDonut(oldState, newState);
			cmdUpdate.execute();
		} else if(shape instanceof HexagonAdapter) {
			HexagonAdapter oldState = ((HexagonAdapter) shape);
			HexagonAdapter newState = oldState.clone();
			newState.setSelected(false);
			CmdUpdateHexagon cmdUpdate = new CmdUpdateHexagon(oldState, newState);
			cmdUpdate.execute();
		}
		
		model.getSelectedShapes().remove(shape);
		
	}

	@Override
	public void unexecute() {
		
		if(shape instanceof Point) {
			Point oldState = (Point)shape;
			Point newState = oldState.clone();
			newState.setSelected(true);
			CmdUpdatePoint cmdUpdate = new CmdUpdatePoint(oldState, newState);
			cmdUpdate.execute();
		}else if(shape instanceof Line) {
			Line oldState = (Line)shape;
			Line newState = oldState.clone();
			newState.setSelected(true);
			CmdUpdateLine cmdUpdate = new CmdUpdateLine(oldState, newState);
			cmdUpdate.execute();
		} else if(shape instanceof Rectangle) {
			Rectangle oldState = (Rectangle) shape;
			Rectangle newState = oldState.clone();
			newState.setSelected(true);
			CmdUpdateRectangle cmdUpdate = new CmdUpdateRectangle(oldState, newState);
			cmdUpdate.execute();
		} else if(shape instanceof Circle) {
			Circle oldState = (Circle) shape;
			Circle newState = oldState.clone();
			newState.setSelected(true);
			CmdUpdateCircle cmdUpdate = new CmdUpdateCircle(oldState, newState);
			cmdUpdate.execute();
		} else if(shape instanceof Donut) { 
			Donut oldState = (Donut) shape;
			Donut newState = oldState.clone();
			newState.setSelected(true);
			CmdUpdateDonut cmdUpdate = new CmdUpdateDonut(oldState, newState);
			cmdUpdate.execute();
		} else if(shape instanceof HexagonAdapter) {
			HexagonAdapter oldState = ((HexagonAdapter) shape);
			HexagonAdapter newState = oldState.clone();
			newState.setSelected(true);
			CmdUpdateHexagon cmdUpdate = new CmdUpdateHexagon(oldState, newState);
			cmdUpdate.execute();
		}
		
		model.getSelectedShapes().add(shape);
		
	}
}
