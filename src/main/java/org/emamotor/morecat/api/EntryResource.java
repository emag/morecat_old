package org.emamotor.morecat.api;

import org.emamotor.morecat.model.Entry;
import org.emamotor.morecat.service.EntryService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

/**
 * @author tanabe
 */
@Path("/entries")
public interface EntryResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response findAllPublished(@QueryParam("start") int start, @QueryParam("size") int size);

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
                                                  @PathParam("permalink") String permalink);

}
