package org.emamotor.morecat.service;

import org.emamotor.morecat.model.Media;
import org.emamotor.morecat.repository.MediaRepository;
import org.emamotor.morecat.util.DateUtil;
import org.slf4j.Logger;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.List;

/**
 * @author Yoshimasa Tanabe
 */
@Stateless
public class MediaService {

  @Inject
  private Logger logger;

  @Inject
  private Event<Media> mediaEventSrc;

  @Inject
  private MediaRepository mediaRepository;

  public Media find(String uuid, String fileName) {
    return mediaRepository.find(uuid, fileName);
  }

  public List<Media> findAll() {
    return mediaRepository.findAll();
  }

  public Media upload(Media media) {
    Media registeredMedia = mediaRepository.create(media);
    mediaEventSrc.fire(registeredMedia);
    logger.info("Uploaded media {} by {} #{}",
      registeredMedia.getUuid() + "/" + registeredMedia.getName(), registeredMedia.getAuthor().getName(), DateUtil.getFormattedCurrentDateTime());
    return mediaRepository.create(registeredMedia);
  }

}
