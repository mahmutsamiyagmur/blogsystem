package com.msy.blogsystem.domain.user;


import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    @JoinTable(name = "user_following",
            joinColumns = @JoinColumn(name = "follower_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "followee_id", referencedColumnName = "id"))
    @OneToMany(cascade = CascadeType.REMOVE)
    private Set<User> followingUsers = new HashSet<>();

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

    public Profile viewProfile(User user) {
        return user.profile.withFollowing(followingUsers.contains(user));
    }

    /**
     * Remove one user which is followed
     * @param followee user who is followed.
     *
     * @return
     */
    User unfollowUser(User followee) {
        followingUsers.remove(followee);
        return this;
    }

    /**
     * Add followee to the following user list.
     * @param followee user that is tracked.
     * @return User with updated following user list.
     */
    public User followUser(User followee) {
        followingUsers.add(followee);
        return this;
    }
}
