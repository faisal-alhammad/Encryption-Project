public class EncryptionManager {
    private final Visualizer visualizer = new Visualizer();
    private Algorithm activeAlgorithm;
    private String currentKey = ""; // Store the key globally for selection

    public void setCurrentKey(String key) {
        this.currentKey = key;
    }

    public void selectAlgorithm(String name) {
        // Reinstantiate the algorithm with the current key
        switch (name.toLowerCase()) {
            case "monoalphabetic":
                activeAlgorithm = new Monoalphabetic(currentKey, visualizer);
                break;
            case "vigenere":
                activeAlgorithm = new Vigenere(currentKey, visualizer);
                break;
            case "playfair":
                activeAlgorithm = new Playfair(currentKey, visualizer);
                break;
            case "keyedtransposition":
                activeAlgorithm = new KeyedTransposition(currentKey, visualizer);
                break;
            case "des": // Added DES
                activeAlgorithm = new DES(currentKey, visualizer);
                break;
            case "combinationcipher":
                // FIX: CombinationCipher must initialize sub-ciphers with the current key
                activeAlgorithm = new CombinationCipher(
                    new Vigenere(currentKey, visualizer),
                    new Playfair(currentKey, visualizer), 
                    visualizer
                );
                break;
            case "cryptoanalysis":
                activeAlgorithm = new Cryptoanalysis(currentKey, visualizer);
                break;
            default:
                System.out.println("Unknown algorithm.");
                activeAlgorithm = null;
        }
    }

    public String encrypt(String text) {
    	if (activeAlgorithm == null) return "Error: No algorithm selected.";
    	
    	// Check for digits only for substitution/transposition ciphers
    	boolean hasNumbers = false;
    	for(char c : text.toCharArray()) {
    		if(Character.isDigit(c)) {
    			hasNumbers = true;
    			break;
    		}
    	}
    	
    	// Only non-byte ciphers (Substitution/Transposition) are sensitive to digits/spaces
    	if(hasNumbers && !(activeAlgorithm instanceof DES)) {
    		return activeAlgorithm.getClass().getSimpleName() + " algorithm cannot contain digits";
    	}
        
        String result = activeAlgorithm.encrypt(text);
        activeAlgorithm.showSteps();
    	
        return result;
    }

    public String decrypt(String text) {
        if (activeAlgorithm == null) return "Error: No algorithm selected.";
        String result = activeAlgorithm.decrypt(text);
        activeAlgorithm.showSteps();
        return result;
    }
}