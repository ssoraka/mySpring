package my.test.reflection.junit;

@MyAutotest
public class MyAutotests {

    @MyBeforeTests
    public void beforeTests(){
        System.out.println("before all tests");
    }

    @MyBefore
    public void before(){
        System.out.println("before test");
    }

    @MyTest
    public void test1() throws MyAssertException {
        System.out.println("test 1");
        MyAsserts.AssertTrue(false);
    }

    @MyTest
    public void test2() throws MyAssertException {
        System.out.println("test 2");
        MyAsserts.AssertTrue(true);
    }

    @MyTest
    public void test3() throws MyAssertException {
        System.out.println("test 3");
        MyAsserts.AssertTrue(false);
    }

    @MyAfter
    public void after() {
        System.out.println("after test");
    }

    @MyAfterTests
    public void afterTests() {
        System.out.println("after all tests");
    }
}