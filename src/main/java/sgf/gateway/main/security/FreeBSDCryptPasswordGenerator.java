package sgf.gateway.main.security;

import sgf.gateway.utils.security.FreeBSDCrypt;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class FreeBSDCryptPasswordGenerator {

    public static void main(String[] args) throws Exception {

        System.out.print("Enter Password to BCrypt: ");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

        String knownPassword = bufferRead.readLine();

        String hashedPassword = FreeBSDCrypt.crypt(knownPassword);

        System.out.println();
        System.out.println("Hashed Password: " + hashedPassword);
    }
}
