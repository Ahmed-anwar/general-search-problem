package r2_d2;

import java.util.ArrayList;

import grid.GridPosition;
import search_problem.State;

public class HelpR2_D2_State  extends State implements Comparable<HelpR2_D2_State>{
	
	GridPosition currPosition;
	ArrayList<GridPosition> rocksPositions;
	int pressedPads;
	int transitionCost = 0;
	long id = 0;
	
	public HelpR2_D2_State(GridPosition pos, ArrayList<GridPosition> rocks, int pp, int tcost)
	{
		currPosition = pos;
		rocksPositions = rocks;
		pressedPads = pp;
		transitionCost = tcost;
		
		for (int i = 0; i < rocksPositions.size(); i++) 
			id |= (1 << rocksPositions.get(i).getCellNum());
		
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


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@Override
	public boolean equals(Object other)
	{
		HelpR2_D2_State otherState = (HelpR2_D2_State) other;
		return id == otherState.getId() && currPosition.equals(otherState.getCurrPosition());
		
	}

	@Override
	public int compareTo(HelpR2_D2_State o) {
		// TODO Auto-generated method stub
		return 0;
	}



}
