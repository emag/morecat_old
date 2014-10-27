package org.emamotor.morecat.admin.media;

import lombok.Getter;
import lombok.Setter;
import org.emamotor.morecat.model.Media;
import org.emamotor.morecat.model.User;
import org.emamotor.morecat.service.MediaService;
import org.emamotor.morecat.service.UserService;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author Yoshimasa Tanabe
 */
@Model
public class MediaViewController {

  @Inject
  private FacesContext facesContext;

  @Inject
  private MediaService mediaService;

  @Inject
  private UserService userService;

  @Getter @Setter
  private Part file;

  @Getter
  private List<Media> mediaList;

  public void validate(FacesContext context, UIComponent component, Object value) {
    if (value == null) {
      throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_WARN, "Please input a file", null));
    }
  }

  public void onMediaListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Media media) {
    retrieveAllMedia();
  }

  @PostConstruct
  public void retrieveAllMedia() {
    mediaList = mediaService.findAll();
  }

  public String upload() {
    if (file == null) {
      facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Please input a file", null));
      return null;
    }

    Media media = new Media();

    try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
         InputStream is = file.getInputStream()) {
      int length = 0;
      final byte[] buffer = new byte[1024];
      while ((length = is.read(buffer)) != -1) {
        bos.write(buffer, 0, length);
      }
      media.setContent(bos.toByteArray());
    } catch (IOException e) {
      e.printStackTrace();
    }

    HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
    User author = userService.findByEmail(request.getUserPrincipal().getName());
    media.setAuthor(author);

    media.setName(file.getSubmittedFileName());

    mediaService.upload(media);

    return "view?faces-redirect=true";
  }

}
