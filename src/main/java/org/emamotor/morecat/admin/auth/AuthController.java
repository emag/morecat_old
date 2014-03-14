package org.emamotor.morecat.admin.auth;

import lombok.Getter;
import lombok.Setter;
import org.emamotor.morecat.service.AuthService;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author Yoshimasa Tanabe
 */
@Model
public class AuthController {

    @Inject
    private Logger logger;

    @Inject
    private FacesContext facesContext;

    @Inject
    private AuthService authService;

    @Getter
    @Setter
    @NotEmpty(message = "Please Enter Username")
    private String username;

    @Getter
    @Setter
    @NotEmpty(message = "Please Enter Password")
    private String password;

    public void login() {

        HttpServletRequest request = null;

        try {

            request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
            authService.login(request, username, password);
            facesContext.getExternalContext().redirect(request.getContextPath() + "/mc-admin/overview/view.xhtml");

        } catch (ServletException e) {
            logger.warn(e.toString());
            facesContext.addMessage(null, new FacesMessage("Username or password was invalid"));
        } catch (IOException e) {
            logger.error("IOException occurred, Username : " + request.getUserPrincipal().getName(), e);
            facesContext.addMessage(null, new FacesMessage("System error!"));
        }

    }

    public String logout() {

        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        authService.logout(request, request.getSession(false));

        return "/mc-admin/login?faces-redirect=true";

    }

}
