import at.favre.lib.crypto.bcrypt.BCrypt;

public class HashTest {
    public static void main(String[] args) {
        String password = "1234";
        String hashed = BCrypt.withDefaults().hashToString(12, password.toCharArray());
        System.out.println(hashed);
    }
}