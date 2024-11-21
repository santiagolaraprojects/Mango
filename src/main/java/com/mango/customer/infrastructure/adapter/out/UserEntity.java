package com.mango.customer.infrastructure.adapter.out;

//@Entity
//Seria necesario incluir las anotaciones referentes al tipo de base de datos usado por ejemplo @Table en una base de datos relacional y anotaciones para la generacion del id entre otras como validaciones
// @Table(name = "users") EN CASO DE USAR UNA BASE DE DATOS RELACIONAL

public class UserEntity {
	/*@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	CONFIGURACION DEL ID PARA LA TABLA
	*/
    private Long id;
    private String name;
    private String lastName;
    private String address;
    private String city;
    private String email;

    public UserEntity(Long id, String name, String lastName, String address, String city, String email){
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

