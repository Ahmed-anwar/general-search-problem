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

		Cell newCell = GridPosition.updateCell(newRow, newCol, state.getRocksPositions(), state.getPressedPads());



		if(newCell == Cell.EMPTY || newCell == Cell.UNPRESSED_PAD || newCell == Cell.PORTAL)
		{
			int tcost = 3;
			
			if(newCell == Cell.PORTAL)
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
			
			Cell nextCell = GridPosition.updateCell(nextRow, nextCol, state.getRocksPositions(), state.getPressedPads());
			
			boolean possiblePush = GridPosition.validPosition(nextRow, nextCol);
			possiblePush &= (nextCell == Cell.EMPTY || nextCell == Cell.UNPRESSED_PAD || nextCell == Cell.PORTAL);

			if(!possiblePush)
				return null;

			int tcost = 4;
			
			int newPressPads = state.getPressedPads();
			
			if(newCell == Cell.PRESSED_PAD){
				newPressPads--;
				newCell = Cell.UNPRESSED_PAD;
				tcost = 7;
			}
			else if(newCell == Cell.ROCK && newRow == HelpR2_D2.rowPortal && newCol == HelpR2_D2.colPortal)
				newCell = Cell.PORTAL;
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
			else if(nextCell == Cell.PORTAL)
				nextCell = Cell.ROCK;

			GridPosition newPos = new GridPosition(newRow, newCol, newCell);
			GridPosition nextPos = new GridPosition(nextRow, nextCol, nextCell);

			@SuppressWarnings("unchecked")
			ArrayList<GridPosition> rocksPositions = (ArrayList<GridPosition>) state.getRocksPositions().clone();
			rocksPositions.remove(newPos);
			rocksPositions.add(nextPos);

			HelpR2_D2_State newState = new HelpR2_D2_State(newPos, rocksPositions, newPressPads, tcost);
			return newState;
		}
		return null;
	}


	
//	public static void main(String[] args) {
//		HelpR2_D2 help = new HelpR2_D2();
//		GridPosition[][] grid = new GridPosition[3][1];
//		GridPosition initial = new GridPosition(2, 0, Cell.EMPTY);
//		GridPosition.setNumCols(1);
//		GridPosition.setNumRows(4);
//		help.setGrid(grid);
//		grid[0][0] = new GridPosition(0, 0, Cell.UNPRESSED_PAD);
//		grid[1][0] = new GridPosition(0, 1, Cell.ROCK);
//		grid[2][0] = initial;
//		
//		HelpR2_D2_State curr = new HelpR2_D2_State(initial, new ArrayList<GridPosition>(), 0, 0);
//		curr.getRocksPositions().add(new GridPosition(1, 0, Cell.ROCK));
////		curr.getRocksPositions().add(new GridPosition(0, 0, Cell.ROCK));
//		HelpR2_D2_Operator up = new HelpR2_D2_Operator(-1, 0, "Up");
//		HelpR2_D2_State newHelpR2_D2_State = up.apply(curr);
////		System.out.println(grid[0][0]);
////		System.out.println(grid[1][0]);
////		System.out.println(grid[2][0]);
////		System.out.println("New position: " + newHelpR2_D2_State.getCurrPosition());
////		System.out.println("Rock positions: " + curr.getRocksPositions());
////		System.out.println("Rock positions new: " + newHelpR2_D2_State.getRocksPositions());
////		System.out.println("New number of pressed pads: " + newHelpR2_D2_State.getPressedPads());
////		System.out.println(newHelpR2_D2_State.getCurrPosition().getRow() + " " + newHelpR2_D2_State.getCurrPosition().getColumn());
//
//	}
}
