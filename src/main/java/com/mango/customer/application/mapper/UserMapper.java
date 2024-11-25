package com.mango.customer.application.mapper;

import com.mango.customer.application.dto.UserDTO;
import com.mango.customer.domain.User;
import com.mango.customer.infrastructure.adapter.out.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

	public static User toDomain(UserDTO userDTO) {
		if (userDTO == null) {
			return null;
		}
		return new User(
			userDTO.getId(),
			userDTO.getName(),
			userDTO.getLastName(),
			userDTO.getAddress(),
			userDTO.getCity(),
			userDTO.getEmail()
		);
	}

	public static List<UserDTO> toDTOList(List<UserEntity> userEntities) {
		return userEntities.stream()
			.map(UserMapper::toDTO)
			.collect(Collectors.toList());
	}

	public static UserEntity toEntity(User user) {
		if (user == null) {
			return null;
		}
		return new UserEntity(
			user.getId(),
			user.getName(),
			user.getLastName(),
			user.getAddress(),
			user.getCity(),
			user.getEmail()
		);
	}

	public static User toDomain(UserEntity userEntity) {
		if (userEntity == null) {
			return null;
		}
		return new User(
			userEntity.getId(),
			userEntity.getName(),
			userEntity.getLastName(),
			userEntity.getAddress(),
			userEntity.getCity(),
			userEntity.getEmail()
		);
	}

	public static UserDTO toDTO(UserEntity userEntity) {
		if (userEntity == null) {
			return null;
		}
		return new UserDTO(
			userEntity.getId(),
			userEntity.getName(),
			userEntity.getLastName(),
			userEntity.getAddress(),
			userEntity.getCity(),
			userEntity.getEmail()
		);
	}
}
