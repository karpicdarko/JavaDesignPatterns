package mvc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JToggleButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class DrawingFrame extends JFrame{
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
	
	public DrawingFrame() {
		view.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				controller.mouseClicked(arg0);
			}
		});
		getContentPane().add(view, BorderLayout.CENTER);
		
		JPanel westPanel = new JPanel();
		getContentPane().add(westPanel, BorderLayout.WEST);
		GridBagLayout gbl_westPanel = new GridBagLayout();
		gbl_westPanel.columnWidths = new int[]{121, 0};
		gbl_westPanel.rowHeights = new int[]{23, 23, 23, 23, 23, 23, 23, 0, 0, 0};
		gbl_westPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_westPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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
		btnFillColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Color fillColor = JColorChooser.showDialog(getContentPane(), "Choose fill color", controller.getFillColor()); 
				controller.setFillColor(fillColor);
				btnFillColor.setBackground(fillColor);
			}
		});
		westPanel.add(btnFillColor, gbc_btnFillColor);
		
		GridBagConstraints gbc_btnBorderColor = new GridBagConstraints();
		gbc_btnBorderColor.gridx = 0;
		gbc_btnBorderColor.gridy = 8;
		btnBorderColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color borderColor = JColorChooser.showDialog(getContentPane(), "Choose border color", controller.getBorderColor());
				controller.setBorderColor(borderColor);
				btnBorderColor.setBackground(borderColor);
			}
		});
		westPanel.add(btnBorderColor, gbc_btnBorderColor);
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
}
