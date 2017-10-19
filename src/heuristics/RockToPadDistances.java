package heuristics;

import java.util.ArrayList;

import grid.Cell;
import grid.GridPosition;
import r2_d2.HelpR2_D2;
import r2_d2.HelpR2_D2_State;
import search_problem.Node;

public class RockToPadDistances extends HeuristicFunction{

	@Override
	public int heuristicCost(Node node) {
		ArrayList<GridPosition> rocksPositions = ((HelpR2_D2_State) node.getState()).getRocksPositions();
		GridPosition currPosition = ((HelpR2_D2_State) node.getState()).getCurrPosition();
		
		int estimatedCost = 0;
		for (int i = 0; i < rocksPositions.size(); i++) {
			GridPosition currRock = rocksPositions.get(i);
			int min = Integer.MAX_VALUE;
			for (int j = 0; j < HelpR2_D2.getPadsPositions().size(); j++) 
				min = Math.min(min, GridPosition.cityBlockDistance(currRock, HelpR2_D2.getPadsPositions().get(j)));
			
			estimatedCost += min;
		}
		if(estimatedCost == 0)
			return GridPosition.cityBlockDistance(currPosition, new GridPosition(HelpR2_D2.getRowPortal(), HelpR2_D2.getColPortal(), Cell.PORTAL));
		return estimatedCost;
	}

}
