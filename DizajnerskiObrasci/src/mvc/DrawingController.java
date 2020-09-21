package mvc;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.Iterator;


import dialogs.GeometryDialog;
import geometrics.*;



public class DrawingController {
	private DrawingModel model;
	private DrawingFrame frame;
	private boolean firstClick = true;
	private boolean secondClick = false;
	private Point startPoint;
	private Point endPoint;
	private Color fillColor;
	private Color borderColor;

	public DrawingController(DrawingModel model, DrawingFrame frame) {
		this.model = model;
		this.frame = frame;
		
	}
	
	
	
	public Color getFillColor() {
		return fillColor;
	}



	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}



	public Color getBorderColor() {
		return borderColor;
	}



	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}



	private void select(int x, int y) {
		
		for (int i = model.getShapes().size() - 1; i >= 0; i--) {
			if(model.getShapes().get(i).contains(x,y) && !model.getShapes().get(i).isSelected()) {
				model.getShapes().get(i).setSelected(true);
				break;
			}
			else if (model.getShapes().get(i).contains(x,y) && model.getShapes().get(i).isSelected()) {
				model.getShapes().get(i).setSelected(false);
				break;
			}
		}
		frame.getView().repaint();
	}

	public void mouseClicked(MouseEvent arg0) {
		int x = arg0.getX();
		int y = arg0.getY();
		
		if (frame.getRdbtnSelect().isSelected()) {
			select(x, y);
		}
		else if(frame.getRdbtnPoint().isSelected()) {
			Point point = new Point(x, y, false, borderColor);
			model.add(point);
		}
		else if(frame.getRdbtnLine().isSelected()) {
			if(firstClick) {
				startPoint = new Point();
				startPoint.setX(arg0.getX());
				startPoint.setY(arg0.getY());
				firstClick = false;
				secondClick = true;
				
			}
			else if(secondClick) {
				endPoint = new Point();
				endPoint.setX(arg0.getX());
				endPoint.setY(arg0.getY());
				Line line = new Line(startPoint, endPoint, false, borderColor);
				model.add(line);
				firstClick = true;
				secondClick = false;
			}
		}
		else if(frame.getRdbtnRectangle().isSelected()) {
			GeometryDialog rectangleDialog = new GeometryDialog("rectangle");
			int width = Integer.parseInt(rectangleDialog.getTxtFirst().getText());
			int height = Integer.parseInt(rectangleDialog.getTxtSecond().getText());
			Point upperLeftPoint = new Point(x, y);
			Rectangle rectangle = new Rectangle(upperLeftPoint, width, height, false, borderColor, fillColor);
			model.add(rectangle);
			rectangleDialog.dispose();
			
		}
		else if (frame.getRdbtnCircle().isSelected()) {
			GeometryDialog circleDialog = new GeometryDialog("circle");
			Point center = new Point(x, y);
			int r = Integer.parseInt(circleDialog.getTxtFirst().getText());
			Circle circle = new Circle(center, r, false, borderColor, fillColor);
			model.add(circle);
			circleDialog.dispose();
		}
		else if (frame.getRdbtnDonut().isSelected()) {
			GeometryDialog donutDialog = new GeometryDialog("donut");
			Point center = new Point(x, y);
			int outerRadius = Integer.parseInt(donutDialog.getTxtSecond().getText());
			int innerRadius = Integer.parseInt(donutDialog.getTxtFirst().getText());
			Donut donut = new Donut(center, outerRadius, innerRadius, false, borderColor, fillColor);
			model.add(donut);
			donutDialog.dispose();
		}
		else if (frame.getRdbtnHexagon().isSelected()) {
			GeometryDialog hexagonDialog = new GeometryDialog("hexagon");
			int r = Integer.parseInt(hexagonDialog.getTxtFirst().getText());
			HexagonAdapter hexagon = new HexagonAdapter(x, y, r, false, borderColor, fillColor);
			model.add(hexagon);
			hexagonDialog.dispose();
		}
		frame.getView().repaint();
	}
}
