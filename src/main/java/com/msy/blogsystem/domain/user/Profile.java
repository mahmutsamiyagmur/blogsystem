package com.msy.blogsystem.domain.user;

import lombok.AccessLevel;
import lombok.Data;
import lombok.With;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Transient;

@Embeddable
@Data
public class Profile {

    @Embedded
    private UserName userName;

    @Column(name = "bio")
    private String bio;

    @Embedded
    private Image image;

    @Transient
    @With(AccessLevel.PROTECTED)// means ignore this field in database
    // Immutable 'setters' - methods that create a clone but with one changed field
    private boolean following;

    public Profile(UserName userName) {
        this(userName, null, null, false);
    }

    private Profile(UserName userName, String bio, Image image, boolean following) {
        this.userName = userName;
        this.bio = bio;
        this.image = image;
        this.following = following;
    }

    protected Profile() {
    }

    /**
     * Taken from other code part.
     * @param following Following information of the profile.
     * @return new clone object with one different field.
     */
    Profile withFollowing(boolean following) {
        this.following = following;
        return this;
    }


}
