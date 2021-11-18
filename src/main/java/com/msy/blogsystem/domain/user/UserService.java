package com.msy.blogsystem.domain.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService implements UserFindService{

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    /**
     * fixme use interface for this method
     * @param userSignUpRequest
     * @return
     */
    @Transactional
    public User signUp(UserSignUpRequest userSignUpRequest) {
        // Encode the password
        final var encodedPassword =Password.of(userSignUpRequest.getRawPassword(), passwordEncoder);
        return userRepository.save(User.of(
                userSignUpRequest.getEmail(),
                userSignUpRequest.getUserName(),
                encodedPassword
        ));
    }

    /**
     * Find user with mail and proper password.
     * @param email User email
     * @param rawPassword String value of password
     * @return User information if there is any proper user.
     */
    @Transactional(readOnly = true) // Db read only operation.
    public Optional<User> login(Email email, String rawPassword) {
        return userRepository.findFirstByEmail(email)
                .filter(user -> user.matchesPassword(rawPassword, passwordEncoder));
    }




    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByUsername(UserName userName) {
        return userRepository.findFirstByProfileUserName(userName);
    }

    /**
     * Update user information
     * @param id info of the user
     * @param userUpdateRequest user update information container
     * @return
     */
    public User updateUser(long id, UserUpdateRequest userUpdateRequest) {
        final var user = userRepository.findById(id).orElseThrow(NoSuchElementException::new);

        // Otherwise save the user with update information.
        userUpdateRequest.getEmailToUpdate()
            .ifPresent(user::setEmail);

        userUpdateRequest.getPasswordToUpdate()
                .map(rawPassword -> Password.of(rawPassword, passwordEncoder))
                .ifPresent(user::setPassword);

        // Profile related info
        userUpdateRequest.getUserNameToUpdate()
                .ifPresent(user::changeUserName);

        userUpdateRequest.getBioToUpdate()
                .ifPresent(user::setBio);

        userUpdateRequest.getImageToUpdate()
                .ifPresent(user::setImage);

        return userRepository.save(user);

    }
}
