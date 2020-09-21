package geometrics;
import java.awt.Color;
import java.awt.Graphics;

import hexagon.*;

public class HexagonAdapter extends Shape{
	private Hexagon hexagon;
	
	
	public HexagonAdapter(int x, int y, int r) {
		this.hexagon = new Hexagon(x, y, r);
		
	}

	public HexagonAdapter(int x, int y, int r, boolean selected) {
		this(x,y,r);
		this.setSelected(selected);
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
}
