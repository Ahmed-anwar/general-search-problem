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
	int numberOfExpandedNodes;
	
	public DFS(SearchProblem p, boolean v)
	{
		problem = p;
		visualize = v;
	}
	
	@Override
	public Node search() {
		numberOfExpandedNodes = 0;
		Node end = problem.search(problem, this, visualize);
		if(end == null)
		{
			System.out.println("No solution found!");
			System.out.println("Number of expanded nodes : " + numberOfExpandedNodes);
		}
		else
		{
			System.out.println("Solution found!");
			System.out.println("Number of expanded nodes : " + numberOfExpandedNodes);
			System.out.println("Cost of solution : " + end.getCost());
		}
		return end;
	}
	
	@Override
	public Queue<Node> enqueue(Queue<Node> nodes, ArrayList<Node> children) {
		numberOfExpandedNodes++;
		Deque<Node> enqueuedNodes = new LinkedList<Node>();
		enqueuedNodes.addAll(nodes);
		for (int i = 0; i < children.size(); i++) 
			enqueuedNodes.addFirst(children.get(i));
		
		return enqueuedNodes;
	}


}
