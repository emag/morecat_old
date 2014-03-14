package org.emamotor.morecat.service;

import org.emamotor.morecat.model.User;
import org.emamotor.morecat.util.DateUtil;
import org.slf4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author tanabe
 */
@Stateless
public class AuthService {

    @Inject
    private Logger logger;

    @Inject
    private UserService userService;

    public void login(HttpServletRequest request, String username, String password) throws ServletException {
        request.login(username, password);
        logger.info("User ({}) loging in #" + DateUtil.getFormattedCurrentDateTime(), request.getUserPrincipal().getName());
    }

    public void logout(HttpServletRequest request, HttpSession session) {
        logger.info("User ({}) loging out #" + DateUtil.getFormattedCurrentDateTime(), request.getUserPrincipal().getName());
        if (session != null) {
            session.invalidate();
        }
    }

    public User getLoginUserByName(String loginUserName) {
        return userService.findByName(loginUserName);
    }

}
