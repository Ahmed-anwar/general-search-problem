package search_strategies;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

import search_problem.Node;
import search_problem.SearchProblem;

public class DFS extends SearchStrategy{
	SearchProblem problem;
	boolean visualize;
	
	public DFS(SearchProblem p, boolean v)
	{
		problem = p;
		visualize = v;
	}
	
	@Override
	public Queue<Node> enqueue(Queue<Node> nodes, ArrayList<Node> children) {
		Deque<Node> enqueuedNodes = new LinkedList<Node>();
		enqueuedNodes.addAll(nodes);
		for (int i = 0; i < children.size(); i++) 
			enqueuedNodes.addFirst(children.get(i));
		
		return enqueuedNodes;
	}

	@Override
	public Node search() {
		return problem.search(problem, this, visualize);
	}

}
