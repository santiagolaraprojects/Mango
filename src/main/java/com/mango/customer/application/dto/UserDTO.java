package com.mango.customer.application.dto;

public class UserDTO {
    private Long id;
    private String name;
    private String lastName;
    private String address;
    private String city;
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
