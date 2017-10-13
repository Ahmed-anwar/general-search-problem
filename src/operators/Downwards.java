package operators;

import search_problem.State;

public class Downwards extends Operator{
	
	@Override
	public State apply(State currState) {
		return super.apply(currState, 1, 0);
	}
	
}
