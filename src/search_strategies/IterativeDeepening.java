package search_strategies;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

import search_problem.Node;
import search_problem.SearchProblem;

public class IterativeDeepening extends SearchStrategy{
	SearchProblem problem;
	boolean visualize;
	int itrDepth;

	public IterativeDeepening(SearchProblem problem, boolean v)
	{
		this.problem = problem;
		visualize = v;
	}

	public Node search() {
		Node resultNode = null;
		for(int i = 0 ; i < 200 ; i++){
			itrDepth = i + 1;
			resultNode = problem.search(problem, this, visualize);

			if(resultNode != null)
				break;
		}

		return resultNode;

	}

	@Override
	public Queue<Node> enqueue(Queue<Node> nodesQueue, ArrayList<Node> children) {
		Deque<Node> enqueuedNodes = new LinkedList<Node>();
		enqueuedNodes.addAll(nodesQueue);
		Queue<Node> returnQueue = new LinkedList<Node>();
		for (int i = 0; i < children.size(); i++){
			if(children.get(i).getDepth() <= itrDepth)
				enqueuedNodes.addFirst(children.get(i));
		} 

		returnQueue.addAll(enqueuedNodes);

		return returnQueue;
	}

}
