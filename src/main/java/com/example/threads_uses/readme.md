# 进程
一个进程对应了一个应用程序，只要这个应用程序启动了，就代表启动了一个进程。

# 线程
## 线程定义
单个进程中执行的每一个任务都是一个线程，线程是进程中执行运算的最小单位。
## 线程的生命周期
1. 新建状态
使用new关键字和Thread类或其子类建立了一个线程兑现过之后，该线程对象就处于新建状态。它保持这个状态知道程序start()这个线程
2. 就绪状态
当线程对象调用了start()方法之后，该线程就进入到了就绪状态。就绪状态的线程处于就绪队列中，要等待JVM线程调度器的调度
3. 运行状态
如果就绪状态的线程获取CPU资源，就可以执行run()，此时线程就处于运行状态。处于运行状态的线程最为复杂，它可以变为阻塞状态、就绪状态或者死亡状态
4. 阻塞状态
如果一个线程执行了sleep（睡眠）、suspend（挂起）等发发，失去所占用的资源之后，该线程就从运行状态进入到阻塞状态。在睡眠时间已到或者获得设备资源后可以重新进入到就绪状态。可以分为三种：
   1. 等待阻塞：运行状态中的线程执行wait()方法，使线程进入到等待阻塞状态
   2. 同步阻塞：线程在获取synchronized同步锁失败（因为同步锁被其他线程占用）
   3. 其他阻塞：通过调用线程的sleep()或join()发出了I/O请求时，线程就会进入到阻塞状态。当sleep()状态超时，join()等待线程终止或者超时，或者I/O处理完毕，线程重新进入就绪状态
5. 死亡状态
一个运行状态的线程完成任务或者其他终止条件发生时，该线程就切换到终止状态。

## 线程的优先级
Java线程是有优先级的，这样可以方便操作系统确定线程的调度顺序
Java中线程的优先级是一个整数，取值范围是：1-10。默认情况下，每个线程都会被分配到一个优先级：5

## 创建线程
- 通过实现Runnable接口
- 通过继承Thread类本身
- 通过Callable和Future创建线程

###三种方式对比：
1. 采用实现 Runnable、Callable 接口的方式创建多线程时，线程类只是实现了 Runnable 接口或 Callable 接口，还可以继承其他类。
2. 使用继承 Thread 类的方式创建多线程时，编写简单，如果需要访问当前线程，则无需使用 Thread.currentThread() 方法，直接使用 this 即可获得当前线程。

## 线程同步
当有一个线程在对内存进行操作时，其他线程都不可以读i这个内存地址进行操作，直到该线程完成操作，其他线程才能对该内存地址进行操作。
## 线程间通信
多个线程在处理同一个资源，并且任务不同时，需要线程通信来帮助解决线程之间对同一个变量的使用或操作。多线程间通信能够避免对同一共享变量的争夺。
## 线程死锁
线程死锁指的是两个或者两个以上的线程因为争夺同一个资源而造成的一种相互等待的现象。


















# 参考资料
《Java 多线程编程 》
https://www.runoob.com/java/java-multithreading.html