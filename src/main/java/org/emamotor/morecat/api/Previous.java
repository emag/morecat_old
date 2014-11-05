package org.emamotor.morecat.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Yoshimasa Tanabe
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Previous {
  private String title;
  private Date createdDate;
  private String permalink;
}
