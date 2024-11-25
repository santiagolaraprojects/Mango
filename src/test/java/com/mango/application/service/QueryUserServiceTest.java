package com.mango.application.service;

import com.mango.common.exception.ResourceNotFoundException;
import com.mango.common.exception.UserNotFoundException;
import com.mango.customer.application.dto.UserDTO;
import com.mango.customer.application.port.out.IUserRepositoryPort;
import com.mango.customer.application.service.QueryUserService;
import com.mango.customer.infrastructure.adapter.out.SloganEntity;
import com.mango.customer.infrastructure.adapter.out.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class QueryUserServiceTest {
	@Mock
	IUserRepositoryPort repository;

	@InjectMocks
	QueryUserService service;

	@Test
	void whenThereAreNoUsers_thenShouldThrowException() {
		Mockito.when(repository.findAll()).thenReturn(Optional.empty());
		assertThrows(ResourceNotFoundException.class, () -> service.getAllUsers());

		verify(repository, times(1)).findAll();
	}

	@Test
	void whenSuccess_ShouldReturnUsers() {
		UserEntity ue = new UserEntity(null, "Test Name", "Test LastName", "Test Address", "Test City", "test");
		List<UserEntity> users = List.of(
			ue
		);
		Mockito.when(repository.findAll()).thenReturn(Optional.of(users));

		List<UserDTO> result = service.getAllUsers();

		assertEquals(1, result.size());

		assertEquals("Test Name", result.get(0).getName());
		assertEquals("Test LastName", result.get(0).getLastName());
		assertEquals("Test Address", result.get(0).getAddress());
		assertEquals("Test City", result.get(0).getCity());

		verify(repository, times(1)).findAll();
	}

	@Test
	void whenUserWithIdDoesNotExist_thenShouldThrowException() {
		Mockito.when(repository.findById(any(Long.class))).thenReturn(Optional.empty());
		assertThrows(UserNotFoundException.class, () -> service.getUser(1L));

		verify(repository, times(1)).findById(any(Long.class));
	}

	@Test
	void whenUserWithIdExists_thenShouldReturnUser() {
		UserEntity ue = new UserEntity(null, "Test Name", "Test LastName", "Test Address", "Test City", "test");

		Mockito.when(repository.findById(any(Long.class))).thenReturn(Optional.of(ue));

		UserDTO result = service.getUser(1L);


		assertEquals("Test Name", result.getName());
		assertEquals("Test LastName", result.getLastName());
		assertEquals("Test Address", result.getAddress());
		assertEquals("Test City", result.getCity());

		verify(repository, times(1)).findById(any(Long.class));
	}
}
