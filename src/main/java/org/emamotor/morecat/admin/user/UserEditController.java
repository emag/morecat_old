package org.emamotor.morecat.admin.user;

import lombok.Getter;
import lombok.Setter;
import org.emamotor.morecat.admin.common.stereotype.View;
import org.emamotor.morecat.model.Role;
import org.emamotor.morecat.model.User;
import org.emamotor.morecat.service.UserService;
import org.emamotor.morecat.util.PasswordUtil;
import org.slf4j.Logger;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.Serializable;

/**
 * @author Yoshimasa Tanabe
 */
@View
public class UserEditController implements Serializable {

  @Inject
  private FacesContext facesContext;

  @Inject
  private Logger logger;

  @Inject
  private UserService userService;

  @Getter
  @Setter
  private User user = new User();

  @Getter
  private boolean newUser;

  public void doFind() {

    // new user
    if (user.getId() == null
      || userService.findById(user.getId()) == null) {
      this.user.setRole(Role.AUTHOR);
      this.newUser = true;
      return;
    }

    // existing user
    this.user = userService.findById(user.getId());

  }

  public String doSave() {

    this.user.setPassword(PasswordUtil.hasing(this.user.getPassword()));
    boolean newUser = this.user.getId() == null;

    userService.update(this.user);

    String successMessage = newUser ? "Created!" : "Updated!";
    facesContext.getExternalContext().getFlash().put("message", successMessage);
    return "view?faces-redirect=true";

  }

  public String getRole_() {
    return this.user.getRole().name();
  }

  public void setRole_(String selectedRole) {
    this.user.setRole(Role.valueOf(selectedRole));
  }

}
