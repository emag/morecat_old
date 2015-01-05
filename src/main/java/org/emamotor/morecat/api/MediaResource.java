package org.emamotor.morecat.api;

import org.emamotor.morecat.model.Media;
import org.emamotor.morecat.service.MediaService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Yoshimasa Tanabe
 */
@ApplicationScoped
@Path("/media")
public class MediaResource {

  @Inject
  private MediaService mediaService;

  @GET
  @Path("/{uuid}/{file-name}")
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  public Response find(@PathParam("uuid") String uuid, @PathParam("file-name") String fileName) {
    Media media = mediaService.find(uuid, fileName);

    if (media == null) {
      return Response.status(Response.Status.NOT_FOUND).entity(null).build();
    }

    return Response
      .ok(media.getContent(), MediaType.APPLICATION_OCTET_STREAM)
      .header("Content-Disposition", "attachment; filename=\"" + media.getName() + "\"" )
      .build();
  }

}
