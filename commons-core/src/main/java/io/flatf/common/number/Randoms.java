package io.flatf.common.number;

import javax.annotation.concurrent.NotThreadSafe;
import java.security.SecureRandom;

import static java.lang.Math.abs;

@NotThreadSafe
public final class Randoms {

	private static final SecureRandom Random = new SecureRandom();

	/**
	 * 
	 * @return int
	 */
	public static int nextInt() {
		return Random.nextInt();
	}

	/**
	 * 
	 * @return unsigned int
	 */

	public static int nextUnsignedInt() {
		int next;
		do {
			next = Random.nextInt();
		} while (next == Integer.MIN_VALUE);
		return abs(next);
	}

	/**
	 * 
	 * @return long
	 */
	public static long nextLong() {
		return Random.nextLong();
	}

	/**
	 * 
	 * @return unsigned long
	 */
	public static long nextUnsignedLong() {
		long next;
		do {
			next = Random.nextLong();
		} while (next == Long.MIN_VALUE);
		return abs(next);
	}

	/**
	 * 
	 * @return double
	 */
	public static double nextDouble() {
		return Random.nextDouble();
	}

	/**
	 * 
	 * @return unsigned double
	 */
	public static double nextUnsignedDouble() {
		return abs(Random.nextDouble());
	}

}
