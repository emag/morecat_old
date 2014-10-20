package org.emamotor.morecat.util;

import lombok.Data;

import java.util.List;

/**
 * @author Yoshimasa Tanabe
 */
@Data
public class Pageable<E> {

  private List<E> elements;
  private long totalNumberOfElements;
  private long totalNumberOfPages;
  private long size;
  private long number;
  private long currentPageSize;
  private boolean firstPage;
  private boolean lastPage;

}
