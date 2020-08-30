package my.test.reflection.cucumber;

import my.test.reflection.di.InvalidConfigurationException;
import my.test.reflection.di.ObjectTypeConverter;
import my.test.reflection.junit.ClassFinder;
import my.test.reflection.junit.MyAfter;
import my.test.reflection.junit.MyBefore;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CucumberRunner {
    public static final String FEATURE = ".feature";
    Map<String,MyStep> stepStore = new HashMap<>();
    List<FeatureFile> features = new ArrayList<>();

    public CucumberRunner() {}

    public void run(String stepsPackPath, String featurePackPath) throws IllegalAccessException, InstantiationException, IOException, InvocationTargetException, FeatureFileException, InvalidConfigurationException {
        getAllSteps(stepsPackPath);
        getAllFeatures(featurePackPath);
        executeFeatures();
    }

    private void executeFeatures() throws InvocationTargetException, IllegalAccessException, FeatureFileException, InvalidConfigurationException {
        for (FeatureFile feature : features) {
            List<String> lines = feature.getSteps();

            int i = 0;
            for (String line : lines) {
                MyStep step = findStepByName(line);
                step.runTest(getArguments(line, step));
            }
        }
    }

    private Object[] getArguments(String line, MyStep step) throws FeatureFileException, InvalidConfigurationException {
        if (step.getArgCount() == 0)
            return null;
        String regex = step.getAnnotation();

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(line);

        List<Object> list = new ArrayList<>();
        if (!m.find() || m.groupCount() != step.getArgCount())
            throw new FeatureFileException("не соответствует количество найденных " +
                    "аргументов и аргументов метода для шага " + line);
        for (int i = 0; i < step.getArgCount(); i++) {
            list.add(ObjectTypeConverter.convert(step.getArgClasses()[i].getSimpleName(), m.group(i + 1)));
        }
        return list.toArray();
    }

    private MyStep findStepByName(String line) throws FeatureFileException {
        for (Map.Entry env : stepStore.entrySet()) {
            if (line.matches((String) (env.getKey()))) {
                return ((MyStep)env.getValue());
            }
        }
        throw new FeatureFileException("нет соответствующего шага для " + line);
    }

    private void getAllFeatures(String featurePackPath) throws IOException {
        List<File> files = ClassFinder.getFiles(featurePackPath, FEATURE);

        for (File file : files) {
            features.add(new FeatureFile(file));
        }
    }


    public void getAllSteps(String packPath) throws InstantiationException, IllegalAccessException {
        List<Class<?>> classes = ClassFinder.find(packPath);
        for (Class clazz : classes) {
            getStepsFromClass(clazz);
        }
    }

    public void getStepsFromClass(Class aClass) throws IllegalAccessException, InstantiationException {
        if (!aClass.isAnnotationPresent(MyCucumberSteps.class))
            return ;

        Object classInstance = aClass.newInstance();
        Method[] methods = aClass.getMethods();

        List<Method> before = new ArrayList<>();
        List<Method> after = new ArrayList<>();
        for (Method method : methods) {
            if (method.isAnnotationPresent(MyBefore.class)) {
                before.add(method);
            } else if (method.isAnnotationPresent(MyAfter.class)) {
                after.add(method);
            }
        }

        for (Method method : methods) {
            if (method.isAnnotationPresent(MyIf.class)) {
                MyStep step = new MyStep(classInstance, method, method.getAnnotation(MyIf.class).value());
                step.setAfter(after);
                step.setAfter(before);
                stepStore.put(step.getAnnotation(), step);
            }
        }
    }
}
