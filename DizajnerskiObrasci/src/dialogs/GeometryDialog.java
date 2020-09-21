package dialogs;

import javax.swing.JDialog;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GeometryDialog extends JDialog {
	private JTextField txtFirst;
	private JTextField txtSecond;
	JLabel lblFirst = new JLabel("New label");
	JLabel lblSecond = new JLabel("New label");
	public boolean ok;
	
	public GeometryDialog() {
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setBounds(100, 100, 450, 300);
		
		
		JPanel southPanel = new JPanel();
		getContentPane().add(southPanel, BorderLayout.SOUTH);
		GridBagLayout gbl_southPanel = new GridBagLayout();
		gbl_southPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_southPanel.rowHeights = new int[]{0, 0};
		gbl_southPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_southPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		southPanel.setLayout(gbl_southPanel);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setOk(true);
				setVisible(false);
			}
		});
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.insets = new Insets(0, 0, 0, 5);
		gbc_btnOk.gridx = 3;
		gbc_btnOk.gridy = 0;
		southPanel.add(btnOk, gbc_btnOk);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.gridx = 9;
		gbc_btnCancel.gridy = 0;
		southPanel.add(btnCancel, gbc_btnCancel);
		
		JPanel centerPanel = new JPanel();
		getContentPane().add(centerPanel, BorderLayout.CENTER);
		GridBagLayout gbl_centerPanel = new GridBagLayout();
		gbl_centerPanel.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_centerPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_centerPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_centerPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		centerPanel.setLayout(gbl_centerPanel);
		
		
		GridBagConstraints gbc_lblFirst = new GridBagConstraints();
		gbc_lblFirst.insets = new Insets(0, 0, 5, 5);
		gbc_lblFirst.anchor = GridBagConstraints.WEST;
		gbc_lblFirst.gridx = 1;
		gbc_lblFirst.gridy = 2;
		centerPanel.add(lblFirst, gbc_lblFirst);
		
		txtFirst = new JTextField();
		GridBagConstraints gbc_txtFirst = new GridBagConstraints();
		gbc_txtFirst.anchor = GridBagConstraints.WEST;
		gbc_txtFirst.insets = new Insets(0, 0, 5, 0);
		gbc_txtFirst.gridx = 3;
		gbc_txtFirst.gridy = 2;
		centerPanel.add(txtFirst, gbc_txtFirst);
		txtFirst.setColumns(10);
		

		GridBagConstraints gbc_lblSecond = new GridBagConstraints();
		gbc_lblSecond.insets = new Insets(0, 0, 5, 5);
		gbc_lblSecond.gridx = 1;
		gbc_lblSecond.gridy = 3;
		centerPanel.add(lblSecond, gbc_lblSecond);
		
		txtSecond = new JTextField();
		GridBagConstraints gbc_txtSecond = new GridBagConstraints();
		gbc_txtSecond.anchor = GridBagConstraints.WEST;
		gbc_txtSecond.insets = new Insets(0, 0, 5, 0);
		gbc_txtSecond.gridx = 3;
		gbc_txtSecond.gridy = 3;
		centerPanel.add(txtSecond, gbc_txtSecond);
		txtSecond.setColumns(10);
	}
	
	public GeometryDialog(String shape) {
		this();
		if(shape.equals("rectangle")) {
			lblFirst.setText("Width");
			lblSecond.setText("Height");
			
		}
		else if (shape.equals("circle")) {
			lblFirst.setText("Radius");
			lblSecond.setVisible(false);
			txtSecond.setVisible(false);
		}
		else if (shape.equals("donut")) {
			lblFirst.setText("Inner Radius");
			lblSecond.setText("Outer Radius");
		}
		else if (shape.equals("hexagon")) {
			lblFirst.setText("Radius");
			lblSecond.setVisible(false);
			txtSecond.setVisible(false);
		}
		setVisible(true);
	}
	
	public JTextField getTxtFirst() {
		return txtFirst;
	}

	public void setTxtFirst(JTextField txtFirst) {
		this.txtFirst = txtFirst;
	}

	public JTextField getTxtSecond() {
		return txtSecond;
	}

	public void setTxtSecond(JTextField txtSecond) {
		this.txtSecond = txtSecond;
	}

	public JLabel getLblFirst() {
		return lblFirst;
	}

	public void setLblFirst(JLabel lblFirst) {
		this.lblFirst = lblFirst;
	}

	public JLabel getLblSecond() {
		return lblSecond;
	}

	public void setLblSecond(JLabel lblSecond) {
		this.lblSecond = lblSecond;
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}
}
