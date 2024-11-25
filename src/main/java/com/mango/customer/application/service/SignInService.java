package com.mango.customer.application.service;

import java.util.Optional;

import com.mango.common.exception.UserAlreadyExistsException;
import com.mango.customer.application.mapper.UserMapper;
import com.mango.customer.domain.ValidationMessages;
import org.springframework.stereotype.Service;

import com.mango.customer.application.dto.UserDTO;
import com.mango.customer.application.port.in.ISignInUseCase;
import com.mango.customer.application.port.out.IUserRepositoryPort;
import com.mango.customer.domain.User;
import com.mango.customer.infrastructure.adapter.out.UserEntity;

@Service
public class SignInService implements ISignInUseCase{
    private final IUserRepositoryPort userRepositoryPort;

    public SignInService(IUserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

	@Override
    public UserDTO signIn(UserDTO userDTO) {

        Optional<UserEntity> userOptional = userRepositoryPort.findByEmail(userDTO.getEmail());

        if (userOptional.isPresent()) {
            throw new UserAlreadyExistsException(ValidationMessages.EXISTING_USER);
        }

		User user = UserMapper.toDomain(userDTO);

		UserEntity userEntity = UserMapper.toEntity(user);

        UserEntity newUser = userRepositoryPort.save(userEntity);
        return UserMapper.toDTO(newUser);
    }
}
