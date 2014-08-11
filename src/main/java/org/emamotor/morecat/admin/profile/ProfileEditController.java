package org.emamotor.morecat.admin.profile;

import lombok.Getter;
import lombok.Setter;
import org.emamotor.morecat.admin.common.stereotype.View;
import org.emamotor.morecat.model.User;
import org.emamotor.morecat.service.AuthService;
import org.emamotor.morecat.service.UserService;
import org.emamotor.morecat.util.PasswordUtil;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * @author tanabe
 */
@View
public class ProfileEditController implements Serializable {

  @Inject
  private FacesContext facesContext;

  @Inject
  private AuthService authService;

  @Inject
  private UserService userService;

  @Getter
  @Setter
  private User profile = new User();

  @PostConstruct
  public void init() {
    String loginUserName = authService.getLoginUserByName(
      ((HttpServletRequest) facesContext.getExternalContext().getRequest()).getUserPrincipal().getName())
      .getName();
    this.profile = userService.findByName(loginUserName);
  }

  public void doSave() {
    this.profile.setPassword(PasswordUtil.hasing(this.profile.getPassword()));
    userService.update(this.profile);
    facesContext.addMessage(null, new FacesMessage("Saved!"));
  }

}
