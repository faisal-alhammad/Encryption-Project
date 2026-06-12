import java.util.*;

// NOTE: Class name remains Cryptoanalysis as requested.
public class Cryptoanalysis extends Algorithm {
    
    // The most frequent English letter (position 4, 'E')
    private static final char ENGLISH_MOST_FREQUENT = 'E';

    public Cryptoanalysis(String key, Visualizer visualizer) {
        super(key, visualizer);
    }

    @Override
    public String encrypt(String text) {
        // Cryptoanalysis is used for decryption/breaking, not encryption
        return "Cryptoanalysis cannot be used for encryption.";
    }

    @Override
    public String decrypt(String text) {
        visualizer.clear();
        visualizer.addStep("Cryptoanalysis: Attempting to break the shift cipher using frequency analysis.");
        
        String cleanText = text.toUpperCase().replaceAll("[^A-Z]", ""); 
        
        if (cleanText.isEmpty()) {
            visualizer.addStep("No valid letters found for analysis.");
            return "No valid text for analysis.";
        }

        // Step 1: Count letter frequencies
        int[] freq = new int[26];
        for (char c : cleanText.toCharArray()) {
            freq[c - 'A']++;
        }

        // Step 2: Find most frequent letter in the ciphertext
        int maxIndex = 0;
        for (int i = 1; i < 26; i++) {
            if (freq[i] > freq[maxIndex]) {
                maxIndex = i;
            }
        }

        char mostFrequent = (char) ('A' + maxIndex);

        // Step 3: Calculate assumed shift (K = C - P)
        // Shift (K) = Most Frequent Letter Index (C) - 'E' Index (P)
        // Since 'E' = 4, and 'A' = 0
        int cipherIndex = mostFrequent - 'A';
        int englishIndex = ENGLISH_MOST_FREQUENT - 'A';
        
        // This is the shift used to *encrypt* (which we need to reverse)
        int shiftToE = (cipherIndex - englishIndex + 26) % 26; 

        visualizer.addStep("Calculated Frequencies (Max: " + mostFrequent + ")");
        visualizer.addStep("Assuming '" + mostFrequent + "' corresponds to English '" + ENGLISH_MOST_FREQUENT + "'.");
        visualizer.addStep("Calculated encryption shift (K): " + shiftToE);


        // Step 4: Decrypt by reversing the shift
        StringBuilder decrypted = new StringBuilder();
        for (char c : cleanText.toCharArray()) {
            // Decryption: P = C - K
            int decryptedCharValue = (c - 'A' - shiftToE + 26) % 26;
            char p = (char) ('A' + decryptedCharValue);
            decrypted.append(p);
        }

        visualizer.addStep("Final Decrypted Plaintext: " + decrypted);
        return decrypted.toString();
    }

    @Override
    public void showSteps() {
        visualizer.displaySteps();
    }
}