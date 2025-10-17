import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import java.util.Base64;
import java.util.Scanner;

public class CifradoAsimetrico {

    private static KeyPair keyPair;
    private static KeyPairGenerator key;
    private static PublicKey publicKey;
    private static PrivateKey privateKey;
    private static byte[] msgEncriptar;
    private static byte[] msgDesencriptar;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n--- MenU RSA ---");
            System.out.println("1. Generar par de claves");
            System.out.println("2. Cifrar mensaje");
            System.out.println("3. Descifrar mensaje");
            System.out.println("4. Salir");
            System.out.print("Eligir opción: ");
            int option = 0;

            try {
                option = sc.nextInt();
                if (option>4 || option<1){
                    System.out.println("\nOpcion incorrecta, 1-4");
                }
            }
            catch (Exception ex){
                System.out.println("\nOpcion incorrecta, solo enteros");
            }
            sc.nextLine();

            String msg;

            switch (option){
                case(1):
                    try {
                        key = KeyPairGenerator.getInstance("RSA");
                        key.initialize(2048);
                        keyPair = key.generateKeyPair();
                        publicKey = keyPair.getPublic();
                        privateKey = keyPair.getPrivate();
                        System.out.println("\nKeys Generadas");
                    }
                    catch (Exception ex){
                        System.out.println("\nKey no generada");
                    }
                    break;
                case(2):
                    if (publicKey == null){
                        System.out.println("\nGenera las claves primero.");
                    }
                    else{
                        System.out.println("Introduce el mensaje: ");
                        msg = sc.nextLine();
                        msgEncriptar = encriptar(msg.getBytes(), publicKey, key.getAlgorithm());
                        System.out.println("Texto encriptado:\n"+Base64.getEncoder().encodeToString(msgEncriptar));
                    }
                    break;
                case(3):
                    if (privateKey == null){
                        System.out.println("\nGenera las claves primero.");
                    }
                    else {
                        System.out.println("Introduce el mensaje: ");
                        msg = sc.nextLine();
                        msgDesencriptar = desencriptar(msgEncriptar, privateKey, key.getAlgorithm());
                        System.out.println("Texto desencriptado:\n" + new String(msgDesencriptar));
                    }
                    break;
                case(4):
                    System.out.println("Cerrando...");
                    exit = true;
                    break;
            }
        }
    }

    public static byte[] encriptar(byte[] inputBytes, PublicKey publicKey, String algorithm){
        try{
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher.doFinal(inputBytes);
        }
        catch (Exception ex){
            System.out.println("Error: "+ex);
        }
        return null;
    }

    public static byte[] desencriptar(byte[] inputBytes, PrivateKey privateKey, String algorithm){
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(inputBytes);
        }
        catch (Exception ex){
            System.out.println("Error: "+ex);
        }
        return null;
    }
}
