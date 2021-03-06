/**
 * @description 胡成杰(chengjiehu@creditease.cn)
 * 2017/1/9 20:01
 */
public class TestException {
    public TestException() {
    }

    void testEx22() {
        try {
            System.out.println("try block");
            throw new Exception();
        } catch (Exception e) {
            System.out.println("exit block");
            System.exit(0);
        } finally {
            System.out.println("finally block");
        }
    }

    boolean testEx() throws Exception {
        boolean ret = true;
        try {
            ret = testEx1();
        } catch (Exception e) {
            System.out.println("testEx, catch exception");
            ret = false;
            throw e;
        } finally {
            System.out.println("testEx, finally; return value=" + ret);
            return ret;
        }
    }

    boolean testEx1() throws Exception {
        boolean ret = true;
        try {
            ret = testEx2();
            if (!ret) {
                return false;
            }
            System.out.println("testEx1, at the end of try");
            return ret;
        } catch (Exception e) {
            System.out.println("testEx1, catch exception");
            ret = false;
            throw e;
        } finally {
            System.out.println("testEx1, finally; return value=" + ret);
            return ret;
        }
    }

    boolean testEx2() throws Exception {
        boolean ret = true;
        try {
            int b = 12;
            int c;
            for (int i = 2; i >= -2; i--) {
                c = b / i;
                System.out.println("i=" + i);
            }
            return true;
        } catch (Exception e) {
            System.out.println("testEx2, catch exception");
            ret = false;
            throw e;
        } finally {
            System.out.println("testEx2, finally; return value=" + ret);
            return ret;//导致该方法不会抛出异常
        }
    }

    public static void main(String[] args) {
        TestException testException1 = new TestException();
        try {
            //testException1.testEx();
            testException1.testEx22();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}