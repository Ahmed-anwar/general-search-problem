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
	int numberOfExpandedNodes;
	boolean leafCheck;

	public IterativeDeepening(SearchProblem problem, boolean v)
	{
		this.problem = problem;
		visualize = v;
		leafCheck = true;
	}

	public Node search() {
		Node resultNode = null;
		numberOfExpandedNodes = 0;
		for(int i = 0 ; i <= 7 ; i++){
			leafCheck = false;
			itrDepth = i + 1;
			resultNode = problem.search(problem, this, visualize);

			if(resultNode != null)
			{
				System.out.println("Solution found!");
				System.out.println("Number of expanded nodes : " + numberOfExpandedNodes);
				System.out.println("Cost of solution : " + resultNode.getCost());
				break;
			}
			
		}
		if(resultNode == null)
		{
			System.out.println("No solution found!");
			System.out.println("Number of expanded nodes : " + numberOfExpandedNodes);
		}

		return resultNode;

	}

	@Override
	public Queue<Node> enqueue(Queue<Node> nodesQueue, ArrayList<Node> children) {
		numberOfExpandedNodes++;
		Deque<Node> enqueuedNodes = new LinkedList<Node>();
		enqueuedNodes.addAll(nodesQueue);
		Queue<Node> returnQueue = new LinkedList<Node>();
		for (int i = 0; i < children.size(); i++){
			if(children.get(i).getDepth() <= itrDepth)
				enqueuedNodes.addFirst(children.get(i));
			else
				leafCheck = true;
		} 

		returnQueue.addAll(enqueuedNodes);

		return returnQueue;
	}

}
