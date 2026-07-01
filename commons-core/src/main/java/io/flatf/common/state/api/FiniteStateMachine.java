package io.flatf.common.state.api;

public interface FiniteStateMachine {

	State getState();

	State handleSignal(Signal signal);

}
