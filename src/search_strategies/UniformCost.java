package search_strategies;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import search_problem.Node;
import search_problem.SearchProblem;

public class UniformCost extends SearchStrategy{
	SearchProblem problem;
	boolean visualize;
	int numberOfExpandedNodes;
	
	public UniformCost(SearchProblem problem, boolean v)
	{
		this.problem = problem;
		visualize = v;
	}
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
	public Queue<Node> enqueue(Queue<Node> nodesQueue, ArrayList<Node> children) {
		numberOfExpandedNodes++;
		PriorityQueue<Node> enqueuedNodes = new PriorityQueue<Node>();
		enqueuedNodes.addAll(nodesQueue);
		Queue<Node> returnQueue = new LinkedList<Node>();
		for (int i = 0; i < children.size(); i++) 
			enqueuedNodes.add(children.get(i));
		returnQueue.addAll(enqueuedNodes);
		return returnQueue;
	}

}
