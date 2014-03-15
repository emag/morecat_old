package org.emamotor.morecat.api.impl;

import org.emamotor.morecat.api.EntryResource;
import org.emamotor.morecat.model.Entry;
import org.emamotor.morecat.service.EntryService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

/**
 * @author tanabe
 */
public class EntryResourceImpl implements EntryResource {

    @Inject
    private EntryService entryService;

    @Override
    public List<Entry> findAll() {
        return entryService.findAll();
    }

    @Override
    public Response getSingleInstance(@PathParam("id") Integer id) {
        Entry aEntry = entryService.findById(id);
        if (aEntry == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(null).build();
        }
        return Response.ok(aEntry, MediaType.APPLICATION_JSON).build();
    }

    @Override
    public Response create(Entry newEntry) {
        Entry registeredEntry = entryService.create(newEntry);
        return Response.created(URI.create("/entries/" + registeredEntry.getId())).build();
    }

    @Override
    public Response update(@PathParam("id") Integer id, Entry updateContent) {
        Entry updatedEntry = entryService.update(updateContent);
        return Response
                .ok()
                .entity(updatedEntry)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

    @Override
    public Response delete(@PathParam("id") Integer id) {
        entryService.delete(id);
        return Response.noContent().build();
    }

}
