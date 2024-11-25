package com.mango.customer.application.port.out;

import java.util.List;

import com.mango.customer.infrastructure.adapter.out.SloganEntity;

public interface ISloganRepositoryPort {
    SloganEntity save(SloganEntity newSlogan);

    List<SloganEntity> findByUserId(Long userId);

	int countByUserId(Long userId);
}
