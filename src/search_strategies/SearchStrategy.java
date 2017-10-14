package search_strategies;
import java.util.ArrayList;
import java.util.Queue;

import search_problem.Node;

public abstract class SearchStrategy {
	
	public abstract Queue<Node> enqueue(Queue<Node> nodesQueue, ArrayList<Node> children);
}
