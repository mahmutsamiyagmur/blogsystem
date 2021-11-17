package com.msy.blogsystem.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSignUpRequest {

    private Email email;

    private UserName userName;

    private String rawPassword;


}
