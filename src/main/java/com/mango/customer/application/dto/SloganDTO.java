package com.mango.customer.application.dto;

public class SloganDTO {

    private Long id;

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
