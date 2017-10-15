package search_problem;

public class Node implements Comparable<Node>{
	State state;
	Node parent;
	Operator getAncestor;
	int depth;
	int cost; // from the root node
	
	public Node(State s, Node p, Operator op, int d, int c){
		state = s;
		parent = p;
		getAncestor = op;
		depth = d;
		cost = c;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public Operator getGetAncestor() {
		return getAncestor;
	}

	public void setGetAncestor(Operator getAncestor) {
		this.getAncestor = getAncestor;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}
	
	@Override
	public boolean equals(Object other)
	{
		Node otherNode = (Node) other;
		return state.equals(otherNode.state);
	}

	@Override
	public int compareTo(Node other) {
		return state.compareTo(other.state);
	}
	
	@Override
	public String toString()
	{
		
		return state.toString() + "Depth : " + depth;
	}
	
	

}