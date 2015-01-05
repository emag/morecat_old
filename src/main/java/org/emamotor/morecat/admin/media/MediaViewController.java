package org.emamotor.morecat.admin.media;

import lombok.Getter;
import lombok.Setter;
import org.emamotor.morecat.model.Media;
import org.emamotor.morecat.service.AuthService;
import org.emamotor.morecat.service.MediaService;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
  private AuthService authService;

  @Getter @Setter
  private Part file;

  @Getter
  private List<Media> mediaList;

  @PostConstruct
  public void init() {
    mediaList = mediaService.findAll();

    String previousViewMessage = (String) facesContext.getExternalContext().getFlash().get("message");
    if (previousViewMessage == null) {
      return;
    }
    facesContext.addMessage(null, new FacesMessage(previousViewMessage));
  }

  public String upload() {
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
      throw new RuntimeException(e);
    }

    media.setAuthorName(
      authService.getLoginUserName(
        (HttpServletRequest) facesContext.getExternalContext().getRequest()));

    media.setName(file.getSubmittedFileName());

    mediaService.upload(media);

    facesContext.getExternalContext().getFlash().put("message", "Uploaded!");

    return "view?faces-redirect=true";
  }

}
