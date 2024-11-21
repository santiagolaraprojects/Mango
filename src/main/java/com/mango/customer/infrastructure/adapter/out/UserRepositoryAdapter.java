package com.mango.customer.infrastructure.adapter.out;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.mango.customer.application.port.out.IUserRepositoryPort;

@Repository
/*
* 	EL CONTENIDO NO COMENTADO DE ESTE DOCUMENTO CONTIENE EL CODIGO NECESARIO PARA COMPILAR YA QUE DE OTRA FORMA SERIA NECESARIO TENER CONFIGURADA UNA
* 	BASE DE DATOS PARA PODER ARRANCAR EL SERVIDOR.
*
* 	DE TODAS FORMAS HAY UNA IMPLEMENTACION PARA PODER REALIZAR PRUEBAS CON UNA BASE DE DATOS POSTGRESQL
*
*	AL SER UN ADAPTADOR PUEDE SER FACILMENTE REEMPLAZADO POR CUALQUIER OTRA BASE DE DATOS DE FORMA EFICIENTE SIN AFECTAR AL RESTO DEL SISTEMA
* */
public class UserRepositoryAdapter implements IUserRepositoryPort {

	public UserRepositoryAdapter() {}
	@Override
	public UserEntity save(UserEntity user) {
		return null;
	}

	@Override
    public Optional<UserEntity> findById(Long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
}

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByEmail'");
    }

	@Override
	public Optional<List<UserEntity>> findAll() {
		return Optional.empty();
	}
}

/* EJEMPLO DE IMPLEMENTACION DE REPOSITORIO PARA UNA BASE DE DATOS

import org.springframework.stereotype.Repository;

import com.mango.customer.application.port.out.IUserRepositoryPort;


@Component
public class UserRepositoryAdapter implements IUserRepositoryPort {

    private final UserJpaRepository userJpaRepository;

    public UserRepositoryAdapter(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public UserEntity save(UserEntity user) {
        return userJpaRepository.save(user);
    }

    @Override
    public Optional<UserEntity> findById(Long userId) {
        return userJpaRepository.findById(userId);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userJpaRepository.findByEmail(email);
    }

    @Override
    public Optional<List<UserEntity>> findAll() {
        List<UserEntity> users = userJpaRepository.findAll();
        return Optional.ofNullable(users.isEmpty() ? null : users);
    }
}
    */
