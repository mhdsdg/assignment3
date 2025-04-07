import java.util.*;
import java.util.regex.*;
public class EmailValidator {
    public boolean isUsernameValid(String username) {
        if (username.length() <= 10 && username.length() >= 4 && username.matches("^[a-zA-Z][a-zA-Z-_.]*$")) {
            return true;
        }
        return false;
    }

    // Method to validate the domain
    public boolean isDomainValid(String domain) {
        // Check length of domain (excluding TLD)
        if (domain.length() < 3 || domain.length() > 7) {
            return false;
        }

        // Check if domain starts and ends with a letter
        if (!Character.isLetter(domain.charAt(0)) || !Character.isLetter(domain.charAt(domain.length() - 1))) {
            return false;
        }

        // Check if domain contains only letters, '-', and '.'
        if (!domain.matches("^[a-zA-Z.-]+$")) {
            return false;
        }

        // Check if special characters (- or .) appear only once
        if (domain.chars().filter(ch -> ch == '-').count() > 1 || domain.chars().filter(ch -> ch == '.').count() > 1) {
            return false;
        }

        return true;
    }

    // Method to validate the TLD
    public boolean isTLDValid(String tld) {
        // List of valid TLDs
        String[] validTLDs = {"org", "com", "net", "edu"};
        for (String validTLD : validTLDs) {
            if (tld.equals(validTLD)) {
                return true;
            }
        }
        return false;
    }

    // Method to validate the entire email using regex groups
    public boolean validateEmail(String email) {
        // Regex to capture user, domain, and TLD in groups
        String regex = "^(?<user>[a-zA-Z][a-zA-Z-_.]{3,10})@(?<domain>[a-zA-Z.-]{3,7})\\.(?<tld>org|com|net|edu)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            return false; // Email does not match the overall pattern
        }

        // Extract groups
        String user = matcher.group("user");
        String domain = matcher.group("domain");
        String tld = matcher.group("tld");

        // Validate user
        if (!isUsernameValid(user)) {
            return false;
        }

        // Validate domain
        if (!isDomainValid(domain)) {
            return false;
        }

        // Validate TLD
        if (!isTLDValid(tld)) {
            return false;
        }

        return true;
    }

    // Main method for testing
    public static void main(String[] args) {
        EmailValidator validator = new EmailValidator();
        Scanner sc = new Scanner(System.in);
        String email = sc.nextLine();
        if (validator.validateEmail(email)) {
            System.out.println("Email is valid.");
        } else {
            System.out.println("Email is invalid.");
        }
    }
}
