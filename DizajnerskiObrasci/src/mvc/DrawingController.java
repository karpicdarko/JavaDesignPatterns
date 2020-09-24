package mvc;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JOptionPane;

import commands.*;
import dialogs.GeometryDialog;
import dialogs.ModificationDialog;
import geometrics.*;




public class DrawingController {
	private DrawingModel model;
	private DrawingFrame frame;
	private boolean firstClick = true;
	private boolean secondClick = false;
	private Point startPoint;
	private Point endPoint;
	private Color fillColor = Color.white;
	private Color borderColor = Color.black;
	private PropertyChangeSupport propertyChangeSupport;
	private Stack<Command> commandsNormal = new Stack<Command>();
	private Stack<Command> commandsReverse = new Stack<Command>();
	

	
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
				/*selectShape(model.getShapes().get(i));*/
				CmdSelect select = new CmdSelect(model.getShapes().get(i), model);
				commandsNormal.push(select);
				select.execute();
				if(model.getShapes().get(i) instanceof HexagonAdapter) {
					frame.getListModel().addElement("Select Hexagon "  + model.getShapes().get(i).toString());
				} else
					frame.getListModel().addElement("Select " + model.getShapes().get(i).getClass().getSimpleName() + ", " + model.getShapes().get(i).toString());
				propertyChangeSupport.firePropertyChange("delete", false, true);
				propertyChangeSupport.firePropertyChange("modify", false, true);
				propertyChangeSupport.firePropertyChange("undo", false, true);
				propertyChangeSupport.firePropertyChange("redo", false, true);
				break;
			}
			else if (model.getShapes().get(i).contains(x,y) && model.getShapes().get(i).isSelected()) {
				
				CmdDeselect deselect = new CmdDeselect(model.getShapes().get(i), model);
				commandsNormal.push(deselect);
				deselect.execute();
				if(model.getShapes().get(i) instanceof HexagonAdapter) {
					frame.getListModel().addElement("Deselect Hexagon "  + model.getShapes().get(i).toString());
				} else
					frame.getListModel().addElement("Deslect " + model.getShapes().get(i).getClass().getSimpleName() + ", " + model.getShapes().get(i).toString());
				propertyChangeSupport.firePropertyChange("modify", false, true);
				propertyChangeSupport.firePropertyChange("delete", false, true);
				propertyChangeSupport.firePropertyChange("undo", false, true);
				propertyChangeSupport.firePropertyChange("redo", false, true);
				break;
			}
		}
		frame.getView().repaint();
	}
	
	
	public void delete() {
		if (model.getSelectedShapes().size() > 0) {
			int button = JOptionPane.YES_NO_OPTION;
			int dialogResult = JOptionPane.showConfirmDialog(null, "Delete the selected objects?", "Alert", button);
			if (dialogResult == JOptionPane.YES_OPTION) {
				for (int i = 0; i < model.getSelectedShapes().size(); i++) {
					CmdDeleteShape removeCmd = new CmdDeleteShape(model, model.getSelectedShapes().get(i));
					commandsNormal.push(removeCmd);
					removeCmd.execute();
					frame.getListModel().addElement(removeCmd.toString());
				}
				model.setSelectedShapes(new ArrayList<Shape>());
				propertyChangeSupport.firePropertyChange("delete", false, true);
				propertyChangeSupport.firePropertyChange("modify", false, true);
				propertyChangeSupport.firePropertyChange("undo", false, true);
				propertyChangeSupport.firePropertyChange("redo", false, true);
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
						frame.getListModel().addElement(cmdUpdate.toString());
						commandsNormal.push(cmdUpdate);
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
						frame.getListModel().addElement(cmdUpdate.toString());
						commandsNormal.push(cmdUpdate);
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
						frame.getListModel().addElement(cmdUpdate.toString());
						commandsNormal.push(cmdUpdate);
						cmdUpdate.execute();
					}
					
					rectangleModify.dispose();
					
				} else if (selected instanceof Circle && !(selected instanceof Donut)) {
					ModificationDialog circleModify = new ModificationDialog(selected);
					if(circleModify.isOk()) {
						Circle oldState = (Circle)selected;
						Circle newState = new Circle(new Point(Integer.parseInt(circleModify.getTextField().getText()),
								 Integer.parseInt(circleModify.getTextField_1().getText())), Integer.parseInt(circleModify.getTextField_2().getText()), oldState.isSelected(), circleModify.getBorderColor(), circleModify.getFillColor());
						CmdUpdateCircle cmdUpdate = new CmdUpdateCircle(oldState, newState);
						frame.getListModel().addElement(cmdUpdate.toString());
						commandsNormal.push(cmdUpdate);
						cmdUpdate.execute();
					}
					
					circleModify.dispose();
					
				} else if (selected instanceof Donut) {
					ModificationDialog donutModify = new ModificationDialog(selected);
					if(donutModify.isOk()) {
						Donut oldState = (Donut)selected;
						Donut newState = new Donut(new Point(Integer.parseInt(donutModify.getTextField().getText()),
								Integer.parseInt(donutModify.getTextField_1().getText())), Integer.parseInt(donutModify.getTextField_3().getText()),
								Integer.parseInt(donutModify.getTextField_2().getText()),oldState.isSelected(), donutModify.getBorderColor(), donutModify.getFillColor());
						CmdUpdateDonut cmdUpdate = new CmdUpdateDonut(oldState, newState);
						frame.getListModel().addElement(cmdUpdate.toString());
						commandsNormal.push(cmdUpdate);
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
						frame.getListModel().addElement(cmdUpdate.toString());
						commandsNormal.push(cmdUpdate);
						cmdUpdate.execute();
					}
					
					hexagonModify.dispose();
				}
				propertyChangeSupport.firePropertyChange("undo", false, true);
				propertyChangeSupport.firePropertyChange("redo", false, true);
				frame.getView().repaint();

			}
	}
	
	public void toBack() {
		if (model.getSelectedShapes().size() == 1 && model.getShapes().indexOf(model.getSelectedShapes().get(0)) > 0) {
			CmdToBack cmd = new CmdToBack(model.getShapes(), model.getShapes().indexOf(model.getSelectedShapes().get(0)));
			commandsNormal.push(cmd);
			cmd.execute();
			frame.getListModel().addElement(cmd.toString());
			frame.getView().repaint();
			
		}
		propertyChangeSupport.firePropertyChange("undo", false, true);
		propertyChangeSupport.firePropertyChange("redo", false, true);
	}
	
	public void toFront() {
		if (model.getSelectedShapes().size() == 1 && model.getShapes().indexOf(model.getSelectedShapes().get(0)) < model.getShapes().size()-1) {
			CmdToFront cmd = new CmdToFront(model.getShapes(), model.getShapes().indexOf(model.getSelectedShapes().get(0)));
			commandsNormal.push(cmd);
			cmd.execute();
			frame.getListModel().addElement(cmd.toString());
			frame.getView().repaint();
			
		}
		propertyChangeSupport.firePropertyChange("undo", false, true);
		propertyChangeSupport.firePropertyChange("redo", false, true);
	}
	
	public void bringToBack() {
		if (model.getSelectedShapes().size() == 1 && model.getShapes().indexOf(model.getSelectedShapes().get(0)) > 0) {
			CmdBringToBack cmd = new CmdBringToBack(model.getShapes(), model.getShapes().indexOf(model.getSelectedShapes().get(0)));
			commandsNormal.push(cmd);
			cmd.execute();
			frame.getListModel().addElement(cmd.toString());
			frame.getView().repaint();
			
		}
		propertyChangeSupport.firePropertyChange("undo", false, true);
		propertyChangeSupport.firePropertyChange("redo", false, true);
	}
	
	public void bringToFront() {
		if (model.getSelectedShapes().size() == 1 && model.getShapes().indexOf(model.getSelectedShapes().get(0)) < model.getShapes().size()-1) {
			CmdBringToFront cmd = new CmdBringToFront(model.getShapes(), model.getShapes().indexOf(model.getSelectedShapes().get(0)));
			commandsNormal.push(cmd);
			cmd.execute();
			frame.getListModel().addElement(cmd.toString());
			frame.getView().repaint();
			
		}
		propertyChangeSupport.firePropertyChange("undo", false, true);
		propertyChangeSupport.firePropertyChange("redo", false, true);
	}
	
	public void undo() {
		if(commandsNormal.isEmpty())
			return;
		commandsNormal.peek().unexecute();
		commandsReverse.push(commandsNormal.peek());
		commandsNormal.pop();
		frame.getListModel().addElement("Undo");
		propertyChangeSupport.firePropertyChange("modify", false, true);
		propertyChangeSupport.firePropertyChange("delete", false, true);
		propertyChangeSupport.firePropertyChange("undo", false, true);
		propertyChangeSupport.firePropertyChange("redo", false, true);
		frame.getView().repaint();
	}
	
	public void redo() {
		if(commandsReverse.isEmpty())
			return;
		commandsReverse.peek().execute();
		commandsNormal.push(commandsReverse.peek());
		commandsReverse.pop();
		frame.getListModel().addElement("Redo");
		propertyChangeSupport.firePropertyChange("modify", false, true);
		propertyChangeSupport.firePropertyChange("delete", false, true);
		propertyChangeSupport.firePropertyChange("undo", false, true);
		propertyChangeSupport.firePropertyChange("redo", false, true);
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
			CmdAddShape addPoint = new CmdAddShape(model, point);
			commandsReverse = new Stack<Command>();
			commandsNormal.push(addPoint);
			addPoint.execute();
			frame.getListModel().addElement(addPoint.toString());
			
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
				commandsReverse = new Stack<Command>();
				commandsNormal.push(addLine);
				addLine.execute();
				frame.getListModel().addElement(addLine.toString());
				firstClick = true;
				secondClick = false;
			}
		}
		else if(frame.getRdbtnRectangle().isSelected()) {
			GeometryDialog rectangleDialog = new GeometryDialog("rectangle");
			if(rectangleDialog.isOk()) {
				int width = Integer.parseInt(rectangleDialog.getTxtFirst().getText());
				int height = Integer.parseInt(rectangleDialog.getTxtSecond().getText());
				Point upperLeftPoint = new Point(x, y);
				Rectangle rectangle = new Rectangle(upperLeftPoint, width, height, false, borderColor, fillColor);
				CmdAddShape addRectangle = new CmdAddShape(model, rectangle);
				commandsReverse = new Stack<Command>();
				commandsNormal.push(addRectangle);
				addRectangle.execute();
				frame.getListModel().addElement(addRectangle.toString());
			}
			rectangleDialog.dispose();
			
		}
		else if (frame.getRdbtnCircle().isSelected()) {
			GeometryDialog circleDialog = new GeometryDialog("circle");
			if(circleDialog.isOk()) {
				Point center = new Point(x, y);
				int r = Integer.parseInt(circleDialog.getTxtFirst().getText());
				Circle circle = new Circle(center, r, false, borderColor, fillColor);
				CmdAddShape addCircle = new CmdAddShape(model, circle);
				commandsReverse = new Stack<Command>();
				commandsNormal.push(addCircle);
				addCircle.execute();
				frame.getListModel().addElement(addCircle.toString());
			}
			circleDialog.dispose();
		}
		else if (frame.getRdbtnDonut().isSelected()) {
			GeometryDialog donutDialog = new GeometryDialog("donut");
			if(donutDialog.isOk()) {
				Point center = new Point(x, y);
				int outerRadius = Integer.parseInt(donutDialog.getTxtSecond().getText());
				int innerRadius = Integer.parseInt(donutDialog.getTxtFirst().getText());
				Donut donut = new Donut(center, outerRadius, innerRadius, false, borderColor, fillColor);
				CmdAddShape addDonut = new CmdAddShape(model, donut);
				commandsReverse = new Stack<Command>();
				commandsNormal.push(addDonut);
				addDonut.execute();
				frame.getListModel().addElement(addDonut.toString());
			}
			donutDialog.dispose();
		}
		else if (frame.getRdbtnHexagon().isSelected()) {
			GeometryDialog hexagonDialog = new GeometryDialog("hexagon");
			if(hexagonDialog.isOk()) {
				int r = Integer.parseInt(hexagonDialog.getTxtFirst().getText());
				HexagonAdapter hexagon = new HexagonAdapter(x, y, r, false, borderColor, fillColor);
				CmdAddShape addHexagon = new CmdAddShape(model, hexagon);
				commandsReverse = new Stack<Command>();
				commandsNormal.push(addHexagon);
				addHexagon.execute();
				frame.getListModel().addElement(addHexagon.toString());
			}
			
			hexagonDialog.dispose();
		}
		propertyChangeSupport.firePropertyChange("undo", false, true);
		propertyChangeSupport.firePropertyChange("redo", false, true);
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

	public DrawingModel getModel() {
		return model;
	}

	public Stack<Command> getCommandsNormal() {
		return commandsNormal;
	}

	public Stack<Command> getCommandsReverse() {
		return commandsReverse;
	}
	
	
	
	
}
