package com.msy.blogsystem.domain.user;


import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

/**
 *
 */
@Table(name ="user")
@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Email email;

    @Embedded
    private Profile profile;

    @Embedded
    private Password password;

    private User(Email email, Profile profile, Password password) {
        this.email = email;
        this.profile = profile;
        this.password = password;
    }

    protected User() {

    }

    static User of (Email email, UserName userName, Password password) {
        return new User(email, new Profile(userName), password);
    }

    boolean matchesPassword(String rawPassword, PasswordEncoder passwordEncoder) {
        return password.matchesPassword(rawPassword, passwordEncoder);
    }

    // fixme move this method
    public void changeUserName(UserName userName) {
        profile.setUserName(userName);
    }

    public void setBio(String bio) {
        profile.setBio(bio);
    }

    public void setImage(Image image) {
        profile.setImage(image);
    }
}
