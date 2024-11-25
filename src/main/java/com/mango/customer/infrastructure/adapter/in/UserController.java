package com.mango.customer.infrastructure.adapter.in;

import com.mango.customer.application.dto.SloganDTO;
import com.mango.customer.application.dto.UserDTO;
import com.mango.customer.application.port.in.IQueryUserDataUseCase;
import com.mango.customer.application.port.in.IShareSloganUseCase;
import com.mango.customer.application.port.in.ISignInUseCase;
import com.mango.customer.application.port.in.IUpdateUserUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

	private final ISignInUseCase signInUseCase;
	private final IUpdateUserUseCase updateUserUseCase;
	private final IShareSloganUseCase shareSloganUseCase;
	private final IQueryUserDataUseCase queryUserDataUseCase;

	public UserController(ISignInUseCase signInUseCase, IUpdateUserUseCase updateUserUseCase,
						  IShareSloganUseCase shareSloganUseCase, IQueryUserDataUseCase queryUserDataUseCase) {
		this.signInUseCase = signInUseCase;
		this.updateUserUseCase = updateUserUseCase;
		this.shareSloganUseCase = shareSloganUseCase;
		this.queryUserDataUseCase = queryUserDataUseCase;
	}

	@PostMapping
	public ResponseEntity<UserDTO> signIn(@Valid @RequestBody UserDTO user) {
		UserDTO response = signInUseCase.signIn(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PatchMapping("/{userId}")
	public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId, @Valid @RequestBody UserDTO user) {
		UserDTO response = updateUserUseCase.updateUser(userId, user);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	/* AL NO TENER TOKENS QUE IDENTIFIQUEN AL USUARIO CREAMOS ESTE ENDPOINT CON ESTE PATH Y EN ESTE CONTROLADOR
	*
	* EN CASO DE TENER TOKENS, PODRIAMOS TENER EL CONTROLADOR SLOGANCONTROLLER Y EN EL ENDPOINT /SLOGANS CREARIAMOS EL POST
	* EXTRAYENDO LA INFORMACION NECESARIA DEL USUARIO DIRECTAMENTE DEL TOKEN.
	*/
	@PostMapping("/{userId}/slogans")
	public ResponseEntity<SloganDTO> createSlogan(@PathVariable Long userId, @Valid @RequestBody SloganDTO sloganRequest) {
		SloganDTO newSlogan = shareSloganUseCase.createSlogan(userId, sloganRequest.getSlogan());
		return ResponseEntity.status(HttpStatus.CREATED).body(newSlogan);
	}

	@GetMapping
	public ResponseEntity<List<UserDTO>> getUsers() {
		List<UserDTO> users = queryUserDataUseCase.getAllUsers();
		return ResponseEntity.status(HttpStatus.OK).body(users);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserDTO> getUser(@PathVariable Long userId) {
		UserDTO user = queryUserDataUseCase.getUser(userId);
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}
}

