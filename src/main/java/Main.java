import my.test.reflection.Aspects;
import my.test.reflection.Car;
import my.test.reflection.Manual;
import my.test.reflection.configurations.ConfigurationContext;
import my.test.reflection.cucumber.CucumberRunner;
import my.test.reflection.cucumber.FeatureFileException;
import my.test.reflection.di.Context;
import my.test.reflection.di.InvalidConfigurationException;
import my.test.reflection.junit.*;
import my.test.reflection.validate.MyValidationException;
import my.test.reflection.validate.Validator;
import sun.reflect.Reflection;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;


public class Main {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, InstantiationException, URISyntaxException, InvalidConfigurationException, IOException, FeatureFileException, NoSuchMethodException, MyValidationException, ClassNotFoundException {

        String path = Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
//        System.out.println(Main.class.getResource(Main.class.getSimpleName() + ".class"));
//        System.out.println(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().toString());
//        System.out.println(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());
//        System.out.println(path);

//        Context context = new Context(path + "/config.xml");
//        Car car = (Car)context.getBean("car");
//        System.out.println(car.toString());
//
//        Validator.validateInstance(car, Car.class);


//        AutotestRunner test = new AutotestRunner();
//        test.runTests("my.test.reflection.junit");
//
//        CucumberRunner cucumberRunner = new CucumberRunner();
//        cucumberRunner.run("my.test.reflection.cucumber", path + "features");


        ConfigurationContext context1 = new ConfigurationContext("my.test.reflection.configurations.Configurations");

//        Car car3 = (Car)context1.getBean("car");
    }
}
