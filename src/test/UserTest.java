package test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import banksy.User;


class UserTest {
	
	@Test
	void checkIfAlpha() {
		User test = new User("Dog","1234");//Create new user 
		
		assertFalse(test.isAlphabetical("1234"));//1234 is not purely alphabetical
		assertTrue(test.isAlphabetical("Dog"));//Dog is purely alphabetical
		//assertNull(test.isAlpha(""));
		
		return;
		
	}
	
	@Test
	void checkPassword() {
		String pwTrue = "Helloworld123";
		String pwFalse = "aaa";
		User test = new User();
		assertTrue(test.passwordCheck(pwTrue));
		assertFalse(test.passwordCheck(pwFalse));
	
		
		
	}

	

}
