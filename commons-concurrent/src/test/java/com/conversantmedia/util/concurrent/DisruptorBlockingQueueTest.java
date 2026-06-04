package com.conversantmedia.util.concurrent;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author John Cairns <jcairns@dotomi.com> Date: 4//25/12 Time: 3:27 PM
 */
public class DisruptorBlockingQueueTest {

	final static boolean ALLOW_LONG_RUN = false;

	private ThreadPoolExecutor executor;

	@Before
	public void setup() {
		executor = new ThreadPoolExecutor(5, 5, 1, TimeUnit.MINUTES, new DisruptorBlockingQueue<>(1024));
	}

	@After
	public void teardown() {
		executor.shutdown();
	}

	@Test
	public void testAsSynchronousQueue() {
		final int cap = 1;
		BlockingQueue<Integer> dbq = new DisruptorBlockingQueue<>(cap);
		while (dbq.offer(0))
			;

		Assert.assertFalse(dbq.offer(10));

		Assert.assertEquals(1, dbq.size());

		Assert.assertEquals(Integer.valueOf(0), dbq.poll());
	}

	@Test
	public void testDisruptorBlockingQueueTestC1() {
		final int cap = 10;
		BlockingQueue<Integer> dbq = new DisruptorBlockingQueue<>(cap);
		while (dbq.offer(0))
			;
		Assert.assertEquals(16, dbq.size());
	}

	@Test
	public void testDisruptorBlockingQueueTestC2() {

		final int cap = 50;

		Set<Integer> x = new HashSet<>(cap);
		for (int i = 0; i < 2 * cap; i++) {
			x.add(i);
		}

		BlockingQueue<Integer> dbq = new DisruptorBlockingQueue<>(cap, x);
		// next power of two
		Assert.assertEquals(64, dbq.size());
	}

	@Test
	public void testOffer() {

		final int cap = 16;
		BlockingQueue<Integer> dbq = new DisruptorBlockingQueue<>(cap);
		for (int i = 0; i < cap; i++) {
			dbq.offer(i);
		}

		Assert.assertFalse(dbq.offer(cap));

		for (int i = 0; i < cap; i++) {
			Assert.assertEquals(Integer.valueOf(i), dbq.poll());
		}

	}

	@Test
	public void offerTooManyTest() {
		final DisruptorBlockingQueue<Integer> dbq = new DisruptorBlockingQueue<>(8);

		for (int i = 0; i < 16; i++) {
			dbq.offer(i);
		}

		Assert.assertEquals(dbq.capacity(), dbq.size());
	}

	@Test
	public void remove() {

		final int cap = 10;
		BlockingQueue<Integer> dbq = new DisruptorBlockingQueue<>(cap);
		for (int i = 0; i < cap; i++) {
			dbq.offer(i);
		}

		Integer i = dbq.peek();
		Integer x = dbq.remove();

		Assert.assertEquals(i, x);
		Assert.assertEquals(i, Integer.valueOf(0));
        Assert.assertNotNull(i);
        Assert.assertNotEquals(i, dbq.peek());
	}

	@Test
	public void testPoll() {
		final int cap = 10;
		BlockingQueue<Integer> dbq = new DisruptorBlockingQueue<>(cap);

		Assert.assertNull(dbq.poll());

		dbq.offer(1);
		dbq.offer(2);
		Assert.assertEquals(dbq.poll(), Integer.valueOf(1));
		Assert.assertEquals(dbq.poll(), Integer.valueOf(2));

		Assert.assertNull(dbq.poll());
	}

	@Test
	public void inOutIn() {
		final int cap = 8;
		BlockingQueue<Integer> dbq = new DisruptorBlockingQueue<>(cap);

		Assert.assertNull(dbq.poll());

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < cap; j++) {
				dbq.offer(j);
			}

			Assert.assertFalse(dbq.offer(1000));

			for (int j = 0; j < cap; j++) {
				Assert.assertEquals(Integer.valueOf(j), dbq.poll());
			}

			Assert.assertNull(dbq.poll());
		}
	}

	@Test
	public void testElement() {
		final int cap = 10;
		BlockingQueue<Integer> dbq = new DisruptorBlockingQueue<>(cap);

		try {
			dbq.element();
			Assert.fail();
		} catch (NoSuchElementException ex) {
			// expected
		}
	}

	@Test
	public void testPeek() {
		final int cap = 10;
		BlockingQueue<Integer> dbq = new DisruptorBlockingQueue<>(cap);

		try {

			Assert.assertNull(dbq.peek());

		} catch (NoSuchElementException nsex) {
			Assert.fail();
		}

		for (int i = 0; i < cap; i++) {
			dbq.offer(i);
			Assert.assertEquals(Integer.valueOf(0), dbq.peek());
		}

		for (int i = 0; i < cap; i++) {
			Assert.assertEquals(Integer.valueOf(i), dbq.peek());
			dbq.poll(); // count up values checking peeks
		}
	}

	@Test
	public void testPut() throws InterruptedException {

		final int cap = 10;
		final BlockingQueue<Integer> dbq = new DisruptorBlockingQueue<>(cap);

		for (int i = 0; i < cap; i++) {
			dbq.offer(i);
		}

		executor.execute(() -> {
			try {
				Thread.sleep(1000);
				// after a second remove one
				dbq.poll();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		// in main thread add one
		// this operation must wait for thread
		dbq.put(cap);

		boolean hasValCap = false;
		while (!dbq.isEmpty()) {
			if (dbq.poll().equals(cap))
				hasValCap = true;
		}
		Assert.assertTrue(hasValCap);

	}

	@Ignore // FIXME - this test flickers in parallel test runner
	public void testTimeOffer() throws InterruptedException {

		final int cap = 16;
		final BlockingQueue<Integer> dbq = new DisruptorBlockingQueue<>(cap);

		for (int i = 0; i < cap; i++) {
			dbq.offer(i);
		}

		executor.execute(() -> {
			try {
				Thread.sleep(1500);
				// after a second remove one
				dbq.poll();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		// expect to fail for only 50 ms
		Assert.assertFalse(dbq.offer(cap, 50, TimeUnit.MILLISECONDS));

		Assert.assertTrue(dbq.offer(cap, 16550, TimeUnit.MILLISECONDS));

		boolean hasValCap = false;
		while (!dbq.isEmpty()) {
			if (dbq.poll().equals(cap))
				hasValCap = true;
		}
		Assert.assertTrue(hasValCap);
	}

	@Ignore // timing test not suitable for build
	public void pollTimeIsAccurate() throws InterruptedException {
		final DisruptorBlockingQueue<Integer> dbq = new DisruptorBlockingQueue<>(256);

		final long startTime = System.nanoTime();

		for (int i = 0; i < 50; i++) {
			dbq.poll(100, TimeUnit.MICROSECONDS);
		}

		final long runTime = System.nanoTime() - startTime;

		final long expTime = 50 * 100 * 1000;
		Assert.assertTrue(runTime >= expTime / 2);

		Assert.assertTrue(runTime <= expTime * 2);
	}

	@Ignore // timing test are not suitable for build
	public void offerTimeIsAccurate() throws InterruptedException {
		final DisruptorBlockingQueue<Integer> dbq = new DisruptorBlockingQueue<>(256);

		for (int i = 0; i < 256; i++) {
			dbq.offer(1);
		}

		final long startTime = System.nanoTime();

		for (int i = 0; i < 50; i++) {
			dbq.offer(1, 100, TimeUnit.MICROSECONDS);
		}

		final long runTime = System.nanoTime() - startTime;

		final long expTime = 50 * 100 * 1000;
		Assert.assertTrue(runTime >= expTime / 2);
		Assert.assertTrue(runTime <= expTime * 2);

	}

	@Test
	public void testTake() throws InterruptedException {

		final int cap = 10;
		final BlockingQueue<Integer> dbq = new DisruptorBlockingQueue<>(cap);

		executor.execute(() -> {
			try {
				Thread.sleep(1000);
				// after a second remove one
				dbq.offer(cap);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		// wait for value to be added
		Assert.assertEquals(Integer.valueOf(cap), dbq.take());
	}

	@Test
	public void testTimePoll() throws InterruptedException {
		final int cap = 10;
		final BlockingQueue<Integer> dbq = new DisruptorBlockingQueue<>(cap);

		executor.execute(() -> {
			try {
				Thread.sleep(1000);
				// after a second remove one
				dbq.offer(cap);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		// wait for value to be added
		Assert.assertNull(dbq.poll(50, TimeUnit.MICROSECONDS));
		Assert.assertEquals(Integer.valueOf(cap), dbq.poll(50, TimeUnit.SECONDS));
	}

	@Test
	public void testRemainingCapacity() {
		final int cap = 128;
		final BlockingQueue<Integer> dbq = new DisruptorBlockingQueue<>(cap);

		for (int i = 0; i < cap; i++) {
			Assert.assertEquals(cap - i, dbq.remainingCapacity());
			dbq.offer(i);
		}

	}

	@Test
	public void testDrainToC() {
		final int cap = 100;
		final BlockingQueue<Integer> dbq = new DisruptorBlockingQueue<>(cap);

		for (int i = 0; i < cap; i++) {
			dbq.offer(i);
		}

		final List<Integer> c = new LinkedList<>();
		Assert.assertEquals(cap, dbq.drainTo(c));
		int i = 0;
		for (final Integer a : c) {
			Assert.assertEquals(a, Integer.valueOf(i++));
		}

	}

	@Test
	public void drainToToCMax() {
		final int cap = 100;
		final int max = 75;
		final BlockingQueue<Integer> dbq = new DisruptorBlockingQueue<>(cap);

		for (int i = 0; i < cap; i++) {
			dbq.offer(i);
		}

		final List<Integer> c = new LinkedList<>();
		Assert.assertEquals(max, dbq.drainTo(c, max));
		Assert.assertEquals(max, c.size());
		int i = 0;
		for (final Integer a : c) {
			Assert.assertEquals(a, Integer.valueOf(i++));
		}
	}

	@Test
	public void testSize() {
		final int cap = 100;
		final BlockingQueue<Integer> dbq = new DisruptorBlockingQueue<>(cap);

		Assert.assertEquals(0, dbq.size());

		for (int i = 0; i < cap; i++) {
			dbq.offer(i);
			Assert.assertEquals(i + 1, dbq.size());
		}

		Assert.assertEquals(cap, dbq.size());

		for (int i = 0; i < cap; i++) {
			Assert.assertEquals(dbq.size(), cap - i);
			dbq.poll();
		}

		Assert.assertEquals(0, dbq.size());

		for (int i = 0; i < cap; i++) {
			dbq.offer(i);
			Assert.assertEquals(i + 1, dbq.size());
		}

		Assert.assertEquals(cap, dbq.size());

		for (int i = 0; i < cap; i++) {
			Assert.assertEquals(dbq.size(), cap - i);
			dbq.poll();
		}

	}

	@Test
	public void testIsEmpty() {
		final int cap = 100;
		final BlockingQueue<Integer> dbq = new DisruptorBlockingQueue<>(cap);

		Assert.assertTrue(dbq.isEmpty());

		for (int i = 0; i < cap; i++) {
			dbq.offer(i);
			Assert.assertFalse(dbq.isEmpty());
		}

		for (int i = 0; i < cap; i++) {
			Assert.assertFalse(dbq.isEmpty());
			dbq.poll();
		}

		Assert.assertTrue(dbq.isEmpty());

		for (int i = 0; i < cap; i++) {
			dbq.offer(i);
			Assert.assertFalse(dbq.isEmpty());
		}

		for (int i = 0; i < cap; i++) {
			Assert.assertFalse(dbq.isEmpty());
			dbq.poll();
		}

		Assert.assertTrue(dbq.isEmpty());
	}

	@Test
	public void testContains() {

		final int cap = 100;
		final BlockingQueue<Integer> dbq = new DisruptorBlockingQueue<>(cap);

		for (int i = 0; i < cap; i++) {
			Assert.assertFalse(dbq.contains(i));
		}

		for (int i = 0; i < cap; i++) {
			dbq.offer(i);
		}

		for (int i = 0; i < cap; i++) {
			Assert.assertTrue(dbq.contains(i));
		}

		for (int i = cap; i < 2 * cap; i++) {
			Assert.assertFalse(dbq.contains(i));
		}
	}

	@Test
	public void testToArray() {

		final int cap = 100;
		final BlockingQueue<Integer> dbq = new DisruptorBlockingQueue<>(cap);

		for (int i = 0; i < cap; i++) {
			Assert.assertTrue(dbq.offer(i));
		}

		Object[] objArray = dbq.toArray();
		for (int i = 0; i < cap; i++) {
			Assert.assertEquals(objArray[i], i);
		}

	}

	@Test
	public void testAdd() {
		final int cap = 16;
		final BlockingQueue<Integer> dbq = new DisruptorBlockingQueue<>(cap);

		for (int i = 0; i < cap; i++) {
			dbq.add(i);
		}

		try {
			dbq.add(cap);
			Assert.fail();
		} catch (IllegalStateException ex) {
			// expected;
		}
	}

	@Test
	public void testRemoveObj() {
		final int cap = 100;
		final BlockingQueue<Integer> dbq = new DisruptorBlockingQueue<>(cap);

		for (int i = 0; i < cap; i++) {
			dbq.offer(i);
		}

		for (int i = 0; i < cap; i += 2) {
			dbq.remove(i);
		}

		Assert.assertEquals(cap / 2, dbq.size());

		for (int i = 1; i < cap; i += 2) {
			Assert.assertEquals(Integer.valueOf(i), dbq.poll());
		}
	}

	@Test
	public void testRemoveObjDups() {
		final int cap = 100;
		final BlockingQueue<Integer> dbq = new DisruptorBlockingQueue<>(cap);

		for (int i = 0; i < cap; i++) {
			// all just zeros and ones
			dbq.offer(i & 1);
		}

		// nothing removed
		dbq.remove(777);

		Assert.assertEquals(cap, dbq.size());

		dbq.remove(1);

		Assert.assertEquals(cap / 2, dbq.size());

		for (int i = 1; i < cap; i += 2) {
			Assert.assertEquals(Integer.valueOf(0), dbq.poll());
		}
	}

	@Test
	public void testContainsAll() {

		final int cap = 100;
		final BlockingQueue<Integer> dbq = new DisruptorBlockingQueue<>(cap);

		for (int i = 0; i < cap; i++) {
			dbq.offer(i);
		}

		Set<Integer> si = new HashSet<>(10);
		for (int i = 0; i < cap / 10; i++) {
			si.add(i);
		}
		Assert.assertTrue(dbq.containsAll(si));

		si.add(-1);
		Assert.assertFalse(dbq.containsAll(si));
		si.remove(-1);
		dbq.clear();
		Assert.assertFalse(dbq.containsAll(si));
	}

	@Test
	public void testAddAll() {

		final int cap = 100;
		final BlockingQueue<Integer> dbq = new DisruptorBlockingQueue<>(cap);

		Set<Integer> si = new HashSet<>(cap);
		for (int i = 0; i < cap / 10; i++) {
			si.add(i);
		}
		dbq.addAll(si);
		Assert.assertTrue(dbq.containsAll(si));

		Set<Integer> ni = new HashSet<>(cap);
		for (int i = 0; i < cap / 10; i++) {
			ni.add(-i);
		}
		dbq.addAll(ni);
		Assert.assertTrue(dbq.containsAll(si));
		Assert.assertTrue(dbq.containsAll(ni));

		for (int i = 2 * cap / 10; i < 2 * cap; i++) {
			si.add(i);
		}
		dbq.addAll(si);
		Assert.assertEquals(128, dbq.size());
	}

	@Test
	public void testAddAllReturnTrue() {

		final int cap = 8;
		final BlockingQueue<Integer> dbq = new DisruptorBlockingQueue<>(cap);

		final Set<Integer> set = new HashSet<>();

		for (int i = 0; i < 8; i++) {
			set.add(i);
		}

		Assert.assertTrue(dbq.addAll(set));

		Integer iVal = dbq.poll();
		while (iVal != null) {
			Assert.assertTrue(set.contains(iVal));
			iVal = dbq.poll();
		}

		for (int i = 0; i < 20; i++) {
			set.add(i);
		}

		// at least one will fail
		Assert.assertTrue(dbq.addAll(set));
	}

	@Test
	public void testRemoveAll() {

		final int cap = 100;
		final BlockingQueue<Integer> dbq = new DisruptorBlockingQueue<>(cap);

		Set<Integer> si = new HashSet<>(cap);
		for (int i = 0; i < cap / 10; i++) {
			si.add(i);
		}

		dbq.addAll(si);
		Assert.assertTrue(dbq.containsAll(si));

		Set<Integer> ni = new HashSet<>(cap);
		for (int i = 1; i < cap / 10; i++) {
			ni.add(-i);
		}

		dbq.addAll(ni);
		Assert.assertTrue(dbq.containsAll(si));
		Assert.assertTrue(dbq.containsAll(ni));

		Assert.assertTrue(dbq.removeAll(si));
		Assert.assertTrue(dbq.containsAll(ni));
		Assert.assertFalse(dbq.containsAll(si));

		Assert.assertTrue(dbq.removeAll(ni));
		Assert.assertFalse(dbq.containsAll(ni));
		Assert.assertFalse(dbq.containsAll(si));
	}

	@Test
	public void testRetainAll() {

		final int cap = 100;
		final BlockingQueue<Integer> dbq = new DisruptorBlockingQueue<>(cap);

		Set<Integer> removedEle = new HashSet<>(cap);
		for (int i = 0; i < cap; i++) {
			final Integer iVal = i;
			dbq.offer(iVal);
			removedEle.add(iVal);
		}

		Set<Integer> si = new HashSet<>(cap);
		for (int i = 0; i < cap / 10; i++) {
			final Integer iVal = i;
			si.add(iVal);
			removedEle.remove(iVal);
		}

		Assert.assertTrue(dbq.retainAll(si));

		Assert.assertEquals(cap / 10, dbq.size());

		Assert.assertTrue(dbq.containsAll(si));

		for (final Integer i : removedEle) {
			Assert.assertFalse(dbq.contains(i));
		}
	}

	@Test
	public void testRemoveAll_with_empty_Collection_returns_false_with_no_exception() {

		final int cap = 8;
		final BlockingQueue<Integer> dbq = new DisruptorBlockingQueue<>(cap);

		final Set<Integer> set = new HashSet<>();

		for (int i = 0; i < cap; i++) {
			set.add(i);
		}

		dbq.addAll(set);

		Assert.assertFalse(dbq.removeAll(Collections.emptySet()));
		Assert.assertEquals(cap, dbq.size());
	}

	@Test
	public void testRetainAll_with_equal_Collection_returns_false_with_no_exception() {
		final int cap = 100;
		final BlockingQueue<Integer> dbq = new DisruptorBlockingQueue<>(cap);
		Set<Integer> si = new HashSet<>(cap);

		for (int i = 0; i < cap; i++) {
			si.add(i);
			dbq.offer(i);
		}

		Assert.assertFalse(dbq.retainAll(si));
		Assert.assertEquals(cap, dbq.size());
	}

	@Test
	public void testClear() {
		final int cap = 100;
		final BlockingQueue<Integer> dbq = new DisruptorBlockingQueue<>(cap);

		for (int i = 0; i < cap; i++) {
			dbq.offer(i);
		}

		Set<Integer> si = new HashSet<>(cap);
		for (int i = 0; i < cap / 10; i++) {
			si.add(i);
		}

		Assert.assertTrue(dbq.containsAll(si));
		dbq.clear();
		Assert.assertFalse(dbq.containsAll(si));
		Assert.assertEquals(0, dbq.size());
		Assert.assertTrue(dbq.isEmpty());
		Assert.assertNull(dbq.poll());
	}

	@Test
	public void testIterator() {
		final int cap = 100;
		final BlockingQueue<Integer> dbq = new DisruptorBlockingQueue<>(cap);

		for (int i = 0; i < cap; i++) {
			dbq.offer(i);
		}

		int i = 0;
		for (final Integer c : dbq) {
			Assert.assertEquals(Integer.valueOf(i++), c);
		}
	}

	@Test
	public void testTypeToArray() {
		final int cap = 100;
		final BlockingQueue<Integer> dbq = new DisruptorBlockingQueue<>(cap);

		for (int i = 0; i < cap; i++) {
			dbq.offer(i);
		}

		Integer[] t = new Integer[cap];
		dbq.toArray(t);
		for (int i = 0; i < cap; i++) {
			Assert.assertEquals(Integer.valueOf(i), t[i]);
		}
	}

	@Test
	public void textIntMaxValue() {

		// the blocking queue depends on sequence numbers that are integers
		// be sure the blocking queue operates normally over
		// a range spanning integer values

		if (ALLOW_LONG_RUN) {
			final int cap = 3;
			final BlockingQueue<Integer> dbq = new DisruptorBlockingQueue<>(cap);

			long nIter = 0;

			for (int i = 0; i < Integer.MAX_VALUE; i++) {

				for (int a = 0; a < cap; a++) {
					Assert.assertEquals(dbq.size(), a);
					dbq.offer(a);
					nIter++;
				}

				for (int a = 0; a < cap; a++) {
					Assert.assertEquals(dbq.size(), cap - a);
					Assert.assertEquals("At i=" + i, dbq.poll(), Integer.valueOf(a));
				}

				if (nIter % Integer.MAX_VALUE == 0)
					System.out.println(nIter + "times MAX_VALUE");

			}
		} else {
			System.out.println("max value test not executed");
		}
	}

	@Ignore // FIXME - Spurious failures
	public void testSequentialFeed() throws InterruptedException {

		final int feedCount = 8192;
		final BlockingQueue<Integer> dbq = new DisruptorBlockingQueue<>(128);
		final AtomicInteger nFed = new AtomicInteger(0);
		final AtomicInteger nRead = new AtomicInteger(0);

		final int nFeeders = 128;

		for (int i = 0; i < nFeeders; i++) {
			new Thread(() -> {
                try {
                    for (int i1 = 0; i1 < feedCount / nFeeders; i1++) {
                        while (!dbq.offer(i1, 50L, TimeUnit.MICROSECONDS))
                            Thread.yield();
                        nFed.incrementAndGet();
                    }
                } catch (InterruptedException ignored) {

                }
            }).start();
		}

		final int nReaders = 64;
		Thread[] t = new Thread[nReaders];
		for (int i = 0; i < nReaders; i++) {
			t[i] = new Thread(() -> {
                try {
                    while (nRead.get() < feedCount) {
                        Integer r;
                        do {
                            r = dbq.poll(50, TimeUnit.MILLISECONDS);
                            if (r == null)
                                Thread.yield();
                        } while ((r == null) && (nRead.get() < feedCount));
                        if (r != null) {
                            // we can't control which thread will return
                            // first, but the expected still must be within
                            // the number of threads range
                            Assert.assertTrue(r <= nRead.get() + nReaders + 1);
                            nRead.incrementAndGet();
                        }
                    }
                } catch (InterruptedException ignored) {

                }
            });
			t[i].start();
		}
		for (int i = 0; i < nReaders; i++)
			t[i].join();

		Assert.assertEquals(nFed.get(), nRead.get());

	}
}
