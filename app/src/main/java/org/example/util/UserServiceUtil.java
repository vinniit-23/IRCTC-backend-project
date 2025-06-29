package org.example.util;
import org.mindrot.jbcrypt.BCrypt;


public class UserServiceUtil {
    public static String hashedPassword(String plainPassword){
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }
    public static String checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword) ? "Valid Password" : "Invalid Password";
    }
}
