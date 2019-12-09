package banksy;

public class User {

public String firstName;
public String lastName;

public boolean createUser(){
        return false;
}


public boolean isAlpha(String input) {
        if (input == null) return false;
        for (char c : input.toCharArray()) {
                if (!Character.isLetter(c)) {
                        return false;
                }
        }
        return true;
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

private boolean passwordCheck(){
        //must contain a capital letter, must be at least eight characters, must contain a number
        return false;
}

private boolean emailCheck(){
        //regex that i found on google.
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                            "[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                            "A-Z]{2,7}$";
        return false;
}

private boolean ssnCheck(){

        return false;
}

private boolean checksPassed(){
        return false;
}
}
