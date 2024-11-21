package com.mango.infrastructure.controllers;

import com.mango.common.exception.UserNotFoundException;
import com.mango.customer.application.dto.SloganDTO;
import com.mango.customer.application.dto.UserDTO;
import com.mango.customer.application.port.in.IQueryUserDataUseCase;
import com.mango.customer.application.port.in.IShareSloganUseCase;
import com.mango.customer.application.port.in.ISignInUseCase;
import com.mango.customer.application.port.in.IUpdateUserUseCase;
import com.mango.customer.infrastructure.adapter.in.UserController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

	@Mock
	private ISignInUseCase signInUseCase;

	@Mock
	private IUpdateUserUseCase updateUserUseCase;

	@Mock
	private IShareSloganUseCase shareSloganUseCase;

	@Mock
	private IQueryUserDataUseCase queryUserDataUseCase;

	@InjectMocks
	private UserController userController;

	private UserDTO expectedResult;
	private SloganDTO expectedSloganDTO;

	@BeforeEach
	public void setUp() {
		expectedResult = new UserDTO(1L, "Test Name", "Test LastName", "Test Address", "Test City", "test@example.com");
		expectedSloganDTO = new SloganDTO(1L, "Test Slogan");
	}

	@Test
	void testSignIn_Success() {
		when(signInUseCase.signIn(any(UserDTO.class))).thenReturn(expectedResult);

		ResponseEntity<UserDTO> response = userController.signIn(expectedResult);

		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
		Assertions.assertEquals(expectedResult, response.getBody());
	}

	@Test
	void testSignIn_BadRequest() {
		when(signInUseCase.signIn(any(UserDTO.class))).thenThrow(IllegalArgumentException.class);

		ResponseEntity<UserDTO> response = userController.signIn(expectedResult);

		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	void testSignIn_InternalServerError() {
		when(signInUseCase.signIn(any(UserDTO.class))).thenThrow(RuntimeException.class);

		ResponseEntity<UserDTO> response = userController.signIn(expectedResult);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	void testUpdateUser_Success() {
		when(updateUserUseCase.updateUser(any(Long.class), any(UserDTO.class))).thenReturn(expectedResult);

		ResponseEntity<UserDTO> response = userController.updateUser(1L, expectedResult);

		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals(expectedResult, response.getBody());
	}

	@Test
	void testUpdateUser_NotFound() {
		when(updateUserUseCase.updateUser(any(Long.class), any(UserDTO.class))).thenThrow(UserNotFoundException.class);

		ResponseEntity<UserDTO> response = userController.updateUser(1L, expectedResult);

		Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	void testUpdateUser_BadRequest() {
		when(updateUserUseCase.updateUser(any(Long.class), any(UserDTO.class))).thenThrow(IllegalArgumentException.class);

		ResponseEntity<UserDTO> response = userController.updateUser(1L, expectedResult);

		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	void testUpdateUser_InternalServerError() {
		when(updateUserUseCase.updateUser(any(Long.class), any(UserDTO.class))).thenThrow(RuntimeException.class);

		ResponseEntity<UserDTO> response = userController.updateUser(1L, expectedResult);

		Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	void testCreateSlogan_Success() {
		when(shareSloganUseCase.createSlogan(any(Long.class), any(String.class))).thenReturn(expectedSloganDTO);

		ResponseEntity<SloganDTO> response = userController.createSlogan(1L, "Test Slogan");

		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
		Assertions.assertEquals(expectedSloganDTO, response.getBody());
	}

	@Test
	void testCreateSlogan_UserNotFound() {
		when(shareSloganUseCase.createSlogan(any(Long.class), any(String.class))).thenThrow(UserNotFoundException.class);

		ResponseEntity<SloganDTO> response = userController.createSlogan(1L, "Test Slogan");

		Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	void testCreateSlogan_BadRequest() {
		when(shareSloganUseCase.createSlogan(any(Long.class), any(String.class))).thenThrow(IllegalArgumentException.class);

		ResponseEntity<SloganDTO> response = userController.createSlogan(1L, "Test Slogan");

		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	void testCreateSlogan_InternalServerError() {
		when(shareSloganUseCase.createSlogan(any(Long.class), any(String.class))).thenThrow(RuntimeException.class);

		ResponseEntity<SloganDTO> response = userController.createSlogan(1L, "Test Slogan");

		Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}


	@Test
	void testGetUsers_Success() {
		List<UserDTO> mockUserList = Arrays.asList(expectedResult);
		when(queryUserDataUseCase.getAllUsers()).thenReturn(mockUserList);

		ResponseEntity<List<UserDTO>> response = userController.getUsers();

		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals(mockUserList, response.getBody());
	}

	@Test
	void testGetUsers_InternalServerError() {
		when(queryUserDataUseCase.getAllUsers()).thenThrow(RuntimeException.class);

		ResponseEntity<List<UserDTO>> response = userController.getUsers();

		Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	void testGetUser_Success() {
		when(queryUserDataUseCase.getUser(any(Long.class))).thenReturn(expectedResult);

		ResponseEntity<UserDTO> response = userController.getUser(1L);

		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals(expectedResult, response.getBody());
	}

	@Test
	void testGetUser_NotFound() {
		when(queryUserDataUseCase.getUser(any(Long.class))).thenThrow(UserNotFoundException.class);

		ResponseEntity<UserDTO> response = userController.getUser(1L);

		Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	void testGetUser_InternalServerError() {
		when(queryUserDataUseCase.getUser(any(Long.class))).thenThrow(RuntimeException.class);

		ResponseEntity<UserDTO> response = userController.getUser(1L);

		Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}
}
