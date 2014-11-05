package org.emamotor.morecat.api;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author tanabe
 */
@Path("/entries")
public interface EntryResource {

  @GET
  @Path("/recent")
  @Produces(MediaType.APPLICATION_JSON)
  Response findRecentOnes(@QueryParam("size") @DefaultValue("5") int size);

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  Response findAllPublished(@QueryParam("page") int page,
                            @QueryParam("size") @DefaultValue("5") int size);

  @GET
  @Path("/{year}/{month}/{day}/{permalink}")
  @Produces(MediaType.APPLICATION_JSON)
  Response findPagerPublishedByYearMonthDayPermalink(@PathParam("year") int year,
                                                     @PathParam("month") int month,
                                                     @PathParam("day") int day,
                                                     @PathParam("permalink") String permalink);

  @GET
  @Path("/tags")
  @Produces(MediaType.APPLICATION_JSON)
  Response findAllPublishedTags();

  @GET
  @Path("/tags/{tag}")
  @Produces(MediaType.APPLICATION_JSON)
  Response findAllPublishedByTag(@PathParam("tag") String tag,
                                 @QueryParam("page") int page,
                                 @QueryParam("size") @DefaultValue("5") int size);

}
