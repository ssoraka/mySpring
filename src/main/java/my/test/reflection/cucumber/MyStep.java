package my.test.reflection.cucumber;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class MyStep {
    private Object object;
    private String annotation;
    private List<Method> before;
    private Method  test;
    private List<Method> after;
    private Class[] argClasses;

    public MyStep(Object object, Method method, String annotation) {
        this.object = object;
        this.test = method;
        this.annotation = annotation;
        argClasses = method.getParameterTypes();
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setBefore(List<Method> before) {
        this.before = before;
    }

    public void setAfter(List<Method> after) {
        this.after = after;
    }

    public int getArgCount() {
        return argClasses.length;
    }

    public Class[] getArgClasses() {
        return argClasses;
    }

    public void runTest(Object[] args) throws InvocationTargetException, IllegalAccessException {
        if (before != null)
            runMethods(before, null);
        runMethod(test, args);
        if (after != null)
            runMethods(after, null);
    }

    private void runMethods(List<Method> methodList, Object[] args) throws InvocationTargetException, IllegalAccessException {
        for (Method m : methodList) {
            runMethod(m, args);
        }
    }

    private void runMethod(Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        method.invoke(object, args);
    }
}
