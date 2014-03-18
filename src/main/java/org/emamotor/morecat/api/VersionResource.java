package org.emamotor.morecat.api;

import org.emamotor.morecat.util.Version;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author Yoshimasa Tanabe
 */
@Path("/version")
public class VersionResource {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String getVersion() {
    return Version.getInstance().getVersion();
  }

}
