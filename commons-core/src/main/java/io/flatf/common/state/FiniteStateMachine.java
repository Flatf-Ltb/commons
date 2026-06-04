package io.flatf.common.state;

public interface FiniteStateMachine {

	State getState();

	State handleSignal(Signal signal);

}
