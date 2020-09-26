package my.test.reflection.di;

import my.test.reflection.cucumber.MyIf;
import my.test.reflection.validate.MyValidationException;
import my.test.reflection.validate.Validator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Context {

    private Map<String, Object> storeById = new HashMap<>();
    private Map<String, Object> storeByClassName = new HashMap<>();
    private List<RefCreator> refSave = new ArrayList<>();
    private List<Bean> beans;

    public Context(String xmlPath) {
        //парсим хмл
        BeansCreator beansCreator = new BeansCreator();
        try {
            beans = beansCreator.getBeansFromXml(xmlPath);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        //создаем бины
        try {
            instantiateBeans();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        //валидируем бины
        try {
            validateBeans();
        } catch (Exception | MyValidationException e) {
            e.printStackTrace();
            return;
        }
    }


    private void instantiateBeans() throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchFieldException, InvalidConfigurationException {
        for (Bean bean : beans) {
            Class<?> beanClass = bean.getBeanClass();
            //тут могут быть созданы несколько экземпляров одного класса
            //нужно проверять квалифаер или типа того
            Object object = bean.createObject();

            processAnnotation(beanClass, object);

            for (String fieldName : bean.getProperties().keySet()) {
                Field field = getField(beanClass, fieldName);
                if (field == null)
                    throw new InvalidConfigurationException("Failed to set field " + fieldName + " in class " + bean.getClassName());

                Property property = bean.getProperties().get(fieldName);

                RefCreator ref = new RefCreator(object, field, property);
                if (ref.isRef())
                    refSave.add(ref);
                else
                    ref.placeObjectInField(property.getValue());
            }
            storeById.put(bean.getId(), object);
            storeByClassName.put(bean.getClassName(), object);
        }

        for (RefCreator ref : refSave) {
            if (storeById.containsKey(ref.getRefValue())) {
                ref.placeObjectInField(getBean(ref.getRefValue()));
            } else if (storeByClassName.containsKey(ref.getRefValue())) {
                ref.placeObjectInField(getBeanByClass(ref.getRefValue()));
            } else if (canChangeReferOfSuperClass(ref)) {
                ref.placeObjectInField(getBeanByClass(ref.getRefValue()));
            } else {
                throw new InvalidConfigurationException("Can't find bean " + ref.getRefValue());
            }
        }
        refSave.clear();
    }

    private boolean canChangeReferOfSuperClass(RefCreator ref) throws ClassNotFoundException, IllegalAccessException {
        Class<?> aClass = Class.forName(ref.getRefValue());

        for (Map.Entry entry : storeByClassName.entrySet()) {
            if (aClass.isInstance(entry.getValue())) {
                ref.setRefValue(entry.getValue().getClass().getName());
                return true;
            }
        }
        return false;
    }

    private void processAnnotation(Class<?> aClass, Object object) throws InvalidConfigurationException {
        Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Auto.class)) {
                if (field.getAnnotation(Auto.class).isRequired()) {
                    field.setAccessible(true);
                    refSave.add(new RefCreator(object, field, field.getType().getName()));
                }
            }
        }
    }


    private Field getField(Class<?> aClass, String fieldName) throws InvalidConfigurationException {
        try {
            return aClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            Class<?> superclass = aClass.getSuperclass();
            if (superclass != null)
                return (getField(superclass, fieldName));
            else
                throw new InvalidConfigurationException("Can't find field " + fieldName);
        }
    }

    private void validateBeans() throws MyValidationException, IllegalAccessException {
        for (Bean bean : beans) {
            Validator.validateInstance(bean.getObject(), bean.getBeanClass());
        }
    }


    public Object getBean(String beanId) {
        return (storeById.get(beanId));
    }

    public Object getBeanByClass(String beanClassName) {
        return (storeByClassName.get(beanClassName));
    }
}
