import java.util.ArrayList;
import java.util.function.IntBinaryOperator;

public abstract class SearchProblem {
	ArrayList<Operator> operators;
	State initialState;
	ArrayList<State> stateSpace;
	
	public abstract boolean goalTest(SearchProblem problem, State state);
	
	public abstract long pathCost(SearchProblem problem, Node node);
	
	public abstract ArrayList<Node> expand(Node node, ArrayList<Operator> ops);
	
	public abstract ArrayList<Operator> search(SearchProblem problem, IntBinaryOperator QingFunction);
}
