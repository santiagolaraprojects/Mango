package com.mango.customer.application.port.in;

import com.mango.customer.application.dto.SloganDTO;

public interface IShareSloganUseCase {

    SloganDTO createSlogan(Long userId, String sloganText);
    
}
