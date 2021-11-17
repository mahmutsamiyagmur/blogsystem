package com.msy.blogsystem.domain.user;


import java.util.Optional;

import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

public class UserUpdateRequest {

    private final Email emailtoUpdate;

    private final UserName userNameToUpdate;

    private final String passwordToUpdate;

    private final Image imageToUpdate;

    private final String bioToUpdate;

    private UserUpdateRequest(UserUpdateRequestBuilder builder) {
        this.emailtoUpdate = builder.emailToUpdate;
        this.userNameToUpdate = builder.userNameToUpdate;
        this.passwordToUpdate = builder.passwordToUpdate;
        this.imageToUpdate = builder.imageToUpdate;
        this.bioToUpdate = builder.bioToUpdate;

    }

    Optional<Email> getEmailToUpdate() {
        return ofNullable(emailtoUpdate);
    }

    Optional<UserName> getUserNameToUpdate() {
        return ofNullable(userNameToUpdate);
    }

    Optional<String> getPasswordToUpdate() {
        return ofNullable(passwordToUpdate);
    }

    Optional<Image> getImageToUpdate() {
        return ofNullable(imageToUpdate);
    }

    Optional<String> getBioToUpdate() {
        return ofNullable(bioToUpdate);
    }

    public static UserUpdateRequestBuilder builder() {
        return new UserUpdateRequestBuilder();
    }

    private static class UserUpdateRequestBuilder {

        private Email emailToUpdate;
        private UserName userNameToUpdate;
        private String passwordToUpdate;
        private Image imageToUpdate;
        private String bioToUpdate;

        public UserUpdateRequestBuilder emailToUpdate(Email emailToUpdate) {
            this.emailToUpdate = emailToUpdate;
            return this;
        }

        public UserUpdateRequestBuilder userNameToUpdate(UserName userName) {
            this.userNameToUpdate = userName;
            return this;
        }

        public UserUpdateRequestBuilder passwordToUpdate(String passwordToUpdate) {
            this.passwordToUpdate = passwordToUpdate;
            return this;
        }

        public UserUpdateRequestBuilder imageToUpdate(Image imageToUpdate) {
            this.imageToUpdate = imageToUpdate;
            return this;
        }

        public UserUpdateRequestBuilder bioToUpdate(String bioToUpdate) {
            this.bioToUpdate = bioToUpdate;
            return this;
        }

        public UserUpdateRequest build() {
            return new UserUpdateRequest(this);
        }

    }
}
