package com.mango.customer.application.dto;

import com.mango.customer.domain.ValidationMessages;

import javax.validation.constraints.NotBlank;

public class SloganDTO {

    private Long id;

	@NotBlank(message = ValidationMessages.SLOGAN_REQUIRED)
	private String slogan;

    public SloganDTO(Long id, String slogan){
        this.id = id;
        this.slogan = slogan;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

}
