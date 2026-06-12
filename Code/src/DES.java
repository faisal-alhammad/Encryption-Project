import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.util.Base64;
import java.io.UnsupportedEncodingException;
import java.security.spec.InvalidKeySpecException;
import java.security.InvalidKeyException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.NoSuchPaddingException;

public class DES extends Algorithm {
    // Note: The key is fixed to an 8-byte array as required by the Java DES Cipher implementation.
    private static final byte[] BUILT_IN_KEY = {
        (byte)0x01, (byte)0x23, (byte)0x45, (byte)0x67,
        (byte)0x89, (byte)0xAB, (byte)0xCD, (byte)0xEF
    };

    public DES(String key, Visualizer visualizer) {
        super(key, visualizer);
    }

    @Override
    public String encrypt(String text) {
        visualizer.clear();
        visualizer.addStep("DES (Data Encryption Standard) encryption started.");
        visualizer.addStep("Input text is converted to bytes and encrypted using the fixed internal key.");

        try {
            SecretKey secretKey = makeKey(BUILT_IN_KEY);
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding"); // Using ECB mode for simplicity
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] enc = cipher.doFinal(text.getBytes("UTF-8"));
            String result = Base64.getEncoder().encodeToString(enc);
            
            visualizer.addStep("Final Ciphertext (Base64): " + result);
            return result;
        } catch (UnsupportedEncodingException | InvalidKeyException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | InvalidKeySpecException | NoSuchAlgorithmException e) {
            visualizer.addStep("Error during encryption: " + e.getMessage());
            return "ENCRYPTION_ERROR";
        } catch (Exception e) {
            visualizer.addStep("General Error: " + e.getMessage());
            return "ENCRYPTION_ERROR";
        }
    }

    @Override
    public String decrypt(String text) {
        visualizer.clear();
        visualizer.addStep("DES decryption started (requires Base64 encoded input).");

        try {
            SecretKey secretKey = makeKey(BUILT_IN_KEY);
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            
            byte[] dec = cipher.doFinal(Base64.getDecoder().decode(text));
            String result = new String(dec, "UTF-8");
            
            visualizer.addStep("Base64 input decoded and decrypted to plaintext.");
            visualizer.addStep("Final Plaintext: " + result);
            return result;
        } catch (Exception e) {
            visualizer.addStep("Error during decryption. Ensure input is valid DES Base64 ciphertext.");
            visualizer.addStep("Error details: " + e.getMessage());
            return "DECRYPTION_ERROR";
        }
    }

    @Override
    public void showSteps() {
        visualizer.displaySteps();
    }

    private SecretKey makeKey(byte[] keyBytes) throws Exception {
        DESKeySpec spec = new DESKeySpec(keyBytes);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
        return factory.generateSecret(spec);
    }
}