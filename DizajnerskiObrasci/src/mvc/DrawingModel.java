package mvc;

import java.util.ArrayList;
import java.util.List;

import geometrics.Shape;

public class DrawingModel {
	private List<Shape> shapes = new ArrayList<Shape>();
	private List<Shape> selectedShapes = new ArrayList<Shape>();

	public List<Shape> getShapes() {
		return shapes;
	}
	
	public void setShapes(List<Shape> shapes) {
		this.shapes = shapes;
	}
	
	public List<Shape> getSelectedShapes() {
		return selectedShapes;
	}
	
	public void setSelectedShapes(List<Shape> selectedShapes) {
		this.selectedShapes = selectedShapes;
	}

	public void add(Shape p) {
		shapes.add(p);
	}
	
	public void remove(Shape p) {
		shapes.remove(p);
	}
	
	public Shape get(int i) {
		return shapes.get(i);
	}
	
}
