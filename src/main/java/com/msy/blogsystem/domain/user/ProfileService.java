package com.msy.blogsystem.domain.user;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

/**
 * Profile viewing and follow and unfollow operations were done.
 */
@Service
public class ProfileService {


    private final UserFindService userFindService;

    ProfileService(UserFindService userFindService) {
        this.userFindService = userFindService;
    }

    /**
     * Getting profile information of the user.
     * @param viewerId User viewer id.
     * @param userNameToView user name for viewing.
     *
     * @return user profile information.
     */
    @Transactional(readOnly = true)
    public Profile viewProfile(long viewerId, UserName userNameToView) {
        // first find user and then find the profile
        final var viewer = userFindService.findById(viewerId).orElseThrow(NoSuchElementException::new);

        return userFindService.findByUsername(userNameToView)
                .map(viewer::viewProfile)
                .orElseThrow(NoSuchElementException::new);
    }

    // fixme viewProfile differences must be differentiated!
    @Transactional(readOnly = true)
    public Profile viewProfile (UserName userName) {
        return userFindService.findByUsername(userName)
                .map(User::getProfile)
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional(readOnly = true)
    public Profile unfollowAndViewProfile(long followerId, UserName followeeUserName) {
        final var followee = userFindService.findByUsername(followeeUserName).orElseThrow(NoSuchElementException::new);

        return userFindService.findById(followerId)
                .map(follower -> follower.unfollowUser(followee))
                .map(follower -> follower.viewProfile(followee))
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional(readOnly = true)
    public Profile followAndViewProfile(long followerId, UserName followeeUserName) {
        final var followee = userFindService.findByUsername(followeeUserName).orElseThrow(NoSuchElementException::new);

        return userFindService.findById(followerId)
                .map(follower -> follower.followUser(followee))
                .map(follower -> follower.viewProfile(followee))
                .orElseThrow(NoSuchElementException::new);
    }


}
