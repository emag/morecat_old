package org.emamotor.morecat.api;

import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Yoshimasa Tanabe
 */
@Data
public class PublishedEntryResponse {

  private String title;

  private String content;

  private String permalink;

  private String authorName;

  private String createdDate;

  private String createdTime;

  private Set<String> tags = new HashSet<>();

}
