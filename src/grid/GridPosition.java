package grid;

public  class GridPosition implements Comparable<GridPosition> , Cloneable{
	int row;
	int column;
	static int numRows;
	static int numCols;
	Cell cell;
	int cellNum;

	public GridPosition(int row, int column, Cell cell)
	{
		this.row = row;
		this.column = column;
		this.cell = cell;
		this.cellNum = row*numRows + column;
	}
	
	
	public int getCellNum() {
		return cellNum;
	}


	public void setCellNum(int cellNum) {
		this.cellNum = cellNum;
	}


	public String toString(){
		return "Row: " + this.row + '\n' + "Column: " + this.column  + '\n' + "Cell: " + cell;
	}

	@Override
	public int compareTo(GridPosition other) {
		return row - other.row  == 0 ? column - other.column : row - other.row;
	}

	@Override
	public boolean equals(Object other)
	{
		GridPosition gridPosition = (GridPosition)other;
		return row == gridPosition.row && column == gridPosition.column;
	}

	public static boolean validPosition(int gRow, int gCol)
	{
		boolean ret = gRow >= 0 && gRow < numRows;
		ret &= gCol >= 0 && gCol < numCols;
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
