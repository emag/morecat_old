package org.emamotor.morecat.service;

import org.emamotor.morecat.util.DateUtil;
import org.keycloak.KeycloakSecurityContext;
import org.slf4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * @author tanabe
 */
@Stateless
public class AuthService {

  @Inject
  private Logger logger;

  public void logout(HttpServletRequest request) {
    KeycloakSecurityContext session = (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
    logger.info("User ({}) logged out #{}", session.getIdToken().getPreferredUsername(), DateUtil.getFormattedCurrentDateTime());
    try {
      request.logout();
    } catch (ServletException e) {
      throw new RuntimeException(e);
    }
  }

  public String getLoginUserName(HttpServletRequest request) {
    KeycloakSecurityContext session = (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
    return session.getIdToken().getPreferredUsername();
  }

}
