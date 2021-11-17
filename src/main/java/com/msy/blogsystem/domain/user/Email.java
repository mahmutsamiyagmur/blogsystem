package com.msy.blogsystem.domain.user;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Data
public class Email {

    @Column(name = "email", nullable = false)
    private String address;

    public Email(String address) {
        this.address = address;
    }

    protected Email() {

    }

}
