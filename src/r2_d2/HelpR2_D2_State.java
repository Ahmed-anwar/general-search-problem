package r2_d2;

import java.util.ArrayList;

import grid.Cell;
import grid.GridPosition;
import search_problem.State;

public class HelpR2_D2_State  extends State{

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
		id = 0;

		//		System.out.println(rocksPositions);
		for (int i = 0; i < rocksPositions.size(); i++) 
		{
			id |= (1 << rocksPositions.get(i).getCellNum());
		}
		//		System.out.println(Long.toBinaryString(id));

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
		boolean ret = id == otherState.getId() && currPosition.equals(otherState.getCurrPosition());
		return ret;
	}

	@Override
	public int compareTo(State other) {
		HelpR2_D2_State o = (HelpR2_D2_State) other;
		return (transitionCost - pressedPads) - (o.transitionCost - pressedPads);
	}

	
//	public int h1()
//	{
//		int min = Integer.MAX_VALUE;
//		for (int i = 0; i < rocksPositions.size(); i++) {
//			if(rocksPositions.get(i).getCell() == Cell.ROCK)
//				min = Math.min(min, cityBlockDistance(currPosition, rocksPositions.get(i)));
//		}
//		if(min == Integer.MAX_VALUE)
//			return cityBlockDistance(currPosition, new GridPosition(HelpR2_D2.getRowPortal(), HelpR2_D2.getColPortal(), Cell.INACTIVE_PORTAL));
//		return min;
//	}

//	public int h2()
//	{
//		return cityBlockDistance(currPosition, new GridPosition(HelpR2_D2.getRowPortal(), HelpR2_D2.getColPortal(), Cell.INACTIVE_PORTAL));
//	}
	
//	public int h3()
//	{
//		int estimatedCost = 0;
//		for (int i = 0; i < rocksPositions.size(); i++) {
//			GridPosition currRock = rocksPositions.get(i);
//			int min = Integer.MAX_VALUE;
//			
//			for (int j = 0; j < HelpR2_D2.padsPositions.size(); j++) 
//				min = Math.min(min, cityBlockDistance(currRock, HelpR2_D2.padsPositions.get(j)));
//			
//			estimatedCost += min;
//		}
//		if(estimatedCost == 0)
//			return cityBlockDistance(currPosition, new GridPosition(HelpR2_D2.getRowPortal(), HelpR2_D2.getColPortal(), Cell.INACTIVE_PORTAL));
//		return estimatedCost;
//	}

	public String toString()
	{
		String ret = "Robot's position : Row = " + currPosition.getRow() + " Column = " + currPosition.getColumn() + "\n" 
				+ "Rocks positions : " + Long.toBinaryString(id) + "\n"
				+ "Number of pressed pads : " + pressedPads + "\n"
				+ "Transition cost : " + transitionCost+ "\n";
		GridPosition[][] grid = HelpR2_D2.getGrid();

		StringBuilder sb = new StringBuilder();
		sb.append(ret + '\n');

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if(i == currPosition.getRow() && j == currPosition.getColumn())
				{
					sb.append("R ");
					continue;
				}
				char c = 'x';
				Cell currCell = GridPosition.updateCell(i, j, rocksPositions, pressedPads);
				switch(currCell){
				case EMPTY: c = '.'; break;
				case ROCK: c = 'x'; break;
				case ACTIVE_PORTAL: 
				case INACTIVE_PORTAL: c = (char)187 ; break;
				case BLOCKED: c = (char)569; break;
				case UNPRESSED_PAD: c = 'o'; break;
				case PRESSED_PAD: c = (char)186 ; break;
				}
				sb.append(c + " ");
			}
			sb.append('\n');
		}

		return sb.toString();

	}



}
