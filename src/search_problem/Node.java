package search_problem;

import r2_d2.HelpR2_D2_State;

public class Node implements Comparable<Node>{
	State state;
	Node parent;
	Operator getAncestor;
	int depth;
	int cost = 1; // from the root node
	
	public Node(State s, Node p, Operator op, int d){
		state = s;
		parent = p;
		getAncestor = op;
		depth = d;
		if(p != null)
			cost = p.cost;
		cost += ((HelpR2_D2_State) s).getTransitionCost();
		System.out.println("Node Cost : " + cost);
		System.out.println("Transition Cost : " + ((HelpR2_D2_State) s).getTransitionCost());
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
		return cost - other.cost;
	}
	
	@Override
	public String toString()
	{
		
		return state.toString() + "Depth : " + depth;
	}
	
	

}