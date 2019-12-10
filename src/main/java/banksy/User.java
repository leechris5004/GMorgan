package banksy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

public class User {

	private String firstName;
	private String lastName;

	public User() {

	}

	public User generateUser(){
		//Return's a user object with certain constraints to populate a table.
		//Generating a first name and last name

		User genUser = new User(); //Simply creates a new User Object
		String alpha = ["ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmopqrstuvwxyz"];


		return genUser;
	}

	public User(String name1, String name2) {
		this.firstName = name1;
		this.lastName = name2;
	}

	public boolean createUser() {
		return false;
	}

	public boolean isAlphabetical(String input) {//Simply checks if a String is Alphabetical
		String alphaRegex = "^[a-zA-Z]+$";
		Pattern pat = Pattern.compile(alphaRegex);
		if (input == null)
			return false;
		return pat.matcher(input).matches();
	}

	public boolean nameCheck(String fname, String lname) {//Simply checks if the firstname and last name strings are
		// Must be alphabetic, no numbers, no special characters
		if (isAlphabetical(fname)) {
			this.firstName = fname;
		} else {
			return false;
		}
		if (isAlphabetical(lname)) {
			this.lastName = lname;
		} else {
			return false;
		}
		return true;
	}


	public boolean passwordCheck(String passwordCheck) {
		// password must have an uppercase, a lowercase, a number and a symbol.
		String passwordRegex = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)";
		String passwordRegexTwo = "((?=.*[a-z])(?=.*d)(?=.*[@#$%])(?=.*[A-Z]).{6,16})";


		Pattern pat = Pattern.compile(passwordRegexTwo);
		if (passwordCheck == null)
			return false;
		return pat.matcher(passwordCheck).matches();
	}

	public boolean passCheck(String passwordCheck){

		//Regex Strings for password conditions
		String oneDigit = "(?=.*[0-9])";//At least oneDigit
		String lowerCase =  "(?=.*[a-z])";//At least oneLowerDigit
		String upperCase = "(?=.*[A-Z])";//at least one upperCase
		String noSpace = "(?=\\S+$)";//at least noSpace


		//Matching patterns for password validation
		Pattern oneDigitPat = Pattern.compile(oneDigit);
		Pattern lowerCasePat = Pattern.compile(lowerCase);
		Pattern upperCasePat = Pattern.compile(upperCase);
		Pattern noSpacePat = Pattern.compile(noSpace);


		//If Statements for regex
		if (passwordCheck == null){
			return false;
		}
		if(passwordCheck.length() < 8){
			return false;
		}

		if(!oneDigitPat.matcher(passwordCheck).matches()){
			return false;
		}
		if(!lowerCasePat.matcher(passwordCheck).matches()){
			return false;
		}
		if(!upperCasePat.matcher(passwordCheck).matches()){
			return false;
		}
		if(!noSpacePat.matcher(passwordCheck).matches()){
			return false;
		}



		return true;


	}

	public byte[] hashit(String plaintext) {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] encodedhash = digest.digest(plaintext.getBytes(StandardCharsets.UTF_8));

		return encodedhash;

	}

	public boolean emailCheck(String email) {
		// regex that i found on google.
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
				+ "A-Z]{2,7}$";

		Pattern pat = Pattern.compile(emailRegex);
		if (email == null)
			return false;
		return pat.matcher(email).matches();
	}

	public boolean ssnCheck(String ssn) {
		//Expect number 9 digits

		String lengthregex = "^(\\d{9})$";

		Pattern pat = Pattern.compile(lengthregex);
		if (ssn == null)
			return false;
		return pat.matcher(ssn).matches();
	}

	public boolean checksPassed() {
		return false;
	}
}
