package backend;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class password {
    private String password;
    private String algorithm = "SHA-256";
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public password(String password){
        this.password = password;
    }

    public String hashPassword(){
        String temp = "1";
        try{
             temp = generateHash(password, algorithm);
        }
        catch (Exception e){
            e.printStackTrace();
        }
            return temp;  
    }

    private static String generateHash(String data, String algorithm) throws NoSuchAlgorithmException{
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        digest.reset();
        byte[] hash = digest.digest(data.getBytes());
        return bytesToStringHex(hash);
    }

    public static String bytesToStringHex(byte[] bytes){
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++){
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}