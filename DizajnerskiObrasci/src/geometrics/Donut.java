package geometrics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import geometrics.Point;

public class Donut extends Circle{
	private int innerRadius;

	public Donut() {

	}

	public Donut(Point center, int outerRadius, int innerRadius) {
		super(center, outerRadius);
		// this.center = center;
		// this.r = outerRadius;
		// setR(outerRadius);
		this.innerRadius = innerRadius;
	}

	public Donut(Point center, int outerRadius, int innerRadius, boolean selected, Color borderColor, Color fillColor) {
		this(center, outerRadius, innerRadius);
		setSelected(selected);
		setBorderColor(borderColor);
		setFillColor(fillColor);
	}

	public void draw(Graphics g) {
		Ellipse2D outerCircle =new Ellipse2D.Double(this.getCenter().getX()-this.getR(),this.getCenter().getY()-this.getR(),this.getR()*2,this.getR()*2);
		Ellipse2D innerCircle=new Ellipse2D.Double(this.getCenter().getX()-this.getInnerRadius(),this.getCenter().getY()-this.getInnerRadius(),this.getInnerRadius()*2,this.getInnerRadius()*2);
		Area donut=new Area(outerCircle);
		donut.subtract(new Area(innerCircle));
		Graphics2D graphics2D =(Graphics2D)g;
		graphics2D.setColor(getFillColor());
		graphics2D.fill(donut);
		graphics2D.setColor(getBorderColor());
		graphics2D.drawOval(this.getCenter().getX()-this.getInnerRadius(),this.getCenter().getY()-this.getInnerRadius(),this.getInnerRadius()*2,this.getInnerRadius()*2);
		graphics2D.drawOval(this.getCenter().getX()-this.getR(),this.getCenter().getY()-this.getR(),this.getR()*2,this.getR()*2);
		if (isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(center.getX() - r - 3, center.getY() - 3, 6, 6);
			g.drawRect(center.getX() + r - 3, center.getY() - 3, 6, 6);
			g.drawRect(center.getX() - 3, center.getY() - r - 3, 6, 6);
			g.drawRect(center.getX() - 3, center.getY() + r - 3, 6, 6);
			g.drawRect(center.getX() - 3, center.getY() - 3, 6, 6);
			
			
		}
	}

	public String toString() {
		return super.toString() + ", innerRadius: " + innerRadius;
	}

	public boolean contains(int x, int y) {
		return super.contains(x, y) && center.distance(x, y) > innerRadius;
	}

	public double area() {
		return super.area() - (innerRadius * innerRadius * Math.PI);
	}

	public int getInnerRadius() {
		return innerRadius;
	}

	public void setInnerRadius(int innerRadius) throws Exception {
		
		this.innerRadius = innerRadius;
	}
}
