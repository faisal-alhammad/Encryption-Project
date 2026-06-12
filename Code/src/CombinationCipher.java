public class CombinationCipher extends Algorithm {
    private final Algorithm first;
    private final Algorithm second;

    // This constructor now correctly receives algorithms already initialized with the necessary key
    public CombinationCipher(Algorithm first, Algorithm second, Visualizer visualizer) {
        super("Combination", visualizer);
        this.first = first;
        this.second = second;
    }

    @Override
    public String encrypt(String text) {
        visualizer.clear();
        visualizer.addStep("Combination Cipher (Product Cipher) Encryption Started.");
        
        // Encryption: First Cipher -> Second Cipher
        visualizer.addStep("Step 1: Encrypting with " + first.getClass().getSimpleName() + "...");
        String mid = first.encrypt(text);
        
        visualizer.addStep("Intermediate Ciphertext (from Step 1): " + mid);
        
        visualizer.addStep("Step 2: Encrypting Intermediate Ciphertext with " + second.getClass().getSimpleName() + "...");
        String result = second.encrypt(mid);
        
        visualizer.addStep("Final Ciphertext: " + result);
        return result;
    }

    @Override
    public String decrypt(String text) {
        visualizer.clear();
        visualizer.addStep("Combination Cipher (Product Cipher) Decryption Started.");
        
        // Decryption: Second Cipher (Reverse) -> First Cipher (Reverse)
        visualizer.addStep("Step 1 (Reverse): Decrypting with " + second.getClass().getSimpleName() + " (reverses Step 2 encryption)...");
        String mid = second.decrypt(text);
        
        visualizer.addStep("Intermediate Plaintext (after Step 1): " + mid);
        
        visualizer.addStep("Step 2 (Reverse): Decrypting Intermediate Plaintext with " + first.getClass().getSimpleName() + " (reverses Step 1 encryption)...");
        String plain = first.decrypt(mid);
        
        visualizer.addStep("Final Plaintext: " + plain);
        return plain;
    }

    @Override
    public void showSteps() {
        visualizer.displaySteps();
    }
}