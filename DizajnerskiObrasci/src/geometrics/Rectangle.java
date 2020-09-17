package geometrics;

import java.awt.Color;
import java.awt.Graphics;

import geometrics.*;

public class Rectangle extends Shape{
	protected Point upperLeftPoint;
	protected int width;
	protected int height;

	public Rectangle() {

	}

	public Rectangle(Point upperLeftPoint, int width, int height) {
		this.upperLeftPoint = upperLeftPoint;
		this.width = width;
		this.height = height;
	}

	public Rectangle(Point upperLeftPoint, int width, int height, boolean selected) {
		this(upperLeftPoint, width, height);
		setSelected(selected);
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		if(o instanceof Rectangle)
			return this.area() - ((Rectangle)o).area();
		return 0;
	}

	@Override
	public void moveBy(int x, int y) {
		// TODO Auto-generated method stub
		upperLeftPoint.moveBy(x, y);
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(this.getColor());
		g.fillRect(upperLeftPoint.getX(), upperLeftPoint.getY(), width, height);
		g.setColor(Color.BLACK);
		g.drawRect(upperLeftPoint.getX(), upperLeftPoint.getY(), width, height);
		if (isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(upperLeftPoint.getX() - 3, upperLeftPoint.getY() - 3, 6, 6);
			g.drawRect(upperLeftPoint.getX() + width - 3, upperLeftPoint.getY() - 3, 6, 6);
			g.drawRect(upperLeftPoint.getX() - 3, upperLeftPoint.getY() + height - 3, 6, 6);
			g.drawRect(upperLeftPoint.getX() + width  - 3, upperLeftPoint.getY() + height - 3, 6, 6);
			
		}
	}
	
	public boolean contains(int x, int y) {
		return (x >= upperLeftPoint.getX() &&
			x <= upperLeftPoint.getX() + width &&
			y >= upperLeftPoint.getY() &&
			y <= upperLeftPoint.getY() + height);
	}
	
	public boolean contains(Point p) {
		return this.contains(p.getX(), p.getY());
	}

	public String toString() {
		return upperLeftPoint+", width: "+width+", height: "+height;
	}

	public int area() {
		return width * height;
	}

	public Point getUpperLeftPoint() {
		return upperLeftPoint;
	}

	public void setUpperLeftPoint(Point upperLeftPoint) {
		this.upperLeftPoint = upperLeftPoint;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) throws Exception {
		if(width<=0)
			throw new Exception("Sirina mora biti veca od nule");
		this.width = width;
	}

	public int getHeight() {
		
		return height;
	}

	public void setHeight(int height) throws Exception {
		if(height<=0)
			throw new Exception("Visina mora biti veca od nule");
		this.height = height;
	}
}
