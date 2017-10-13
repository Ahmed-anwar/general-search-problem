package operators;

import java.util.ArrayList;

import grid.Cell;
import grid.GridPosition;
import r2_d2.HelpR2_D2;
import search_problem.State;

public class Upwards extends Operator{
	
	@Override
	public State apply(State currState) {
		return super.apply(currState, -1, 0);
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
		
		State curr = new State(initial, new ArrayList<GridPosition>(), 0);
		curr.getRocksPositions().add(new GridPosition(1, 0, Cell.ROCK));
//		curr.getRocksPositions().add(new GridPosition(0, 0, Cell.ROCK));
		Upwards up = new Upwards();
		State newState = up.apply(curr);
//		System.out.println(grid[0][0]);
//		System.out.println(grid[1][0]);
//		System.out.println(grid[2][0]);
		System.out.println("New position: " + newState.getCurrPosition());
		System.out.println("Rock positions: " + curr.getRocksPositions());
		System.out.println("Rock positions new: " + newState.getRocksPositions());
		System.out.println("New number of pressed pads: " + newState.getPressedPads());
//		System.out.println(newState.getCurrPosition().getRow() + " " + newState.getCurrPosition().getColumn());
		
	}
	
}
