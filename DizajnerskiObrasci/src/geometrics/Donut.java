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

	public Donut(Point center, int outerRadius, int innerRadius, boolean selected) {
		this(center, outerRadius, innerRadius);
		setSelected(selected);
	}

	public void draw(Graphics g) {
		Ellipse2D outerCircle =new Ellipse2D.Double(this.getCenter().getX()-this.getR(),this.getCenter().getY()-this.getR(),this.getR()*2,this.getR()*2);
		Ellipse2D innerCircle=new Ellipse2D.Double(this.getCenter().getX()-this.getInnerRadius(),this.getCenter().getY()-this.getInnerRadius(),this.getInnerRadius()*2,this.getInnerRadius()*2);
		Area donut=new Area(outerCircle);
		donut.subtract(new Area(innerCircle));
		Graphics2D graphics2D =(Graphics2D)g;
		
		graphics2D.setColor(Color.black);
		graphics2D.fill(donut);
		
		/*super.draw(g);
		g.setColor(Color.WHITE);
		g.fillOval(center.getX() - innerRadius, center.getY() - innerRadius, 2 * innerRadius, 2 * innerRadius);
		g.setColor(Color.BLACK);
		g.drawOval(center.getX() - innerRadius, center.getY() - innerRadius, 2 * innerRadius, 2 * innerRadius);
		if (isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(this.getCenter().getX() - innerRadius - 3, this.getCenter().getY() - 3, 6, 6);
			g.drawRect(this.getCenter().getX() + innerRadius - 3, this.getCenter().getY() - 3, 6, 6);
			g.drawRect(this.getCenter().getX() - 3, this.getCenter().getY() - innerRadius - 3, 6, 6);
			g.drawRect(this.getCenter().getX() - 3, this.getCenter().getY() + innerRadius - 3, 6, 6);
			
		}*/
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
