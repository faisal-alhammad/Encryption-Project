import java.util.ArrayList;
import java.util.List;

public class Visualizer {
    private final List<String> steps = new ArrayList<>();

    public void addStep(String step) {
        steps.add(step);
    }

    public void displaySteps() {
        System.out.println("\n---- Step-by-Step Explanation ----");
        for (int i = 0; i < steps.size(); i++) {
            System.out.printf("%02d: %s%n", i + 1, steps.get(i));
        }
        System.out.println("---- End of Steps ----\n");
    }

    public void clear() {
        steps.clear();
    }
}
