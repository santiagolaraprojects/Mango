package com.mango.application.service;


import com.mango.common.exception.UserNotFoundException;
import com.mango.customer.application.dto.UserDTO;
import com.mango.customer.application.port.out.IUserRepositoryPort;
import com.mango.customer.application.service.UpdateUserService;
import com.mango.customer.infrastructure.adapter.out.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateUserServiceTest {

    @Mock
    private IUserRepositoryPort userRepository;

    @InjectMocks
    private UpdateUserService updateUserservice;

	@Test
	void whenUserDoesNotExist_thenThrowException() {
		when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());
		Assertions.assertThrows(UserNotFoundException.class, () -> updateUserservice.updateUser(1L, new UserDTO(1L, "Test Name", "Test LastName", "Test Address", "Test City", "test@example.com")));

		verify(userRepository, times(1)).findById(any(Long.class));
		verify(userRepository, times(0)).save(any(UserEntity.class));

	}

	@Test
	void whenEmailIsInvalid_thenShouldThrowException() {
		UserDTO userDTO = new UserDTO(null, "Test Name", "Test LastName", "Test Address", "Test City", "test");
		when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(new UserEntity(1L, "Test Name", "Test LastName", "Test Address", "Test City", "test@example.com")));
		assertThrows(IllegalArgumentException.class, () -> updateUserservice.updateUser(1L, new UserDTO(1L, "Test Name", "Test LastName", "Test Address", "Test City", "test")));

		verify(userRepository, times(1)).findById(any(Long.class));
		verify(userRepository, times(0)).save(any(UserEntity.class));

	}

	@Test
	void whenSuccess_thenUserIsUpdated() {
		when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(new UserEntity(1L, "Test Name", "Test LastName", "Test Address", "Test City", "test@example.com")));
		when(userRepository.save(any())).thenReturn(new UserEntity(1L, "Test Name", "Test LastName", "Test Address", "Test City", "test@example.com"));
		UserDTO userDTO = new UserDTO(null, "Test Name", "Test LastName", "Test Address", "Test City", "test@example.com");

		UserDTO result = updateUserservice.updateUser(1L, userDTO);
		Assertions.assertNotNull(result);

		Assertions.assertEquals(Long.valueOf(1L), result.getId());
		Assertions.assertEquals("Test Name", result.getName());
		Assertions.assertEquals("Test LastName", result.getLastName());
		Assertions.assertEquals("Test Address", result.getAddress());
		Assertions.assertEquals("Test City", result.getCity());
		Assertions.assertEquals("test@example.com", result.getEmail());


		verify(userRepository, times(1)).findById(any(Long.class));
		verify(userRepository, times(1)).save(any(UserEntity.class));

	}
}

