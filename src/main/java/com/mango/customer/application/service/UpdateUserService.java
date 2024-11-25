package com.mango.customer.application.service;

import java.util.Objects;
import java.util.Optional;

import com.mango.common.exception.UserAlreadyExistsException;
import com.mango.customer.application.mapper.UserMapper;
import com.mango.customer.domain.ValidationMessages;
import com.mango.customer.infrastructure.adapter.out.UserEntity;
import org.springframework.stereotype.Service;

import com.mango.common.exception.UserNotFoundException;
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
            throw new UserNotFoundException(userId);
        }

		Optional<UserEntity> existingEmailUser = userRepositoryPort.findByEmail(newUser.getEmail());

		//Comprobar que el mail que se quiera actualizar no este siendo usado ya por otro usuario.
		if (existingEmailUser.isPresent() && !Objects.equals(existingEmailUser.get().getId(), userId)) {
			/*En este caso decimos que el email es invalido, ya que al no contar con un sistema de login con contraseña
			queremos evitar exponer información como el email de otro usuario */
			throw new UserAlreadyExistsException(ValidationMessages.EMAIL_INVALID);
		}

		UserEntity userEntity = userOptional.get();

		User user = UserMapper.toDomain(userEntity);
		user.update(newUser.getName(), newUser.getLastName(), newUser.getAddress(), newUser.getCity(), newUser.getEmail());

		UserEntity updatedUserEntity = UserMapper.toEntity(user);

		userRepositoryPort.save(updatedUserEntity);

        return UserMapper.toDTO(updatedUserEntity);
    }
}
