# 线程池的使用

## 线程池的优势
1. 降低资源消耗。通过重复利用已创建的线程降低线程创建和销毁造成的消耗。
2. 提高响应速度。当任务到达时，任务可以不需要等到线程创建就能立即执行。
3. 提高线程的可管理性。线程是稀缺资源，如果无限制的创建，不仅会消耗系统资源，还会降低系统的稳定性，使用线程池可以进行统一的分配，调优和监控。

## 步骤
1. 创建线程池  
实现ThreadPoolExecutor类：  
Executor threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS, sPoolWorkQueue, sThreadFactory);  
2. 向线程池提交任务
```java
threadPool.execute(new Runnable() {
    public void run() {
       // 线程执行的任务
    }
})
```
3. 关闭线程池
```java
threadPool.shutdown();  // 设置线程池状态为SHUTDOWN，然后中断所有没有正在执行任务的线程
threadPool.shutdown();  // 设置线程池状态为STOP，然后尝试停止所有正在执行或暂停任务的数据，并返回等待执行任务的列表
```
## 参数介绍
1. corePoolSize（必需）：核心线程数。默认情况下，核心线程会一直存活，但是当将allowCoreThreadTimeout设置为true时，核心线程也会超时收回。
2. maximumPoolSize（必需）：线程池所能容纳的最大线程数。当活跃线程数达到该数值后，后续的新任务将会阻塞。
3. keepAliveTime（必需）：线程闲置超时时长。如果超过该时长，非核心线程就会被回收。如果将allowCoreThreadTime设置为true，核心线程也会被超时回收。
4. timeUnit（必需）：指定keepAliveTime参数的时间单位。常用的有：TimeUnit.MILLISECONDS(毫秒)、TimeUnit.SECONDS(秒)、TimeUnit.MINUTES(分)。
5. workQueue（必需）：任务队列。通过线程池的execute()方法体骄傲的Runnable对象将存储在该参数中。其采用阻塞队列实现。
6. threadFactory（可选）：线程工厂。用于指定为线程池创建心线程的方式。
7. handler（可选）：拒绝策略。当达到最大线程数时需要执行的饱和策略。

### 任务队列
任务队列是基于阻塞队列实现的，即采用生产者消费者的模式，在Java中需要实现BlockingQueue接口。Java中已经为我们提供了7种阻塞队列的实现：
1. ArrayBlockingQueue：一个由数组结构组成的有界阻塞队列。
2. LinkedBlockingQueue：一个由链表结构组成的有界阻塞队列。在未指明容量时，默认容量为Integer.MAX_VALUE。
3. PriorityBlockingQueue：一个支持优先级排序的无界阻塞队列。对元素没有要求，可以实现Comparable接口，也可以提供Comparator来对队列中的元素进行比较。跟时间没有关系，仅仅时按照优先级取任务。
4. DelayQueue：类似于PriorityBlockingQueue，是二叉堆实现的无界优先级阻塞队列。要求元素都实现Delayed接口，通过执行时延从队列中提取任务，时间没到任务取不出来。
5. SynchronousQueue：一个不存储元素的阻塞队列，消费者线程调用take()方法的时候就会发生阻塞，直到有一个生产者线程生产了一个元素，消费者线程就可以拿到这个元素并返回；生产者线程调用put()方法的时候也会发生阻塞，直到一个消费者线程消费了一个元素，生产者才会返回。
6. LinkedBlockingDeque：使用双向队列实现的有界双端阻塞队列。双端意味着可以像普通队列一样FIFO（先进先出），也可以像栈一样FILO（先进后出）。
7. LinkedTransferQueue：它是ConcurrentLinkedQueue、LinkedBlockingQueue和SynchronousQueue的结合体，但是把它用在ThreadPoolExecutor中，和LinkedBlockingQueue行为一致，但是是无界的阻塞队列。  
有界队列和无界队列的区别是：如果使用有界队列，当队列饱和时并超过最大线程数时就会执行拒绝策略；而如果使用无界队列，因为任务队列永远都可以添加任务，所以设置maximumPoolSize没有任何意义。
### 线程工厂
线程工厂指定创建线程的方式，需要实现ThreadFactory接口，并实现newThread(Runnable r)方法
### 拒绝策略
当线程池的线程数达到最大线程数时，需要执行拒绝策略。拒绝策略需要实现RejectedExecutionHandler接口，并实现rejectedExecution(Runnable r, ThreadPoolExecutor executor)方法。Executors框架已经为我们实现了4中拒绝策略：
1. AbortPolicy(默认)：丢弃任务并抛出RejectedExecutionException异常。
2. CallerRunsPolicy：由调用线程处理该任务。
3. DiscardPolicy：丢弃任务，但是不抛出异常。可以配合这种模式进行自定义的处理方式。
4. DiscardOldestPolicy：丢弃队列最早的未处理任务，然后重新尝试执行任务。

## 功能线程池
Executors已经封装好了4中常见的功能线程池：
- 定长线程池
- 定时线程池
- 可缓存线程池
- 单线程化线程池
### 定长线程池--FixedThreadPool
#### 特点
只有核心线程，线程数量固定，执行完立即回收，任务队列为链表结构的有界队列。
#### 应用场景
控制线程最大并发数
### 定时线程池--ScheduledThreadPool 
#### 特点
核心线程数固定，非核心线程数量无限，执行完闲置10ms后回收，任务队列为延时阻塞队列。
#### 应用场景
执行定时或周期性的任务
### 可缓存线程池--CachedThreadPool
#### 特点
无核心线程，非核心线程数量无线，执行完闲置60s后回收，任务队列为不存储元素的阻塞队列。
#### 应用场景
执行大量、耗时少的任务。
### 单线程化线程池--SingleThreadExecutor
#### 特点
只有1个核心线程，无非核心线程，执行完立即回收，任务队列为链表结构的有界队列。
#### 应用场景
不适合并发但可能引起IO阻塞性及影响UI线程影响的操作，如数据库操作、文件操作等。














# 参考
1.  《Java多线程：彻底搞懂线程池》
https://blog.csdn.net/u013541140/article/details/95225769  
2.  《一次Java线程池误用引发的血案和总结》
https://zhuanlan.zhihu.com/p/32867181  
3.  《Java中线程池，你真的会用吗？》
https://www.hollischuang.com/archives/2888
4.  《阿里java代码规范》
https://www.jianshu.com/p/6a88cf7b18e8
