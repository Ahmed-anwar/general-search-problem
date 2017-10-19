package r2_d2;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import grid.Cell;
import grid.GridPosition;
import heuristics.NearestFreeRock;
import heuristics.RockToPadDistances;
import search_problem.Node;
import search_problem.Operator;
import search_problem.SearchProblem;
import search_problem.State;
import search_strategies.AStarSearch;
import search_strategies.BFS;
import search_strategies.DFS;
import search_strategies.GreedySearch;
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

		ops.add(new HelpR2_D2_Operator(-1,  0, "Up"));
		ops.add(new HelpR2_D2_Operator(1,  0, "Down"));
		ops.add(new HelpR2_D2_Operator(0,  -1, "Left"));
		ops.add(new HelpR2_D2_Operator(0,  1, "Right"));
		setOperators(ops);		

		initialState = genGrid();

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

		ArrayList<Integer> rowsAndColumns = new ArrayList<Integer>();
		rowsAndColumns.add(2);	rowsAndColumns.add(3);	rowsAndColumns.add(4);	rowsAndColumns.add(5);	//rowsAndColumns.add(6);
		Collections.shuffle(rowsAndColumns);
		int rows = rowsAndColumns.get(0);
		Collections.shuffle(rowsAndColumns);
		int columns = rowsAndColumns.get(0);
		if(rows == 2 && columns == 2)
			rows = 3;
		//		System.out.println("rows: " + rows);
		//		System.out.println("Cols: " + columns);
		GridPosition.setNumRows(rows);
		GridPosition.setNumCols(columns);
		grid = new GridPosition[rows][columns];

		ArrayList<Integer> possibleCell = new ArrayList<Integer>();
		for (int i = 0; i < rows * columns; i++) 
		{
			possibleCell.add(i);
			int row = GridPosition.getPosition(i).getRow();
			int col = GridPosition.getPosition(i).getColumn();
			grid[row][col] = new GridPosition(row, col, Cell.EMPTY);
		}

		Collections.shuffle(possibleCell);

		ArrayList<Integer> rnum = new ArrayList<Integer>();
		rnum.add(1);
		for (int i = 1; i < (rows*columns - 2) / 3; i++) 
			rnum.add(i);

		Collections.shuffle(rnum);
		numberOfRocks = rnum.get(0);
		numberOfPads = numberOfRocks;
		rnum.add(0);
		Collections.shuffle(rnum);
		numberOfBlocks = rnum.get(rnum.size()-1);
		numPressedPads = 0;
		numUnpressedPads = numberOfPads;
		padsPositions = new ArrayList<GridPosition>();

		ArrayList<GridPosition> rockPositions = new ArrayList<GridPosition>();

		for (int i = 0; i < numberOfRocks; i++) {
			int rpos = possibleCell.remove(0);
			//			System.out.println("rock cell: " + rpos);
			GridPosition pos = GridPosition.getPosition(rpos);
			//			System.out.println("rock position: " + pos);
			while(GridPosition.isCorner(pos.getRow(), pos.getColumn())){
				//				System.out.println("corner position: " + pos);
				possibleCell.add(pos.getCellNum());
				//				System.out.println("corner cell: " + pos.getCellNum());
				pos = GridPosition.getPosition(possibleCell.remove(0));
			}
			rockPositions.add(new GridPosition(pos.getRow(), pos.getColumn(), Cell.ROCK));
			grid[pos.getRow()][pos.getColumn()].setCell(Cell.ROCK);

			pos = GridPosition.getPosition(possibleCell.remove(0));
			padsPositions.add(new GridPosition(pos.getRow(), pos.getColumn(), Cell.UNPRESSED_PAD));
			grid[pos.getRow()][pos.getColumn()].setCell(Cell.UNPRESSED_PAD);
		}

		for (int i = 0; i < numberOfBlocks; i++) {
			GridPosition pos = GridPosition.getPosition(possibleCell.remove(0));
			padsPositions.add(new GridPosition(pos.getRow(), pos.getColumn(), Cell.BLOCKED));
			grid[pos.getRow()][pos.getColumn()].setCell(Cell.BLOCKED);
		}

		GridPosition initialPos = GridPosition.getPosition(possibleCell.remove(0));

		int portalCell = possibleCell.remove(0);
		GridPosition portalPos = GridPosition.getPosition(portalCell);
		rowPortal = portalPos.getRow();
		colPortal = portalPos.getColumn();

		//		System.out.println("position: " + portalCell);
		//		System.out.println("row portal: " + rowPortal);
		//		System.out.println("col portal: " + colPortal);
		grid[portalPos.getRow()][portalPos.getColumn()].setCell(Cell.PORTAL);

		return new HelpR2_D2_State(initialPos, rockPositions, 0, 0);
	}

	public HelpR2_D2_State genGrid2(){
		ArrayList<Integer> rowsAndColumns = new ArrayList<Integer>();
		rowsAndColumns.add(2);	rowsAndColumns.add(3);	rowsAndColumns.add(4);	rowsAndColumns.add(5);	rowsAndColumns.add(6);
		Collections.shuffle(rowsAndColumns);
		int rows = rowsAndColumns.get(0);
		int columns = rowsAndColumns.get(1);
		//		System.out.println(rows);
		//		System.out.println(columns);
		GridPosition.setNumRows(rows);
		GridPosition.setNumCols(columns);

		grid = new GridPosition[rows][columns];
		ArrayList<GridPosition> possiblePos = new ArrayList<GridPosition>();
		ArrayList<Integer> rnum = new ArrayList<Integer>();
		rnum.add(1);
		for (int i = 1; i < (rows*columns - 2) / 3; i++) {
			rnum.add(i);
		}
		Collections.shuffle(rnum);
		numberOfRocks = rnum.get(0);
		numberOfPads = numberOfRocks;
		numberOfBlocks = rnum.get(rnum.size()-1);
		numPressedPads = 0;
		numUnpressedPads = numberOfPads;
		padsPositions = new ArrayList<GridPosition>();

		if(numberOfRocks + numberOfPads + numberOfBlocks + 2 >= rows * columns)
			return genGrid();
		ArrayList<GridPosition> rockPositions = new ArrayList<GridPosition>();
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				grid[i][j] = new GridPosition(i, j, Cell.EMPTY);
				if(i > 0 && i < rows - 1&& j > 0 && j < columns - 1)
					possiblePos.add(new GridPosition(i, j, Cell.EMPTY));
			}
		}

		if(possiblePos.size() == 0)
		{
			if(rows <= 2)
			{
				for (int i = 0; i < columns; i++) {
					possiblePos.add(new GridPosition(0, i, Cell.EMPTY));
				}
			}
			else if(columns <= 2)
			{
				for (int i = 0; i < rows; i++) {
					possiblePos.add(new GridPosition(i, 0, Cell.EMPTY));
				}
			}
		}
		//		System.out.println("possible positions : \n" + possiblePos);
		//		System.out.println("number of rocks : " + numberOfRocks);
		//		System.out.println("number of pads : " + numberOfPads);
		//		System.out.println("number of blocks : " + numberOfBlocks);
		Collections.shuffle(possiblePos);
		int insertedRocks = 0;
		for (int i = 0; possiblePos.size() > 1 && i < numberOfRocks * 2; i += 2) {
			GridPosition rock = possiblePos.remove(0);
			GridPosition pad = possiblePos.remove(0);
			grid[rock.getRow()][rock.getColumn()].setCell(Cell.ROCK);
			grid[pad.getRow()][pad.getColumn()].setCell(Cell.UNPRESSED_PAD);
			rockPositions.add(new GridPosition(rock.getRow(), rock.getColumn(), Cell.ROCK));
			padsPositions.add(new GridPosition(pad.getRow(), pad.getColumn(), Cell.UNPRESSED_PAD));
			insertedRocks++;
		}
		numberOfRocks = insertedRocks;
		numberOfPads = insertedRocks;

		//		System.out.println("number of inserted rocks : " + insertedRocks);
		//		System.out.println("rocks positions : " + rockPositions);
		//		System.out.println("pads positions : " + padsPositions);

		if(numberOfRocks == 0)
			return genGrid();

		ArrayList<GridPosition> remPositions = new ArrayList<GridPosition>();
		for (int j = 1; j < grid[0].length - 1; j++) {
			remPositions.add(new GridPosition(0, j, Cell.EMPTY));
			remPositions.add(new GridPosition(grid.length - 1, j, Cell.EMPTY));
		}
		for (int j = 0; j < grid.length; j++) {
			remPositions.add(new GridPosition(j, 0, Cell.EMPTY));
			remPositions.add(new GridPosition(j, grid[0].length - 1, Cell.EMPTY));
		}

		Collections.shuffle(remPositions);
		possiblePos.addAll(remPositions);
		for (int i = 0; i < numberOfBlocks; i++) {
			GridPosition block = possiblePos.remove(0);
			grid[block.getRow()][block.getColumn()].setCell(Cell.BLOCKED);
		}

		GridPosition initialPos = possiblePos.remove(0);
		GridPosition portalPos = possiblePos.get(0);
		rowPortal = portalPos.getRow();
		colPortal = portalPos.getColumn();

		//		System.out.println("initial position : " + initialPos);
		//		System.out.println("portal position : " + portalPos);
		grid[portalPos.getRow()][portalPos.getColumn()].setCell(Cell.PORTAL);
		return new HelpR2_D2_State(initialPos, rockPositions, 0, 0);
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
				currPos.getRow() == rowPortal  && currPos.getColumn() == colPortal;
	}

	@Override
	public int pathCost(SearchProblem problem, Node node, int cost) {
		return node.getCost();
	}

	@Override
	public ArrayList<Node> expand(Node node, ArrayList<Operator> ops) {
		ArrayList<Node> children = new ArrayList<Node>();
		for (int i = 0; i < ops.size(); i++) 
		{
			Operator currOp = ops.get(i);
			HelpR2_D2_State transitionState = currOp.apply(node.getState());
			if(transitionState == null)
				continue;
			Node transitionNode = new Node(transitionState, node, currOp, node.getDepth() + 1);
			children.add(transitionNode);
		}
		return children;
	}

	@Override
	public Node search(SearchProblem problem, SearchStrategy QingFunction, boolean visualize) {
		Node root = new Node(initialState, null, null, 0);
		ArrayList<Node> visited = new ArrayList<Node>();
		visited.add(root);
		Queue<Node> queuedNodes = new LinkedList<Node>();
		queuedNodes.add(root);
		while(!queuedNodes.isEmpty())
		{
			Node curr = queuedNodes.poll();

			if(goalTest(this, curr.getState()))
			{
				if(visualize)
					traceSolution(curr);
				return curr;
			}

			ArrayList<Node> children = expand(curr, this.getOperators());
			ArrayList<Node> notVisitedChildren = new ArrayList<Node>();
			for(Node child : children)
				if(!visited.contains(child))
				{
					visited.add(child);
					notVisitedChildren.add(child);
				}
			queuedNodes = QingFunction.enqueue(queuedNodes, notVisitedChildren);
		}

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

	public static ArrayList<GridPosition> getPadsPositions() {
		return padsPositions;
	}

	public static void setPadsPositions(ArrayList<GridPosition> padsPositions) {
		HelpR2_D2.padsPositions = padsPositions;
	}

	public static void main(String[] args) {

		//		HelpR2_D2 help = new HelpR2_D2();
		//		GridPosition[][] grid = new GridPosition[4][4];
		//		GridPosition initial = new GridPosition(0, 2, Cell.EMPTY);
		//		GridPosition.setNumCols(4);
		//		GridPosition.setNumRows(4);
		//		help.numPressedPads = 0;
		//		help.numberOfPads = 2;
		//		help.setGrid(grid);
		//		grid[0][0] = new GridPosition(0, 0, Cell.UNPRESSED_PAD);
		//		grid[0][1] = new GridPosition(0, 1, Cell.ROCK);
		//		grid[0][2] = initial;
		//		grid[0][3] = new GridPosition(0, 3, Cell.EMPTY);
		//		
		//		grid[1][0] = new GridPosition(1, 0, Cell.EMPTY);
		//		grid[1][1] = new GridPosition(1, 1, Cell.EMPTY);
		//		grid[1][2] = new GridPosition(1, 2, Cell.EMPTY);
		//		grid[1][3] = new GridPosition(1, 3, Cell.PORTAL);
		//		
		//		grid[2][0] = new GridPosition(2, 0, Cell.EMPTY);
		//		grid[2][1] = new GridPosition(2, 1, Cell.ROCK);
		//		grid[2][2] = new GridPosition(2, 2, Cell.UNPRESSED_PAD);
		//		grid[2][3] = new GridPosition(2, 3, Cell.EMPTY);
		//		
		//		grid[3][0] = new GridPosition(3, 0, Cell.EMPTY);
		//		grid[3][1] = new GridPosition(3, 1, Cell.EMPTY);
		//		grid[3][2] = new GridPosition(3, 2, Cell.EMPTY);
		//		grid[3][3] = new GridPosition(3, 3, Cell.EMPTY);
		//		ArrayList<GridPosition> rocks = new ArrayList<GridPosition>();
		//		rocks.add(new GridPosition(0, 1, Cell.ROCK)); 
		//		rocks.add(new GridPosition(2, 1, Cell.ROCK));
		//		HelpR2_D2.initialState = new HelpR2_D2_State(initial, rocks, 0, 0);
		//		HelpR2_D2.padsPositions = new ArrayList<GridPosition>();
		//		padsPositions.add(new GridPosition(0, 0, Cell.UNPRESSED_PAD));
		//		padsPositions.add(new GridPosition(2, 2, Cell.UNPRESSED_PAD));
		////		help.printGrid();


		//		for(int i = 0; i < 50 ; i++)
		//		{

		HelpR2_D2 help = new HelpR2_D2();

		//		GridPosition[][] grid = new GridPosition[4][2];
		//		GridPosition initial = new GridPosition(2, 0, Cell.EMPTY);
		//		GridPosition.setNumCols(2);
		//		GridPosition.setNumRows(4);
		//		help.numPressedPads = 0;
		//		help.numberOfPads = 1;
		//		help.setGrid(grid);
		//		grid[0][0] = new GridPosition(0, 0, Cell.UNPRESSED_PAD);
		//		grid[0][1] = new GridPosition(0, 1, Cell.PORTAL);
		//		grid[1][0] = new GridPosition(1, 0, Cell.ROCK);
		//		grid[1][1] = new GridPosition(1, 1, Cell.EMPTY);
		//
		//		grid[2][0] = initial;
		//		grid[2][1] = new GridPosition(2, 1, Cell.EMPTY);
		//		grid[3][0] = new GridPosition(3, 0, Cell.EMPTY);
		//		grid[3][1] = new GridPosition(3, 1, Cell.EMPTY);
		//		
		//		ArrayList<GridPosition> rocks = new ArrayList<GridPosition>();
		//		rocks.add(new GridPosition(1, 0, Cell.ROCK)); 
		//		HelpR2_D2.padsPositions = new ArrayList<GridPosition>();
		//		padsPositions.add(new GridPosition(0, 0, Cell.UNPRESSED_PAD));
		//		HelpR2_D2.initialState = new HelpR2_D2_State(initial, rocks, 0, 0);
		//		
		//		
		//		GridPosition[][] grid = new GridPosition[1][4];
		//		GridPosition initial = new GridPosition(0, 0, Cell.EMPTY);
		//		GridPosition.setNumCols(4);
		//		GridPosition.setNumRows(1);
		//		help.numPressedPads = 0;
		//		help.numberOfPads = 1;
		//		help.setGrid(grid);
		//		grid[0][0] = new GridPosition(0, 0, Cell.EMPTY);
		//		grid[0][1] = new GridPosition(0, 1, Cell.ROCK);
		//		grid[0][2] = new GridPosition(0, 2, Cell.PORTAL);
		//		grid[0][3] = new GridPosition(0, 3, Cell.UNPRESSED_PAD);
		//
		//		ArrayList<GridPosition> rocks = new ArrayList<GridPosition>();
		//		rocks.add(new GridPosition(0, 1, Cell.ROCK)); 
		//		HelpR2_D2.padsPositions = new ArrayList<GridPosition>();
		//		padsPositions.add(new GridPosition(0, 3, Cell.UNPRESSED_PAD));
		//		HelpR2_D2.initialState = new HelpR2_D2_State(initial, rocks, 0, 0);


		//		GridPosition[][] grid = new GridPosition[2][3];
		//		GridPosition initial = new GridPosition(1, 0, Cell.EMPTY);
		//		GridPosition.setNumRows(2);
		//		GridPosition.setNumCols(3);
		//		help.numPressedPads = 0;
		//		help.numberOfPads = 1;
		//		grid[0][0] = new GridPosition(0, 0, Cell.UNPRESSED_PAD);
		//		grid[0][1] = new GridPosition(0, 1, Cell.ROCK);
		//		grid[0][2] = new GridPosition(0, 2, Cell.EMPTY);
		//		grid[1][0] = new GridPosition(1, 0, Cell.EMPTY);
		//		grid[1][1] = new GridPosition(1, 1, Cell.PORTAL);
		//		grid[1][2] = new GridPosition(1, 2, Cell.EMPTY);
		//		help.setGrid(grid);
		//
		//		ArrayList<GridPosition> rocks = new ArrayList<GridPosition>();
		//		rocks.add(new GridPosition(0, 1, Cell.ROCK)); 
		//		HelpR2_D2.padsPositions = new ArrayList<GridPosition>();
		//		padsPositions.add(new GridPosition(0, 0, Cell.UNPRESSED_PAD));
		//		HelpR2_D2.initialState = new HelpR2_D2_State(initial, rocks, 0, 0);
		//		HelpR2_D2.setRowPortal(1);
		//		HelpR2_D2.setColPortal(1);


		System.out.println(HelpR2_D2.initialState);

		System.out.println("\nBreadth-First Search : ");
		BFS bfs = new BFS(help, false);
		bfs.search();


		System.out.println("\nUniform-Cost Search : ");
		UniformCost ufc = new UniformCost(help, false);
		ufc.search();


		System.out.println("\nIterative Deepening Search : ");
		IterativeDeepening iterDeep = new IterativeDeepening(help, false);
		iterDeep.search();


		System.out.println("\nDepth-First Search : ");
		DFS dfs = new DFS(help, false);
		dfs.search();

		RockToPadDistances rpd = new RockToPadDistances();
		NearestFreeRock nfr = new NearestFreeRock();


		System.out.println("\nGreedy Search 1: ");
		GreedySearch gs1 = new GreedySearch(help, false, rpd);
		gs1.search();


		System.out.println("\nGreedy Search 2: ");
		GreedySearch gs2 = new GreedySearch(help, false, nfr);
		gs2.search();


		System.out.println("\nA* Search 1: ");
		AStarSearch astar1 = new AStarSearch(help, false, rpd);
		astar1.search();


		System.out.println("\nA* Search 2: ");
		AStarSearch astar2 = new AStarSearch(help, false, nfr);
		astar2.search();
	}

	//	}

}
