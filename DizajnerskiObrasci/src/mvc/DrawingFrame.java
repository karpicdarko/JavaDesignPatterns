package mvc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JToggleButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JList;
import javax.management.modelmbean.ModelMBean;
import javax.swing.BoxLayout;
import net.miginfocom.swing.MigLayout;
import javax.swing.border.LineBorder;
import javax.swing.JScrollBar;


public class DrawingFrame extends JFrame implements PropertyChangeListener{
	private DrawingView view = new DrawingView();
	private DrawingController controller;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton rdbtnSelect = new JRadioButton("Select");
	private JRadioButton rdbtnPoint = new JRadioButton("Point");
	private JRadioButton rdbtnLine = new JRadioButton("Line");
	private JRadioButton rdbtnRectangle = new JRadioButton("Rectangle");
	private JRadioButton rdbtnCircle = new JRadioButton("Circle");
	private JRadioButton rdbtnDonut = new JRadioButton("Donut");
	private JRadioButton rdbtnHexagon = new JRadioButton("Hexagon");
	private final JButton btnFillColor = new JButton("Fill Color");
	private final JButton btnBorderColor = new JButton("Border Color");
	private final JButton btnModify = new JButton("Modify");
	private final JButton btnDelete = new JButton("Delete");
	private final JButton btnToBack = new JButton("To Back");
	private final JButton btnToFront = new JButton("To Front");
	private final JButton btnBringToBack = new JButton("Bring To Back");
	private final JButton btnBringToFront = new JButton("Bring to Front");
	private DefaultListModel<String> listModel = new DefaultListModel<String>();
	private final JPanel southPanel = new JPanel();
	private JList<String> list = new JList<String>();
	private final JScrollPane scrollPane_1 = new JScrollPane(list);
	private final JButton btnUndo = new JButton("Undo");
	private final JButton btnRedo = new JButton("Redo");
	private final JButton btnSave = new JButton("Save");
	private final JButton btnLoad = new JButton("Load");
	
	
	public DrawingFrame() {
		
		view.setBackground(Color.WHITE);
		view.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				controller.mouseClicked(arg0);
			}
		});
		getContentPane().add(view, BorderLayout.CENTER);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.delete();
			}
		});
		
		getBtnDelete().setVisible(false);
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.modify();
			}
		});
		getBtnModify().setVisible(false);
		getBtnUndo().setVisible(false);
		getBtnRedo().setVisible(false);
		
		
		JPanel westPanel = new JPanel();
		getContentPane().add(westPanel, BorderLayout.WEST);
		GridBagLayout gbl_westPanel = new GridBagLayout();
		gbl_westPanel.columnWidths = new int[]{121, 0};
		gbl_westPanel.rowHeights = new int[]{23, 23, 23, 23, 23, 23, 23, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_westPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_westPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		westPanel.setLayout(gbl_westPanel);
		
	
		buttonGroup.add(rdbtnSelect);
		GridBagConstraints gbc_rdbtnSelect = new GridBagConstraints();
		gbc_rdbtnSelect.anchor = GridBagConstraints.WEST;
		gbc_rdbtnSelect.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnSelect.gridx = 0;
		gbc_rdbtnSelect.gridy = 0;
		westPanel.add(rdbtnSelect, gbc_rdbtnSelect);
		
	
		buttonGroup.add(rdbtnPoint);
		GridBagConstraints gbc_rdbtnPoint = new GridBagConstraints();
		gbc_rdbtnPoint.anchor = GridBagConstraints.WEST;
		gbc_rdbtnPoint.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnPoint.gridx = 0;
		gbc_rdbtnPoint.gridy = 1;
		westPanel.add(rdbtnPoint, gbc_rdbtnPoint);
		
		
		buttonGroup.add(rdbtnLine);
		GridBagConstraints gbc_rdbtnLine = new GridBagConstraints();
		gbc_rdbtnLine.anchor = GridBagConstraints.WEST;
		gbc_rdbtnLine.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnLine.gridx = 0;
		gbc_rdbtnLine.gridy = 2;
		westPanel.add(rdbtnLine, gbc_rdbtnLine);
		
		
		buttonGroup.add(rdbtnRectangle);
		GridBagConstraints gbc_rdbtnRectangle = new GridBagConstraints();
		gbc_rdbtnRectangle.anchor = GridBagConstraints.WEST;
		gbc_rdbtnRectangle.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnRectangle.gridx = 0;
		gbc_rdbtnRectangle.gridy = 3;
		westPanel.add(rdbtnRectangle, gbc_rdbtnRectangle);
		
		
		buttonGroup.add(rdbtnCircle);
		GridBagConstraints gbc_rdbtnCircle = new GridBagConstraints();
		gbc_rdbtnCircle.anchor = GridBagConstraints.WEST;
		gbc_rdbtnCircle.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnCircle.gridx = 0;
		gbc_rdbtnCircle.gridy = 4;
		westPanel.add(rdbtnCircle, gbc_rdbtnCircle);
		
		
		buttonGroup.add(rdbtnDonut);
		GridBagConstraints gbc_rdbtnDonut = new GridBagConstraints();
		gbc_rdbtnDonut.anchor = GridBagConstraints.WEST;
		gbc_rdbtnDonut.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnDonut.gridx = 0;
		gbc_rdbtnDonut.gridy = 5;
		westPanel.add(rdbtnDonut, gbc_rdbtnDonut);
		
		
		buttonGroup.add(rdbtnHexagon);
		GridBagConstraints gbc_rdbtnHexagon = new GridBagConstraints();
		gbc_rdbtnHexagon.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnHexagon.anchor = GridBagConstraints.WEST;
		gbc_rdbtnHexagon.gridx = 0;
		gbc_rdbtnHexagon.gridy = 6;
		westPanel.add(rdbtnHexagon, gbc_rdbtnHexagon);
		
		GridBagConstraints gbc_btnFillColor = new GridBagConstraints();
		gbc_btnFillColor.insets = new Insets(0, 0, 5, 0);
		gbc_btnFillColor.gridx = 0;
		gbc_btnFillColor.gridy = 7;
		btnFillColor.setBackground(Color.white);
		btnFillColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final JColorChooser colorChooser = new JColorChooser(controller.getFillColor());

			    ActionListener okActionListener = new ActionListener() {
			      public void actionPerformed(ActionEvent actionEvent) {
			        controller.setFillColor(colorChooser.getColor()); 
			        btnFillColor.setBackground(colorChooser.getColor());

			        
			      }
			    };

			    ActionListener cancelActionListener = new ActionListener() {
			      public void actionPerformed(ActionEvent actionEvent) {
			        controller.setFillColor(controller.getFillColor());
			        btnFillColor.setBackground(controller.getFillColor());

			      }
			    };

			    final JDialog dialog = JColorChooser.createDialog(null, "Choose shape color", true,
			        colorChooser, okActionListener, cancelActionListener);

			    dialog.setVisible(true);
				
	
			}
		});
		westPanel.add(btnFillColor, gbc_btnFillColor);
		
		GridBagConstraints gbc_btnBorderColor = new GridBagConstraints();
		gbc_btnBorderColor.insets = new Insets(0, 0, 5, 0);
		gbc_btnBorderColor.gridx = 0;
		gbc_btnBorderColor.gridy = 8;
		btnBorderColor.setBackground(Color.black);
		btnBorderColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				final JColorChooser colorChooser = new JColorChooser(controller.getBorderColor());

			    ActionListener okActionListener = new ActionListener() {
			      public void actionPerformed(ActionEvent actionEvent) {
			        controller.setBorderColor(colorChooser.getColor()); 
			        btnBorderColor.setBackground(colorChooser.getColor());

			        
			      }
			    };

			    ActionListener cancelActionListener = new ActionListener() {
			      public void actionPerformed(ActionEvent actionEvent) {
			        controller.setBorderColor(controller.getBorderColor());
			        btnBorderColor.setBackground(controller.getBorderColor());

			      }
			    };

			    final JDialog dialog = JColorChooser.createDialog(null, "Choose shape color", true,
			        colorChooser, okActionListener, cancelActionListener);

			    dialog.setVisible(true);
				
			}
		});
		westPanel.add(btnBorderColor, gbc_btnBorderColor);
		
		GridBagConstraints gbc_btnModify = new GridBagConstraints();
		gbc_btnModify.insets = new Insets(0, 0, 5, 0);
		gbc_btnModify.gridx = 0;
		gbc_btnModify.gridy = 9;
		westPanel.add(btnModify, gbc_btnModify);
		
		GridBagConstraints gbc_btnDelete = new GridBagConstraints();
		gbc_btnDelete.insets = new Insets(0, 0, 5, 0);
		gbc_btnDelete.gridx = 0;
		gbc_btnDelete.gridy = 10;
		westPanel.add(btnDelete, gbc_btnDelete);
		
		GridBagConstraints gbc_btnToBack = new GridBagConstraints();
		gbc_btnToBack.insets = new Insets(0, 0, 5, 0);
		gbc_btnToBack.gridx = 0;
		gbc_btnToBack.gridy = 11;
		btnToBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.toBack();
			}
		});
		westPanel.add(btnToBack, gbc_btnToBack);
		
		GridBagConstraints gbc_btnToFront = new GridBagConstraints();
		gbc_btnToFront.insets = new Insets(0, 0, 5, 0);
		gbc_btnToFront.gridx = 0;
		gbc_btnToFront.gridy = 12;
		btnToFront.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.toFront();
			}
		});
		westPanel.add(btnToFront, gbc_btnToFront);
		
		GridBagConstraints gbc_btnBringToBack = new GridBagConstraints();
		gbc_btnBringToBack.insets = new Insets(0, 0, 5, 0);
		gbc_btnBringToBack.gridx = 0;
		gbc_btnBringToBack.gridy = 13;
		btnBringToBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.bringToBack();
			}
		});
		westPanel.add(btnBringToBack, gbc_btnBringToBack);
		
		GridBagConstraints gbc_btnBringToFront = new GridBagConstraints();
		gbc_btnBringToFront.insets = new Insets(0, 0, 5, 0);
		gbc_btnBringToFront.gridx = 0;
		gbc_btnBringToFront.gridy = 14;
		btnBringToFront.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.bringToFront();
			}
		});
		westPanel.add(btnBringToFront, gbc_btnBringToFront);
		
		GridBagConstraints gbc_btnUndo = new GridBagConstraints();
		gbc_btnUndo.insets = new Insets(0, 0, 5, 0);
		gbc_btnUndo.gridx = 0;
		gbc_btnUndo.gridy = 15;
		btnUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.undo();
			}
		});
		westPanel.add(btnUndo, gbc_btnUndo);
		
		GridBagConstraints gbc_btnRedo = new GridBagConstraints();
		gbc_btnRedo.insets = new Insets(0, 0, 5, 0);
		gbc_btnRedo.gridx = 0;
		gbc_btnRedo.gridy = 16;
		btnRedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.redo();
			}
		});
		westPanel.add(btnRedo, gbc_btnRedo);
		
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.insets = new Insets(0, 0, 5, 0);
		gbc_btnSave.gridx = 0;
		gbc_btnSave.gridy = 17;
		westPanel.add(btnSave, gbc_btnSave);
		
		GridBagConstraints gbc_btnLoad = new GridBagConstraints();
		gbc_btnLoad.gridx = 0;
		gbc_btnLoad.gridy = 18;
		westPanel.add(btnLoad, gbc_btnLoad);
		
		getContentPane().add(southPanel, BorderLayout.SOUTH);
		southPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
		list.setVisibleRowCount(9);
		list.setModel(listModel);
		
		southPanel.add(scrollPane_1, "cell 0 0,grow");
		
	
		
	}
	
	public DrawingView getView() {
		return view;
	}

	public void setController(DrawingController controller) {
		this.controller = controller;
	}
	
	public JRadioButton getRdbtnSelect() {
		return rdbtnSelect;
	}

	public void setRdbtnSelect(JRadioButton rdbtnSelect) {
		this.rdbtnSelect = rdbtnSelect;
	}

	public JRadioButton getRdbtnPoint() {
		return rdbtnPoint;
	}

	public void setRdbtnPoint(JRadioButton rdbtnPoint) {
		this.rdbtnPoint = rdbtnPoint;
	}

	public JRadioButton getRdbtnLine() {
		return rdbtnLine;
	}

	public void setRdbtnLine(JRadioButton rdbtnLine) {
		this.rdbtnLine = rdbtnLine;
	}

	public JRadioButton getRdbtnRectangle() {
		return rdbtnRectangle;
	}

	public void setRdbtnRectangle(JRadioButton rdbtnRectangle) {
		this.rdbtnRectangle = rdbtnRectangle;
	}

	public JRadioButton getRdbtnCircle() {
		return rdbtnCircle;
	}

	public void setRdbtnCircle(JRadioButton rdbtnCircle) {
		this.rdbtnCircle = rdbtnCircle;
	}

	public JRadioButton getRdbtnDonut() {
		return rdbtnDonut;
	}

	public void setRdbtnDonut(JRadioButton rdbtnDonut) {
		this.rdbtnDonut = rdbtnDonut;
	}

	public JRadioButton getRdbtnHexagon() {
		return rdbtnHexagon;
	}

	public void setRdbtnHexagon(JRadioButton rdbtnHexagon) {
		this.rdbtnHexagon = rdbtnHexagon;
	}

	public JButton getBtnModify() {
		return btnModify;
	}

	public JButton getBtnDelete() {
		return btnDelete;
	}
	
	public DefaultListModel<String> getListModel() {
		return listModel;
	}

	public void setListModel(DefaultListModel<String> listModel) {
		this.listModel = listModel;
	}
	
	public JButton getBtnUndo() {
		return btnUndo;
	}

	public JButton getBtnRedo() {
		return btnRedo;
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		if (arg0.getPropertyName().equals("delete")) {
			if(controller.getModel().getSelectedShapes().size()  > 0) {
				getBtnDelete().setVisible((boolean)arg0.getNewValue());
			} else {
				getBtnDelete().setVisible((boolean)arg0.getOldValue());
			}
			
		} else if (arg0.getPropertyName().equals("modify")) {
			if(controller.getModel().getSelectedShapes().size() == 1) {
				getBtnModify().setVisible((boolean)arg0.getNewValue());
			} else {
				getBtnModify().setVisible((boolean)arg0.getOldValue());
			}
			
		} else if (arg0.getPropertyName().equals("redo")) {
			if(controller.getCommandsReverse().size() > 0) {
				getBtnRedo().setVisible((boolean)arg0.getNewValue());
			} else {
				getBtnRedo().setVisible((boolean)arg0.getOldValue());
			}
		}  else if (arg0.getPropertyName().equals("undo")) {
			if(controller.getCommandsNormal().size() > 0) {
				getBtnUndo().setVisible((boolean)arg0.getNewValue());
			} else {
				getBtnUndo().setVisible((boolean)arg0.getOldValue());
			}
				
			
		}
			
		
		
	}
}
