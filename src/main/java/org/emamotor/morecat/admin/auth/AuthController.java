package org.emamotor.morecat.admin.auth;

import lombok.Getter;
import lombok.Setter;
import org.emamotor.morecat.service.AuthService;
import org.emamotor.morecat.util.SecurityUtil;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

            String redirectView = "/mc-admin/overview/view.xhtml";
            String referer = request.getHeader("Referer");
            if (SecurityUtil.validateReferer(request.getScheme(),
                                             request.getServerName(),
                                             request.getServerPort(),
                                             request.getContextPath() + "/mc-admin/",
                                             referer)) {
                Pattern pattern = Pattern.compile(request.getContextPath());
                Matcher matcher = pattern.matcher(new URI(referer).getPath());
                redirectView = matcher.replaceFirst("");
            }

            facesContext.getExternalContext().redirect(request.getContextPath() + redirectView);

        } catch (ServletException e) {
            logger.warn(e.toString());
            facesContext.addMessage(null, new FacesMessage("Username or password was invalid"));
        } catch (IOException e) {
            logger.error("IOException occurred, Username : " + request.getUserPrincipal().getName(), e);
            facesContext.addMessage(null, new FacesMessage("System error!"));
        } catch (URISyntaxException e) {
            logger.error("URISyntaxException occurred, Username : " + request.getUserPrincipal().getName(), e);
            facesContext.addMessage(null, new FacesMessage("System error!"));
        }

    }

    public String logout() {

        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        authService.logout(request, request.getSession(false));

        return "/mc-admin/login?faces-redirect=true";

    }

    public String getLoginUserName() {
        String loginUserName =
                ((HttpServletRequest) facesContext.getExternalContext().getRequest()).getUserPrincipal().getName();
        return authService.getLoginUserByName(loginUserName).getName();
    }

}
