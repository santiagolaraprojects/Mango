package com.mango.customer.application.port.out;

import java.util.Optional;


import java.util.List;
import com.mango.customer.infrastructure.adapter.out.UserEntity;

public interface IUserRepositoryPort {
    UserEntity save(UserEntity user);

    Optional<UserEntity> findById(Long userId);

    Optional<UserEntity> findByEmail(String email);

	Optional<List<UserEntity>> findAll();
}
