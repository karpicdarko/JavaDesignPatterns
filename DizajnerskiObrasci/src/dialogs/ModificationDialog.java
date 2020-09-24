package dialogs;

import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JColorChooser;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;

import geometrics.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ModificationDialog extends JDialog{
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JLabel lblNewLabel = new JLabel("New label");
	private JLabel lblNewLabel_1 = new JLabel("New label");
	private JLabel lblNewLabel_2 = new JLabel("New label");
	private JLabel lblNewLabel_3 = new JLabel("New label");
	private JButton btnBorderColor = new JButton("Border Color");
	private JButton btnFillColor = new JButton("Fill Color");
	private boolean ok = false;
	private Color borderColor;
	private Color fillColor;
	private Shape shape;
	
	public ModificationDialog() {
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setBounds(100, 100, 608, 414);
		
		JPanel panelSouth = new JPanel();
		getContentPane().add(panelSouth, BorderLayout.SOUTH);
		
		JButton btnOK = new JButton("OK");
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setOk(true);
				setVisible(false);
			}
		});
		panelSouth.add(btnOK);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		panelSouth.add(btnCancel);
		
		JPanel panelCenter = new JPanel();
		getContentPane().add(panelCenter, BorderLayout.CENTER);
		GridBagLayout gbl_panelCenter = new GridBagLayout();
		gbl_panelCenter.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_panelCenter.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelCenter.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panelCenter.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		panelCenter.setLayout(gbl_panelCenter);
		
		
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		panelCenter.add(lblNewLabel, gbc_lblNewLabel);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.anchor = GridBagConstraints.WEST;
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.gridx = 3;
		gbc_textField.gridy = 1;
		panelCenter.add(textField, gbc_textField);
		textField.setColumns(10);
		
		
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 3;
		panelCenter.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		textField_1 = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.anchor = GridBagConstraints.WEST;
		gbc_textField_1.insets = new Insets(0, 0, 5, 0);
		gbc_textField_1.gridx = 3;
		gbc_textField_1.gridy = 3;
		panelCenter.add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);
		
		
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 1;
		gbc_lblNewLabel_2.gridy = 5;
		panelCenter.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		textField_2 = new JTextField();
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.anchor = GridBagConstraints.WEST;
		gbc_textField_2.insets = new Insets(0, 0, 5, 0);
		gbc_textField_2.gridx = 3;
		gbc_textField_2.gridy = 5;
		panelCenter.add(textField_2, gbc_textField_2);
		textField_2.setColumns(10);
		
		
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 1;
		gbc_lblNewLabel_3.gridy = 7;
		panelCenter.add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		textField_3 = new JTextField();
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.insets = new Insets(0, 0, 5, 0);
		gbc_textField_3.anchor = GridBagConstraints.WEST;
		gbc_textField_3.gridx = 3;
		gbc_textField_3.gridy = 7;
		panelCenter.add(textField_3, gbc_textField_3);
		textField_3.setColumns(10);
		
		
		GridBagConstraints gbc_btnBorderColor = new GridBagConstraints();
		gbc_btnBorderColor.insets = new Insets(0, 0, 5, 5);
		gbc_btnBorderColor.gridx = 1;
		gbc_btnBorderColor.gridy = 8;
		btnBorderColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JColorChooser colorChooser = new JColorChooser(shape.getBorderColor());

			    ActionListener okActionListener = new ActionListener() {
			      public void actionPerformed(ActionEvent actionEvent) {
			        borderColor = colorChooser.getColor();
			        btnBorderColor.setBackground(borderColor);

			      }
			    };

			    ActionListener cancelActionListener = new ActionListener() {
			      public void actionPerformed(ActionEvent actionEvent) {
			        borderColor = shape.getBorderColor();
			        btnBorderColor.setBackground(shape.getBorderColor());
			      }
			    };

			    final JDialog dialog = JColorChooser.createDialog(null, "Change shape color", true,
			        colorChooser, okActionListener, cancelActionListener);

			    dialog.setVisible(true);
			}
		});
		panelCenter.add(btnBorderColor, gbc_btnBorderColor);
		
		
		GridBagConstraints gbc_btnFillColor = new GridBagConstraints();
		gbc_btnFillColor.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnFillColor.insets = new Insets(0, 0, 0, 5);
		gbc_btnFillColor.gridx = 1;
		gbc_btnFillColor.gridy = 9;
		btnFillColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JColorChooser colorChooser = new JColorChooser(shape.getFillColor());

			    ActionListener okActionListener = new ActionListener() {
			      public void actionPerformed(ActionEvent actionEvent) {
			        fillColor = colorChooser.getColor();
			        btnFillColor.setBackground(fillColor);
			        
			      }
			    };

			    ActionListener cancelActionListener = new ActionListener() {
			      public void actionPerformed(ActionEvent actionEvent) {
			        fillColor = shape.getFillColor();
			        btnFillColor.setBackground(shape.getFillColor());
			      }
			    };

			    final JDialog dialog = JColorChooser.createDialog(null, "Change shape color", true,
			        colorChooser, okActionListener, cancelActionListener);

			    dialog.setVisible(true);
				
				
			}
		});
		panelCenter.add(btnFillColor, gbc_btnFillColor);
	}
	
	public ModificationDialog(Shape shape) { 
		this();
		
		this.shape = shape;
		if (shape instanceof Point) {
			borderColor = shape.getBorderColor();
			lblNewLabel.setText("X coordinate");
			lblNewLabel_1.setText("Y coordinate");
			textField.setText(String.valueOf(((Point) shape).getX()));
			textField_1.setText(String.valueOf(((Point) shape).getY()));
			btnBorderColor.setBackground(shape.getBorderColor());
			btnFillColor.setVisible(false);
			lblNewLabel_2.setVisible(false);
			lblNewLabel_3.setVisible(false);
			textField_2.setVisible(false);
			textField_3.setVisible(false);
			
		}
		else if (shape instanceof Line) {
			borderColor = shape.getBorderColor();
			lblNewLabel.setText("Start Point X coordinate");
			lblNewLabel_1.setText("Start Point Y coordinate");
			lblNewLabel_2.setText("End Point X coordinate");
			lblNewLabel_3.setText("End Point Y coordinate");
			textField.setText(String.valueOf(((Line) shape).getStartPoint().getX()));
			textField_1.setText(String.valueOf(((Line) shape).getStartPoint().getY()));
			textField_2.setText(String.valueOf((((Line) shape).getEndPoint().getX())));
			textField_3.setText(String.valueOf((((Line) shape).getEndPoint().getY())));
			btnBorderColor.setBackground(shape.getBorderColor());
			btnFillColor.setVisible(false);
			
		}
		else if(shape instanceof Rectangle) {
			borderColor = shape.getBorderColor();
			fillColor = shape.getFillColor();
			lblNewLabel.setText("Upper Left Point X coordinate");
			lblNewLabel_1.setText("Upper Left Y coordinate");
			lblNewLabel_2.setText("Width");
			lblNewLabel_3.setText("Height");
			textField.setText(String.valueOf(((Rectangle) shape).getUpperLeftPoint().getX()));
			textField_1.setText(String.valueOf(((Rectangle) shape).getUpperLeftPoint().getY()));
			textField_2.setText(String.valueOf(((Rectangle) shape).getWidth()));
			textField_3.setText(String.valueOf(((Rectangle) shape).getWidth()));
			btnBorderColor.setBackground(shape.getBorderColor());
			btnFillColor.setBackground(shape.getFillColor());
			
		}
		else if (shape instanceof Circle && !(shape instanceof Donut)) {
			borderColor = shape.getBorderColor();
			fillColor = shape.getFillColor();
			lblNewLabel.setText("Center Point X coordinate");
			lblNewLabel_1.setText("Center Left Y coordinate");
			lblNewLabel_2.setText("Radius");
			lblNewLabel_3.setVisible(false);
			textField.setText(String.valueOf(((Circle) shape).getCenter().getX()));
			textField_1.setText(String.valueOf(((Circle) shape).getCenter().getY()));			
			textField_2.setText(String.valueOf(((Circle) shape).getR()));
			textField_3.setVisible(false);
			btnBorderColor.setBackground(shape.getBorderColor());
			btnFillColor.setBackground(shape.getFillColor());
		}
		else if (shape instanceof Donut) {
			setBorderColor(shape.getBorderColor());
			setFillColor(shape.getFillColor());
			lblNewLabel.setText("Center Point X coordinate");
			lblNewLabel_1.setText("Center Left Y coordinate");
			lblNewLabel_2.setText("Inner Radius");
			lblNewLabel_3.setText("Outer Radius");
			textField.setText(String.valueOf(((Donut) shape).getCenter().getX()));
			textField_1.setText(String.valueOf(((Donut) shape).getCenter().getY()));
			textField_2.setText(String.valueOf(((Donut) shape).getInnerRadius()));
			textField_3.setText(String.valueOf(((Donut) shape).getR()));
			btnBorderColor.setBackground(getBorderColor());
			btnFillColor.setBackground(getFillColor());
		}
		else if (shape instanceof HexagonAdapter) {
			borderColor = shape.getBorderColor();
			fillColor = shape.getFillColor();
			lblNewLabel.setText("X coordinate");
			lblNewLabel_1.setText("Y coordinate");
			lblNewLabel_2.setText("Radius");
			lblNewLabel_3.setVisible(false);
			textField.setText(String.valueOf(((HexagonAdapter) shape).getHexagon().getX()));
			textField_1.setText(String.valueOf(((HexagonAdapter) shape).getHexagon().getY()));
			textField_2.setText(String.valueOf(((HexagonAdapter) shape).getHexagon().getR()));
			textField_3.setVisible(false);
			btnBorderColor.setBackground(((HexagonAdapter) shape).getHexagon().getBorderColor());
			btnFillColor.setBackground(((HexagonAdapter) shape).getHexagon().getAreaColor());
		}
		setVisible(true);
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public JTextField getTextField() {
		return textField;
	}

	public void setTextField(JTextField textField) {
		this.textField = textField;
	}

	public JTextField getTextField_1() {
		return textField_1;
	}

	public void setTextField_1(JTextField textField_1) {
		this.textField_1 = textField_1;
	}

	public JTextField getTextField_2() {
		return textField_2;
	}

	public void setTextField_2(JTextField textField_2) {
		this.textField_2 = textField_2;
	}

	public JTextField getTextField_3() {
		return textField_3;
	}

	public void setTextField_3(JTextField textField_3) {
		this.textField_3 = textField_3;
	}

	public JLabel getLblNewLabel() {
		return lblNewLabel;
	}

	public void setLblNewLabel(JLabel lblNewLabel) {
		this.lblNewLabel = lblNewLabel;
	}

	public JLabel getLblNewLabel_1() {
		return lblNewLabel_1;
	}

	public void setLblNewLabel_1(JLabel lblNewLabel_1) {
		this.lblNewLabel_1 = lblNewLabel_1;
	}

	public JLabel getLblNewLabel_2() {
		return lblNewLabel_2;
	}

	public void setLblNewLabel_2(JLabel lblNewLabel_2) {
		this.lblNewLabel_2 = lblNewLabel_2;
	}

	public JLabel getLblNewLabel_3() {
		return lblNewLabel_3;
	}

	public void setLblNewLabel_3(JLabel lblNewLabel_3) {
		this.lblNewLabel_3 = lblNewLabel_3;
	}

	public JButton getBtnBorderColor() {
		return btnBorderColor;
	}

	public void setBtnBorderColor(JButton btnBorderColor) {
		this.btnBorderColor = btnBorderColor;
	}

	public JButton getBtnFillColor() {
		return btnFillColor;
	}

	public void setBtnFillColor(JButton btnFillColor) {
		this.btnFillColor = btnFillColor;
	}

	public Color getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}

	public Color getFillColor() {
		return fillColor;
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

	public Shape getDialogShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}
	
	

}
