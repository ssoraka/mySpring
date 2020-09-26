package my.test.reflection.configurations;

import my.test.reflection.di.Context;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ConfigurationContext {
    Map<String, Class> store = new HashMap<>();

    public ConfigurationContext(String classPath) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        /*поиск класса в пакете с аннотацией компонент
        */
        Class<?> originalClass = Class.forName(classPath);
        if (!originalClass.isAnnotationPresent(MyConfiguration.class))
            throw new RuntimeException("Нет найдено класса с аннотацией MyConfiguration");
        final Object o = originalClass.newInstance();

        //нужно создать прокси этого класса
        final Object o1 = Proxy.newProxyInstance(originalClass.getClassLoader(), originalClass.getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println(method.getName());
                Object retVal = method.invoke(o, args);
                return retVal;
            }
        });
        Class<?> newClass = o1.getClass();

        Method[] methods = originalClass.getMethods();

        Method[] methods2 = newClass.getMethods();

        for (Method method : methods2) {
            System.out.println(method.getName());
        }

        for (Method method : methods) {
            if (method.isAnnotationPresent(MyBean.class)) {
                try {

                    Method newMethod = newClass.getMethod(method.getName(), method.getParameterTypes());

                    newMethod.invoke(o);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public Object getBean(String car) {
        return null;
    }
}
