package com.mango.application.service;

import com.mango.common.exception.UserAlreadyExistsException;
import com.mango.customer.application.dto.UserDTO;
import com.mango.customer.application.port.out.IUserRepositoryPort;
import com.mango.customer.application.service.SignInService;
import com.mango.customer.infrastructure.adapter.out.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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
class SignInServiceTest {

    @Mock
    private IUserRepositoryPort userRepository;

    @InjectMocks
    private SignInService signInService;

	private static UserEntity returnEntity;
	private static UserDTO userDTO;

	@BeforeAll
	static void setUp() {
		returnEntity = new UserEntity(1L, "Test Name", "Test LastName", "Test Address", "Test City", "test@example.com");
		userDTO =  new UserDTO(null, "Test Name", "Test LastName", "Test Address", "Test City", "test@example.com");
	}

	@Test
	void whenUserExists_thenShouldThrowException() {
		when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(returnEntity));
		assertThrows(UserAlreadyExistsException.class, () -> signInService.signIn(userDTO));

		verify(userRepository, times(1)).findByEmail(any(String.class));
		verify(userRepository, times(0)).save(any(UserEntity.class));
	}

	@Test
	void whenEmailIsInvalid_thenShouldThrowException() {
		when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());
		userDTO.setEmail("invalid");
		assertThrows(IllegalArgumentException.class, () -> signInService.signIn(userDTO));

		verify(userRepository, times(1)).findByEmail(any(String.class));
		verify(userRepository, times(0)).save(any(UserEntity.class));

	}

	@Test
	void whenSuccess_thenShouldAccessTwiceToRepository() {
		when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());
		when(userRepository.save(any(UserEntity.class))).thenReturn(returnEntity);

		UserDTO result = signInService.signIn(userDTO);
		Assertions.assertNotNull(result);

		Assertions.assertEquals(Long.valueOf(1L), result.getId());
		Assertions.assertEquals("Test Name", result.getName());
		Assertions.assertEquals("Test LastName", result.getLastName());
		Assertions.assertEquals("Test Address", result.getAddress());
		Assertions.assertEquals("Test City", result.getCity());
		Assertions.assertEquals("test@example.com", result.getEmail());

		verify(userRepository, times(1)).findByEmail(any(String.class));
		verify(userRepository, times(1)).save(any(UserEntity.class));

	}
}

