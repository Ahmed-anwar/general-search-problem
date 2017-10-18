package search_strategies;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import heuristics.HeuristicFunction;
import search_problem.Node;
import search_problem.SearchProblem;

public class AStarSearch extends SearchStrategy{

	SearchProblem problem;
	boolean visualize;
	HeuristicFunction h;
	
	public AStarSearch(SearchProblem p, boolean v, HeuristicFunction hf)
	{
		problem = p;
		visualize = v;
		h = hf;
	}
	
	@Override
	public Node search() {
		return problem.search(problem, this, visualize);
	}
	
	@Override
	public Queue<Node> enqueue(Queue<Node> nodesQueue, ArrayList<Node> children) {
		int size = nodesQueue.size() + children.size() + 1;
		PriorityQueue<Node> enqueuedNodes = new PriorityQueue<Node>(size, new Comparator<Node>() {

			@Override
			public int compare(Node node1, Node node2) {
				return (h.heuristicCost(node1) + node1.getCost()) - (h.heuristicCost(node2) + node2.getCost());
			}
			
		});
		enqueuedNodes.addAll(nodesQueue);
		Queue<Node> returnQueue = new LinkedList<Node>();
		for (int i = 0; i < children.size(); i++) 
			enqueuedNodes.add(children.get(i));
		returnQueue.addAll(enqueuedNodes);
		return returnQueue;

	}


}
