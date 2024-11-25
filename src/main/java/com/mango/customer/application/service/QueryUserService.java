package com.mango.customer.application.service;

import com.mango.common.exception.ResourceNotFoundException;
import com.mango.common.exception.UserNotFoundException;
import com.mango.customer.application.constants.ResponseMessages;
import com.mango.customer.application.dto.UserDTO;
import com.mango.customer.application.mapper.UserMapper;
import com.mango.customer.application.port.in.IQueryUserDataUseCase;
import com.mango.customer.application.port.out.IUserRepositoryPort;
import com.mango.customer.infrastructure.adapter.out.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QueryUserService implements IQueryUserDataUseCase{
	private final IUserRepositoryPort repository;

	public QueryUserService(IUserRepositoryPort repository) {
		this.repository = repository;
	}

	@Override
	public List<UserDTO> getAllUsers() {
		Optional<List<UserEntity>> users = repository.findAll();
		if(users.isEmpty()) {
			throw new ResourceNotFoundException(ResponseMessages.USERS);
		}

		return UserMapper.toDTOList(users.get());
	}

	@Override
	public UserDTO getUser(Long userId) {
		Optional<UserEntity> user = repository.findById(userId);
		if(user.isEmpty()) {
			throw new UserNotFoundException(userId);
		}

		return UserMapper.toDTO(user.get());
	}
}
