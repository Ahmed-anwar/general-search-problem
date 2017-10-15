package r2_d2;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import grid.Cell;
import grid.GridPosition;
import search_problem.Node;
import search_problem.Operator;
import search_problem.SearchProblem;
import search_problem.State;
import search_strategies.BFS;
import search_strategies.IterativeDeepening;
import search_strategies.SearchStrategy;
import search_strategies.UniformCost;

public class HelpR2_D2 extends SearchProblem{
	static GridPosition[][] grid;
	static int numberOfRocks;
	static int numberOfPads;
	static int numberOfBlocks;
	static int rowPortal;
	static int colPortal;
	static ArrayList<GridPosition> padsPositions;
	int numPressedPads;
	int numUnpressedPads;
	
	
	public HelpR2_D2() {
//		genGrid();
		
		ArrayList<Operator> ops = new ArrayList<Operator>();

		ops.add(new HelpR2_D2_Operator(0,  -1, "Up"));
		ops.add(new HelpR2_D2_Operator(0,  1, "Down"));
		ops.add(new HelpR2_D2_Operator(-1,  0, "Left"));
		ops.add(new HelpR2_D2_Operator(1,  0, "Right"));
		setOperators(ops);		
		
//		initialState = genGrid();
		
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


	public HelpR2_D2_State genGrid(){
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
		padsPositions = new ArrayList<GridPosition>();
		
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
			rockPositions.add(new GridPosition(row, column, Cell.ROCK));
		}

		for (int i = 0; i < numberOfPads; i++) {
			int row = (int) (Math.random() * rows);
			int column = (int) (Math.random() * columns);

			while(grid[row][column].getCell() != Cell.EMPTY )
			{
				row = (int) (Math.random() * rows);
				column = (int) (Math.random() * columns);
			}

				grid[row][column].setCell(Cell.UNPRESSED_PAD);
				padsPositions.add(new GridPosition(row, column, Cell.UNPRESSED_PAD));
				numUnpressedPads++;

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
		
		rowPortal = portalRow;
		colPortal = portalCol;

		if(numPressedPads == numberOfPads)
			grid[portalRow][portalCol].setCell(Cell.ACTIVE_PORTAL);
		else
			grid[portalRow][portalCol].setCell(Cell.INACTIVE_PORTAL);
		
		int r2d2Row = (int) (Math.random() * rows);
		int r2d2Col = (int) (Math.random() * columns);

		while(grid[r2d2Row][r2d2Col].getCell() != Cell.EMPTY  && grid[r2d2Row][r2d2Col].getCell() != Cell.UNPRESSED_PAD)
		{
			r2d2Row = (int) (Math.random() * rows);
			r2d2Col = (int) (Math.random() * columns);
		}
		
		GridPosition initialPos = new GridPosition(r2d2Row, r2d2Col, grid[r2d2Row][r2d2Col].getCell());
		return new HelpR2_D2_State(initialPos, rockPositions, numPressedPads, 0);

	}
	
	public static int getRowPortal() {
		return rowPortal;
	}

	public static void setRowPortal(int rowPortal) {
		HelpR2_D2.rowPortal = rowPortal;
	}

	public static int getColPortal() {
		return colPortal;
	}

	public static void setColPortal(int colPortal) {
		HelpR2_D2.colPortal = colPortal;
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
		HelpR2_D2_State helpState = (HelpR2_D2_State) state;
		GridPosition currPos = helpState.getCurrPosition();
		
		return helpState.getPressedPads() == numberOfPads && 
				(grid[currPos.getRow()][currPos.getColumn()].getCell() == Cell.ACTIVE_PORTAL ||
						grid[currPos.getRow()][currPos.getColumn()].getCell() == Cell.INACTIVE_PORTAL);
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
			HelpR2_D2_State transitionState = currOp.apply(node.getState());
			if(transitionState == null)
				continue;
			Node transitionNode = new Node(transitionState, node, currOp, node.getDepth() + 1, pathCost(this, node,transitionState.getTransitionCost()));
			children.add(transitionNode);
		}
		return children;
	}

	@Override
	public Node search(SearchProblem problem, SearchStrategy QingFunction, boolean visualize) {
		Node root = new Node(initialState, null, null, 0, 0);
		ArrayList<Node> visited = new ArrayList<Node>();
		visited.add(root);
		Queue<Node> queuedNodes = new LinkedList<Node>();
		queuedNodes.add(root);
		int numberOfExpandedNodes = 0;
		while(!queuedNodes.isEmpty())
		{
			Node curr = queuedNodes.poll();
			System.out.println(curr.getDepth());
//			if(((HelpR2_D2_State) curr.getState()).getCurrPosition().equals(new GridPosition(1, 3, Cell.ACTIVE_PORTAL)))
//				System.out.println("Current state : " + curr.getState() + " " + grid[1][3].getCell());
			
			if(goalTest(this, curr.getState()))
			{
				System.out.println("Solution found!");
				System.out.println("Number of expanded nodes : " + numberOfExpandedNodes);

//				System.out.println(curr.getState());
				// TODO trace operators until root
				if(visualize)
					traceSolution(curr);
				return curr;
			}
			
			ArrayList<Node> children = expand(curr, this.getOperators());
			numberOfExpandedNodes++;
			ArrayList<Node> notVisitedChildren = new ArrayList<Node>();
			for(Node child : children)
				if(!visited.contains(child)){
//					System.out.println("visited node found");
					visited.add(child);
					notVisitedChildren.add(child);
				}
			queuedNodes = QingFunction.enqueue(queuedNodes, notVisitedChildren);
		}
		
		System.out.println("No solution found!");
		System.out.println("Number of expanded nodes : " + numberOfExpandedNodes);
		return null;
	}
	
	public static GridPosition[][] getGrid()
	{
		return grid;
	}
	
	public void setGrid(GridPosition[][] Grid){
		grid = Grid;
	}
	
	public static void traceSolution(Node goalNode)
	{
		Stack<Node> path = new Stack<Node>();
		path.add(goalNode);
		Node parent = goalNode.getParent();
		while(parent != null)
		{
			path.add(parent);
			parent = parent.getParent();
		}
		while(!path.isEmpty())
			System.out.println(path.pop());
	}
	
	public static void main(String[] args) {
	
		HelpR2_D2 help = new HelpR2_D2();
		GridPosition[][] grid = new GridPosition[4][4];
		GridPosition initial = new GridPosition(0, 2, Cell.EMPTY);
		GridPosition.setNumCols(4);
		GridPosition.setNumRows(4);
		help.numPressedPads = 0;
		help.numberOfPads = 2;
		help.setGrid(grid);
		grid[0][0] = new GridPosition(0, 0, Cell.UNPRESSED_PAD);
		grid[0][1] = new GridPosition(0, 1, Cell.ROCK);
		grid[0][2] = initial;
		grid[0][3] = new GridPosition(0, 3, Cell.EMPTY);
		
		grid[1][0] = new GridPosition(1, 0, Cell.EMPTY);
		grid[1][1] = new GridPosition(1, 1, Cell.EMPTY);
		grid[1][2] = new GridPosition(1, 2, Cell.EMPTY);
		grid[1][3] = new GridPosition(1, 3, Cell.INACTIVE_PORTAL);
		
		grid[2][0] = new GridPosition(2, 0, Cell.EMPTY);
		grid[2][1] = new GridPosition(2, 1, Cell.ROCK);
		grid[2][2] = new GridPosition(2, 2, Cell.UNPRESSED_PAD);
		grid[2][3] = new GridPosition(2, 3, Cell.EMPTY);
		
		grid[3][0] = new GridPosition(3, 0, Cell.EMPTY);
		grid[3][1] = new GridPosition(3, 1, Cell.EMPTY);
		grid[3][2] = new GridPosition(3, 2, Cell.EMPTY);
		grid[3][3] = new GridPosition(3, 3, Cell.EMPTY);
		ArrayList<GridPosition> rocks = new ArrayList<GridPosition>();
		rocks.add(new GridPosition(0, 1, Cell.ROCK)); 
		rocks.add(new GridPosition(2, 1, Cell.ROCK));
		HelpR2_D2.initialState = new HelpR2_D2_State(initial, rocks, 0, 0);
//		test.printGrid();
		
		BFS bfs = new BFS(help, true);
		bfs.search();
		
		UniformCost ufc = new UniformCost(help, false);
		ufc.search();
		
//		IterativeDeepening iterDeep = new IterativeDeepening(help, false);
//		iterDeep.search();
		
	}
}
