package search_problem;

public abstract class State implements Comparable<State>{
	
	@Override
	public abstract boolean equals(Object other);
	
	@Override
	public abstract String toString();
	
	@Override
	public abstract int compareTo(State other);
}
