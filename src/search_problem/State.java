package search_problem;

import java.util.ArrayList;

import grid.GridPosition;

public class State implements Comparable<State>{
	
	GridPosition currPosition;
	ArrayList<GridPosition> rocksPositions;
	
	public State(GridPosition pos, ArrayList<GridPosition> rocks)
	{
		currPosition = pos;
		rocksPositions = rocks;
	}
	
	public GridPosition getCurrPosition() {
		return currPosition;
	}

	public void setCurrPosition(GridPosition currPosition) {
		this.currPosition = currPosition;
	}

	public ArrayList<GridPosition> getRocksPositions() {
		return rocksPositions;
	}

	public void setRocksPositions(ArrayList<GridPosition> rockPositions) {
		this.rocksPositions = rockPositions;
	}

	@Override
	public int compareTo(State s) {
		// TODO Auto-generated method stub
		return 0;
	}

}
