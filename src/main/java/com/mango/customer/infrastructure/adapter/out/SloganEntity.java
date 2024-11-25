package com.mango.customer.infrastructure.adapter.out;

//@Entity
// @Table(name = "slogans") EN CASO DE USAR UNA BASE DE DATOS RELACIONAL
public class SloganEntity {
	/*@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	CONFIGURACION DEL ID PARA LA TABLA
	*/
	private Long id;
    private UserEntity user;

    private String slogan;

	public SloganEntity() {}
	public SloganEntity(Long id, UserEntity user, String slogan) {
		this.id = id;
		this.user = user;
		this.slogan = slogan;
	}

    public UserEntity getUser() {
        return this.user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getSlogan() {
        return this.slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long slogansId) {this.id = slogansId;	}
}
