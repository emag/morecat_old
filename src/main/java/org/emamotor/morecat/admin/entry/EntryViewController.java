package org.emamotor.morecat.admin.entry;

import lombok.Getter;
import org.emamotor.morecat.model.Entry;
import org.emamotor.morecat.model.Role;
import org.emamotor.morecat.service.AuthService;
import org.emamotor.morecat.service.EntryService;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Yoshimasa Tanabe
 */
@Model
public class EntryViewController {

  @Inject
  private FacesContext facesContext;

  @Inject
  private EntryService entryService;

  @Inject
  private AuthService authService;

  @Getter
  private List<Entry> entries;

  @PostConstruct
  public void init() {

    HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
    if (request.isUserInRole(String.valueOf(Role.ADMIN))) {
      entries = entryService.findAllByAdmin();
    } else {
      entries = entryService.findAllByAuthor(
        authService.getLoginUserByName(request.getUserPrincipal().getName()));
    }

    String previousViewMessage = (String) facesContext.getExternalContext().getFlash().get("message");
    if (previousViewMessage == null) {
      return;
    }
    facesContext.addMessage(null, new FacesMessage(previousViewMessage));

  }

}
