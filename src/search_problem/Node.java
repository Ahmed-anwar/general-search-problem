package search_problem;
import operators.Operator;

public class Node {
	State state;
	Node parent;
	Operator getAncestor;
	int depth;
	int cost;
	
	public Node(){
		
	}
}
