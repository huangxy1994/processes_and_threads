# 通过继承Thread来创建线程

## 步骤
- 继承Thread类
- 重写类中的run()方法
- 启动：new Thread(target, threadName)

## 常用方法
|方法|描述|
|----|----|
|public void start()|使该线程开始执行；Java 虚拟机调用该线程的 run 方法。|
|public void run()|如果该线程是使用独立的 Runnable 运行对象构造的，则调用该 Runnable 对象的 run 方法；否则，该方法不执行任何操作并返回。|
|public final void setName(String name)|改变线程名称，使之与参数 name 相同。|
|public final void setPriority(int priority)|更改线程的优先级。|
|public final void setDaemon(boolean on)|将该线程标记为守护线程或用户线程。|
|public final void join(long millisec)|等待该线程终止的时间最长为 millis 毫秒。|
|public void interrupt()|中断线程。|
|public final boolean isAlive()|测试线程是否处于活动状态|