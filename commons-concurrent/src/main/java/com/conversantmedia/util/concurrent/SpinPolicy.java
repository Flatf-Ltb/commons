package com.conversantmedia.util.concurrent;

/**
 * Three SpinPolicy values are supported.
 * <p>
 * WAITING - An experimental queuing strategy that supports applications that
 * use a small number of threads. In general, "WAITING" performs better than
 * BLOCKING - Java style synchronization, fair and well-behaved on the
 * processor. This is very slow and undercuts some advantage of using the
 * Disruptor in the first place. SPINNING - For extreme performance scenarios
 * where processor utilization is not a concern, this will slightly lower
 * latency over WAITING but at a cost of spin locking the CPU.
 * <p>
 */
public enum SpinPolicy {

    WAITING,
    BLOCKING,
    SPINNING

}
