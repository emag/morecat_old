package org.emamotor.morecat.admin.auth;

import org.emamotor.morecat.service.AuthService;
import org.keycloak.representations.adapters.config.AdapterConfig;

import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Yoshimasa Tanabe
 */
@Model
public class AuthController {

  @Inject
  private FacesContext facesContext;

  @Inject
  private AuthService authService;

  @Inject
  private AdapterConfig keyCloakConfig;

  public String logout() {
    authService.logout((HttpServletRequest) facesContext.getExternalContext().getRequest());
    return "/mc-admin/index?faces-redirect=true";
  }

  public String getLoginUserName() {
    return authService.getLoginUserName((HttpServletRequest) facesContext.getExternalContext().getRequest());
  }

  public String getAccountManagerUrl() {
    return keyCloakConfig.getAuthServerUrl() + "/realms/morecat/account";
  }

  public String getKeycloakAdminConsoleUrl() {
    return keyCloakConfig.getAuthServerUrl() + "/admin/morecat/console";
  }
}
