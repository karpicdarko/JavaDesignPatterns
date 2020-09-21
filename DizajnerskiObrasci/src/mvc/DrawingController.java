package mvc;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.StringTokenizer;

import dialogs.GeometryDialog;
import geometrics.*;


public class DrawingController {
	private DrawingModel model;
	private DrawingFrame frame;
	private boolean firstClick = true;
	private boolean secondClick = false;
	protected Point startPoint;
	protected Point endPoint;

	public DrawingController(DrawingModel model, DrawingFrame frame) {
		this.model = model;
		this.frame = frame;
		
	}

	public void mouseClicked(MouseEvent arg0) {
		int x = arg0.getX();
		int y = arg0.getY();
		
		
		if(frame.getRdbtnPoint().isSelected()) {
			Point point = new Point(x, y);
			model.add(point);
			frame.getView().repaint();
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
				Line line = new Line(startPoint, endPoint);
				model.add(line);
				frame.getView().repaint();
				firstClick = true;
				secondClick = false;
			}
		}
		else if(frame.getRdbtnRectangle().isSelected()) {
			GeometryDialog rectangleDialog = new GeometryDialog("rectangle");
			int width = Integer.parseInt(rectangleDialog.getTxtFirst().getText());
			int height = Integer.parseInt(rectangleDialog.getTxtSecond().getText());
			Point upperLeftPoint = new Point(x, y);
			Rectangle rectangle = new Rectangle(upperLeftPoint, width, height);
			model.add(rectangle);
			frame.getView().repaint();
			
		}
		else if (frame.getRdbtnCircle().isSelected()) {
			GeometryDialog circleDialog = new GeometryDialog("circle");
			Point center = new Point(x, y);
			int r = Integer.parseInt(circleDialog.getTxtFirst().getText());
			Circle circle = new Circle(center, r);
			model.add(circle);
			frame.getView().repaint();
		}
		else if (frame.getRdbtnDonut().isSelected()) {
			GeometryDialog donutDialog = new GeometryDialog("donut");
			Point center = new Point(x, y);
			int outerRadius = Integer.parseInt(donutDialog.getTxtSecond().getText());
			int innerRadius = Integer.parseInt(donutDialog.getTxtFirst().getText());
			Donut donut = new Donut(center, outerRadius, innerRadius);
			model.add(donut);
			frame.getView().repaint();
		}
		else if (frame.getRdbtnHexagon().isSelected()) {
			GeometryDialog hexagonDialog = new GeometryDialog("hexagon");
			int r = Integer.parseInt(hexagonDialog.getTxtFirst().getText());
			HexagonAdapter hexagon = new HexagonAdapter(x, y, r);
			model.add(hexagon);
			frame.getView().repaint();
		}
	}
}
