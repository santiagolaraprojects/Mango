package com.mango.domain;

import com.mango.customer.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserTest {

	private User user;

	@BeforeEach
	public void setUp() {
		user = new User(1L, "Initial Name", "Initial LastName", "Initial Address", "Initial City", "initial@example.com");
	}

	@Test
	public void testUpdate_SuccessfulUpdate() {
		user.update("Updated Name", "Updated LastName", "Updated Address", "Updated City", "updated@example.com");

		Assertions.assertEquals("Updated Name", user.getName());
		Assertions.assertEquals("Updated LastName", user.getLastName());
		Assertions.assertEquals("Updated Address", user.getAddress());
		Assertions.assertEquals("Updated City", user.getCity());
		Assertions.assertEquals("updated@example.com", user.getEmail());
	}


	@Test
	public void testUpdate_InvalidEmailFormat() {
		assertThrows(IllegalArgumentException.class, () -> {
			user.update("New Name", "New LastName", "New Address", "New City", "invalid-email");
		});
	}

}
