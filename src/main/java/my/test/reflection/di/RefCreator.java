package my.test.reflection.di;

import java.lang.reflect.Field;

public class RefCreator {
    private Object object;
    private Field field;
    private Property property;
    private boolean isPrivateField;

    public RefCreator(Object object, Field field, Property property) {
        this.object = object;
        this.field = field;
        this.property = property;
        isPrivateField = !field.isAccessible();
    }

    public RefCreator(Object object, Field field, String refName) {
        this.object = object;
        this.field = field;
        this.property = new Property(refName, refName, ValueType.REF);
    }

    public boolean isRef() {
        if (property.getType() == ValueType.REF)
            return true;
        return false;
    }

    public boolean isVal() {
        if (property.getType() == ValueType.VALUE)
            return true;
        return false;
    }

    public String getRefValue() {
        return property.getValue();
    }

    public void setRefValue(String value) {
        property.setValue(value);
    }

    public void placeObjectInField(Object refObject) throws IllegalAccessException, InvalidConfigurationException {
        if (isPrivateField)
            field.setAccessible(true);
        switch (property.getType()) {
            case REF:
                field.set(object, refObject);
                break;
            case VALUE:
                field.set(object, convert(field.getType().getName(), (String)refObject));
                break;
            default:
                throw new InvalidConfigurationException("Type error " + property.getType().toString());
        }
        if (isPrivateField)
            field.setAccessible(false);
    }

    private Object convert(String typeName, String value) throws InvalidConfigurationException {
        switch (typeName) {
            case "int":
            case "Integer":
                return (Integer.parseInt(value));
            case "long":
            case "Long":
                return (Long.parseLong(value));
            case "bool":
            case "Boolean":
                return (Boolean.parseBoolean(value));
            case "float":
            case "Float":
                return (Float.parseFloat(value));
            case "double":
            case "Double":
                return (Double.parseDouble(value));
            case "short":
            case "Short":
                return (Short.parseShort(value));
            case "String":
                return (value);
            default:
                throw new InvalidConfigurationException("Unexpected type " + typeName);
        }
    }
}
