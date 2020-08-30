package my.test.reflection;

import com.sun.istack.internal.NotNull;

import java.lang.reflect.Method;

public class Aspects {
	public void printText(@NotNull String s) {
		if (s != null)
			System.out.println(s);
	}
}
