package org.emamotor.morecat.api;

import org.emamotor.morecat.service.SettingService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author Yoshimasa Tanabe
 */
@ApplicationScoped
@Path("/settings")
public class SettingResource {

  @Inject
  private SettingService settingService;

  @GET
  @Path("/blog-name")
  @Produces(MediaType.APPLICATION_JSON)
  public String getBlogName() {
    return settingService.find().getBlogName();
  }

  @GET
  @Path("/blog-description")
  @Produces(MediaType.APPLICATION_JSON)
  public String getBlogDescription() {
    return settingService.find().getBlogDescription();
  }

}
