package mvc;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

import commands.*;
import dialogs.GeometryDialog;
import dialogs.ModificationDialog;
import geometrics.*;
import commands.*;



public class DrawingController {
	private DrawingModel model;
	private DrawingFrame frame;
	private boolean firstClick = true;
	private boolean secondClick = false;
	private Point startPoint;
	private Point endPoint;
	private Color fillColor = Color.BLACK;
	private Color borderColor = Color.BLACK;
	private PropertyChangeSupport propertyChangeSupport;
	

	
	public DrawingController() {
		propertyChangeSupport = new PropertyChangeSupport(this);
	}

	public DrawingController(DrawingModel model, DrawingFrame frame) {
		this();
		this.model = model;
		this.frame = frame;
		
		
	}

	private void select(int x, int y) {
		
		for (int i = model.getShapes().size() - 1; i >= 0; i--) {
			if(model.getShapes().get(i).contains(x,y) && !model.getShapes().get(i).isSelected()) {
				selectShape(model.getShapes().get(i));
				propertyChangeSupport.firePropertyChange("delete", false, true);
				
				if(model.getSelectedShapes().size() == 1) {
					propertyChangeSupport.firePropertyChange("modify", false, true);
				} else {
					propertyChangeSupport.firePropertyChange("modify", true, false);
				}
				
				break;
			}
			else if (model.getShapes().get(i).contains(x,y) && model.getShapes().get(i).isSelected()) {
				deselectShape(model.getShapes().get(i));
				if(model.getSelectedShapes().size() == 1) {
					propertyChangeSupport.firePropertyChange("modify", false, true);
					propertyChangeSupport.firePropertyChange("delete", false, true);
				} else if (model.getSelectedShapes().size() > 1) {
					propertyChangeSupport.firePropertyChange("delete", false, true);
				} else {
					propertyChangeSupport.firePropertyChange("modify", true, false);
					propertyChangeSupport.firePropertyChange("delete", true, false);
				}
				break;
			}
		}
		frame.getView().repaint();
	}
	
	private void selectShape(Shape selected) {
		if(selected instanceof Point) {
			Point oldState = (Point)selected;
			Point newState = oldState.clone();
			newState.setSelected(true);
			CmdUpdatePoint cmdUpdate = new CmdUpdatePoint(oldState, newState);
			cmdUpdate.execute();
		}else if(selected instanceof Line) {
			Line oldState = (Line)selected;
			Line newState = oldState.clone();
			newState.setSelected(true);
			CmdUpdateLine cmdUpdate = new CmdUpdateLine(oldState, newState);
			cmdUpdate.execute();
		} else if(selected instanceof Rectangle) {
			Rectangle oldState = (Rectangle) selected;
			Rectangle newState = oldState.clone();
			newState.setSelected(true);
			CmdUpdateRectangle cmdUpdate = new CmdUpdateRectangle(oldState, newState);
			cmdUpdate.execute();
		} else if(selected instanceof Circle) {
			Circle oldState = (Circle) selected;
			Circle newState = oldState.clone();
			newState.setSelected(true);
			CmdUpdateCircle cmdUpdate = new CmdUpdateCircle(oldState, newState);
			cmdUpdate.execute();
		} else if(selected instanceof Donut) { 
			Donut oldState = (Donut) selected;
			Donut newState = oldState.clone();
			newState.setSelected(true);
			CmdUpdateDonut cmdUpdate = new CmdUpdateDonut(oldState, newState);
			cmdUpdate.execute();
		} else if(selected instanceof HexagonAdapter) {
			HexagonAdapter oldState = ((HexagonAdapter) selected);
			HexagonAdapter newState = oldState.clone();
			newState.setSelected(true);
			CmdUpdateHexagon cmdUpdate = new CmdUpdateHexagon(oldState, newState);
			cmdUpdate.execute();
		}
		
		model.getSelectedShapes().add(selected);
	}
	
	private void deselectShape(Shape selected) {
		if(selected instanceof Point) {
			Point oldState = (Point)selected;
			Point newState = oldState.clone();
			newState.setSelected(false);
			CmdUpdatePoint cmdUpdate = new CmdUpdatePoint(oldState, newState);
			cmdUpdate.execute();
		}else if(selected instanceof Line) {
			Line oldState = (Line)selected;
			Line newState = oldState.clone();
			newState.setSelected(false);
			CmdUpdateLine cmdUpdate = new CmdUpdateLine(oldState, newState);
			cmdUpdate.execute();
		} else if(selected instanceof Rectangle) {
			Rectangle oldState = (Rectangle) selected;
			Rectangle newState = oldState.clone();
			newState.setSelected(false);
			CmdUpdateRectangle cmdUpdate = new CmdUpdateRectangle(oldState, newState);
			cmdUpdate.execute();
		} else if(selected instanceof Circle) {
			Circle oldState = (Circle) selected;
			Circle newState = oldState.clone();
			newState.setSelected(false);
			CmdUpdateCircle cmdUpdate = new CmdUpdateCircle(oldState, newState);
			cmdUpdate.execute();
		} else if(selected instanceof Donut) { 
			Donut oldState = (Donut) selected;
			Donut newState = oldState.clone();
			newState.setSelected(false);
			CmdUpdateDonut cmdUpdate = new CmdUpdateDonut(oldState, newState);
			cmdUpdate.execute();
		} else if(selected instanceof HexagonAdapter) {
			HexagonAdapter oldState = ((HexagonAdapter) selected);
			HexagonAdapter newState = oldState.clone();
			newState.setSelected(false);
			CmdUpdateHexagon cmdUpdate = new CmdUpdateHexagon(oldState, newState);
			cmdUpdate.execute();
		}
		
		model.getSelectedShapes().remove(selected);
	}
	
	public void delete() {
		if (model.getSelectedShapes().size() > 0) {
			int button = JOptionPane.YES_NO_OPTION;
			int dialogResult = JOptionPane.showConfirmDialog(null, "Delete the selected objects?", "Alert", button);
			if (dialogResult == JOptionPane.YES_OPTION) {
				for (int i = 0; i < model.getSelectedShapes().size(); i++) {
					CmdDeleteShape removeCmd = new CmdDeleteShape(model, model.getSelectedShapes().get(i));
					removeCmd.execute();
				}
				model.setSelectedShapes(new ArrayList<Shape>());
				propertyChangeSupport.firePropertyChange("delete", true, false);
				propertyChangeSupport.firePropertyChange("modify", true, false);
				frame.repaint();
			}
		} else {
			JOptionPane.showMessageDialog(frame, "You haven't selected anything!");
		}

	}
	
	public void modify() {
		if (model.getSelectedShapes().size() == 1) {
			Shape selected = model.getSelectedShapes().get(0);
				if (selected instanceof Point) {
					ModificationDialog pointModify = new ModificationDialog(selected);
					if(pointModify.isOk()) {
						Point oldState = (Point)selected;
						Point newState = new Point(Integer.parseInt(pointModify.getTextField().getText()), Integer.parseInt(pointModify.getTextField_1().getText()), oldState.isSelected(), pointModify.getBorderColor());
						CmdUpdatePoint cmdUpdate = new CmdUpdatePoint(oldState, newState);
						cmdUpdate.execute();
					}
					
					pointModify.dispose();
					
				} else if (selected instanceof Line) {
					ModificationDialog lineModify = new ModificationDialog(selected);
					if(lineModify.isOk()) {
						Line oldState = (Line)selected;
						Line newState = new Line(new Point(Integer.parseInt(lineModify.getTextField().getText()),
								 Integer.parseInt(lineModify.getTextField_1().getText())), new Point(Integer.parseInt(lineModify.getTextField_2().getText()),
										 Integer.parseInt(lineModify.getTextField_3().getText())), oldState.isSelected(), lineModify.getBorderColor());
						CmdUpdateLine cmdUpdate = new CmdUpdateLine(oldState, newState);
						cmdUpdate.execute();
					}
					
					lineModify.dispose();
					
				} else if (selected instanceof Rectangle) {
					ModificationDialog rectangleModify = new ModificationDialog(selected);
					if(rectangleModify.isOk()) {
						Rectangle oldState = (Rectangle)selected;
						Rectangle newState = new Rectangle(new Point(Integer.parseInt(rectangleModify.getTextField().getText()),
								 Integer.parseInt(rectangleModify.getTextField_1().getText())), Integer.parseInt(rectangleModify.getTextField_2().getText()), Integer.parseInt(rectangleModify.getTextField_3().getText()),
								 oldState.isSelected(), rectangleModify.getBorderColor(), rectangleModify.getFillColor());
						CmdUpdateRectangle cmdUpdate = new CmdUpdateRectangle(oldState, newState);
						cmdUpdate.execute();
					}
					
					rectangleModify.dispose();
					
				} else if (selected instanceof Circle) {
					ModificationDialog circleModify = new ModificationDialog(selected);
					if(circleModify.isOk()) {
						Circle oldState = (Circle)selected;
						Circle newState = new Circle(new Point(Integer.parseInt(circleModify.getTextField().getText()),
								 Integer.parseInt(circleModify.getTextField_1().getText())), Integer.parseInt(circleModify.getTextField_2().getText()), oldState.isSelected(), circleModify.getBorderColor(), circleModify.getFillColor());
						CmdUpdateCircle cmdUpdate = new CmdUpdateCircle(oldState, newState);
						cmdUpdate.execute();
					}
					
					circleModify.dispose();
					
				} else if (selected instanceof Donut) {
					ModificationDialog donutModify = new ModificationDialog(selected);
					if(donutModify.isOk()) {
						Donut oldState = (Donut)selected;
						Donut newState = new Donut(new Point(Integer.parseInt(donutModify.getTextField().getText()),
								 Integer.parseInt(donutModify.getTextField_1().getText())), Integer.parseInt(donutModify.getTextField_2().getText()), Integer.parseInt(donutModify.getTextField_3().getText()),
								 oldState.isSelected(), donutModify.getBorderColor(), donutModify.getFillColor());
						CmdUpdateDonut cmdUpdate = new CmdUpdateDonut(oldState, newState);
						cmdUpdate.execute();
					}
					
					donutModify.dispose();
					
				} else if (selected instanceof HexagonAdapter) {
					ModificationDialog hexagonModify = new ModificationDialog(selected);
					if(hexagonModify.isOk()) {
						HexagonAdapter oldState = (HexagonAdapter)selected;
						HexagonAdapter newState = new HexagonAdapter(Integer.parseInt(hexagonModify.getTextField().getText()),
								 Integer.parseInt(hexagonModify.getTextField_1().getText()), Integer.parseInt(hexagonModify.getTextField_2().getText()), oldState.isSelected(), hexagonModify.getBorderColor(), hexagonModify.getFillColor());
						CmdUpdateHexagon cmdUpdate = new CmdUpdateHexagon(oldState, newState);
						cmdUpdate.execute();
					}
					
					hexagonModify.dispose();
				}
				frame.getView().repaint();

			}
	}

	public void mouseClicked(MouseEvent arg0) {
		int x = arg0.getX();
		int y = arg0.getY();
		
		if (frame.getRdbtnSelect().isSelected()) {
			select(x, y);
		}
		else if(frame.getRdbtnPoint().isSelected()) {
			Point point = new Point(x, y, false, borderColor);
			CmdAddShape addPoint = new CmdAddShape(model, point);
			addPoint.execute();
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
				CmdAddShape addLine = new CmdAddShape(model, line);
				addLine.execute();
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
			CmdAddShape addRectangle = new CmdAddShape(model, rectangle);
			addRectangle.execute();
			rectangleDialog.dispose();
			
		}
		else if (frame.getRdbtnCircle().isSelected()) {
			GeometryDialog circleDialog = new GeometryDialog("circle");
			Point center = new Point(x, y);
			int r = Integer.parseInt(circleDialog.getTxtFirst().getText());
			Circle circle = new Circle(center, r, false, borderColor, fillColor);
			CmdAddShape addCircle = new CmdAddShape(model, circle);
			addCircle.execute();
			circleDialog.dispose();
		}
		else if (frame.getRdbtnDonut().isSelected()) {
			GeometryDialog donutDialog = new GeometryDialog("donut");
			Point center = new Point(x, y);
			int outerRadius = Integer.parseInt(donutDialog.getTxtSecond().getText());
			int innerRadius = Integer.parseInt(donutDialog.getTxtFirst().getText());
			Donut donut = new Donut(center, outerRadius, innerRadius, false, borderColor, fillColor);
			CmdAddShape addDonut = new CmdAddShape(model, donut);
			addDonut.execute();
			donutDialog.dispose();
		}
		else if (frame.getRdbtnHexagon().isSelected()) {
			GeometryDialog hexagonDialog = new GeometryDialog("hexagon");
			int r = Integer.parseInt(hexagonDialog.getTxtFirst().getText());
			HexagonAdapter hexagon = new HexagonAdapter(x, y, r, false, borderColor, fillColor);
			CmdAddShape addHexagon = new CmdAddShape(model, hexagon);
			addHexagon.execute();
			hexagonDialog.dispose();
		}
		frame.getView().repaint();
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
	
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		propertyChangeSupport.addPropertyChangeListener(pcl);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		propertyChangeSupport.removePropertyChangeListener(pcl);
	}

	
	
	
}
