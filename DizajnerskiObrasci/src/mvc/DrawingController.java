package mvc;

import java.awt.Color;
import java.awt.event.MouseEvent;

import dialogs.GeometryDialog;
import geometrics.*;


public class DrawingController {
	private DrawingModel model;
	private DrawingFrame frame;
	private boolean firstClick = true;
	private boolean secondClick = false;
	protected Point startPoint = new Point();
	protected Point endPoint = new Point();

	public DrawingController(DrawingModel model, DrawingFrame frame) {
		this.model = model;
		this.frame = frame;
		
	}

	public void mouseClicked(MouseEvent arg0) {
		startPoint.setX(arg0.getX());
		startPoint.setY(arg0.getY());
		GeometryDialog geoDialog = new GeometryDialog("rectangle");
		Rectangle rec = new Rectangle(startPoint, Integer.parseInt(geoDialog.getTxtFirst().getText()), Integer.parseInt(geoDialog.getTxtSecond().getText()));
		geoDialog.dispose();
		model.add(rec);
		frame.getView().repaint();
		
		
		
		
		
		/*if(firstClick) {
			startPoint.setX(arg0.getX());
			startPoint.setY(arg0.getY());
			startPoint.setColor(Color.black);
			secondClick = true;
			firstClick = false;
		}
		else if(secondClick) {
			endPoint.setX(arg0.getX());
			endPoint.setY(arg0.getY());
			endPoint.setColor(Color.red);
			Line linie = new Line(startPoint, endPoint);
			linie.setColor(Color.BLUE);
			model.add(linie);
			frame.getView().repaint();
			secondClick = false;
			firstClick = true;

		}*/
	
		
	}
}
