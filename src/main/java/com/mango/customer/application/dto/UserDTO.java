package com.mango.customer.application.dto;

import com.mango.customer.domain.ValidationConstants;
import com.mango.customer.domain.ValidationMessages;

import javax.validation.constraints.*;

public class UserDTO {
    private Long id;

	@NotNull(message = ValidationMessages.NAME_REQUIRED)
	@Size(min = ValidationConstants.NAME_MIN_LENGTH, max = ValidationConstants.NAME_MAX_LENGTH, message = ValidationMessages.NAME_INVALID)
	@Pattern(regexp = ValidationConstants.ONLY_ALPHABET_CHARACTERS, message = ValidationMessages.NAME_INVALID)
	private String name;

	@NotBlank(message = ValidationMessages.LASTNAME_REQUIRED)
	@Size(min = ValidationConstants.LASTNAME_MIN_LENGTH, max = ValidationConstants.LASTNAME_MAX_LENGTH, message = ValidationMessages.LASTNAME_INVALID)
	@Pattern(regexp = ValidationConstants.ONLY_ALPHABET_CHARACTERS, message = ValidationMessages.LASTNAME_INVALID)
    private String lastName;

	//Having an address is not mandatory
	@Size(max = ValidationConstants.ADDRESS_MAX_LENGTH, message = ValidationMessages.ADDRESS_INVALID)
	@Pattern(regexp = ValidationConstants.ALPHANUMERIC_WITH_PUNCTUATION_REGEX, message = ValidationMessages.ADDRESS_INVALID)
    private String address;

	@NotBlank(message = ValidationMessages.CITY_REQUIRED)
	@Size(min = ValidationConstants.CITY_MIN_LENGTH, max = ValidationConstants.CITY_MAX_LENGTH, message = ValidationMessages.CITY_INVALID)
	@Pattern(regexp = ValidationConstants.ONLY_ALPHABET_CHARACTERS, message = ValidationMessages.CITY_INVALID)
    private String city;

	@NotBlank(message = ValidationMessages.EMAIL_REQUIRED)
	@Email(message = ValidationMessages.EMAIL_INVALID)
    private String email;

    public UserDTO(Long id, String name, String lastName, String address, String city, String email){
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.email = email;
    }

    public Long getId() {return this.id;}

    public String getName() {return this.name;}

    public String getLastName() {return this.lastName;}

    public String getAddress() {return this.address;}

    public String getCity() {return this.city;}

    public String getEmail() {return this.email;}


    public void setId(Long id) {this.id = id;}

    public void setName(String name) { this.name = name;}

    public void setLastName(String lastName) {this.lastName = lastName;}

    public void setAddress(String address) {this.address = address;}

    public void setCity(String city) {this.city = city;}

    public void setEmail(String email) {this.email = email;}


}
