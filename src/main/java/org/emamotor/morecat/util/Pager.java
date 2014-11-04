package org.emamotor.morecat.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

/**
 * @author Yoshimasa Tanabe
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pager<E> {

  private E element;
  private Optional<E> next;
  private Optional<E> previous;

}
