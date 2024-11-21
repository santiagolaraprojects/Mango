package com.mango.application.service;

import com.mango.common.exception.UserNotFoundException;
import com.mango.customer.application.dto.SloganDTO;
import com.mango.customer.application.port.out.ISloganRepositoryPort;
import com.mango.customer.application.port.out.IUserRepositoryPort;
import com.mango.customer.application.service.ShareSloganService;
import com.mango.customer.infrastructure.adapter.out.SloganEntity;
import com.mango.customer.infrastructure.adapter.out.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShareSloganServiceTest {
    @Mock
    private ISloganRepositoryPort sloganRepository;

    @Mock
    private IUserRepositoryPort userRepository;

    @InjectMocks
    private ShareSloganService createSloganUseCase;

	@Test
	void whenUserDoesNotExist_thenThrowException() {
		when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());
		Assertions.assertThrows(UserNotFoundException.class, () -> createSloganUseCase.createSlogan(1L, "Test"));

		verify(userRepository, times(1)).findById(any(Long.class));
		verify(sloganRepository, times(0)).findByUserId(any(Long.class));
		verify(sloganRepository, times(0)).save(any(SloganEntity.class));
	}

	@Test
	void whenUserHasAlready3Slogans_thenThrowException() {
		when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(new UserEntity(1L, "Test Name", "Test LastName", "Test Address", "Test City", "test@example.com")));

		UserEntity ue = new UserEntity(1L, "Test Name", "Test LastName", "Test Address", "Test City", "test@example.com");
		List<SloganEntity> slogans = Arrays.asList(
			new SloganEntity(1L, ue,"Descripción del Slogan 1"),
			new SloganEntity(2L, ue, "Descripción del Slogan 2"),
			new SloganEntity(2L, ue, "Descripción del Slogan 2")
		);

		when(sloganRepository.findByUserId(any(Long.class))).thenReturn(slogans);
		Assertions.assertThrows(IllegalArgumentException.class, () -> createSloganUseCase.createSlogan(1L, "Test"));

		verify(userRepository, times(1)).findById(any(Long.class));
		verify(sloganRepository, times(1)).findByUserId(any(Long.class));
		verify(sloganRepository, times(0)).save(any(SloganEntity.class));
	}

	@Test
	void whenSuccess_thenSloganIsCreated() {
		when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(new UserEntity(1L, "Test Name", "Test LastName", "Test Address", "Test City", "test@example.com")));

		UserEntity ue = new UserEntity(1L, "Test Name", "Test LastName", "Test Address", "Test City", "test@example.com");
		List<SloganEntity> slogans = Arrays.asList(
			new SloganEntity(1L, ue,"Descripción del Slogan 1"),
			new SloganEntity(2L, ue, "Descripción del Slogan 2")
		);

		when(sloganRepository.findByUserId(any(Long.class))).thenReturn(slogans);
		when(sloganRepository.save(any(SloganEntity.class))).thenReturn(new SloganEntity(1L, ue, "Slogan"));

		SloganDTO result = createSloganUseCase.createSlogan(1L, "Test");

		Assertions.assertEquals(Long.valueOf(1L), result.getId());
		Assertions.assertEquals("Slogan", result.getSlogan());


		verify(userRepository, times(1)).findById(any(Long.class));
		verify(sloganRepository, times(1)).findByUserId(any(Long.class));
		verify(sloganRepository, times(1)).save(any(SloganEntity.class));
	}

}
