package my.test.reflection.cucumber;

@MyCucumberSteps
public class MySteps {

    @MyIf("вывести \"(.*)\" и \"(.*)\"")
    public void printHelloWorld(String str, String str2){
        System.out.println(str + " и " + str2);
    }

    @MyIf("вывести сообщение")
    public void printMessage(){
        System.out.println("Сообщение");
    }

    @MyIf("вывести другое сообщение")
    public void printAnotherMessage(){
        System.out.println("Другое сообщение");
    }
}
