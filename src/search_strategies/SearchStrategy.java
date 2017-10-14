package search_strategies;
import java.util.ArrayList;
import java.util.Queue;

import search_problem.Node;

public interface SearchStrategy {
	
	public Queue<Node> enqueue(Queue<Node> nodesQueue, ArrayList<Node> children);
}
