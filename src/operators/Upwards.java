package operators;

import java.util.ArrayList;

import grid.Cell;
import grid.GridPosition;
import r2_d2.HelpR2_D2;
import search_problem.State;

public class Upwards extends Operator{

	@Override
	public State apply(State currState) {
		
		GridPosition currPosition = currState.getCurrPosition();
		
		int newRow = currPosition.getRow() - 1;
		int newCol = currPosition.getColumn();
		
		boolean possibleMove = GridPosition.validPosition(newRow, newCol);
		
		if(!possibleMove)
			return null;
		
		Cell newCell = HelpR2_D2.getGrid()[newRow][newCol].getCell();
		
		int nextRow = newRow -1;
		int nextCol = newCol;
		
		Cell nextCell = HelpR2_D2.getGrid()[nextRow][nextCol].getCell();
		
		GridPosition newPos = new GridPosition(newRow, newCol, newCell);
		
		if(newCell == Cell.EMPTY || newCell == Cell.UNPRESSED_PAD || newCell == Cell.ACTIVE_PORTAL)
		{
			State newState = new State(newPos, currState.getRocksPositions());
			return newState;
		}
		else if(newCell == Cell.PRESSED_PAD || newCell == Cell.ROCK)
		{
			boolean possiblePush = GridPosition.validPosition(nextRow, nextCol);
			possiblePush &= (nextCell == Cell.EMPTY || nextCell == Cell.UNPRESSED_PAD);
			
			if(!possiblePush)
				return null;
			
			if(newCell == Cell.PRESSED_PAD)
				newCell = Cell.UNPRESSED_PAD;
			else if(newCell == Cell.ROCK)
				newCell = Cell.EMPTY;
			
			if(nextCell == Cell.UNPRESSED_PAD)
				nextCell = Cell.PRESSED_PAD;
			else if(nextCell == Cell.EMPTY)
				nextCell = Cell.ROCK;

			GridPosition nextPos = new GridPosition(nextRow, nextCol, nextCell);
			
			HelpR2_D2.getGrid()[newRow][newCol].setCell(newCell);
			HelpR2_D2.getGrid()[nextRow][nextCol].setCell(nextCell);
			
			ArrayList<GridPosition> rocksPositions = (ArrayList<GridPosition>) currState.getRocksPositions().clone();
			rocksPositions.remove(newPos);
			rocksPositions.add(nextPos);
		
			State newState = new State(newPos, rocksPositions);
			return newState;
		}
		
		
		
		return null;
	}
	
}