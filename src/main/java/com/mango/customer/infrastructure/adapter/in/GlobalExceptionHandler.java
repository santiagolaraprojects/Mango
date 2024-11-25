package com.mango.customer.infrastructure.adapter.in;

import com.mango.common.exception.MaxSloganLimitExceededException;
import com.mango.common.exception.ResourceNotFoundException;
import com.mango.common.exception.UserAlreadyExistsException;
import com.mango.common.exception.UserNotFoundException;
import com.mango.customer.application.constants.ResponseMessages;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}

	@ExceptionHandler(MaxSloganLimitExceededException.class)
	public ResponseEntity<String> handleIMaxSloganLimitExceededException(MaxSloganLimitExceededException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGeneralException(Exception ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseMessages.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<String> handleAlreadyExistingUser(Exception ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
	}

	//Validaciones
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		StringBuilder errorMessage = new StringBuilder(ResponseMessages.VALIDATION_FIELDS_ERROR);
		ex.getBindingResult().getFieldErrors().forEach(error ->
			errorMessage.append(error.getField()).append(", ")
		);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage.toString());
	}

	//NO CONSIDERO EL TRATAMIENTO DE RESPUESTAS COMO ERRORES DE ATORIZACION O AUTENTIFICACIÓN DEBIDO A QUE NO HAY LA FUNCIÓN DE LOGIN


	/* Esta excepción es útil en caso de utilizar una Base de Datos
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input according to database constraints.");
	}
	*/

}
