package search_problem;
import java.util.ArrayList;

import r2_d2.HelpR2_D2_State;
import search_strategies.SearchStrategy;

public abstract class SearchProblem {
	ArrayList<Operator> operators;
	protected static HelpR2_D2_State initialState;
	ArrayList<HelpR2_D2_State> stateSpace;
	
	public ArrayList<Operator> getOperators() {
		return operators;
	}

	public void setOperators(ArrayList<Operator> operators) {
		this.operators = operators;
	}

	public abstract boolean goalTest(SearchProblem problem, HelpR2_D2_State state);
	
	public abstract int pathCost(SearchProblem problem, Node node, int childCost);
	
	public abstract ArrayList<Node> expand(Node node, ArrayList<Operator> ops);
	
	public abstract ArrayList<Operator> search(SearchProblem problem, SearchStrategy QingFunction, boolean visualize);
}
