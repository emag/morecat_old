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
public class EntryResource {

    @Inject
    private EntryService entryService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Entry> findAll() {
        return entryService.findAll();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSingleInstance(@PathParam("id") Integer id) {

        Entry aEntry = entryService.findById(id);

        if (aEntry == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(null).build();
        }

        return Response.ok(aEntry, MediaType.APPLICATION_JSON).build();

    }

    @POST
    @Path("/post")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Entry newEntry) {

        Entry registeredEntry = entryService.create(newEntry);

        return Response.created(URI.create("/entries/" + registeredEntry.getId())).build();

    }

    @PUT
    @Path("/{id:[0-9][0-9]*}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Integer id, Entry updateContent) {

        Entry updatedEntry = entryService.update(updateContent);

        return Response
                .ok()
                .entity(updatedEntry)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();

    }

    @DELETE
    @Path("/{id:[0-9][0-9]*}")
    public Response delete(@PathParam("id") Integer id) {

        entryService.delete(id);

        return Response.noContent().build();

    }


}
