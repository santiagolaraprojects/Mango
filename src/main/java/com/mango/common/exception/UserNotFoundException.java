package com.mango.common.exception;

import com.mango.customer.application.constants.ResponseMessages;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long userId) {
        super(ResponseMessages.USER_NOT_FOUND + userId);
    }
}

