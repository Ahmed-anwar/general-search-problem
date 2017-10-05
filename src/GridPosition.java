
public  class GridPosition implements Comparable<GridPosition>{
	int row;
	int column;
	
	public GridPosition(int x, int y)
	{
		row = x;
		column = y;
	}
	
	@Override
	public int compareTo(GridPosition other) {
		return row - other.row  == 0 ? column - other.column : row - other.row;
	}

	public boolean equals(GridPosition other)
	{
		return row == other.row && column == other.column;
	}
}
