package io.flatf.common.sys;

public final class CurrentRuntime {

    private CurrentRuntime() {
    }

    /**
     * Returns the number of processors available to the Java virtual machine.
     *
     * <p>
     * This value may change during a particular invocation of the virtual machine.
     * Applications that are sensitive to the number of available processors should
     * therefore occasionally poll this property and adjust their resource usage
     * appropriately.
     * </p>
     *
     * @return the maximum number of processors available to the virtual machine;
     * never smaller than one
     */
    public static int availableProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * Returns the amount of free memory in the Java Virtual Machine. Calling the
     * <code>GC</code> method may result in increasing the value returned by
     * <code>freeMemory.</code>
     *
     * @return an approximation to the total amount of memory currently available
     * for future allocated objects, measured in bytes.
     */
    public static long freeMemory() {
        return Runtime.getRuntime().freeMemory();
    }

    /**
     * Returns the total amount of memory in the Java virtual machine. The value
     * returned by this method may vary over time, depending on the host
     * environment.
     * <p>
     * Note that the amount of memory required to hold an object of any given type
     * may be implementation-dependent.
     *
     * @return the total amount of memory currently available for current and future
     * objects, measured in bytes.
     */
    public static long totalMemory() {
        return Runtime.getRuntime().totalMemory();
    }

    /**
     * Returns the maximum amount of memory that the Java virtual machine will
     * attempt to use. If there is no inherent limit then the value
     * {@link java.lang.Long#MAX_VALUE} will be returned.
     *
     * @return the maximum amount of memory that the virtual machine will attempt to
     * use, measured in bytes
     */
    public static long maxMemory() {
        return Runtime.getRuntime().maxMemory();
    }

}
