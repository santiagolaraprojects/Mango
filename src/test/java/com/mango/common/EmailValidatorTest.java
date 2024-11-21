package com.mango.common;

import com.mango.common.util.EmailValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EmailValidatorTest {
	@Test
	void testValidEmails() {
		// Valid email addresses
		assertTrue(EmailValidator.isValid("test@example.com"));
		assertTrue(EmailValidator.isValid("test.email@example.com"));
		assertTrue(EmailValidator.isValid("user_name123@example.co.uk"));
		assertTrue(EmailValidator.isValid("first.last@sub.domain.com"));
		assertTrue(EmailValidator.isValid("email+filter@domain.com"));
		assertTrue(EmailValidator.isValid("simple@email.com"));
		assertTrue(EmailValidator.isValid("longemailaddress@longdomain.com"));
	}

	@Test
	void testInvalidEmails() {
		Assertions.assertFalse(EmailValidator.isValid("plainaddress"));
		Assertions.assertFalse(EmailValidator.isValid("missingatsign.com"));
		Assertions.assertFalse(EmailValidator.isValid("missingdomain@.com"));
		Assertions.assertFalse(EmailValidator.isValid("@missingusername.com"));
		Assertions.assertFalse(EmailValidator.isValid("user@domain..com"));
		Assertions.assertFalse(EmailValidator.isValid("user@domain,com"));
		Assertions.assertFalse(EmailValidator.isValid("user@domain@com"));
		Assertions.assertFalse(EmailValidator.isValid("user@domain@com@"));
		Assertions.assertFalse(EmailValidator.isValid("user@domain@com"));
		Assertions.assertFalse(EmailValidator.isValid("user@domain..com"));
		Assertions.assertFalse(EmailValidator.isValid("user@domain#example.com"));
	}

	@Test
	void testNullEmail() {
		Assertions.assertFalse(EmailValidator.isValid(null));
	}

	@Test
	void testEmptyEmail() {
		Assertions.assertFalse(EmailValidator.isValid(""));
	}
}
