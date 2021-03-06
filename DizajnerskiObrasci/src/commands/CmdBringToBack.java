package commands;

import java.util.List;

import geometrics.HexagonAdapter;
import geometrics.Shape;

public class CmdBringToBack implements Command{
	private List<Shape> shapes;
	private int startIndex;
	private int destinationIndex;
	
	public int getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	public int getDestinationIndex() {
		return destinationIndex;
	}
	public void setDestinationIndex(int destinationIndex) {
		this.destinationIndex = destinationIndex;
	}
	public CmdBringToBack(List<Shape> s,int startIndex)
	{
		shapes=s;
		this.startIndex=startIndex;
		destinationIndex=0;
	}
	@Override
	public void execute() {
		for(int i =getStartIndex();i>getDestinationIndex();i--)
		{
			Shape swap=shapes.get(i);
			shapes.set(i,shapes.get(i-1));
			shapes.set(i-1,swap);
		}

	}

	@Override
	public void unexecute() {
		for(int i =getDestinationIndex();i<getStartIndex();i++)
		{
			Shape swap=shapes.get(i);
			shapes.set(i, shapes.get(i+1));
			shapes.set(i+1,swap);
		}

	}
	
	public String toString() {
		if(shapes.get(destinationIndex) instanceof HexagonAdapter) {
			return "Bring to back Hexagon "  + shapes.get(destinationIndex).toString();
		} else 
			return "Bring to back " + shapes.get(destinationIndex).getClass().getSimpleName() + " " + shapes.get(destinationIndex).toString();
		
	}
}
