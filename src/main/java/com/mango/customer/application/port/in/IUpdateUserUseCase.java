package com.mango.customer.application.port.in;

import com.mango.customer.application.dto.UserDTO;
import com.mango.customer.domain.User;

public interface IUpdateUserUseCase {

    UserDTO updateUser(Long userId, UserDTO user);

}
