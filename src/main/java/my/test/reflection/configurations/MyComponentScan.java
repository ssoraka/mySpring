package my.test.reflection.configurations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
public @interface MyComponentScan {
    String[] value();
}
