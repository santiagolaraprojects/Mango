package com.mango.customer.infrastructure.adapter.out;

import java.util.List;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.mango.customer.application.port.out.ISloganRepositoryPort;

@Component
public class SloganRepositoryAdapter implements ISloganRepositoryPort {

    @Override
    public SloganEntity save(SloganEntity newSlogan) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public List<SloganEntity> findByUserId(Long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByUserId'");
    }

}

/* EJEMPLO DE IMPLEMENTACION DE REPOSITORIO

public class SloganRepositoryAdapter implements ISloganRepositoryPort {

	private final SloganJpaRepository sloganJpaRepository;

	public JPASloganRepository(SloganJpaRepository sloganJpaRepository) {
		this.sloganJpaRepository = sloganJpaRepository;
	}

	@Override
	public SloganEntity save(SloganEntity newSlogan) {
		return sloganJpaRepository.save(newSlogan);
	}

	@Override
	public List<SloganEntity> findByUserId(Long userId) {
		return sloganJpaRepository.findByUserId(userId);
	}
}
*/

