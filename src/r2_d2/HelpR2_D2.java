package r2_d2;
import java.io.PrintWriter;
import java.util.ArrayList;

import grid.Cell;
import grid.GridPosition;
import operators.Operator;
import search_problem.Node;
import search_problem.SearchProblem;
import search_problem.State;
import search_strategies.SearchStrategy;

public class HelpR2_D2 extends SearchProblem{
	static GridPosition[][] grid;
	int numberOfRocks;
	int numberOfPads;
	int numberOfBlocks;

	int numPressedPads;
	int numUnpressedPads;
	
	public HelpR2_D2() {

//		genGrid();
		
		
	}

	public void genGrid(){
		int rows = (int) (Math.random() * 10 + 1);
		int columns = (int) (Math.random() * 10 + 1);
		grid = new GridPosition[rows][columns];
		GridPosition.setNumRows(rows);
		GridPosition.setNumCols(columns);
		for (int i = 0; i < grid.length; i++) 
			for (int j = 0; j < grid[i].length; j++) 
				grid[i][j] = new GridPosition(i, j, Cell.EMPTY);


		numberOfRocks = (int) (Math.random() * (rows * columns / 2)  + 1);
		numberOfPads = numberOfRocks;
		numberOfBlocks = (int) (Math.random() * rows + 1);
		numPressedPads = 0;
		numUnpressedPads = 0;
		
		
		// TODO Save rock positions for initial state
		for (int i = 0; i < numberOfRocks; i++) {
			int row = (int) (Math.random() * rows);
			int column = (int) (Math.random() * columns);

			while(grid[row][column].getCell() != Cell.EMPTY)
			{
				row = (int) (Math.random() * rows);
				column = (int) (Math.random() * columns);
			}

			grid[row][column].setCell(Cell.ROCK);
		}

		for (int i = 0; i < numberOfPads; i++) {
			int row = (int) (Math.random() * rows);
			int column = (int) (Math.random() * columns);

			while(grid[row][column].getCell() != Cell.EMPTY && grid[row][column].getCell() != Cell.ROCK)
			{
				row = (int) (Math.random() * rows);
				column = (int) (Math.random() * columns);
			}

			if(grid[row][column].getCell() == Cell.EMPTY)
			{
				grid[row][column].setCell(Cell.UNPRESSED_PAD);
				numUnpressedPads++;
			}

			else if(grid[row][column].getCell() == Cell.ROCK)
			{
				grid[row][column].setCell(Cell.PRESSED_PAD);
				numPressedPads++;
			}
		}

		for (int i = 0; i < numberOfBlocks; i++) {
			int row = (int) (Math.random() * rows);
			int column = (int) (Math.random() * columns);

			while(grid[row][column].getCell() != Cell.EMPTY)
			{
				row = (int) (Math.random() * rows);
				column = (int) (Math.random() * columns);
			}

			grid[row][column].setCell(Cell.BLOCKED);
		}

		int portalRow = (int) (Math.random() * rows);
		int portalCol = (int) (Math.random() * columns);

		while(grid[portalRow][portalCol].getCell() != Cell.EMPTY)
		{
			portalRow = (int) (Math.random() * rows);
			portalCol = (int) (Math.random() * columns);
		}

		if(numPressedPads == numberOfPads)
			grid[portalRow][portalCol].setCell(Cell.ACTIVE_PORTAL);
		else
			grid[portalRow][portalCol].setCell(Cell.INACTIVE_PORTAL);
		
		int r2d2Row = (int) (Math.random() * rows);
		int r2d2Col = (int) (Math.random() * columns);

		while(grid[r2d2Row][r2d2Col].getCell() != Cell.EMPTY)
		{
			r2d2Row = (int) (Math.random() * rows);
			r2d2Col = (int) (Math.random() * columns);
		}

		

	}
	
	public void printGrid()
	{
		PrintWriter pw = new PrintWriter(System.out);
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				sb.append(grid[i][j] + "  ");
			}
			sb.append("\n");
		}
		pw.println(sb);
		pw.flush();
		pw.close();
	}

	@Override
	public boolean goalTest(SearchProblem problem, State state) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long pathCost(SearchProblem problem, Node node) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<Node> expand(Node node, ArrayList<Operator> ops) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Operator> search(SearchProblem problem, SearchStrategy QingFunction, boolean visualize) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static GridPosition[][] getGrid()
	{
		return grid;
	}
	
	public static void main(String[] args) {
		HelpR2_D2 test = new HelpR2_D2();
		test.printGrid();
	}
}
