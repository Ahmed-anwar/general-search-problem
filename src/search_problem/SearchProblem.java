package search_problem;
import java.util.ArrayList;

import operators.Operator;
import search_strategies.SearchStrategy;

public abstract class SearchProblem {
	ArrayList<Operator> operators;
	protected static State initialState;
	ArrayList<State> stateSpace;
	
	public ArrayList<Operator> getOperators() {
		return operators;
	}

	public void setOperators(ArrayList<Operator> operators) {
		this.operators = operators;
	}

	public abstract boolean goalTest(SearchProblem problem, State state);
	
	public abstract int pathCost(SearchProblem problem, Node node, int childCost);
	
	public abstract ArrayList<Node> expand(Node node, ArrayList<Operator> ops);
	
	public abstract ArrayList<Operator> search(SearchProblem problem, SearchStrategy QingFunction, boolean visualize);
}
