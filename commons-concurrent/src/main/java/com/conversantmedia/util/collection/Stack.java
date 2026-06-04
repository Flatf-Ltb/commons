package com.conversantmedia.util.collection;

public interface Stack<N> {

    /**
     * Linear search the stack for contains - not an efficient operation
     *
     * @param n - Object to test for containment
     * @return boolean - true if n is contained somewhere in the stack
     */
    boolean contains(N n);

    /**
     * Add the element to the stack top, optionally failing if there is no capacity
     * (overflow)
     *
     * @param n - element to push
     * @return boolean - true if push succeeded
     */
    boolean push(N n);

    /**
     * show the current stack top
     *
     * @return N - the element at the top of the stack or null
     */
    N peek();

    /**
     * pop and return the element from the top of the stack
     *
     * @return N - the element, or null if the stack is empty
     */
    N pop();

    /**
     * @return int - the size of the stack in number of elements
     */
    int size();

    /**
     * @return int - the number of empty slots available in the stack
     */
    int remainingCapacity();

    /**
     * @return boolean - true if stack is empty
     */
    boolean isEmpty();

    /**
     * clear the stack
     */
    void clear();

}
