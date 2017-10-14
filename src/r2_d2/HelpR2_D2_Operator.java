package r2_d2;

import java.util.ArrayList;

import grid.Cell;
import grid.GridPosition;
import search_problem.Operator;
import search_problem.State;

public class HelpR2_D2_Operator extends Operator {
	
	int dx;
	int dy;
	String name;
	
	public HelpR2_D2_Operator(int x, int y, String n)
	{
		dx = x;
		dy = y;
		name = n;
	}
	
	@Override
	public HelpR2_D2_State apply(State state) {
		return apply((HelpR2_D2_State) state, dx, dy);
	}
	
	public HelpR2_D2_State apply(HelpR2_D2_State state, int dx, int dy){
		GridPosition currPosition = state.getCurrPosition();

		int newRow = currPosition.getRow() + dx;
		int newCol = currPosition.getColumn() + dy;

		boolean possibleMove = GridPosition.validPosition(newRow, newCol);

		if(!possibleMove)
			return null;

		Cell newCell = updateCell(newRow, newCol, state.getRocksPositions(), state.getPressedPads());



		if(newCell == Cell.EMPTY || newCell == Cell.UNPRESSED_PAD || newCell == Cell.ACTIVE_PORTAL || newCell == Cell.INACTIVE_PORTAL)
		{
			int tcost = 3;
			
			if(newCell == Cell.ACTIVE_PORTAL)
				tcost = 1;
			GridPosition newPos = new GridPosition(newRow, newCol, newCell);
			HelpR2_D2_State newState = new HelpR2_D2_State(newPos, state.getRocksPositions(), state.getPressedPads(), tcost);
			return newState;
		}
		else if(newCell == Cell.PRESSED_PAD || newCell == Cell.ROCK)
		{
			
			int nextRow = newRow + dx;
			int nextCol = newCol + dy;
			
			if(!GridPosition.validPosition(nextRow, nextCol))
				return null;
			
			Cell nextCell = updateCell(nextRow, nextCol, state.getRocksPositions(), state.getPressedPads());
			
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

			HelpR2_D2_State newState = new HelpR2_D2_State(newPos, rocksPositions, newPressPads, tcost);
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
	
	public static void main(String[] args) {
		HelpR2_D2 help = new HelpR2_D2();
		GridPosition[][] grid = new GridPosition[3][1];
		GridPosition initial = new GridPosition(2, 0, Cell.EMPTY);
		GridPosition.setNumCols(1);
		GridPosition.setNumRows(3);
		help.setGrid(grid);
		grid[0][0] = new GridPosition(0, 0, Cell.UNPRESSED_PAD);
		grid[1][0] = new GridPosition(0, 1, Cell.ROCK);
		grid[2][0] = initial;
		
		HelpR2_D2_State curr = new HelpR2_D2_State(initial, new ArrayList<GridPosition>(), 0, 0);
		curr.getRocksPositions().add(new GridPosition(1, 0, Cell.ROCK));
//		curr.getRocksPositions().add(new GridPosition(0, 0, Cell.ROCK));
		HelpR2_D2_Operator up = new HelpR2_D2_Operator(-1, 0, "Up");
		HelpR2_D2_State newHelpR2_D2_State = up.apply(curr);
//		System.out.println(grid[0][0]);
//		System.out.println(grid[1][0]);
//		System.out.println(grid[2][0]);
//		System.out.println("New position: " + newHelpR2_D2_State.getCurrPosition());
//		System.out.println("Rock positions: " + curr.getRocksPositions());
//		System.out.println("Rock positions new: " + newHelpR2_D2_State.getRocksPositions());
//		System.out.println("New number of pressed pads: " + newHelpR2_D2_State.getPressedPads());
//		System.out.println(newHelpR2_D2_State.getCurrPosition().getRow() + " " + newHelpR2_D2_State.getCurrPosition().getColumn());

	}
}
