package com.mango.customer.application.service;

import java.util.Objects;
import java.util.Optional;

import com.mango.customer.infrastructure.adapter.out.UserEntity;
import org.springframework.stereotype.Service;

import com.mango.common.exception.UserNotFoundException;
import com.mango.common.util.EmailValidator;
import com.mango.customer.application.dto.UserDTO;
import com.mango.customer.application.port.in.IUpdateUserUseCase;
import com.mango.customer.application.port.out.IUserRepositoryPort;
import com.mango.customer.domain.User;

@Service
public class UpdateUserService implements IUpdateUserUseCase{
    private final IUserRepositoryPort userRepositoryPort;

    public UpdateUserService(IUserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    public UserDTO updateUser(Long userId, UserDTO newUser) {

        Optional<UserEntity> userOptional = userRepositoryPort.findById(userId);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(newUser.getId());
        }
		UserEntity userEntity = userOptional.get();
		User user = new User(userEntity.getId(), userEntity.getName(), userEntity.getLastName(), userEntity.getAddress(), userEntity.getCity(), userEntity.getEmail());
		user.update(newUser.getName(), newUser.getLastName(), newUser.getAddress(), newUser.getCity(), newUser.getEmail());

		userEntity.setName(newUser.getName());
		userEntity.setLastName(newUser.getLastName());
		userEntity.setAddress(newUser.getAddress());
		userEntity.setCity(newUser.getCity());
		userEntity.setEmail(newUser.getEmail());


        userRepositoryPort.save(userEntity);

        return new UserDTO(userEntity.getId(), userEntity.getName(), userEntity.getLastName(), userEntity.getAddress(), userEntity.getCity(), userEntity.getEmail());
    }
}
