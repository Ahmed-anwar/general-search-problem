package r2_d2;
import java.io.PrintWriter;
import java.util.ArrayList;

import grid.Cell;
import grid.GridPosition;
import operators.Downwards;
import operators.Left;
import operators.Operator;
import operators.Right;
import operators.Upwards;
import search_problem.Node;
import search_problem.SearchProblem;
import search_problem.State;
import search_strategies.SearchStrategy;

public class HelpR2_D2 extends SearchProblem{
	static GridPosition[][] grid;
	static int numberOfRocks;
	static int numberOfPads;
	static int numberOfBlocks;
	
	int numPressedPads;
	int numUnpressedPads;
	
	public HelpR2_D2() {
//		genGrid();
		ArrayList<Operator> ops = new ArrayList<Operator>();
		ops.add(new Upwards());
		ops.add(new Downwards());
		ops.add(new Left());
		ops.add(new Right());
		setOperators(ops);				
	}
	
	public static int getNumberOfRocks() {
		return numberOfRocks;
	}

	public static void setNumberOfRocks(int numberOfRocks) {
		HelpR2_D2.numberOfRocks = numberOfRocks;
	}

	public static int getNumberOfPads() {
		return numberOfPads;
	}

	public static void setNumberOfPads(int numberOfPads) {
		HelpR2_D2.numberOfPads = numberOfPads;
	}

	public static int getNumberOfBlocks() {
		return numberOfBlocks;
	}

	public static void setNumberOfBlocks(int numberOfBlocks) {
		HelpR2_D2.numberOfBlocks = numberOfBlocks;
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
		
		ArrayList<GridPosition>	rockPositions = new ArrayList<GridPosition>();
		
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
	public int pathCost(SearchProblem problem, Node parent, int cost) {
		return parent.getCost() + cost;
	}

	@Override
	public ArrayList<Node> expand(Node node, ArrayList<Operator> ops) {
		ArrayList<Node> children = new ArrayList<Node>();
		for (int i = 0; i < ops.size(); i++) {
			Operator currOp = ops.get(i);
			State transitionState = currOp.apply(node.getState());
			if(transitionState == null)
				continue;
			Node transitionNode = new Node(transitionState, node, currOp, node.getDepth() + 1, pathCost(this, node,transitionState.getTransitionCost()));
			children.add(transitionNode);
		}
		return children;
	}

	@Override
	public ArrayList<Operator> search(SearchProblem problem, SearchStrategy QingFunction, boolean visualize) {
		Node root = new Node(initialState, null, null, 0, 0);
		ArrayList<Node> children = expand(root, getOperators());
		
		return null;
	}
	
	public static GridPosition[][] getGrid()
	{
		return grid;
	}
	
	public void setGrid(GridPosition[][] Grid){
		grid = Grid;
	}
	
	public static void main(String[] args) {
		HelpR2_D2 test = new HelpR2_D2();
		test.printGrid();
	}
}
