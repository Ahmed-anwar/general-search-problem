package operators;
import java.util.ArrayList;

import grid.Cell;
import grid.GridPosition;
import r2_d2.HelpR2_D2;
import search_problem.State;

public abstract class Operator {

	public abstract State apply(State state);

	public State apply(State state, int dx, int dy){
		GridPosition currPosition = state.getCurrPosition();

		int newRow = currPosition.getRow() + dx;
		int newCol = currPosition.getColumn() + dy;

		boolean possibleMove = GridPosition.validPosition(newRow, newCol);

		if(!possibleMove)
			return null;

		Cell newCell = updateCell(newRow, newCol, state.getRocksPositions(), state.getPressedPads());

		int nextRow = newRow + dx;
		int nextCol = newCol + dy;

		Cell nextCell = updateCell(nextRow, nextCol, state.getRocksPositions(), state.getPressedPads());


		if(newCell == Cell.EMPTY || newCell == Cell.UNPRESSED_PAD || newCell == Cell.ACTIVE_PORTAL)
		{
			int tcost = 3;
			
			if(newCell == Cell.ACTIVE_PORTAL)
				tcost = 1;
			GridPosition newPos = new GridPosition(newRow, newCol, newCell);
			State newState = new State(newPos, state.getRocksPositions(), state.getPressedPads(), tcost);
			return newState;
		}
		else if(newCell == Cell.PRESSED_PAD || newCell == Cell.ROCK)
		{
			boolean possiblePush = GridPosition.validPosition(nextRow, nextCol);
			possiblePush &= (nextCell == Cell.EMPTY || nextCell == Cell.UNPRESSED_PAD);

			if(!possiblePush)
				return null;

			int tcost = 4;
			
			int newPressPads = state.getPressedPads();
			
			if(newCell == Cell.PRESSED_PAD){
				newPressPads--;
				newCell = Cell.UNPRESSED_PAD;
				tcost = 7;
			}
			else if(newCell == Cell.ROCK)
				newCell = Cell.EMPTY;

			
			if(nextCell == Cell.UNPRESSED_PAD)
			{
				newPressPads++;
				nextCell = Cell.PRESSED_PAD;
				tcost = 1;
			}
			else if(nextCell == Cell.EMPTY)
				nextCell = Cell.ROCK;

			GridPosition newPos = new GridPosition(newRow, newCol, newCell);
			GridPosition nextPos = new GridPosition(nextRow, nextCol, nextCell);

			ArrayList<GridPosition> rocksPositions = (ArrayList<GridPosition>) state.getRocksPositions().clone();
			rocksPositions.remove(newPos);
			rocksPositions.add(nextPos);

			State newState = new State(newPos, rocksPositions, newPressPads, tcost);
			return newState;
		}
		return null;
	}

	public Cell updateCell(int row, int column, ArrayList<GridPosition> rocks, int pp){
		GridPosition[][] grid = HelpR2_D2.getGrid();
		Cell initialCell = grid[row][column].getCell();

		boolean containsRock = rocks.contains(new GridPosition(row, column, Cell.ROCK));

		switch(initialCell){
		case EMPTY : 
		case ROCK : return containsRock ? Cell.ROCK : Cell.EMPTY;
		case UNPRESSED_PAD : 
		case PRESSED_PAD : return containsRock ? Cell.PRESSED_PAD : Cell.UNPRESSED_PAD;
		case ACTIVE_PORTAL : 
		case INACTIVE_PORTAL : return pp == HelpR2_D2.getNumberOfPads() ? Cell.ACTIVE_PORTAL : Cell.INACTIVE_PORTAL;
		case BLOCKED : return Cell.BLOCKED;
		}
		return Cell.EMPTY;
	}

}
