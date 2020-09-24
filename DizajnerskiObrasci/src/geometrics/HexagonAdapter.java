package geometrics;
import java.awt.Color;
import java.awt.Graphics;

import hexagon.*;

public class HexagonAdapter extends Shape{
	private Hexagon hexagon;
	
	
	public HexagonAdapter(int x, int y, int r) {
		this.hexagon = new Hexagon(x, y, r);
		
	}

	public HexagonAdapter(int x, int y, int r, boolean selected, Color borderColor, Color fillColor) {
		this(x,y,r);
		this.setSelected(selected);
		super.setBorderColor(borderColor);
		super.setFillColor(fillColor);
		this.hexagon.setAreaColor(fillColor);
		this.hexagon.setBorderColor(borderColor);
	}
	
	public HexagonAdapter clone() {
		HexagonAdapter cloneHexagon = new HexagonAdapter(this.getHexagon().getX(), this.getHexagon().getY(), this.getHexagon().getR(), this.getHexagon().isSelected(), this.getHexagon().getBorderColor(), this.getHexagon().getAreaColor());
		return cloneHexagon;
	}

	@Override
	public int compareTo(Object arg0) {
		if (arg0 instanceof HexagonAdapter) {
			return (int) (6 * ((this.hexagon.getR() * Math.sqrt(3)) / 4)
					- 6 * ((((HexagonAdapter) arg0).hexagon.getR() * Math.sqrt(3)) / 4));
		}
		return 0;
	}

	@Override
	public void moveBy(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean contains(int x, int y) {
		return this.hexagon.doesContain(x, y);
	}

	@Override
	public void draw(Graphics g) {
		this.hexagon.paint(g);
		
	}
	
	public Hexagon getHexagon() {
		return hexagon;
	}

	public void setHexagon(Hexagon hexagon) {
		this.hexagon = hexagon;
	}
	
	public void setSelected(boolean selected) {
		super.setSelected(selected);
		this.hexagon.setSelected(selected);
	}
	
	public String toString() {
		Point p = new Point(hexagon.getX(), hexagon.getY());

		return "Center=" + p + ",radius= " + hexagon.getR() + " ,borderColor= " + getBorderColor().getRGB()
				+ " ,fillColor= " + getFillColor().getRGB();
		
	}
}
