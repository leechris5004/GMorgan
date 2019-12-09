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
	void test() {
		fail("Not yet implemented");
	}
	
	@Test
	void checkIfAlpha() {
		User test = new User("Dog","1234");
		assertTrue(test.nameCheck(test.firstName,test.lastName));
		return;
		
	}

}
