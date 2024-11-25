package com.mango.common.exception;

public class MaxSloganLimitExceededException extends RuntimeException{
	public MaxSloganLimitExceededException(String message) {
		super(message);
	}
}
