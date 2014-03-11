package org.emamotor.morecat.admin.user;

import lombok.Getter;
import org.emamotor.morecat.model.User;
import org.emamotor.morecat.service.UserService;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.List;

/**
 * @author Yoshimasa Tanabe
 */
@Model
public class UserViewController {

    @Inject
    private FacesContext facesContext;

    @Inject
    private UserService userService;

    @Getter
    private List<User> users;

    @PostConstruct
    public void init() {

        users = userService.findAll();

        String previousViewMessage = (String) facesContext.getExternalContext().getFlash().get("message");
        if (previousViewMessage == null) {
            return;
        }
        facesContext.addMessage(null, new FacesMessage(previousViewMessage));

    }

}
