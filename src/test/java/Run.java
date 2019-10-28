import lombok.extern.slf4j.Slf4j;
import myTestNG.Runner;

/**
 * 描述：
 * <br>
 * <br>创建时间：
 * <br>2018/9/27 19:35
 *
 * @author xiaoyang.huang
 * @version 1.0
 */
@Slf4j
public class Run implements Runnable {

    private Thread thread;
    private String threadName;

    public Run(String name) {
        threadName = name;
        log.info("Creating " + threadName);
    }

    @Override
    public void run() {
        Runner runner = new Runner();
        String threadName = this.threadName;
        String case_path = "src/test/java/wgame/valid/test_wgame.xml";
        String report_path = "src/test/java/wgame/valid/report_" + threadName + ".html";
        runner.run(case_path, report_path);
    }

    public void start() {
        log.info("Starting " + threadName);
        if (thread == null) {
            thread = new Thread(this, threadName);
            thread.start();
        }
    }

    public static void main(String[] args) {
        Run run = new Run("Thread-1");
        run.start();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Run run2 = new Run("Thread-2");
        run2.start();
    }
}
