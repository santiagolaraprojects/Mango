package com.mango.infrastructure.controllers.adapter.out;

import com.mango.customer.infrastructure.adapter.out.UserEntity;
import com.mango.customer.infrastructure.adapter.out.UserRepositoryAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryAdapterTest {

	private UserRepositoryAdapter userRepository;
	private UserEntity user;
	@BeforeEach
	void setUp() {
		userRepository = new UserRepositoryAdapter();
		user = new UserEntity(null, "Santi", "Lara", "Address", "City", "test@test.com");

	}

	@Test
	void save_shouldAssignIdToNewUser() {
		UserEntity savedUser = userRepository.save(user);

		assertNotNull(savedUser.getId());
		assertEquals(user.getName(), savedUser.getName());
		assertEquals(user.getLastName(), savedUser.getLastName());
	}

	@Test
	void save_shouldUpdateExistingUser() {
		UserEntity savedUser = userRepository.save(user);

		savedUser.setName("newUser");
		UserEntity updatedUser = userRepository.save(savedUser);

		assertEquals(savedUser.getId(), updatedUser.getId());
		assertEquals("newUser", updatedUser.getName());
	}

	@Test
	void findById_shouldReturnUserIfExists() {
		UserEntity savedUser = userRepository.save(user);

		Optional<UserEntity> retrievedUser = userRepository.findById(savedUser.getId());

		assertTrue(retrievedUser.isPresent());
		assertEquals(user.getName(), retrievedUser.get().getName());
	}

	@Test
	void findById_shouldReturnEmptyIfUserDoesNotExist() {
		Optional<UserEntity> retrievedUser = userRepository.findById(0L);

		assertFalse(retrievedUser.isPresent());
	}

	@Test
	void findByEmail_shouldReturnUserIfEmailExists() {
		userRepository.save(user);

		Optional<UserEntity> retrievedUser = userRepository.findByEmail(user.getEmail());

		assertTrue(retrievedUser.isPresent());
		assertEquals(user.getName(), retrievedUser.get().getName());
	}

	@Test
	void findByEmail_shouldReturnEmptyIfEmailDoesNotExist() {
		Optional<UserEntity> retrievedUser = userRepository.findByEmail(user.getEmail());

		assertFalse(retrievedUser.isPresent());
	}

	@Test
	void findAll_shouldReturnAllUsers() {
		userRepository.save(new UserEntity(null, "User1", "Last1", "Address1", "City1", "user1@test.com"));
		userRepository.save(new UserEntity(null, "User2", "Last2", "Address2", "City2", "user2@test.com"));

		Optional<List<UserEntity>> allUsers = userRepository.findAll();

		assertTrue(allUsers.isPresent());
		assertEquals(2, allUsers.get().size());
	}

	@Test
	void findAll_shouldReturnEmptyIfNoUsersExist() {
		Optional<List<UserEntity>> allUsers = userRepository.findAll();

		assertFalse(allUsers.isPresent());
	}
}

