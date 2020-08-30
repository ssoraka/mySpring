package my.test.reflection.junit;

@MyAutotest
public class MyAutotests2 {

    @MyBeforeTests
    public void beforeTests2(){
        System.out.println("before all tests2");
    }

    @MyBefore
    public void before2(){
        System.out.println("before test2");
    }

    @MyTest
    public void test21() throws MyAssertException {
        System.out.println("test2 1");
        MyAsserts.AssertTrue(false);
    }

    @MyTest
    public void test22() throws MyAssertException {
        System.out.println("test2 2");
        MyAsserts.AssertTrue(true);
    }

    @MyTest
    public void test23() throws MyAssertException {
        System.out.println("test2 3");
        MyAsserts.AssertTrue(false);
    }

    @MyAfter
    public void after2() {
        System.out.println("after test2");
    }

    @MyAfterTests
    public void afterTests2() {
        System.out.println("after all tests2");
    }
}