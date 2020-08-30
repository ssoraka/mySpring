package my.test.reflection.validate;

import my.test.reflection.di.Auto;
import my.test.reflection.di.RefCreator;

import java.lang.reflect.Field;

public class Validator {
	public Validator() {}

	public static boolean isNotNull(Object object){
		return object != null;
	}

	public static void validateInstance(Object object, String className) throws ClassNotFoundException, MyValidationException, IllegalAccessException {
		Class clazz = Class.forName(className);
		validateInstance(object, clazz);
	}

	public static void validateInstance(Object object, Class clazz) throws IllegalAccessException, MyValidationException {
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(MyNotNull.class)) {
				boolean access = field.isAccessible();
				field.setAccessible(true);

				if (!isNotNull(field.get(object)))
					throw new MyValidationException(String.format("В экземпляре класса %s поле %s имеет значение null", clazz.getSimpleName(), field.getName()));

				field.setAccessible(access);

			}
		}
	}
}
