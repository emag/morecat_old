package org.emamotor.morecat.repository;

import org.emamotor.morecat.model.User;

/**
 * @author Yoshimasa Tanabe
 */
public class UserRepository extends GenericRepository<User> {

    public UserRepository() {
        super(User.class);
    }

}
