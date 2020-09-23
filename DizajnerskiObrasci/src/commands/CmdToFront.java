package commands;

import java.util.List;

import geometrics.Shape;

public class CmdToFront implements Command{
	private List<Shape> shapes;
	private int startIndex;
	private int destinationIndex;
	
	public CmdToFront(List<Shape> s,int startIndex)
	{
		shapes=s;
		this.startIndex=startIndex;
		destinationIndex=startIndex+1;
	}
	@Override
	public void execute() {
		Shape swap=shapes.get(startIndex);
		shapes.set(startIndex, shapes.get(destinationIndex));
		shapes.set(destinationIndex,swap);

	}

	@Override
	public void unexecute() {
		execute();
	}
	
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

}