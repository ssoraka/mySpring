package my.test.reflection.cucumber;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FeatureFile {
    private static final String EMPTY = "";
    private static final String COMMENT = "#";
    private String name;
    private List<String> steps = new ArrayList<>();
    private List<String> lines = new ArrayList<>();

    public FeatureFile(File file) throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        while (line != null) {
            lines.add(line);
            line = br.readLine();
        }
        parseFeatures();
    }

    @Override
    public String toString() {
        return "FeatureFile{" +
                "name='" + name + '\'' +
                ", steps=" + steps.toString() +
                '}';
    }

    private void parseFeatures() {
        for (String line : lines) {
            String str = line.trim();
            if (str.equals(EMPTY) || str.startsWith(COMMENT))
                continue ;
            if (MyKeyWord.isStep(str))
                steps.add(MyKeyWord.getStepWithoutKeyWord(str));
            else if (name == null && MyKeyWord.isScenario(str))
                name = MyKeyWord.getScenarioName(str);
        }
    }

    public String getName() {
        return name;
    }

    public List<String> getSteps() {
        return steps;
    }
}
