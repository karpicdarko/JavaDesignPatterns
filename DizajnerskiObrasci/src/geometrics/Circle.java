package geometrics;

import java.awt.Color;
import java.awt.Graphics;

import geometrics.Point;

public class Circle extends Shape {
	protected Point center;
	protected int r;
	

	public Circle() {

	}

	public Circle(Point center, int r) {
		this.center = center;
		this.r = r;
	}

	public Circle(Point center, int r, boolean selected, Color borderColor, Color fillColor) {
		this(center, r);
		setSelected(selected);
		setBorderColor(borderColor);
		setFillColor(fillColor);
	}
	

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		if(o instanceof Circle)
			return (int) (this.area() - ((Circle)o).area());
		return 0;
	}

	@Override
	public void moveBy(int x, int y) {
		// TODO Auto-generated method stub
		center.moveBy(x, y);
	}
	@Override
	public void draw(Graphics g) {
		g.setColor(this.getFillColor());
		g.fillOval(center.getX()-r, center.getY()-r, 2*r, r+r);
		g.setColor(getBorderColor());
		g.drawOval(center.getX()-r, center.getY()-r, 2*r, r+r);
		if (isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(center.getX() - r - 3, center.getY() - 3, 6, 6);
			g.drawRect(center.getX() + r - 3, center.getY() - 3, 6, 6);
			g.drawRect(center.getX() - 3, center.getY() - r - 3, 6, 6);
			g.drawRect(center.getX() - 3, center.getY() + r - 3, 6, 6);
			g.drawRect(center.getX() - 3, center.getY() - 3, 6, 6);
			
			
		}
	}
	
	public boolean contains(int x, int y) {
		double temp = center.distance(x, y);
		return temp <= r;
	}
	
	public boolean contains(Point p) {
		return this.contains(p.getX(), p.getY());
	}

	// redefinisati toString()
	// (xCenter,yCenter), radius: r
	public String toString() {
		return center+", radius: "+r;
	}
	// redefinisati equals tako da poredi krugove po radijusu

	public double area() {
		return r * r * Math.PI;
	}

	public Point getCenter() {
		return center;
	}

	public void setCenter(Point center) {
		this.center = center;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) throws Exception{
		if(r<=0)
			throw new Exception("radius mora biti veci od nule");
		this.r = r;
	}

}
