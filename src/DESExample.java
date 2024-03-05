import javax.crypto.*;
import javax.crypto.spec.*;

import java.util.Base64;
import java.util.Scanner;

public class DESExample {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.println("¿Qué acción deseas realizar?");
        System.out.println("1. Cifrar");
        System.out.println("2. Descifrar");
        int opcion = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        if (opcion == 1) {
            System.out.println("Introduce la clave para cifrar: (8 caracteres)");
            String clave = leerClave(scanner, 8);
            System.out.println("Introduce el texto a cifrar:");
            String texto = scanner.nextLine();
            String mensajeCifrado = cifrarTexto(clave, texto);
            System.out.println("Texto cifrado: " + mensajeCifrado);
        } else if (opcion == 2) {
            System.out.println("Introduce la clave para descifrar: (8 caracteres)");
            String clave = leerClave(scanner, 8);
            System.out.println("Introduce el texto cifrado:");
            String textoCifrado = scanner.nextLine();

            String mensajeDescifrado = descifrarTexto(clave, textoCifrado);
            if (mensajeDescifrado != null) {
                System.out.println("Texto descifrado: " + mensajeDescifrado);
            }
        } else {
            System.out.println("Opción no válida.");
        }

        scanner.close();
    }

    // Función para leer una cadena de exactamente n caracteres desde el teclado
    public static String leerClave(Scanner scanner, int longitud) {
        String input;
        do {
            input = scanner.nextLine();
            if (input.length() != longitud) {
                System.out.println("La clave debe tener exactamente " + longitud + " caracteres. Inténtalo de nuevo:");
            }
        } while (input.length() != longitud);
        return input;
    }

    // Función para cifrar un texto usando DES
    public static String cifrarTexto(String clave, String texto) throws Exception {
        Cipher cipher = Cipher.getInstance("DES");
        SecretKey secretKey = new SecretKeySpec(clave.getBytes(), "DES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] textoCifrado = cipher.doFinal(texto.getBytes());
        // Codificar los bytes cifrados a Base64
        String textoCifradoBase64 = Base64.getEncoder().encodeToString(textoCifrado);
        return textoCifradoBase64;
    }

    // Función para descifrar un texto cifrado usando DES
    public static String descifrarTexto(String clave, String textoCifradoBase64) throws Exception {
        Cipher cipher = Cipher.getInstance("DES");
        SecretKey secretKey = new SecretKeySpec(clave.getBytes(), "DES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        try {
            // Decodificar la cadena Base64 para obtener los bytes cifrados originales
            byte[] textoCifrado = Base64.getDecoder().decode(textoCifradoBase64);
            byte[] textoDescifrado = cipher.doFinal(textoCifrado);
            return new String(textoDescifrado);
        } catch (BadPaddingException e) {
            // Capturar la excepción si la clave es incorrecta
            System.out.println("Clave incorrecta. Por favor, inténtalo de nuevo.");
            return null;
        }
    }
}
