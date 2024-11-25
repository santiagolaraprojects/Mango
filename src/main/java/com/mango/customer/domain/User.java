package com.mango.customer.domain;

import com.mango.common.util.EmailValidator;

import java.util.Objects;

public class User {
	private Long id;
	private String name;
	private String lastName;
	private String address;
	private String city;
	private String email;

	public User(Long id, String name, String lastName, String address, String city, String email) {
		if(!EmailValidator.isValid(email)) throw new IllegalArgumentException(ValidationMessages.EMAIL_INVALID);

		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.email = email;
	}

	public void update(String name, String lastName, String address, String city, String email) {
		if (!Objects.isNull(email) && !EmailValidator.isValid(email)) {
			throw new IllegalArgumentException(ValidationMessages.EMAIL_INVALID);
		}
		if (!Objects.isNull(name)) this.name = name;
		if (!Objects.isNull(lastName)) this.lastName = lastName;
		if (!Objects.isNull(email)) this.email = email;
		if (!Objects.isNull(address)) this.address = address;
		if (!Objects.isNull(city)) this.city = city;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		if(EmailValidator.isValid(email)) throw new IllegalArgumentException(ValidationMessages.EMAIL_INVALID);
		this.email = email;
	}


}
