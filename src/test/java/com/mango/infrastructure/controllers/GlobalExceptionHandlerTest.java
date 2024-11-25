package com.mango.infrastructure.controllers;

import com.mango.common.exception.MaxSloganLimitExceededException;
import com.mango.common.exception.UserAlreadyExistsException;
import com.mango.customer.application.constants.ResponseMessages;
import com.mango.customer.application.dto.UserDTO;
import com.mango.customer.application.port.in.IShareSloganUseCase;
import com.mango.customer.application.port.in.ISignInUseCase;
import com.mango.customer.domain.ValidationMessages;
import com.mango.customer.infrastructure.adapter.out.UserEntity;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GlobalExceptionHandlerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IShareSloganUseCase shareSloganUseCase;

	@MockBean
	private ISignInUseCase signInUseCase;


	@Test
	void handleUserNotFoundException_shouldReturnNotFoundResponse() throws Exception {
		mockMvc.perform(get("/users/{userId}", 999L))
			.andExpect(status().isNotFound())
			.andExpect(content().string(ResponseMessages.USER_NOT_FOUND + "999"));
	}

	@Test
	void handleResourceNotFoundException_shouldReturnNotFoundResponse() throws Exception {
		mockMvc.perform(get("/users/"))
			.andExpect(status().isNotFound())
			.andExpect(content().string(ResponseMessages.RESOURCE_NOT_FOUND + ResponseMessages.USERS));
	}

	@Test
	void handleIllegalArgumentException_shouldReturnBadRequestResponse() throws Exception {
		mockMvc.perform(post("/users")
				.contentType("application/json")
				.content("{\"name\":\"Santi\", \"lastName\":\"Lara\", \"city\":\"New York\", \"email\":\"email\"}"))
			.andExpect(status().isBadRequest())
			.andExpect(content().string(containsString(ResponseMessages.VALIDATION_FIELDS_ERROR)))
			.andExpect(content().string(containsString("email")))
			.andExpect(content().string(not(containsString("name"))))
			.andExpect(content().string(not(containsString("lastName"))))
			.andExpect(content().string(not(containsString("city"))));
	}

	@Test
	void handleMaxSloganLimitExceededException_shouldReturnConflictResponse() throws Exception {
		doThrow(new MaxSloganLimitExceededException(ValidationMessages.USER_SLOGANS_EXCEEDED))
			.when(shareSloganUseCase).createSlogan(eq(1L), any(String.class));

		mockMvc.perform(post("/users/{userId}/slogans", 1L)
				.contentType("application/json")
				.content("{\"slogan\":\"Slogan Test\"}"))
			.andExpect(status().isConflict())
			.andExpect(content().string(containsString(ValidationMessages.USER_SLOGANS_EXCEEDED)));
	}

	@Test
	void handleGeneralException_shouldReturnInternalServerErrorResponse() throws Exception {
		doThrow(new RuntimeException())
			.when(shareSloganUseCase).createSlogan(eq(1L), any(String.class));


		mockMvc.perform(post("/users/{userId}/slogans", 1L)
				.contentType("application/json")
				.content("{\"slogan\":\"Slogan Test\"}"))
			.andExpect(status().isInternalServerError())
			.andExpect(content().string(containsString(ResponseMessages.INTERNAL_SERVER_ERROR)));
	}

	@Test
	void handleAlreadyExistingUser_shouldReturnConflictResponse() throws Exception {
		doThrow(new UserAlreadyExistsException(ValidationMessages.EXISTING_USER))
			.when(signInUseCase).signIn(any(UserDTO.class));

		mockMvc.perform(post("/users")
				.contentType("application/json")
				.content("{\"name\":\"Santi\", \"lastName\":\"Lara\", \"city\":\"New York\", \"email\":\"email@email.com\"}"))
			.andExpect(status().isConflict())
			.andExpect(content().string(containsString(ValidationMessages.EXISTING_USER)));
	}

	@Test
	void handleMethodArgumentNotValidException_shouldReturnBadRequestResponse() throws Exception {
		mockMvc.perform(post("/users")
				.contentType("application/json")
				.content("{\"name\":\"Santi\", \"lastName\":\"Lara\", \"city\":\"New York\", \"email\":\"email\"}"))
			.andExpect(status().isBadRequest())
			.andExpect(content().string(containsString(ResponseMessages.VALIDATION_FIELDS_ERROR)))
			.andExpect(content().string(containsString("email")))
			.andExpect(content().string(not(containsString("name"))))
			.andExpect(content().string(not(containsString("lastName"))))
			.andExpect(content().string(not(containsString("city"))));
	}


}
