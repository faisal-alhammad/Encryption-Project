import java.util.Scanner;

public class UserInterface {
    private final EncryptionManager manager = new EncryptionManager();
    private final Scanner sc = new Scanner(System.in);

    // Allowed algorithms for ENCRYPTION
    private static final String[] ENCRYPTION_ALGOS = {
        "monoalphabetic", "vigenere", "playfair", "keyedtransposition", "combinationcipher", "des", "exit"
    };
    // Allowed algorithms for DECRYPTION
    private static final String[] DECRYPTION_ALGOS = {
        "monoalphabetic", "vigenere", "des", "cryptoanalysis", "exit"
    };

    public void start() {
        System.out.println("=== Educational Cryptography Tool ===");
        System.out.println("Type 'exit' at any prompt to quit.\n");

        while (true) {
            String mode = promptMode();
            if (mode.equalsIgnoreCase("exit")) break;
            
            String algo;
            String input;
            String key = "";

            if (mode.equalsIgnoreCase("encrypt")) {
                algo = promptAlgorithm(ENCRYPTION_ALGOS, "Encryption");
                if (algo.equalsIgnoreCase("exit")) break;
                
                // DES uses a built-in key in this setup, others require user input
                if (!algo.equalsIgnoreCase("des")) {
                    key = promptKeyForAlgorithm(algo);
                    if (key.equalsIgnoreCase("exit")) break;
                }

                System.out.print("Enter plaintext: ");
                input = sc.nextLine();
                
                manager.setCurrentKey(key);
                manager.selectAlgorithm(algo.toLowerCase());
                String c = manager.encrypt(input);
                System.out.println("Ciphertext: " + c);
                
            } else if (mode.equalsIgnoreCase("decrypt")) {
                algo = promptAlgorithm(DECRYPTION_ALGOS, "Decryption");
                if (algo.equalsIgnoreCase("exit")) break;

                // Check for key prompt requirement
                if (algo.equalsIgnoreCase("des")) {
                    key = ""; // DES uses built-in key
                } else if (!algo.equalsIgnoreCase("cryptoanalysis")) {
                    key = promptKeyForAlgorithm(algo);
                    if (key.equalsIgnoreCase("exit")) break;
                }
                
                System.out.print("Enter ciphertext: ");
                input = sc.nextLine();

                manager.setCurrentKey(key);
                manager.selectAlgorithm(algo.toLowerCase());
                String p = manager.decrypt(input);
                System.out.println("Plaintext: " + p);
            }

            System.out.println("\nOperation complete. Returning to menu...\n");
        }

        System.out.println("Exiting program. Goodbye!");
    }

    private String promptMode() {
        String[] validModes = {"encrypt", "decrypt", "exit"};
        while (true) {
            System.out.print("Select mode (encrypt/decrypt/exit): ");
            String line = sc.nextLine().trim();
            if (isValid(line, validModes)) return line.toLowerCase();
            System.out.println("Input incorrect, please enter a valid input.");
        }
    }
    
    private String promptKeyForAlgorithm(String algo) {
        if (algo.equalsIgnoreCase("monoalphabetic")) {
            // Additive Cipher needs an integer shift
            while (true) {
                System.out.print("Enter Additive Cipher key (0-25 integer shift): ");
                String line = sc.nextLine().trim();
                if (line.equalsIgnoreCase("exit")) return "exit";
                if (line.matches("\\d+")) {
                    try {
                        int shift = Integer.parseInt(line);
                        if (shift >= 0 && shift <= 25) return line;
                    } catch (NumberFormatException ignored) {}
                }
                System.out.println("Invalid key. Please enter an integer between 0 and 25.");
            }
        } else {
            // Vigenere, Playfair, KeyedTransposition need a keyword (string)
            while (true) {
                System.out.print("Enter keyword (letters only): ");
                String key = sc.nextLine().trim();
                if (key.equalsIgnoreCase("exit")) return "exit";

                if (key.matches("[a-zA-Z]+")) {
                    return key;
                }
                System.out.println("Invalid key. Please use letters only (A–Z or a–z).");
            }
        }
    }


    private String promptAlgorithm(String[] validAlgos, String mode) {
        System.out.println("\nAvailable " + mode + " Algorithms:");
        
        // Custom printing logic for the two menus
        if (mode.equals("Encryption")) {
             System.out.println(" - Monoalphabetic (Additive Cipher)");
             System.out.println(" - Vigenere");
             System.out.println(" - Playfair");
             System.out.println(" - KeyedTransposition");
             System.out.println(" - CombinationCipher");
             System.out.println(" - DES");
        } else { // Decryption menu
             System.out.println(" - Monoalphabetic (Additive Cipher)");
             System.out.println(" - Vigenere");
             System.out.println(" - DES");
             System.out.println(" - Cryptoanalysis (Breaks Additive Cipher)");
        }
        
        while (true) {
            System.out.print("Enter algorithm name (or 'exit'): ");
            String line = sc.nextLine().trim();
            if (isValid(line, validAlgos)) return line.toLowerCase();

            System.out.println("Input incorrect, please enter a valid input from the list.");
        }
    }


    private boolean isValid(String input, String[] validList) {
        if (input == null) return false;
        for (String v : validList) {
            if (input.equalsIgnoreCase(v)) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        new UserInterface().start();
    }
}