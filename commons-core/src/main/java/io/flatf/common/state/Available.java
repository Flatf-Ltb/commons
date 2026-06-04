package io.flatf.common.state;

public interface Available {

    boolean isEnabled();

    default boolean isDisabled() {
        return !isEnabled();
    }

    boolean disable();

    boolean enable();

}