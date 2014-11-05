package org.emamotor.morecat.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yoshimasa Tanabe
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublishedPager<E> {

  private E element;
  private Next next;
  private Previous previous;
}