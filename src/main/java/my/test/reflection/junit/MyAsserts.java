package my.test.reflection.junit;

public class MyAsserts {
    public static void AssertTrue(boolean value) throws MyAssertException {
        if (!value)
            throw new MyAssertException("Mismatch of values: was \"false\", expected \"true\"");
    }

    public static void AssertFalse(boolean value) throws MyAssertException {
        if (value)
            throw new MyAssertException("Mismatch of values: was \"true\", expected \"false\"");
    }

    public static <T> void AssertEquals(T value1, T value2) throws MyAssertException {
        if (value1 == null && value2 == null)
            return ;
        if (value1 == null)
            throw new MyAssertException("Mismatch of values: was \"null\", expected \"" + value2.toString() + "\"");
        else if (value2 == null)
            throw new MyAssertException("Mismatch of values: was \"" + value1.toString() + "\", expected \"null\"");
        else if (!value1.equals(value2))
            throw new MyAssertException("Mismatch of values: was \"" + value1.toString() + "\", expected \"" + value2.toString() + "\"");
    }
}
