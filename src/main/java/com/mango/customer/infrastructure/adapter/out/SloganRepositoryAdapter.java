package com.mango.customer.infrastructure.adapter.out;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import org.springframework.stereotype.Component;

import com.mango.customer.application.port.out.ISloganRepositoryPort;

@Component
public class SloganRepositoryAdapter implements ISloganRepositoryPort {

	private HashMap<Long, SloganEntity> slogans;
	private Long slogansId;

	public SloganRepositoryAdapter() {
		slogans = new HashMap<>();
		slogansId = 0L;
	}

    @Override
    public SloganEntity save(SloganEntity newSlogan) {
		slogansId++;
		newSlogan.setId(slogansId);
        slogans.put(slogansId, newSlogan);
		return newSlogan;
    }

    @Override
    public List<SloganEntity> findByUserId(Long userId) {
		List<SloganEntity> result = new ArrayList<>();
		for (SloganEntity slogan : slogans.values()) {
			if (slogan.getUser() != null && userId.equals(slogan.getUser().getId())) {
				result.add(slogan);
			}
		}

		return result;
    }

	@Override
	public int countByUserId(Long userId) {
		int result = 0;
		for (SloganEntity slogan : slogans.values()) {
			if (slogan.getUser() != null && userId.equals(slogan.getUser().getId())) {
				result++;
			}
		}

		return result;
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

