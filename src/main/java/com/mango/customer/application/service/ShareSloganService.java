package com.mango.customer.application.service;

import java.util.List;
import java.util.Optional;

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

        List<SloganEntity> userSlogans = sloganRepositoryPort.findByUserId(userId);
        if (userSlogans.size() == 3) {
            throw new IllegalArgumentException("User has already created 3 slogans.");
        }

        SloganEntity newSlogan = new SloganEntity();
        newSlogan.setSlogan(sloganText);
        newSlogan.setUser(userOptional.get());

		SloganEntity result = sloganRepositoryPort.save(newSlogan);

        return new SloganDTO(result.getUser().getId(), result.getSlogan());
    }
}
