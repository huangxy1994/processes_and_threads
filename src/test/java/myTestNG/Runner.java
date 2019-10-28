package myTestNG;

import com.example.tools.FileUtil;

import java.util.ArrayList;
import java.util.List;

public class Runner {

    public void run(String casePath, String reportPath){
        // xml测试用例
        String path = FileUtil.join(FileUtil.get_engineering_path(), casePath);
        // 测试报告路径
        String report_path = FileUtil.join(FileUtil.get_engineering_path(), reportPath);

        MyTestNG testNG = new MyTestNG();
        // 添加测试套件
        List<String> suites = new ArrayList();
        suites.add(path);
        testNG.setTestSuites(suites);
        // 执行用例
        testNG.run(report_path);
    }

    public static void main(String[] args) {
        Runner runner = new Runner();
        String casePath = "/src/test/java/test_case/JT_Android_D/test_JT_Android_D.xml";
        // String reportPath = "/src/test/java/myTestNG/test.html";
        String reportPath = "/src/main/java/report/JT_Android_D/1538050069150/2018-9-27-20-7-49.html";
        runner.run(casePath, reportPath);
    }
}
