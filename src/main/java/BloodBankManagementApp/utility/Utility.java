package BloodBankManagementApp.utility;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class Utility {

    /**
     * This method can be used to generate a string representing an account password
     * suitable for storing in a database. It will be an OpenBSD-style crypt(3) formatted
     * hash string of length=60
     * The bcrypt workload is specified in the above static variable, a value from 10 to 31.
     * A workload of 12 is a very reasonable safe default as of 2013.
     * This automatically handles secure 128-bit salt generation and storage within the hash.
     * @param password_plaintext The account's plaintext password as provided during account creation,
     *			     or when changing an account's password.
     * @return String - a string of length 60 that is the bcrypt hashed password in crypt(3) format.
     */
    public static String hashPassword(String password_plaintext) {
        //String salt = BCrypt.gensalt(workload);
        String salt = BCrypt.gensalt();
        String hashed_password = BCrypt.hashpw(password_plaintext, salt);

        return(hashed_password);
    }

    // Check if the password matches the hashed password
    /**
     * This method can be used to verify a computed hash from a plaintext (e.g. during a login
     * request) with that of a stored hash from a database. The password hash from the database
     * must be passed as the second variable.
     * @param password_plaintext The account's plaintext password, as provided during a login request
     * @param storedHashedPassword The account's stored password hash, retrieved from the authorization database
     * @return boolean - true if the password matches the password of the stored hash, false otherwise
     */
    public static boolean checkPassword(String password_plaintext, String storedHashedPassword) {
        boolean password_varified = false;
        if(storedHashedPassword == null || !storedHashedPassword.startsWith("$2a$")){
            throw new IllegalArgumentException("Invalid hashPassword provided for comparition");
        }
        // Check if the entered password matches the hashed password
        password_varified = BCrypt.checkpw(password_plaintext, storedHashedPassword);

        return password_varified;
    }
    // Hash a password for the first time

    // Check if the entered password matches the stored hash

    public static void main(String[]args){
        // Original password
        String password = "my_secret_password";

        // Hash the password
        String hashedPassword = hashPassword(password);
        System.out.println("Hashed password: " + hashedPassword);

        // Check if the password matches the hashed password
        if (checkPassword(password, hashedPassword)) {
            System.out.println("Password matches!");
        } else {
            System.out.println("Password does not match!");
        }

    }


    //=======================================================================================================
    //import org.mindrot.jbcrypt.BCrypt;
    // Hash the password
    /*public static String hashPassword(String password) {

        // Generate a salt and hash the password
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    public static boolean checkPassword(String password, String hashedPassword) {

        // Check if the entered password matches the hashed password
        // return BCrypt.checkpw(password, hashedPassword);

    }
    public static void main(String[]args){
        // Original password
        String password = "my_secret_password";

        // Hash the password
        String hashedPassword = hashPassword(password);
        System.out.println("Hashed password: " + hashedPassword);

        // Check if the password matches the hashed password
        if (checkPassword(password, hashedPassword)) {
            System.out.println("Password matches!");
        } else {
            System.out.println("Password does not match!");
        }

    }*/


    //================== AES Hashinh Algorithm ==============================================================================//import javax.crypto.KeyGenerator;
//import javax.crypto.Cipher;
//import javax.crypto.SecretKey;
//import javax.crypto.spec.SecretKeySpec;
//import java.util.Base64;
    //public class Utility {
    /*public static void main(String[] args) throws Exception {
        // Example plaintext
        String plaintext = "Hello, this is a secret message!";

        // Generate an AES key
        SecretKey secretKey = generateAESKey();

        // Encrypt the plaintext
        String encryptedText = encrypt(plaintext, secretKey);
        System.out.println("Encrypted text: " + encryptedText);

        // Decrypt the ciphertext
        String decryptedText = decrypt(encryptedText, secretKey);
        System.out.println("Decrypted text: " + decryptedText);
    }

    // Generate an AES key (128-bit)
    public static SecretKey generateAESKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128); // Key size in bits (128, 192, or 256)
        return keyGenerator.generateKey();
    }

    // Encrypt the plaintext
    public static String encrypt(String plaintext, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
        // Encode the bytes to Base64 for readability
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Decrypt the ciphertext
    public static String decrypt(String ciphertext, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        // Decode the Base64 encoded ciphertext back to bytes
        byte[] decodedBytes = Base64.getDecoder().decode(ciphertext);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }
}*/

    }
