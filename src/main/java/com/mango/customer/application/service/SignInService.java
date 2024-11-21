package com.mango.customer.application.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mango.common.util.EmailValidator;
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

        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User already registered");
        }

		User user = new User(userDTO.getId(), userDTO.getName(), userDTO.getLastName(), userDTO.getAddress(), userDTO.getCity(), userDTO.getEmail());

		UserEntity userEntity = new UserEntity(user.getId(), user.getName(), user.getLastName(), user.getAddress(), user.getCity(), user.getEmail());

        UserEntity newUser = userRepositoryPort.save(userEntity);
        return new UserDTO(newUser.getId(), newUser.getName(), newUser.getLastName(), newUser.getAddress(), newUser.getCity(), newUser.getEmail());
    }
}
