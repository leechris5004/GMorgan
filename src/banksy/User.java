package banksy;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

public class User {

public String firstName;
public String lastName;

public boolean createUser(){
        return false;
}


public boolean isAlpha(String input) {
        String alphaRegex = "/^[A-Za-z]+$/";
        Pattern pat = Pattern.compile(alphaRegex);
        if (input == null)
                return false;
        return pat.matcher(input).matches();
}

private boolean nameCheck(String fname, String lname){
        // Must be alphabetic, no numbers, no special characters
        if(isAlpha(fname)) {
                this.firstName = fname;
        }else{
                return false;
        }
        if(isAlpha(lname)) {
                this.lastName = lname;
        }else{
                return false;
        }
        return true;
}

private boolean passwordCheck(String passwordCheck){
        //password must have an uppercase, a lowercase, a number and a symbol.
        String passwordRegex =  "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)";

        Pattern pat = Pattern.compile(passwordRegex);
        if (passwordCheck == null)
                return false;
        return pat.matcher(passwordCheck).matches();
}

private byte[] hashit(String plaintext) {
        MessageDigest digest = null;
        try {
                digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
// TODO Auto-generated catch block
                e.printStackTrace();
        }
        byte[] encodedhash = digest.digest(
                plaintext.getBytes(StandardCharsets.UTF_8));

        return encodedhash;

}

private boolean emailCheck(String email){
        //regex that i found on google.
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                            "[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                            "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
                return false;
        return pat.matcher(email).matches();
}

private boolean ssnCheck(String ssn){

        String lengthregex = "^(\\d{9})$";

        Pattern pat = Pattern.compile(lengthregex);
        if (ssn == null)
                return false;
        return pat.matcher(ssn).matches();
}

private boolean checksPassed(){
        return false;
}
}
