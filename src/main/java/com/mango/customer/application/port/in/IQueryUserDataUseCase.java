package com.mango.customer.application.port.in;

import com.mango.customer.application.dto.UserDTO;

import java.util.List;

public interface IQueryUserDataUseCase {

	List<UserDTO> getAllUsers();

	UserDTO getUser(Long userId);
}
