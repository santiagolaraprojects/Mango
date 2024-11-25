package com.mango.customer.application.service;

import java.util.Optional;

import com.mango.common.exception.MaxSloganLimitExceededException;
import com.mango.customer.domain.ValidationConstants;
import com.mango.customer.domain.ValidationMessages;
import com.mango.customer.infrastructure.adapter.out.UserEntity;
import org.springframework.stereotype.Service;

import com.mango.common.exception.UserNotFoundException;
import com.mango.customer.application.dto.SloganDTO;
import com.mango.customer.application.port.in.IShareSloganUseCase;
import com.mango.customer.application.port.out.ISloganRepositoryPort;
import com.mango.customer.application.port.out.IUserRepositoryPort;
import com.mango.customer.infrastructure.adapter.out.SloganEntity;

@Service
public class ShareSloganService implements IShareSloganUseCase{
    private final IUserRepositoryPort userRepositoryPort;
    private final ISloganRepositoryPort sloganRepositoryPort;

    public ShareSloganService(IUserRepositoryPort userRepositoryPort, ISloganRepositoryPort sloganRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
        this.sloganRepositoryPort = sloganRepositoryPort;
    }


    public SloganDTO createSlogan(Long userId, String sloganText) {

        Optional<UserEntity> userOptional = userRepositoryPort.findById(userId);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(userId);
        }

		CheckSloganAmountExceded(userId);

        SloganEntity newSlogan = new SloganEntity();
        newSlogan.setSlogan(sloganText);
        newSlogan.setUser(userOptional.get());

		SloganEntity result = sloganRepositoryPort.save(newSlogan);

        return new SloganDTO(result.getId(), result.getSlogan());
    }

	private void CheckSloganAmountExceded(Long userId) {
		//Este tipo de operaciones se pueden calcular eficientemente en la propia Query a la BD evitando extraer excesiva información
		int sloganCount = sloganRepositoryPort.countByUserId(userId);
		if (sloganCount == ValidationConstants.MAX_USER_SLOGANS) {
			throw new MaxSloganLimitExceededException(ValidationMessages.USER_SLOGANS_EXCEEDED);
		}
	}
}
