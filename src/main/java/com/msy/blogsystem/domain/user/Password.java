package com.msy.blogsystem.domain.user;


import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Embeddable;

@Embeddable
public class Password {

    private String encodedPassword;

    static Password of(String rawPassword, PasswordEncoder passwordEncoder) {
        return new Password(passwordEncoder.encode(rawPassword));
    }

    public Password(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }

    protected Password() {

    }

    boolean matchesPassword(String rawPassword, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(encodedPassword, rawPassword);
    }


}
