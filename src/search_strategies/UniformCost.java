package search_strategies;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import search_problem.Node;
import search_problem.Operator;
import search_problem.SearchProblem;

public class UniformCost extends SearchStrategy{
	SearchProblem problem;
	boolean visualize;
	
	public UniformCost(SearchProblem problem, boolean v)
	{
		this.problem = problem;
		visualize = v;
	}
	public Node search() {
		return problem.search(problem, this, visualize);
	}
	
	@Override
	public Queue<Node> enqueue(Queue<Node> nodesQueue, ArrayList<Node> children) {
		PriorityQueue<Node> enqueuedNodes = new PriorityQueue<Node>();
		enqueuedNodes.addAll(nodesQueue);
		Queue<Node> returnQueue = new LinkedList<Node>();
		for (int i = 0; i < children.size(); i++) 
			enqueuedNodes.add(children.get(i));
		returnQueue.addAll(enqueuedNodes);
		return returnQueue;
	}

}
