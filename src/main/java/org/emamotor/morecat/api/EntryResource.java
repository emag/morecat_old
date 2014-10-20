package org.emamotor.morecat.api;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
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
  Response findAllPublished(@QueryParam("start") int start,
                            @QueryParam("size") @DefaultValue("5") int size);

  @GET
  @Path("/{year}")
  @Produces(MediaType.APPLICATION_JSON)
  Response findAllPublishedByYear(@PathParam("year") int year);

  @GET
  @Path("/{year}/{month}")
  @Produces(MediaType.APPLICATION_JSON)
  Response findAllPublishedByYearMonth(@PathParam("year") int year,
                                       @PathParam("month") int month);

  @GET
  @Path("/{year}/{month}/{day}")
  @Produces(MediaType.APPLICATION_JSON)
  Response findAllPublishedByYearMonthDay(@PathParam("year") int year,
                                          @PathParam("month") int month,
                                          @PathParam("day") int day);

  @GET
  @Path("/{year}/{month}/{day}/{permalink}")
  @Produces(MediaType.APPLICATION_JSON)
  Response findPublishedByYearMonthDayPermalink(@PathParam("year") int year,
                                                @PathParam("month") int month,
                                                @PathParam("day") int day,
                                                @PathParam("permalink") String permalink,
                                                @HeaderParam("If-None-Match") String sent,
                                                @Context Request request);

  @GET
  @Path("/tags")
  @Produces(MediaType.APPLICATION_JSON)
  Response findAllTags();

  @GET
  @Path("/tags/{tag}")
  @Produces(MediaType.APPLICATION_JSON)
  Response findAllPublishedByTag(@PathParam("tag") String tag);

}
