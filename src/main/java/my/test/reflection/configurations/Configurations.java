package my.test.reflection.configurations;

import my.test.reflection.Car;
import my.test.reflection.Engine;
import my.test.reflection.Manual;

@MyConfiguration
@MyComponentScan("my.test.reflection")
public class Configurations implements SomeInterface{

    @MyBean
    public Car car() {
        Car car = new Car();
        car.setEngine(engine());
        car.setGear(manual());
        return car;
    }

    @MyBean
    public Engine engine() {
        return new Engine();
    }

    @MyBean
    public Manual manual() {
        return new Manual();
    }

    @Override
    public void printHello() {
        System.out.println("Hello");
    }
}
