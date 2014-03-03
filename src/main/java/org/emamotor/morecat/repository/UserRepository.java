package org.emamotor.morecat.repository;

import org.emamotor.morecat.model.User;

import javax.ejb.Stateless;

/**
 * @author Yoshimasa Tanabe
 */
@Stateless
public class UserRepository extends GenericRepository<User> {

    public UserRepository() {
        super(User.class);
    }

}
