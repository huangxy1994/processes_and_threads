/**
 * @Description
 * @Author 黄晓阳
 * @Date 2019-07-02 17:15
 */

package wgame.valid;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;

@Slf4j
public class test_func1 {

    @BeforeClass
    public void beforeClass() {
        log.info("------------------beforeClass");
        System.out.println(Thread.currentThread().getName() + " " + Thread.currentThread().getId() + " " + Thread.currentThread().getThreadGroup().getName());
    }

    @AfterClass
    public void afterClass() {
        log.info("------------------afterClass");
    }

    @Test(description = "func1组1接口")
    public void test_1_1_1() {
        log.info("------------------test_1");
        try {
            Thread.sleep((int)(Math.random() * 10000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertFalse(false);
    }

    @Test(description = "func1组2接口")
    public void test_1_2() {
        log.info("------------------test_2");
        try {
            Thread.sleep((int)(Math.random() * 10000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals("1", "2", "response log");
    }

    @Test(description = "func1组3接口")
    public void test_1_3() {
        log.info("------------------test_3");
        try {
            Thread.sleep((int)(Math.random() * 10000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(true);
    }
}
