package java.util.concurrent;
import java.util.List;
import java.util.Collection;

/**
 * 一个 ExecutorService 可以被关闭, 拒绝接受新任务。
 *
 * ExecutorService 两关闭服务方法的区别：
 * 1. shutdown:已提交的任务继续执行
 * 2. shutdownNow:尝试停止正在执行的所有任务
 *
 * submit 继承了 Executor 的 execute(Runnable)
 * 创建并返回了一个可用于取消执行和（或）等待完成的Future
 *
 * invokeAny 和 invokeAll
 * 执行常用的批处理，处理一组任务，等待至少一个或者所有任务完成
 */
public interface ExecutorService extends Executor {

    /**
     * 有序的关闭线程池，已提交的任务继续执行
     * 不接受继续提交新任务。
     * 调用如果已经关闭，则没有其他效果。
     */
    void shutdown();

    /**
     * 有序的关闭线程池，尝试停止正在执行的所有任务
     * 不接受继续提交新任务。
     * 暂停等待任务的处理,并返回等待执行的任务的列表.
     */
    List<Runnable> shutdownNow();

    /**
     * 线程池是否已关闭
     */
    boolean isShutdown();

    /**
     * 如果调用了 shutdown() 或 shutdownNow() 方法后，所有任务结束了，那么返回true
     * 这个方法必须在调用shutdown或shutdownNow方法之后调用才会返回true
     */
    boolean isTerminated();

    /**
     * 1.调用 shutdown 或 shutdownNow,
     * 2.或者timeout超时,
     * 3.或者当前线程被中断,
     * 无论上面哪个发生后阻塞，等待所有任务完成
     *
     * @param timeout 最大等待时间
     * @param unit 超时参数的时间单位
     * @return 返回值意味着有没有超时，超时返回false
     * @throws 如果在等待中中断，抛出 InterruptedException 异常
     */
    boolean awaitTermination(long timeout, TimeUnit unit)
        throws InterruptedException;

    /**
     * 提交一个 Callable 任务，返回一个 Future 代表任务的结果。
     * 根据执行结果 Future 的 get 方法会返回任务的结果
     */
    <T> Future<T> submit(Callable<T> task);

    /**
     * 提交一个 Runnable 任务，第二个参数将会放到 Future 中，作为返回值，
     * 因为 Runnable 的 run 方法本身并不返回任何东西
     */
    <T> Future<T> submit(Runnable task, T result);

    /**
     * 提交一个 Runnable
     */
    Future<?> submit(Runnable task);

    /**
     * 执行所有任务，当所有任务完成时返回 Future 类型的一个 list
     * 如果在执行此操作时修改了给定集合，则此方法的结果undefined。
     */
    <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks)
        throws InterruptedException;

    /**
     * 执行所有任务，当 所有任务完成 或者 超时 时返回 Future 类型的一个 list
     * 返回时，未完成的任务将被取消。
     * 如果在执行此操作时修改了给定集合，则此方法的结果undefined。
     */
    <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks,
                                  long timeout, TimeUnit unit)
        throws InterruptedException;

    /**
     * 只有其中的一个任务结束了，就可以返回，返回执行完的那个任务的结果
     * 返回时，未完成的任务将被取消。
     * 如果在执行此操作时修改了给定集合，则此方法的结果undefined。
     */
    <T> T invokeAny(Collection<? extends Callable<T>> tasks)
        throws InterruptedException, ExecutionException;

    /**
     * 只有其中的一个任务在超时前结束了，就可以返回，返回执行完的那个任务的结果
     * 返回时，未完成的任务将被取消。
     * 如果在执行此操作时修改了给定集合，则此方法的结果undefined。
     * 超过指定的时间，抛出 TimeoutException 异常
     */
    <T> T invokeAny(Collection<? extends Callable<T>> tasks,
                    long timeout, TimeUnit unit)
        throws InterruptedException, ExecutionException, TimeoutException;
}
