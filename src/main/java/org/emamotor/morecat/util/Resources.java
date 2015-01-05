package org.emamotor.morecat.util;

import am.ik.marked4j.Marked;
import am.ik.marked4j.MarkedBuilder;
import org.keycloak.representations.adapters.config.AdapterConfig;
import org.keycloak.util.JsonSerialization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Yoshimasa Tanabe
 */
public class Resources {

  @Inject
  private ServletContext context;

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

  @Produces
  @ApplicationScoped
  public AdapterConfig produceAdapterConfig() {
    try (InputStream is = context.getResourceAsStream("/WEB-INF/keycloak.json")) {
      return JsonSerialization.readValue(is, AdapterConfig.class, true);
    } catch (IOException ioe) {
      throw new IllegalStateException("keycloak.json may be invalid", ioe);
    }
  }
}
