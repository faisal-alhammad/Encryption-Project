import java.util.InputMismatchException;

public class Monoalphabetic extends Algorithm {
    private int shift;

    public Monoalphabetic(String key, Visualizer visualizer) {
        super(key, visualizer);
        try {
            // The key is expected to be a number string (e.g., "5")
            this.shift = Integer.parseInt(key) % 26;
        } catch (NumberFormatException e) {
            System.err.println("Error: Additive Cipher requires an integer key. Defaulting to shift 3.");
            this.shift = 3; 
        }
    }

    @Override
    public String encrypt(String text) {
        visualizer.clear();
        visualizer.addStep("Additive Cipher (Shift) encryption: Each letter is shifted forward by " + shift + ".");

        StringBuilder cipher = new StringBuilder();
        String upperText = text.toUpperCase();

        for (char p : upperText.toCharArray()) {
            if (Character.isLetter(p)) {
                // E = (P + K) mod 26
                int encryptedCharValue = (p - 'A' + shift) % 26;
                char c = (char) ('A' + encryptedCharValue);
                cipher.append(c);
                visualizer.addStep("Plain '" + p + "' (+" + shift + ") -> Cipher '" + c + "'");
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
        visualizer.addStep("Additive Cipher (Shift) decryption: Each letter is shifted backward by " + shift + ".");

        StringBuilder plain = new StringBuilder();
        String upperText = text.toUpperCase();

        for (char c : upperText.toCharArray()) {
            if (Character.isLetter(c)) {
                // P = (C - K) mod 26
                int decryptedCharValue = (c - 'A' - shift + 26) % 26;
                char p = (char) ('A' + decryptedCharValue);
                plain.append(p);
                visualizer.addStep("Cipher '" + c + "' (-" + shift + ") -> Plain '" + p + "'");
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
}