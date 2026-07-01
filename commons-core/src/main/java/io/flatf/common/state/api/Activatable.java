package io.flatf.common.state.api;

public interface Activatable {

    boolean isActive();

    default boolean isInactive() {
        return !isActive();
    }

    boolean activate();

    boolean deactivate();

}
