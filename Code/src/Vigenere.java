public class Vigenere extends Algorithm {

    public Vigenere(String key, Visualizer visualizer) {
        super(key, visualizer);
    }

    @Override
    public String encrypt(String text) {
        visualizer.clear();
        visualizer.addStep("Vigenère Cipher uses a repeated keyword to shift each letter of the plaintext.");
        visualizer.addStep("Each key letter defines a shift value (A=0, B=1, ..., Z=25).");

        String plain = text.toUpperCase();
        String repeatedKey = generateRepeatedKey(plain, key.toUpperCase());
        StringBuilder cipher = new StringBuilder();

        for (int i = 0; i < plain.length(); i++) {
            char p = plain.charAt(i);
            char k = repeatedKey.charAt(i);
            if (Character.isLetter(p)) {
                int shift = (p - 'A' + (k - 'A')) % 26;
                char c = (char) ('A' + shift);
                cipher.append(c);
                visualizer.addStep("'" + p + "' shifted by " + (k - 'A') + " using '" + k + "' → '" + c + "'");
            } else {
                cipher.append(p);
            }
        }
        visualizer.addStep("Final Ciphertext: " + cipher);
        return cipher.toString();
    }

    @Override
    public String decrypt(String text) {
        visualizer.clear();
        visualizer.addStep("Vigenère decryption reverses the shifts using the same keyword.");

        String cipher = text.toUpperCase();
        String repeatedKey = generateRepeatedKey(cipher, key.toUpperCase());
        StringBuilder plain = new StringBuilder();

        for (int i = 0; i < cipher.length(); i++) {
            char c = cipher.charAt(i);
            char k = repeatedKey.charAt(i);
            if (Character.isLetter(c)) {
                int shift = (c - k + 26) % 26;
                char p = (char) ('A' + shift);
                plain.append(p);
                visualizer.addStep("'" + c + "' reversed by " + (k - 'A') + " using '" + k + "' → '" + p + "'");
            } else {
                plain.append(c);
            }
        }
        visualizer.addStep("Final Plaintext: " + plain);
        return plain.toString();
    }

    @Override
    public void showSteps() {
        visualizer.displaySteps();
    }

    private String generateRepeatedKey(String text, String key) {
        StringBuilder rep = new StringBuilder();
        int j = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (Character.isLetter(c)) {
                rep.append(key.charAt(j % key.length()));
                j++;
            } else rep.append(c);
        }
        return rep.toString();
    }
}
