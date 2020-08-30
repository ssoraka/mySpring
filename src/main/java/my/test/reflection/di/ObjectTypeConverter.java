package my.test.reflection.di;

public class ObjectTypeConverter {
    public static Object convert(String typeName, String value) throws InvalidConfigurationException {
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
