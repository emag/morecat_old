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
    @Path("/{year}")
    @Produces(MediaType.APPLICATION_JSON)
    Response findPublishedByYear(@PathParam("year") int year);

    @GET
    @Path("/{year}/{month}")
    @Produces(MediaType.APPLICATION_JSON)
    Response findPublishedByYearMonth(@PathParam("year") int year,
                                      @PathParam("month") int month);

    @GET
    @Path("/{year}/{month}/{day}")
    @Produces(MediaType.APPLICATION_JSON)
    Response findPublishedByYearMonthDay(@PathParam("year") int year,
                                         @PathParam("month") int month,
                                         @PathParam("day") int day);

    @GET
    @Path("/{year}/{month}/{day}/{permalink}")
    @Produces(MediaType.APPLICATION_JSON)
    Response findPublishedByYearMonthDayPermalink(@PathParam("year") int year,
                                         @PathParam("month") int month,
                                         @PathParam("day") int day,
                                         @PathParam("permalink") String permalink);

    ////////////////////
    // for management //
    ////////////////////
    @GET
    @Path("/management")
    @Produces(MediaType.APPLICATION_JSON)
    List<Entry> findAll();

    @GET
    @Path("/management/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getSingleInstance(@PathParam("id") Integer id);

    @POST
    @Path("/management")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Entry newEntry);

    @PUT
    @Path("/management/{id:[0-9][0-9]*}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Integer id, Entry updateContent);

    @DELETE
    @Path("/management/{id:[0-9][0-9]*}")
    public Response delete(@PathParam("id") Integer id);

}
