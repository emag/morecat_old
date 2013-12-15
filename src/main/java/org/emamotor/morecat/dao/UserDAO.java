package org.emamotor.morecat.dao;

import org.emamotor.morecat.model.User;

/**
 * @author Yoshimasa Tanabe
 */
public class UserDAO extends GenericDAO<User> {

    public UserDAO() {
        super(User.class);
    }

}
