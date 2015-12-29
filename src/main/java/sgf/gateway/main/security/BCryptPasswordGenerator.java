package sgf.gateway.main.security;

import org.springframework.security.crypto.bcrypt.BCrypt;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BCryptPasswordGenerator {

    public static void main(String[] args) throws Exception {

        System.out.print("Enter Password to BCrypt: ");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

        String knownPassword = bufferRead.readLine();

        System.out.print("Enter Log Rounds [1 - 31]: ");

        int logRounds = Integer.valueOf(bufferRead.readLine());

        System.out.println("Generating Password Hash...");

        String hashedPassword = BCrypt.hashpw(knownPassword, BCrypt.gensalt(logRounds));

        System.out.println();
        System.out.println("Hashed Password: " + hashedPassword);
    }
}
