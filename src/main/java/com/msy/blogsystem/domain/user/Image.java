package com.msy.blogsystem.domain.user;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Data
public class Image {

    @Column(name = "image")
    private String address;


    public Image(String address) {
        this.address = address;
    }

    protected Image() {

    }

}
