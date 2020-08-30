package my.test.reflection.cucumber;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MyKeyWord {
    public static final Set<String> STEP_WORD = new HashSet<String>(Arrays.asList(
            new String[]{"*", "Дано", "Если", "И", "Тогда"}
            ));
    public static final Set<String> SCENARIO_WORD = new HashSet<String>(Arrays.asList(
            new String[]{"Сценарий:"}
    ));

    public static boolean isStep(String step) {
        String[] words = step.trim().split(" ");

        if (STEP_WORD.contains(words[0]))
            return true;
        return false;
    }

    public static String getStepWithoutKeyWord(String step) {
        return step.trim().substring(step.indexOf(" ") + 1, step.length());
    }

    public static boolean isScenario(String step) {
        String[] words = step.trim().split(" ");

        if (SCENARIO_WORD.contains(words[0]))
            return true;
        return false;
    }

    public static String getScenarioName(String step) {
        return step.trim().substring(step.indexOf(" ") + 1, step.length());
    }
}
