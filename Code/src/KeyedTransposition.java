import java.util.Arrays;
import java.util.Comparator;

public class KeyedTransposition extends Algorithm {
    public KeyedTransposition(String key, Visualizer visualizer) {
        super(key, visualizer);
    }

    @Override
    public String encrypt(String text) {
        visualizer.clear();
        visualizer.addStep("Keyed Transposition arranges text in rows under the keyword and reads columns in sorted key order.");
        text = text.replaceAll("\\s+", "");
        int cols = key.length();
        int rows = (int) Math.ceil((double) text.length() / cols);
        char[][] table = new char[rows][cols];
        int k = 0;
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                table[r][c] = (k < text.length()) ? text.charAt(k++) : 'X';

        Character[] keyChars = new Character[cols];
        for (int i = 0; i < cols; i++) keyChars[i] = key.charAt(i);
        Integer[] order = new Integer[cols];
        for (int i = 0; i < cols; i++) order[i] = i;
        Arrays.sort(order, Comparator.comparingInt(i -> keyChars[i]));

        StringBuilder cipher = new StringBuilder();
        for (int c : order)
            for (int r = 0; r < rows; r++)
                cipher.append(table[r][c]);

        visualizer.addStep("Columns read in alphabetical key order to produce ciphertext.");
        visualizer.addStep("Final Ciphertext: " + cipher);
        return cipher.toString();
    }

    @Override
    public String decrypt(String text) {
        visualizer.clear();
        visualizer.addStep("Decryption reconstructs the grid column by column using sorted key order.");

        int cols = key.length();
        int rows = (int) Math.ceil((double) text.length() / cols);
        char[][] table = new char[rows][cols];

        Character[] keyChars = new Character[cols];
        for (int i = 0; i < cols; i++) keyChars[i] = key.charAt(i);
        Integer[] order = new Integer[cols];
        for (int i = 0; i < cols; i++) order[i] = i;
        Arrays.sort(order, Comparator.comparingInt(i -> keyChars[i]));

        int k = 0;
        for (int c : order)
            for (int r = 0; r < rows; r++)
                table[r][c] = (k < text.length()) ? text.charAt(k++) : 'X';

        StringBuilder plain = new StringBuilder();
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                plain.append(table[r][c]);

        visualizer.addStep("Grid read row by row to recover plaintext.");
        visualizer.addStep("Final Plaintext: " + plain);
        return plain.toString();
    }

    @Override
    public void showSteps() {
        visualizer.displaySteps();
    }
}
