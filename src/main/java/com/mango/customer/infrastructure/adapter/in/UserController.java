package com.mango.customer.infrastructure.adapter.in;

import com.mango.customer.application.port.in.IQueryUserDataUseCase;
import com.mango.customer.application.service.UpdateUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mango.common.exception.UserNotFoundException;
import com.mango.customer.application.dto.SloganDTO;
import com.mango.customer.application.dto.UserDTO;
import com.mango.customer.application.port.in.IShareSloganUseCase;
import com.mango.customer.application.port.in.ISignInUseCase;
import com.mango.customer.application.port.in.IUpdateUserUseCase;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

	private final ISignInUseCase signInUseCase;
	private final IUpdateUserUseCase updateUserUseCase;
	private final IShareSloganUseCase shareSloganUseCase;
	private final IQueryUserDataUseCase queryUserDataUseCase;

	public UserController(ISignInUseCase signInUseCase, IUpdateUserUseCase updateUserUseCase, IShareSloganUseCase shareSloganUseCase, IQueryUserDataUseCase queryUserDataUseCase) {
		this.signInUseCase = signInUseCase;
		this.updateUserUseCase = updateUserUseCase;
		this.shareSloganUseCase = shareSloganUseCase;
		this.queryUserDataUseCase = queryUserDataUseCase;
	}


	@PostMapping
	public ResponseEntity<UserDTO> signIn(@RequestBody UserDTO user){
		try{
			UserDTO response = signInUseCase.signIn(user);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		}
		catch(RuntimeException e){
			if(e instanceof IllegalArgumentException) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}

	@PatchMapping("/{userId}")
	public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId, @RequestBody UserDTO user){
		try{
			UserDTO response = updateUserUseCase.updateUser(userId, user);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		catch(RuntimeException e){
			if(e instanceof UserNotFoundException) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			else if(e instanceof IllegalArgumentException) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

    @PostMapping("/{userId}/slogans")
    public ResponseEntity<SloganDTO> createSlogan(@PathVariable Long userId, @RequestBody String sloganText) {
        try {
            SloganDTO newSlogan = shareSloganUseCase.createSlogan(userId, sloganText);
			return ResponseEntity.status(HttpStatus.CREATED).body(newSlogan);

        } catch (RuntimeException e) {
			if(e instanceof UserNotFoundException) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			else if(e instanceof IllegalArgumentException) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

	@GetMapping
	public ResponseEntity<List<UserDTO>> getUsers() {
		try {
			List<UserDTO> users = queryUserDataUseCase.getAllUsers();
			return ResponseEntity.status(HttpStatus.OK).body(users);

		} catch (RuntimeException e) {
			if(e instanceof UserNotFoundException) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserDTO> getUser(@PathVariable Long userId) {
		try {
			UserDTO user = queryUserDataUseCase.getUser(userId);
			return ResponseEntity.status(HttpStatus.OK).body(user);

		} catch (RuntimeException e) {
			if(e instanceof UserNotFoundException) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

}
