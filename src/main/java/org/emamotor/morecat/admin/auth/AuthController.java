package org.emamotor.morecat.admin.auth;

import lombok.Getter;
import lombok.Setter;
import org.emamotor.morecat.util.DateUtil;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;

/**
 * @author Yoshimasa Tanabe
 */
@Model
public class AuthController {

    @Inject
    private Logger logger;

    @Inject
    private FacesContext facesContext;

    @Getter
    @Setter
    @NotEmpty(message = "Please Enter Username")
    private String username;

    @Getter
    @Setter
    @NotEmpty(message = "Please Enter Password")
    private String password;

    public void login(ActionEvent actionEvent) {

        Principal principal = null;

        try {

            HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
            request.login(username, password);
            principal = request.getUserPrincipal();
            logger.info("User ({}) loging in #" + DateUtil.getCurrentDateTime(), principal.getName());

            facesContext.getExternalContext().redirect(request.getContextPath() + "/mc-admin/overview/view.xhtml");

        } catch (ServletException e) {
            logger.error(e.toString());
            facesContext.addMessage(null, new FacesMessage("Username or password was invalid"));
        } catch (IOException e) {
            logger.error("IOException, Login Controller" + "Username : " + principal.getName(), e);
            facesContext.addMessage(null, new FacesMessage("System error!"));
        }

    }

    public String logout() {

        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        logger.info("User ({}) loging out #" + DateUtil.getCurrentDateTime(), request.getUserPrincipal().getName());

        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "/mc-admin/login?faces-redirect=true";

    }

}
