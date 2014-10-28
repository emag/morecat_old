package org.emamotor.morecat.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Yoshimasa Tanabe
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pageable<E> {

  private List<E> elements;
  private long totalNumberOfElements;
  private long totalNumberOfPages;
  private long size;
  private long page;
  private long currentPageSize;
  private boolean firstPage;
  private boolean lastPage;

  public Pageable(List<E> elements, long totalNumberOfElements, int page, int size) {
    this.elements = elements;
    this.totalNumberOfElements = totalNumberOfElements;
    this.totalNumberOfPages = (totalNumberOfElements / size) + ((totalNumberOfElements % size) == 0 ? 0 : 1);
    this.size = size;
    this.page = page;
    this.currentPageSize = elements.size();
    this.firstPage = page == 0;
    this.lastPage = (page + 1) == totalNumberOfPages;
  }

}
