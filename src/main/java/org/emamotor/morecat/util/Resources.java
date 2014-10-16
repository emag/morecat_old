package org.emamotor.morecat.util;

import am.ik.marked4j.Marked;
import am.ik.marked4j.MarkedBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Yoshimasa Tanabe
 */
public class Resources {

  @Produces
  @PersistenceContext
  private EntityManager em;

  @Produces
  public Logger produceLog(InjectionPoint injectionPoint) {
    return LoggerFactory.getLogger(injectionPoint
      .getMember()
      .getDeclaringClass()
      .getName());
  }

  @Produces
  @RequestScoped
  public FacesContext produceFacesContext() {
    return FacesContext.getCurrentInstance();
  }

  @Produces
  @ApplicationScoped
  public Marked produceMarked() {
    return new MarkedBuilder().gfm(true).langPrefix("").build();
  }

}
