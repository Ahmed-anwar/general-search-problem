package heuristics;

import java.util.ArrayList;

import grid.Cell;
import grid.GridPosition;
import r2_d2.HelpR2_D2;
import r2_d2.HelpR2_D2_State;
import search_problem.Node;

public class NearestFreeRock extends HeuristicFunction{


	@Override
	public int heuristicCost(Node node) {
		ArrayList<GridPosition> rocksPositions = ((HelpR2_D2_State) node.getState()).getRocksPositions();
		GridPosition currPosition = ((HelpR2_D2_State) node.getState()).getCurrPosition();
		
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < rocksPositions.size(); i++) {
			if(rocksPositions.get(i).getCell() == Cell.ROCK)
				min = Math.min(min, GridPosition.cityBlockDistance(currPosition, rocksPositions.get(i)));
		}
		if(min == Integer.MAX_VALUE)
			return GridPosition.cityBlockDistance(currPosition, new GridPosition(HelpR2_D2.getRowPortal(), HelpR2_D2.getColPortal(), Cell.INACTIVE_PORTAL));
		return min;
	}

}
