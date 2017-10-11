package grid;

public  class GridPosition implements Comparable<GridPosition> , Cloneable{
	int row;
	int column;
	static int numRows;
	static int numCols;
	Cell cell;

	public GridPosition(int row, int column, Cell cell)
	{
		this.row = row;
		this.column = column;
		this.cell = cell;
	}

	@Override
	public int compareTo(GridPosition other) {
		return row - other.row  == 0 ? column - other.column : row - other.row;
	}

	public boolean equals(GridPosition other)
	{
		return row == other.row && column == other.column;
	}

	public static boolean validPosition(int row, int col)
	{
		boolean ret = row >= 0 && row < numRows;
		ret &= col >= 0 && col < numCols;
		return ret; 
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public static int getNumRows() {
		return numRows;
	}

	public static void setNumRows(int nRows) {
		numRows = nRows;
	}

	public static int getNumCols() {
		return numCols;
	}

	public static void setNumCols(int nCols) {
		numCols = nCols;
	}

	public Cell getCell() {
		return cell;
	}

	public void setCell(Cell cell) {
		this.cell = cell;
	}
}
