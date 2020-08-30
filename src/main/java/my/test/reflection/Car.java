package my.test.reflection;

import com.sun.istack.internal.NotNull;
import my.test.reflection.di.*;
import my.test.reflection.validate.MyNotNull;

public class Car {

    @Auto(isRequired = true)
    private Engine engine;
    @Auto
    private Gear gear;

    public Car() {};

    public Car(String s) {
        printText(s);
    }

    @Override
    public String toString() {
        return "Car{" +
                "engine=" + engine +
                ", gear=" + gear +
                '}';
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public void setGear(Gear gear) {
        this.gear = gear;
    }

    public Engine getEngine() {
        return engine;
    }

    public Gear getGear() {
        return gear;
    }

    public void printText(@NotNull String s) {
        System.out.println(s);
    }
}
