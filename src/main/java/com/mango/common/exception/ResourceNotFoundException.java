package com.mango.common.exception;

import com.mango.customer.application.constants.ResponseMessages;

public class ResourceNotFoundException extends RuntimeException {
	public ResourceNotFoundException(String resource) {
		super(ResponseMessages.RESOURCE_NOT_FOUND + resource);
	}
}
