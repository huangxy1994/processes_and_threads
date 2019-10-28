package myTestNG;

import com.beust.jcommander.JCommander;
import com.example.tools.FileUtil;
import com.example.tools.TimeUtil;
import org.json.JSONObject;
import org.testng.*;
import org.testng.collections.Maps;
import org.testng.internal.Configuration;
import org.testng.internal.ExitCode;
import org.testng.internal.IConfiguration;
import org.testng.xml.internal.XmlSuiteUtils;

import java.util.*;

public class MyTestNG extends TestNG {
    private ExitCode exitCode;
    private org.testng.internal.ExitCodeListener exitCodeListener = new org.testng.internal.ExitCodeListener();
    private static TestNG m_instance;
    private static JCommander m_jCommander;
    private IConfiguration m_configuration = new Configuration();
    private final Map<Class<? extends IAlterSuiteListener>, IAlterSuiteListener> m_alterSuiteListeners = Maps.newHashMap();
    private final Map<Class<? extends IReporter>, IReporter> m_reporters = Maps.newHashMap();
    private String m_outputDir = DEFAULT_OUTPUTDIR;

    private String testName = "";
    private String beginTime = "";
    private String totalTime = "";
    private String phoneMsg = "";
    private String launchTime = "";
    private int testAll = 0;
    private int testPass = 0;
    private int testFail = 0;
    private int testSkip = 0;
    private ArrayList<Map<String, String>> caseresult = new ArrayList<>();

    public void run(String reportPath) {
        initializeEverything();
        sanityCheck();

        runExecutionListeners(true /* start */);

        runSuiteAlterationListeners();

        m_start = System.currentTimeMillis();
        List<ISuite> suiteRunners = runSuites();

        m_end = System.currentTimeMillis();

        if (null != suiteRunners) {
            generateReports(suiteRunners, reportPath);
        }


        runExecutionListeners(false /* finish */);
        exitCode = this.exitCodeListener.getStatus();

        if (!exitCodeListener.hasTests()) {
            if (TestRunner.getVerbose() > 1) {
                System.err.println("[TestNG] No tests found. Nothing was run");
                usage();
            }
        }

        m_instance = null;
        m_jCommander = null;
    }

    /**
     * 测试结果排序，按时间排序
     *
     * @param list
     */
    private void sort(List<ITestResult> list) {
        Collections.sort(list, new Comparator<ITestResult>() {
            @Override
            public int compare(ITestResult r1, ITestResult r2) {
                if (r1.getStartMillis() > r2.getStartMillis()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
    }

    /**
     * 输入测试结果
     *
     * @param list
     */
    private void outputResult(List<ITestResult> list) {
        for (ITestResult iTestResult : list) {
            ITestNGMethod iTestNGMethod = iTestResult.getMethod();
            Map<String, String> map = new HashMap<String, String>();
            map.put("className", iTestNGMethod.getRealClass().getName());
            map.put("methodName", iTestNGMethod.getMethodName());
            map.put("description", iTestNGMethod.getDescription());
            int status = iTestResult.getStatus();
            if (status == 1) {
                map.put("status", "成功");
            } else if (status == 2) {
                map.put("status", "失败");
                StackTraceElement[] stackTraceElements = iTestResult.getThrowable().getStackTrace();
                String log = iTestResult.getThrowable().toString() + "\n";
                for (int i = 0; i < stackTraceElements.length; i++) {
                    log = log + "\t" + stackTraceElements[i] + "\n";
                }
                map.put("log", log);
            } else if (status == 3) {
                map.put("status", "跳过");
            }
            caseresult.add(map);
        }
    }


    private void sanityCheck() {
        XmlSuiteUtils.validateIfSuitesContainDuplicateTests(m_suites);
        XmlSuiteUtils.adjustSuiteNamesToEnsureUniqueness(m_suites);
    }

    private void runExecutionListeners(boolean start) {
        for (IExecutionListener l : m_configuration.getExecutionListeners()) {
            if (start) {
                l.onExecutionStart();
            } else {
                l.onExecutionFinish();
            }
        }
    }

    private void runSuiteAlterationListeners() {
        for (IAlterSuiteListener l : m_alterSuiteListeners.values()) {
            l.alter(m_suites);
        }
    }

    private void generateReports(List<ISuite> suiteRunners, String reportPath) {
        List<ITestResult> list = new ArrayList<ITestResult>();
        for (ISuite suiteRunner : suiteRunners) {
            testName = suiteRunner.getName();
            beginTime = TimeUtil.getFormatTime();
            totalTime = TimeUtil.getHMS(m_end - m_start);

            Map<String, ISuiteResult> result = suiteRunner.getResults();
            for (Map.Entry<String, ISuiteResult> m : result.entrySet()) {

                IResultMap iResultMap = m.getValue().getTestContext().getPassedTests();
                Set<ITestResult> iTestResults = iResultMap.getAllResults();
                testPass = testPass + iTestResults.size();
                ArrayList<ITestResult> passResults = new ArrayList<ITestResult>(iTestResults);
                list.addAll(passResults);

                IResultMap iResultMap2 = m.getValue().getTestContext().getFailedTests();
                Set<ITestResult> iTestResults2 = iResultMap2.getAllResults();
                testFail = testFail + iTestResults2.size();
                ArrayList<ITestResult> failResults = new ArrayList<ITestResult>(iTestResults2);
                list.addAll(failResults);

                IResultMap iResultMap3 = m.getValue().getTestContext().getSkippedTests();
                Set<ITestResult> iTestResults3 = iResultMap3.getAllResults();
                testSkip = testSkip + iTestResults3.size();
                ArrayList<ITestResult> skipResults = new ArrayList<ITestResult>(iTestResults3);
                list.addAll(skipResults);

                ITestNGMethod[] methods = m.getValue().getTestContext().getAllTestMethods();
                testAll = testAll + methods.length;
            }
        }

        sort(list);
        outputResult(list);

        System.out.println("testName：" + testName + " beginTime：" + beginTime + " totalTime：" + totalTime + " testAll：" + testAll + " testPass：" + testPass + " testFail：" + testFail + " testSkip：" + testSkip);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("testName", testName);
        jsonObject.put("beginTime", beginTime);
        jsonObject.put("totalTime", totalTime);
        jsonObject.put("testAll", testAll);
        jsonObject.put("testPass", testPass);
        jsonObject.put("testFail", testFail);
        jsonObject.put("testSkip", testSkip);
        jsonObject.put("testResult", caseresult);

        String dir = FileUtil.get_absolute_dir();
        String path1 = FileUtil.join(dir, "template.html");
        FileUtil.create_file(reportPath);
        FileUtil.modifyFileContent(path1, reportPath, "${resultData}", jsonObject.toString());
    }

    private static void usage() {
        if (m_jCommander == null) {
            m_jCommander = new JCommander(new CommandLineArgs());
        }
        m_jCommander.usage();
    }
}
