import java.util.ArrayList;
import java.util.Queue;

public interface SearchStrategy {
	
	public Queue<Node> enqueue(Queue<Node> nodes, ArrayList<Node> children);
}
