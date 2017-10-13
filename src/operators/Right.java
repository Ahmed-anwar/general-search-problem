package operators;

import search_problem.State;

public class Right extends Operator{
	@Override
	public State apply(State currState) {
		return super.apply(currState, 0, 1);
	}
	

}
