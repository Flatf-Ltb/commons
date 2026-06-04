package io.flatf.common.lang;

import io.flatf.common.lang.exception.RuntimeIOException;

import java.io.IOException;

public final class ThrowsUtil {

    /**
     * @throws RuntimeException exception
     */
    public static void throwsRuntimeException() throws RuntimeException {
        throw new RuntimeException();
    }

    /**
     * @param msg String
     * @throws RuntimeException exception
     */
    public static void throwsRuntimeException(String msg) throws RuntimeException {
        throw new RuntimeException(msg);
    }

    /**
     * @param t Throwable
     * @throws RuntimeException exception
     */
    public static void throwsRuntimeException(Throwable t) throws RuntimeException {
        throw new RuntimeException(t);
    }

    /**
     * @param msg String
     * @param t   Throwable
     * @throws RuntimeException exception
     */
    public static void throwsRuntimeException(String msg, Throwable t) throws RuntimeException {
        throw new RuntimeException(msg, t);
    }

    /**
     * @throws NullPointerException exception
     */
    public static void throwsNPE() throws NullPointerException {
        throw new NullPointerException();
    }

    /**
     * @param objName String
     * @throws NullPointerException exception
     */
    public static void throwsNPE(String objName) throws NullPointerException {
        throw new NullPointerException("object name -> [" + objName + "]");
    }

    /**
     * @throws IllegalArgumentException exception
     */
    public static void throwsIllegalArgument() throws IllegalArgumentException {
        throw new IllegalArgumentException();
    }

    /**
     * @param argumentName String
     * @throws IllegalArgumentException exception
     */
    public static void throwsIllegalArgument(String argumentName) throws IllegalArgumentException {
        throw new IllegalArgumentException("illegal argument -> " + argumentName);
    }

    /**
     * @param t Throwable
     * @throws IllegalArgumentException exception
     */
    public static void throwsIllegalArgument(Throwable t) throws IllegalArgumentException {
        throw new IllegalArgumentException(t);
    }

    /**
     * @param msg String
     * @param t   Throwable
     * @throws IllegalArgumentException exception
     */
    public static void throwsIllegalArgument(String msg, Throwable t) throws IllegalArgumentException {
        throw new IllegalArgumentException("illegal argument -> " + msg, t);
    }

    /**
     * @throws IllegalStateException exception
     */
    public static void throwsIllegalState() throws IllegalStateException {
        throw new IllegalStateException();
    }

    /**
     * @param state String
     * @throws IllegalStateException exception
     */
    public static void throwsIllegalState(String state) throws IllegalStateException {
        throw new IllegalStateException("state -> " + state);
    }

    /**
     * @param t Throwable
     * @throws IllegalStateException exception
     */
    public static void throwsIllegalState(Throwable t) throws IllegalStateException {
        throw new IllegalStateException(t);
    }

    /**
     * @param state String
     * @param t     Throwable
     * @throws IllegalStateException exception
     */
    public static void throwsIllegalState(String state, Throwable t) throws IllegalStateException {
        throw new IllegalStateException("state -> " + state, t);
    }

    /**
     * @param msg String
     * @throws UnsupportedOperationException uoe
     */
    public static void throwsUnsupportedOperation(String msg) throws UnsupportedOperationException {
        throw new UnsupportedOperationException(msg);
    }

    /**
     * @param msg String
     * @throws RuntimeIOException e
     */
    public static void throwsRuntimeIOE(String msg) throws RuntimeIOException {
        throw new RuntimeIOException(msg);
    }

    /**
     * @param ioe IOException
     * @throws RuntimeIOException e
     */
    public static void throwsRuntimeIOE(IOException ioe) throws RuntimeIOException {
        throw new RuntimeIOException(ioe);
    }

    /**
     * @param msg String
     * @param ioe IOException
     * @throws RuntimeIOException e
     */
    public static void throwsRuntimeIOE(String msg, IOException ioe) throws RuntimeIOException {
        throw new RuntimeIOException(msg, ioe);
    }

}
