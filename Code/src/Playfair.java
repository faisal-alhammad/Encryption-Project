import java.util.ArrayList;
import java.util.List;

public class Playfair extends Algorithm {
    private char[][] matrix = new char[5][5];

    public Playfair(String key, Visualizer visualizer) {
        super(key, visualizer);
        prepareMatrix(this.key.toUpperCase());
    }

    @Override
    public String encrypt(String text) {
        visualizer.clear();
        visualizer.addStep("Playfair Cipher uses a 5x5 matrix of letters generated from the keyword.");
        visualizer.addStep("Pairs of letters (digraphs) are substituted according to their positions in the matrix.");
        visualizer.addStep("Rules: same row → shift right; same column → shift down; rectangle → swap columns.");

        String prepared = preparePlaintext(text);
        List<String> digraphs = splitDigraphs(prepared);
        StringBuilder cipher = new StringBuilder();

        for (String dg : digraphs) {
            char a = dg.charAt(0), b = dg.charAt(1);
            int[] posA = findPosition(a);
            int[] posB = findPosition(b);

            if (posA[0] == posB[0]) {
                cipher.append(matrix[posA[0]][(posA[1] + 1) % 5]);
                cipher.append(matrix[posB[0]][(posB[1] + 1) % 5]);
                visualizer.addStep("Pair '" + dg + "' in same row → shift right");
            } else if (posA[1] == posB[1]) {
                cipher.append(matrix[(posA[0] + 1) % 5][posA[1]]);
                cipher.append(matrix[(posB[0] + 1) % 5][posB[1]]);
                visualizer.addStep("Pair '" + dg + "' in same column → shift down");
            } else {
                cipher.append(matrix[posA[0]][posB[1]]);
                cipher.append(matrix[posB[0]][posA[1]]);
                visualizer.addStep("Pair '" + dg + "' forms rectangle → swap columns");
            }
        }
        visualizer.addStep("Final Ciphertext: " + cipher);
        return cipher.toString();
    }

    @Override
    public String decrypt(String text) {
        visualizer.clear();
        visualizer.addStep("Playfair decryption applies the same rules in reverse.");
        List<String> digraphs = splitDigraphs(text);
        StringBuilder plain = new StringBuilder();

        for (String dg : digraphs) {
            char a = dg.charAt(0), b = dg.charAt(1);
            int[] posA = findPosition(a);
            int[] posB = findPosition(b);

            if (posA[0] == posB[0]) {
                plain.append(matrix[posA[0]][(posA[1] + 4) % 5]);
                plain.append(matrix[posB[0]][(posB[1] + 4) % 5]);
                visualizer.addStep("Pair '" + dg + "' same row → shift left");
            } else if (posA[1] == posB[1]) {
                plain.append(matrix[(posA[0] + 4) % 5][posA[1]]);
                plain.append(matrix[(posB[0] + 4) % 5][posB[1]]);
                visualizer.addStep("Pair '" + dg + "' same column → shift up");
            } else {
                plain.append(matrix[posA[0]][posB[1]]);
                plain.append(matrix[posB[0]][posA[1]]);
                visualizer.addStep("Pair '" + dg + "' rectangle → swap back columns");
            }
        }
        visualizer.addStep("Final Plaintext: " + plain);
        return plain.toString();
    }

    @Override
    public void showSteps() {
        visualizer.displaySteps();
    }

    // helper methods...
    private void prepareMatrix(String key) {
        boolean[] used = new boolean[26];
        key = key.replace('J', 'I');
        StringBuilder all = new StringBuilder();
        for (char c : key.toCharArray()) {
            if (Character.isLetter(c) && !used[c - 'A']) {
                used[c - 'A'] = true;
                all.append(c);
            }
        }
        for (char c = 'A'; c <= 'Z'; c++) {
            if (c == 'J') continue;
            if (!used[c - 'A']) {
                used[c - 'A'] = true;
                all.append(c);
            }
        }
        int idx = 0;
        for (int r = 0; r < 5; r++)
            for (int c = 0; c < 5; c++)
                matrix[r][c] = all.charAt(idx++);
    }

    private String preparePlaintext(String p) {
        p = p.toUpperCase().replaceAll("[^A-Z]", "").replace('J', 'I');
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < p.length(); i++) {
            char a = p.charAt(i);
            char b = (i + 1) < p.length() ? p.charAt(i + 1) : 'X';
            if (a == b) {
                sb.append(a).append('X');
            } else {
                sb.append(a).append(b);
                i++;
            }
        }
        if (sb.length() % 2 != 0) sb.append('X');
        return sb.toString();
    }

    private List<String> splitDigraphs(String s) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < s.length(); i += 2)
            list.add(s.substring(i, i + 2));
        return list;
    }

    private int[] findPosition(char ch) {
        for (int r = 0; r < 5; r++)
            for (int c = 0; c < 5; c++)
                if (matrix[r][c] == ch) return new int[]{r, c};
        return null;
    }
}
