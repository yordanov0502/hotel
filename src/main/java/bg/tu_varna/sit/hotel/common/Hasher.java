package bg.tu_varna.sit.hotel.common;

import org.apache.log4j.Logger;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {
    public static class SHA512{
        private static final Logger log = Logger.getLogger(Hasher.SHA512.class);
      public static String hash(String password)
      {
          try {
              //code source: https://www.geeksforgeeks.org/sha-512-hash-in-java/

              // getInstance() method is called with algorithm SHA-512
              MessageDigest md = MessageDigest.getInstance("SHA-512");

              // digest() method is called to calculate message digest of the input string(password) returned as array of byte
              byte[] messageDigest = md.digest(password.getBytes());

              // Convert byte array into signum representation
              BigInteger no = new BigInteger(1, messageDigest);

              // Convert message digest into hex value
              String hashedPassword = no.toString(16);

              // Add preceding 0s to make it 32 bit
              while (hashedPassword.length() < 32) {
                  hashedPassword = "0" + hashedPassword;
              }

              // return the hashed password
              return hashedPassword;
          }

          // For specifying wrong message digest algorithms
          catch (NoSuchAlgorithmException e) {
              log.error("SHA512 password hashing error: " + e.getMessage());
              throw new RuntimeException(e);
          }
      }
    }
}