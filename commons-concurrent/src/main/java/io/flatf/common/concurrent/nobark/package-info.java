/**
 * Low-latency building blocks for state-oriented event pipelines.
 *
 * <p>The main capability of this package is keyed conflation: producers enqueue values with a conflation key, and
 * the queue prevents unbounded backlog by replacing or merging an older queued value with the same key. This is useful
 * for high-frequency streams where consumers need the latest or aggregated state rather than every intermediate event,
 * such as market-data snapshots, order-book refreshes, account-state updates, risk snapshots, and derived metrics.</p>
 *
 * <p>The queue package provides three primary models:</p>
 *
 * <ul>
 *     <li>{@code AtomicConflationQueue}: atomically replaces the queued value for a key with the latest value.</li>
 *     <li>{@code EvictConflationQueue}: evicts older values and supports producer/consumer value exchange to reduce
 *     allocation pressure.</li>
 *     <li>{@code MergeConflationQueue}: merges old and new values through a caller-provided merge strategy, which is
 *     suitable for aggregate state such as OHLC price entries or accumulated risk counters.</li>
 * </ul>
 *
 * <p>The loop package offers small event-loop primitives. A {@code Step} reports whether useful work was performed;
 * a {@code Loop} runs multiple steps until a loop condition stops it; and an {@code IdleStrategy} controls what happens
 * when an iteration is idle. The run package adds simple stoppable and shutdownable thread wrappers for hosting these
 * loops. These components are intentionally minimal and should be treated as building blocks rather than a scheduler,
 * actor runtime, or complete trading-engine framework.</p>
 *
 * <p>For trading systems, this package is a good fit for state streams that can be collapsed by key. It is not a good
 * fit for audit-critical event streams where every event must be preserved and processed in strict order, such as
 * order commands, cancel requests, fills, cash movements, or regulatory records.</p>
 *
 * <p>Operationally, the caller must choose the backing {@code Queue} implementation according to the required
 * producer/consumer concurrency model. Exchange queues also require careful object ownership discipline because values
 * may be recycled between producer and consumer threads. The Java 21 build uses the JDK internal
 * {@code jdk.internal.vm.annotation.Contended} annotation, so production runtimes that rely on false-sharing padding
 * should provide the matching module export and {@code -XX:-RestrictContended} JVM options.</p>
 */
package io.flatf.common.concurrent.nobark;
