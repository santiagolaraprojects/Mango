package com.mango.customer.application.service;

import com.mango.common.exception.UserNotFoundException;
import com.mango.customer.application.dto.UserDTO;
import com.mango.customer.application.port.in.IQueryUserDataUseCase;
import com.mango.customer.application.port.out.IUserRepositoryPort;
import com.mango.customer.infrastructure.adapter.out.UserEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
			throw new UserNotFoundException(0L);
		}
		List<UserEntity> userList = users.get();
		List<UserDTO> userDTOList = new ArrayList<>();
		for(UserEntity user : userList) {
			userDTOList.add(new UserDTO(user.getId(), user.getName(), user.getLastName(), user.getAddress(), user.getCity(), user.getEmail()));
		}
		return userDTOList;
	}

	@Override
	public UserDTO getUser(Long userId) {
		Optional<UserEntity> user = repository.findById(userId);
		if(user.isEmpty()) {
			throw new UserNotFoundException(userId);
		}
		UserEntity userEntity = user.get();
		return new UserDTO(userEntity.getId(), userEntity.getName(), userEntity.getLastName(), userEntity.getAddress(), userEntity.getCity(), userEntity.getEmail());
	}
}
