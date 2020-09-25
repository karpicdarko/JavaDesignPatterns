package mvc;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;
import java.util.Stack;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.omg.CORBA.SystemException;

import Strategy.SaveLog;
import Strategy.SaveManager;
import Strategy.SavePainting;
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
	private Queue<String> loggComm = new LinkedList<String>();
	

	
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
					frame.getListModel().addElement("Deselect " + model.getShapes().get(i).getClass().getSimpleName() + ", " + model.getShapes().get(i).toString());
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
								 Integer.parseInt(lineModify.getTextField_1().getText()), false, lineModify.getBorderColor()), new Point(Integer.parseInt(lineModify.getTextField_2().getText()),
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
								 Integer.parseInt(rectangleModify.getTextField_1().getText()), false, rectangleModify.getBorderColor()), Integer.parseInt(rectangleModify.getTextField_2().getText()), Integer.parseInt(rectangleModify.getTextField_3().getText()),
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
								 Integer.parseInt(circleModify.getTextField_1().getText()), false, circleModify.getBorderColor()), Integer.parseInt(circleModify.getTextField_2().getText()), oldState.isSelected(), circleModify.getBorderColor(), circleModify.getFillColor());
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
								Integer.parseInt(donutModify.getTextField_1().getText()), false, donutModify.getBorderColor()), Integer.parseInt(donutModify.getTextField_3().getText()),
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
		undoForNext();
		frame.getListModel().addElement("Undo");
		propertyChangeSupport.firePropertyChange("modify", false, true);
		propertyChangeSupport.firePropertyChange("delete", false, true);
		propertyChangeSupport.firePropertyChange("undo", false, true);
		propertyChangeSupport.firePropertyChange("redo", false, true);
		frame.getView().repaint();
	}
	
	public void undoForNext() {
		commandsNormal.peek().unexecute();
		commandsReverse.push(commandsNormal.peek());
		commandsNormal.pop();
	}
	
	public void redo() {
		if(commandsReverse.isEmpty())
			return;
		redoForNext();
		frame.getListModel().addElement("Redo");
		propertyChangeSupport.firePropertyChange("modify", false, true);
		propertyChangeSupport.firePropertyChange("delete", false, true);
		propertyChangeSupport.firePropertyChange("undo", false, true);
		propertyChangeSupport.firePropertyChange("redo", false, true);
		frame.getView().repaint();
	}
	
	public void redoForNext() {
		commandsReverse.peek().execute();
		commandsNormal.push(commandsReverse.peek());
		commandsReverse.pop();
	}
	
	public void save() {
		if (model.getShapes().size() == 0) {
			JOptionPane.showMessageDialog(null, "You haven't draw anythig!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		JFileChooser fileCh = new JFileChooser();
		fileCh.setDialogTitle("Specify the location where you want to save your drawing");
		fileCh.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		
		if(fileCh.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
			
			SaveManager strategy = new SaveManager();
			SaveLog saveLog = new SaveLog();
			SavePainting painting = new SavePainting();
			
			String log = "";
			
			for (int i = 0; i < frame.getListModel().getSize(); i++) {
				log = log + frame.getListModel().get(i) + "\n";
			}
			
			saveLog.setLog(log);
			strategy.setStrategy(saveLog);
			strategy.save(fileCh.getSelectedFile().getAbsolutePath() + "\\" + frame.getTxtFileName().getText() + ".txt");
			
			painting.setShapes(model.getShapes());
			strategy.setStrategy(painting);
			strategy.save(fileCh.getSelectedFile().getAbsolutePath() + "\\" + frame.getTxtFileName().getText() + ".bin");
			
		}
	}
	
	public void settingSelected() {
		for(Shape s : model.getShapes()) {
			if(s.isSelected()) {
				model.getSelectedShapes().add(s);
			}
		}
		propertyChangeSupport.firePropertyChange("delete", false, true);
		propertyChangeSupport.firePropertyChange("modify", false, true);
			
	}
	
	public void setPaintingEmpty() {
		model.setShapes(new ArrayList<Shape>());
		model.setSelectedShapes(new ArrayList<Shape>());
		setCommandsNormal(new Stack<Command>());
		setCommandsReverse(new Stack<Command>());
		frame.getView().repaint();
		propertyChangeSupport.firePropertyChange("delete", false, true);
		propertyChangeSupport.firePropertyChange("modify", false, true);
		propertyChangeSupport.firePropertyChange("undo", false, true);
		propertyChangeSupport.firePropertyChange("redo", false, true);
	}
	
	public void loadPainting() {
		JFileChooser fileCh = new JFileChooser();
		fileCh.setDialogTitle("Specify the painting you want to open");
		fileCh.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		if(fileCh.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
			
			try {
				setPaintingEmpty();
				FileInputStream fis = new FileInputStream(fileCh.getSelectedFile().getAbsoluteFile());
				ObjectInputStream ois = new ObjectInputStream(fis);
				model.setShapes((List<Shape>)ois.readObject());
				frame.getView().repaint();
				ois.close();
				
			} catch (Exception ex) {
				
				System.out.println(ex.getMessage());
			}
			settingSelected();
		}
		
		
	}
	
	public void loadLog() {
		setLoggComm(new LinkedList<String>());
		setPaintingEmpty();
		JFileChooser fileCh = new JFileChooser();
		fileCh.setDialogTitle("Choose log you want to open");
		fileCh.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		if(fileCh.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
			
			try {
				
				BufferedReader buffer = new BufferedReader(new FileReader(fileCh.getSelectedFile().getAbsolutePath()));
				String text = "";
				
				while((text = buffer.readLine()) != null) {
					getLoggComm().add(text);
				}
				buffer.close();
				
			} catch (Exception ex) {
				
				System.out.println(ex.getMessage());
			}
			if(getLoggComm().size() > 0)
				frame.getBtnNext().setVisible(true);
				frame.getBtnRedo().setVisible(false);
				frame.getBtnUndo().setVisible(false);
			for(String s : getLoggComm()) {
				System.out.println(s);
			}
		}
	}
	
	public void selectForNext(Shape shape) {
		for(Shape s : model.getShapes()) {
			//boolean bul = shape.equals(s);
			if(shape.equals(s)) {
				CmdSelect cmd = new CmdSelect(s, model);
				commandsNormal.push(cmd);
				cmd.execute();
			}
		}
		
	}
	public void deselectForNext(Shape shape) {
		for(Shape s : model.getShapes()) {
			//boolean bul = shape.equals(s);
			if(shape.equals(s)) {
				CmdDeselect cmd = new CmdDeselect(s, model);
				commandsNormal.push(cmd);
				cmd.execute();
			}
		}
		
	}
	
	public void deleteForNext() {
		for (int i = 0; i < model.getSelectedShapes().size(); i++) {
			
			CmdDeleteShape cmd = new CmdDeleteShape(model, model.getSelectedShapes().get(i));
			cmd.execute();
			commandsReverse = new Stack<Command>();
			commandsNormal.push(cmd);
			
		}
		model.setSelectedShapes(new ArrayList<Shape>());
	}
	
	public void next() {
		String[] line = getLoggComm().peek().split("[] (),]+");
		if(line[0].equals("Add")) {
			if(line[1].equals("Point")) {
				Point p = new Point(Integer.parseInt(line[5]), Integer.parseInt(line[6]), false, (Integer.parseInt(line[8]) == 0 ? new Color(0, 0, 0, 0)
						: new Color(Integer.parseInt(line[8]))));
				CmdAddShape addPoint = new CmdAddShape(model, p);
				addPoint.execute();
				commandsNormal.add(addPoint);
			}
			else if(line[1].equals("Line")) {
				Point p1 = new Point(Integer.parseInt(line[5]), Integer.parseInt(line[6]), false, new Color(Integer.parseInt(line[15])));
				Point p2 = new Point(Integer.parseInt(line[12]), Integer.parseInt(line[13]), false, new Color(Integer.parseInt(line[15])));
				
				Line l = new Line(p1, p2, false, (Integer.parseInt(line[17]) == 0 ? new Color(0, 0, 0, 0)
						: new Color(Integer.parseInt(line[17]))));
				
				CmdAddShape addLine = new CmdAddShape(model, l);
				addLine.execute();
				commandsNormal.add(addLine);
				
			}
			else if(line[1].equals("Rectangle")) {
				Point p = new Point(Integer.parseInt(line[5]), Integer.parseInt(line[6]), false,new Color(Integer.parseInt(line[8])));
				int height = Integer.parseInt(line[12]);
				int width = Integer.parseInt(line[10]);
				Rectangle r = new Rectangle(p, width , height, false, new Color(Integer.parseInt(line[14])), new Color(Integer.parseInt(line[16])));
				
				CmdAddShape addRect = new CmdAddShape(model, r);
				addRect.execute();
				commandsNormal.add(addRect);
			}
			else if(line[1].equals("Circle")) {
				Point p = new Point(Integer.parseInt(line[5]), Integer.parseInt(line[6]), false, new Color(Integer.parseInt(line[8])));
				int radius = Integer.parseInt(line[10]);
				Circle c = new Circle(p, radius, false, (Integer.parseInt(line[12]) == 0 ? new Color(0, 0, 0, 0)
						: new Color(Integer.parseInt(line[12]))), (Integer.parseInt(line[14]) == 0 ? new Color(0, 0, 0, 0)
						: new Color(Integer.parseInt(line[14]))));
				
				CmdAddShape addCircle = new CmdAddShape(model, c);
				addCircle.execute();
				commandsNormal.add(addCircle);
			}
			else if(line[1].equals("Donut")) {
				Point p = new Point(Integer.parseInt(line[5]), Integer.parseInt(line[6]), false, new Color(Integer.parseInt(line[8])));
				int radius = Integer.parseInt(line[10]);
				int innerRadius = Integer.parseInt(line[12]);
				
				Donut d = new Donut(p, radius, innerRadius, false, (Integer.parseInt(line[14]) == 0 ? new Color(0, 0, 0, 0)
						: new Color(Integer.parseInt(line[14]))), (Integer.parseInt(line[16]) == 0 ? new Color(0, 0, 0, 0)
						: new Color(Integer.parseInt(line[16]))));
				
				CmdAddShape addDonut = new CmdAddShape(model, d);
				addDonut.execute();
				commandsNormal.add(addDonut);
			}
			else if(line[1].equals("Hexagon")) {
				int radius = Integer.parseInt(line[8]);
				HexagonAdapter h = new HexagonAdapter(Integer.parseInt(line[5]), Integer.parseInt(line[6]), radius,false, (Integer.parseInt(line[10]) == 0 ? new Color(0, 0, 0, 0)
						: new Color(Integer.parseInt(line[10]))), (Integer.parseInt(line[12]) == 0 ? new Color(0, 0, 0, 0)
						: new Color(Integer.parseInt(line[12]))));
				
				
				CmdAddShape addHex = new CmdAddShape(model, h);
				addHex.execute();
				commandsNormal.add(addHex);
			}
		} else if(line[0].equals("Select")) {
			if(line[1].equals("Point")) {
				
				//select(Integer.parseInt(line[5]), Integer.parseInt(line[6]));
				Point p = new Point(Integer.parseInt(line[5]), Integer.parseInt(line[6]), false, (Integer.parseInt(line[8]) == 0 ? new Color(0, 0, 0, 0)
						: new Color(Integer.parseInt(line[8]))));
				selectForNext(p);
		
			}
			else if(line[1].equals("Line")) {
				//select(Integer.parseInt(line[5]), Integer.parseInt(line[6]));
				Point p1 = new Point(Integer.parseInt(line[5]), Integer.parseInt(line[6]), false, new Color(Integer.parseInt(line[15])));
				Point p2 = new Point(Integer.parseInt(line[12]), Integer.parseInt(line[13]), false, new Color(Integer.parseInt(line[15])));
				
				Line l = new Line(p1, p2, false, (Integer.parseInt(line[15]) == 0 ? new Color(0, 0, 0, 0)
						: new Color(Integer.parseInt(line[15]))));
				selectForNext(l);
				
			}
			else if(line[1].equals("Rectangle")) {
				//select(Integer.parseInt(line[5]), Integer.parseInt(line[6]));
				
				Point p = new Point(Integer.parseInt(line[5]), Integer.parseInt(line[6]), false,new Color(Integer.parseInt(line[14])));
				int height = Integer.parseInt(line[12]);
				int width = Integer.parseInt(line[10]);
				Rectangle r = new Rectangle(p, width , height, false, (Integer.parseInt(line[14]) == 0 ? new Color(0, 0, 0, 0)
						: new Color(Integer.parseInt(line[14]))), (Integer.parseInt(line[16]) == 0 ? new Color(0, 0, 0, 0)
						: new Color(Integer.parseInt(line[16]))));
				selectForNext(r);
			}
			else if(line[1].equals("Circle")) {
				//select(Integer.parseInt(line[5]), Integer.parseInt(line[6]));
				Point p = new Point(Integer.parseInt(line[5]), Integer.parseInt(line[6]), false, new Color(Integer.parseInt(line[8])));
				int radius = Integer.parseInt(line[10]);
				Circle c = new Circle(p, radius, false, (Integer.parseInt(line[12]) == 0 ? new Color(0, 0, 0, 0)
						: new Color(Integer.parseInt(line[12]))), (Integer.parseInt(line[14]) == 0 ? new Color(0, 0, 0, 0)
						: new Color(Integer.parseInt(line[14]))));
				selectForNext(c);
			}
			else if(line[1].equals("Donut")) {
				//select(Integer.parseInt(line[5]), Integer.parseInt(line[6])+Integer.parseInt(line[10])+1);
				
				Point p = new Point(Integer.parseInt(line[5]), Integer.parseInt(line[6]), false, new Color(Integer.parseInt(line[8])));
				int radius = Integer.parseInt(line[10]);
				int innerRadius = Integer.parseInt(line[12]);
				
				Donut d = new Donut(p, radius, innerRadius, false, (Integer.parseInt(line[14]) == 0 ? new Color(0, 0, 0, 0)
						: new Color(Integer.parseInt(line[14]))), (Integer.parseInt(line[16]) == 0 ? new Color(0, 0, 0, 0)
						: new Color(Integer.parseInt(line[16]))));
				selectForNext(d);

			}
			else if(line[1].equals("Hexagon")) {
				//select(Integer.parseInt(line[5]), Integer.parseInt(line[6]));
				int radius = Integer.parseInt(line[8]);
				HexagonAdapter h = new HexagonAdapter(Integer.parseInt(line[5]), Integer.parseInt(line[6]), radius,false, (Integer.parseInt(line[10]) == 0 ? new Color(0, 0, 0, 0)
						: new Color(Integer.parseInt(line[10]))), (Integer.parseInt(line[12]) == 0 ? new Color(0, 0, 0, 0)
						: new Color(Integer.parseInt(line[12]))));
				
				selectForNext(h);
	
			}
		}
		else if(line[0].equals("Deselect")) {
			if(line[1].equals("Point")) {
				
				//select(Integer.parseInt(line[5]), Integer.parseInt(line[6]));
				Point p = new Point(Integer.parseInt(line[5]), Integer.parseInt(line[6]), false, (Integer.parseInt(line[8]) == 0 ? new Color(0, 0, 0, 0)
						: new Color(Integer.parseInt(line[8]))));
				deselectForNext(p);
		
			}
			else if(line[1].equals("Line")) {
				for(String s : line) {
					System.out.println(s);
				}
				//select(Integer.parseInt(line[5]), Integer.parseInt(line[6]));
				Point p1 = new Point(Integer.parseInt(line[5]), Integer.parseInt(line[6]), false, new Color(Integer.parseInt(line[15])));
				Point p2 = new Point(Integer.parseInt(line[12]), Integer.parseInt(line[13]), false, new Color(Integer.parseInt(line[15])));
				
				Line l = new Line(p1, p2, false, (Integer.parseInt(line[15]) == 0 ? new Color(0, 0, 0, 0)
						: new Color(Integer.parseInt(line[15]))));
				deselectForNext(l);
				
			}
			else if(line[1].equals("Rectangle")) {
				//select(Integer.parseInt(line[5]), Integer.parseInt(line[6]));
				
				Point p = new Point(Integer.parseInt(line[5]), Integer.parseInt(line[6]), false,new Color(Integer.parseInt(line[14])));
				int height = Integer.parseInt(line[12]);
				int width = Integer.parseInt(line[10]);
				Rectangle r = new Rectangle(p, width , height, false, (Integer.parseInt(line[14]) == 0 ? new Color(0, 0, 0, 0)
						: new Color(Integer.parseInt(line[14]))), (Integer.parseInt(line[16]) == 0 ? new Color(0, 0, 0, 0)
						: new Color(Integer.parseInt(line[16]))));
				deselectForNext(r);
			}
			else if(line[1].equals("Circle")) {
				//select(Integer.parseInt(line[5]), Integer.parseInt(line[6]));
				Point p = new Point(Integer.parseInt(line[5]), Integer.parseInt(line[6]), false, new Color(Integer.parseInt(line[8])));
				int radius = Integer.parseInt(line[10]);
				Circle c = new Circle(p, radius, false, (Integer.parseInt(line[12]) == 0 ? new Color(0, 0, 0, 0)
						: new Color(Integer.parseInt(line[12]))), (Integer.parseInt(line[14]) == 0 ? new Color(0, 0, 0, 0)
						: new Color(Integer.parseInt(line[14]))));
				deselectForNext(c);
			}
			else if(line[1].equals("Donut")) {
				//select(Integer.parseInt(line[5]), Integer.parseInt(line[6])+Integer.parseInt(line[10])+1);
				
				Point p = new Point(Integer.parseInt(line[5]), Integer.parseInt(line[6]), false, new Color(Integer.parseInt(line[14])));
				int radius = Integer.parseInt(line[10]);
				int innerRadius = Integer.parseInt(line[12]);
				
				Donut d = new Donut(p, radius, innerRadius, false, (Integer.parseInt(line[14]) == 0 ? new Color(0, 0, 0, 0)
						: new Color(Integer.parseInt(line[14]))), (Integer.parseInt(line[16]) == 0 ? new Color(0, 0, 0, 0)
						: new Color(Integer.parseInt(line[16]))));
				deselectForNext(d);

			}
			else if(line[1].equals("Hexagon")) {
				//select(Integer.parseInt(line[5]), Integer.parseInt(line[6]));
				int radius = Integer.parseInt(line[8]);
				HexagonAdapter h = new HexagonAdapter(Integer.parseInt(line[5]), Integer.parseInt(line[6]), radius,false, (Integer.parseInt(line[10]) == 0 ? new Color(0, 0, 0, 0)
						: new Color(Integer.parseInt(line[10]))), (Integer.parseInt(line[12]) == 0 ? new Color(0, 0, 0, 0)
						: new Color(Integer.parseInt(line[12]))));
				
				deselectForNext(h);
	
			}
		} else if (line[0].equals("Modified")) {
			
			Shape selected = model.getSelectedShapes().get(0);
			if(line[1].equals("Point")) {
				
				Point oldState = (Point)selected;
				Point newState = new Point(Integer.parseInt(line[15]),Integer.parseInt(line[16]),true,new Color(Integer.parseInt(line[18])));
				CmdUpdatePoint cmdUpdate = new CmdUpdatePoint(oldState, newState);
				commandsNormal.push(cmdUpdate);
				cmdUpdate.execute();
				
			} else if (line[1].equals("Line")) {
				
				Point p1 = new Point(Integer.parseInt(line[24]),Integer.parseInt(line[25]), false, new Color(Integer.parseInt(line[34])));
				Point p2 = new Point(Integer.parseInt(line[31]),Integer.parseInt(line[32]), false, new Color(Integer.parseInt(line[34])));
				Line oldState = (Line)selected;
				Line newState = new Line(p1,p2,true,new Color(Integer.parseInt(line[34])));
				CmdUpdateLine cmdUpdate = new CmdUpdateLine(oldState, newState);
				commandsNormal.push(cmdUpdate);
				cmdUpdate.execute();
				
			} else if (line[1].equals("Rectangle")) {
				
				Point p1 = new Point(Integer.parseInt(line[23]),Integer.parseInt(line[24]), false, new Color(Integer.parseInt(line[32])));
				Rectangle oldState = (Rectangle)selected;
				Rectangle newState = new Rectangle(p1,Integer.parseInt(line[28]), Integer.parseInt(line[30]), true, new Color(Integer.parseInt(line[32])), new Color(Integer.parseInt(line[34])));
				CmdUpdateRectangle cmdUpdate = new CmdUpdateRectangle(oldState, newState);
				commandsNormal.push(cmdUpdate);
				cmdUpdate.execute();
				
			} else if (line[1].equals("Circle")) {
				
				Point p1 = new Point(Integer.parseInt(line[21]),Integer.parseInt(line[22]), false, new Color(Integer.parseInt(line[24])));
				Circle oldState = (Circle)selected;
				Circle newState = new Circle(p1, Integer.parseInt(line[26]), true ,new Color(Integer.parseInt(line[28])), new Color(Integer.parseInt(line[30])));
				CmdUpdateCircle cmdUpdate = new CmdUpdateCircle(oldState, newState);
				commandsNormal.push(cmdUpdate);
				cmdUpdate.execute();
				
			} else if (line[1].equals("Donut")) {
				
				Point p1 = new Point(Integer.parseInt(line[23]),Integer.parseInt(line[24]), false, new Color(Integer.parseInt(line[26])));
				Donut oldState = (Donut)selected;
				Donut newState = new Donut(p1, Integer.parseInt(line[28]), Integer.parseInt(line[30]), true, new Color(Integer.parseInt(line[32])), new Color(Integer.parseInt(line[34])));
				CmdUpdateDonut cmdUpdate = new CmdUpdateDonut(oldState, newState);
				commandsNormal.push(cmdUpdate);
				cmdUpdate.execute();
				
			} else if (line[1].equals("Hexagon")) {
				
				HexagonAdapter oldState = (HexagonAdapter)selected;
				HexagonAdapter newState = new HexagonAdapter(Integer.parseInt(line[19]), Integer.parseInt(line[20]), Integer.parseInt(line[22]), true, new Color(Integer.parseInt(line[24])), new Color(Integer.parseInt(line[26])));
				CmdUpdateHexagon cmdUpdate = new CmdUpdateHexagon(oldState, newState);
				commandsNormal.push(cmdUpdate);
				cmdUpdate.execute();
				
			}
		} else if(line[0].equals("Delete")) {
			
			deleteForNext();
			
		} else if(line[0].equals("Undo")) {
			undoForNext();
		} else if(line[0].equals("Redo")) {
			redoForNext();
		} else if(line[0].equals("To") && line[1].equals("back")) {
			toBack();
		} else if(line[0].equals("To") && line[1].equals("front")) {
			toFront();
		} else if(line[0].equals("Bring") && line[2].equals("back")) {
			bringToBack();
		} else if(line[0].equals("Bring") && line[2].equals("front")) {
			bringToFront();
		}
		
		
		/*propertyChangeSupport.firePropertyChange("delete", false, true);
		propertyChangeSupport.firePropertyChange("modify", false, true);
		propertyChangeSupport.firePropertyChange("undo", false, true);
		propertyChangeSupport.firePropertyChange("redo", false, true);*/
		
		frame.getView().repaint();
		frame.getListModel().addElement(getLoggComm().poll().toString());
		if(getLoggComm().isEmpty()) {
			frame.getBtnNext().setVisible(false);
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
				startPoint.setBorderColor(borderColor);
				firstClick = false;
				secondClick = true;
				
			}
			else if(secondClick) {
				endPoint = new Point();
				endPoint.setX(arg0.getX());
				endPoint.setY(arg0.getY());
				endPoint.setBorderColor(borderColor);
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
				upperLeftPoint.setBorderColor(borderColor);
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
				center.setBorderColor(borderColor);
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
				center.setBorderColor(borderColor);
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

	public Queue<String> getLoggComm() {
		return loggComm;
	}

	public void setLoggComm(Queue<String> loggComm) {
		this.loggComm = loggComm;
	}

	public void setCommandsNormal(Stack<Command> commandsNormal) {
		this.commandsNormal = commandsNormal;
	}

	public void setCommandsReverse(Stack<Command> commandsReverse) {
		this.commandsReverse = commandsReverse;
	}
	
	
	
	
}
