package com.mango.application.service;

import com.mango.common.exception.MaxSloganLimitExceededException;
import com.mango.common.exception.UserNotFoundException;
import com.mango.customer.application.dto.SloganDTO;
import com.mango.customer.application.port.out.ISloganRepositoryPort;
import com.mango.customer.application.port.out.IUserRepositoryPort;
import com.mango.customer.application.service.ShareSloganService;
import com.mango.customer.domain.ValidationConstants;
import com.mango.customer.infrastructure.adapter.out.SloganEntity;
import com.mango.customer.infrastructure.adapter.out.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

	private static SloganDTO sloganDTO;
	private static UserEntity userEntity;

	@BeforeAll
	static void setUp() {
		sloganDTO = new SloganDTO(1L, "Test");
		userEntity = new UserEntity(1L, "Test Name", "Test LastName", "Test Address", "Test City", "test@example.com");
	}

	@Test
	void whenUserDoesNotExist_thenThrowException() {
		when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());
		Assertions.assertThrows(UserNotFoundException.class, () -> createSloganUseCase.createSlogan(1L, "Test"));

		verify(userRepository, times(1)).findById(any(Long.class));
		verify(sloganRepository, times(0)).countByUserId(any(Long.class));
		verify(sloganRepository, times(0)).save(any(SloganEntity.class));
	}

	@Test
	void whenUserHasAlready3Slogans_thenThrowException() {
		when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(userEntity));
		when(sloganRepository.countByUserId(any(Long.class))).thenReturn(ValidationConstants.MAX_USER_SLOGANS);
		Assertions.assertThrows(MaxSloganLimitExceededException.class, () -> createSloganUseCase.createSlogan(1L, "Test"));

		verify(userRepository, times(1)).findById(any(Long.class));
		verify(sloganRepository, times(1)).countByUserId(any(Long.class));
		verify(sloganRepository, times(0)).save(any(SloganEntity.class));
	}

	@Test
	void whenSuccess_thenSloganIsCreated() {
		when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(userEntity));
		when(sloganRepository.countByUserId(any(Long.class))).thenReturn(ValidationConstants.MAX_USER_SLOGANS - 1);
		when(sloganRepository.save(any(SloganEntity.class))).thenReturn(new SloganEntity(sloganDTO.getId(), userEntity, sloganDTO.getSlogan()));

		SloganDTO result = createSloganUseCase.createSlogan(sloganDTO.getId(), sloganDTO.getSlogan());

		Assertions.assertEquals(sloganDTO.getId(), result.getId());
		Assertions.assertEquals(sloganDTO.getSlogan(), result.getSlogan());


		verify(userRepository, times(1)).findById(any(Long.class));
		verify(sloganRepository, times(1)).countByUserId(any(Long.class));
		verify(sloganRepository, times(1)).save(any(SloganEntity.class));
	}

}
