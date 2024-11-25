package com.mango.infrastructure.controllers;

import com.mango.customer.application.dto.SloganDTO;
import com.mango.customer.application.dto.UserDTO;
import com.mango.customer.application.port.in.IQueryUserDataUseCase;
import com.mango.customer.application.port.in.IShareSloganUseCase;
import com.mango.customer.application.port.in.ISignInUseCase;
import com.mango.customer.application.port.in.IUpdateUserUseCase;
import com.mango.customer.infrastructure.adapter.in.UserController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

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

	private UserDTO userDTO;
	private SloganDTO sloganDTO;

	@BeforeEach
	void setUp() {
		userDTO = new UserDTO(1L, "John", "Doe", "123 Street", "City", "email@example.com");
		sloganDTO = new SloganDTO(1L, "SL");
	}

	@Test
	void signIn_shouldReturnUserDTO_whenUserIsSuccessfullySignedIn() throws Exception {
		when(signInUseCase.signIn(userDTO)).thenReturn(userDTO);

		mockMvc.perform(post("/users")
				.contentType("application/json")
				.content("{\"name\":\"John\", \"lastName\":\"Doe\", \"address\":\"123 Street\", \"city\":\"City\", \"email\":\"email@example.com\"}"))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.email").value(userDTO.getEmail()));
	}

	@Test
	void updateUser_shouldReturnUpdatedUserDTO_whenUserIsSuccessfullyUpdated() throws Exception {
		when(updateUserUseCase.updateUser(1L, userDTO)).thenReturn(userDTO);

		mockMvc.perform(patch("/users/{userId}", 1L)
				.contentType("application/json")
				.content("{\"name\":\"John\", \"lastName\":\"Doe\", \"address\":\"123 Street\", \"city\":\"City\", \"email\":\"email@example.com\"}"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name").value(userDTO.getName()));
	}

	@Test
	void createSlogan_shouldReturnCreatedSloganDTO_whenSloganIsSuccessfullyCreated() throws Exception {
		when(shareSloganUseCase.createSlogan(1L, sloganDTO.getSlogan())).thenReturn(sloganDTO);

		mockMvc.perform(post("/users/{userId}/slogans", 1L)
				.contentType("application/json")
				.content("{\"slogan\":\"SL\"}"))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.slogan").value(sloganDTO.getSlogan()));
	}

	@Test
	void getUsers_shouldReturnUserList_whenUsersExist() throws Exception {
		when(queryUserDataUseCase.getAllUsers()).thenReturn(List.of(userDTO));

		mockMvc.perform(get("/users"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].email").value(userDTO.getEmail()));
	}

	@Test
	void getUser_shouldReturnUserDTO_whenUserExists() throws Exception {
		when(queryUserDataUseCase.getUser(1L)).thenReturn(userDTO);

		mockMvc.perform(get("/users/{userId}", 1L))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name").value(userDTO.getName()));
	}
}
