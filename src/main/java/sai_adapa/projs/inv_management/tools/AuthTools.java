package sai_adapa.projs.inv_management.tools;


import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import java.security.SecureRandom;
import java.util.Base64;

public class AuthTools {

    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe
    private static final Pbkdf2PasswordEncoder pbkdf2PasswordEncoder;

    static {
        pbkdf2PasswordEncoder = new Pbkdf2PasswordEncoder();
    }

    public static String encodePassword(String password) {
        return pbkdf2PasswordEncoder.encode(password);
    }

    public static Boolean verifyPassword(String password, String passwordHash) {
        return pbkdf2PasswordEncoder.matches(password, passwordHash);
    }

    public static String generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

}
