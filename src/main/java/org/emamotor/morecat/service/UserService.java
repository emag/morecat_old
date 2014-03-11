package org.emamotor.morecat.service;

import org.emamotor.morecat.model.User;
import org.emamotor.morecat.repository.UserRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author Yoshimasa Tanabe
 */
@Stateless
public class UserService {

    @Inject
    private UserRepository userRepository;

    public User findByName(String username) {
        return userRepository.findByName(username);
    }

}
