package search_problem;

import java.util.ArrayList;

import grid.GridPosition;

public class State implements Comparable<State>{
	
	GridPosition currPosition;
	ArrayList<GridPosition> rocksPositions;
	int pressedPads;
	int transitionCost = 0;
	
	public State(GridPosition pos, ArrayList<GridPosition> rocks, int pp, int tcost)
	{
		currPosition = pos;
		rocksPositions = rocks;
		pressedPads = pp;
		transitionCost = tcost;
	}
	
	public int getTransitionCost() {
		return transitionCost;
	}

	public void setTransitionCost(int transitionCost) {
		this.transitionCost = transitionCost;
	}

	public int getPressedPads() {
		return pressedPads;
	}

	public void setPressedPads(int pressedPads) {
		this.pressedPads = pressedPads;
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
