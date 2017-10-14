package search_strategies;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import search_problem.Node;
import search_problem.Operator;
import search_problem.SearchProblem;

public class BFS extends SearchStrategy{
	
	SearchProblem problem;
	boolean visualize;
	
	public BFS(SearchProblem problem, boolean v)
	{
		this.problem = problem;
		visualize = v;
	}

	public ArrayList<Operator> search() {
		return problem.search(problem, this, visualize);
	}
	
	@Override
	public Queue<Node> enqueue(Queue<Node> nodesQueue, ArrayList<Node> children) {
		Queue<Node> enqueuedNodes = new LinkedList<Node>();
		enqueuedNodes.addAll(nodesQueue);
		for (int i = 0; i < children.size(); i++) 
			enqueuedNodes.add(children.get(i));
		
		return enqueuedNodes;
	}
	
}
