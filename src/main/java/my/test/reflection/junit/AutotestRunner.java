package my.test.reflection.junit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AutotestRunner {

    public void runTests(String packPath) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        List<Class<?>> classes = ClassFinder.find(packPath);
        for (Class clazz : classes) {
            if (clazz.isAnnotationPresent(MyAutotest.class))
                runTest(clazz);
        }
    }

    private void runTest(Class aClass) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        Object testInstance = aClass.newInstance();

        Method[] method = aClass.getMethods();
        List<Method> beforeTests = new ArrayList<>();
        List<Method> before = new ArrayList<>();
        List<Method> tests = new ArrayList<>();
        List<Method> after = new ArrayList<>();
        List<Method> afterTests = new ArrayList<>();

        for (Method md : method) {
            if (md.isAnnotationPresent(MyBeforeTests.class)) {
                beforeTests.add(md);
            } else if (md.isAnnotationPresent(MyBefore.class)) {
                before.add(md);
            } else if (md.isAnnotationPresent(MyAfter.class)) {
                after.add(md);
            } else if (md.isAnnotationPresent(MyTest.class)) {
                tests.add(md);
            } else if (md.isAnnotationPresent(MyAfterTests.class)) {
                afterTests.add(md);
            }
        }


        runMethods(beforeTests, testInstance);
        for (Method test : tests) {
            runMethods(before, testInstance);
            try {
                test.invoke(testInstance);
                System.out.println(test.getName() + " SUCCESS");
            } catch (InvocationTargetException e) {
                System.out.println(test.getName() + " FAIL " + e.getCause().getMessage());
            }
            runMethods(after, testInstance);
            System.out.println("");
        }
        runMethods(afterTests, testInstance);

    }

    private void runMethods(List<Method> methods, Object testClass) throws InvocationTargetException, IllegalAccessException {
        for (Method md : methods) {
            try {
                md.invoke(testClass);
            } catch (InvocationTargetException e) {
                System.out.println(md.getName() + "\n" +
                        e.getCause().getClass().getName() + "\n" +
                        e.getCause().getMessage());
            }
        }
    }
}
