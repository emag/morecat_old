package org.emamotor.morecat.api.impl;

import org.emamotor.morecat.api.EntryResource;
import org.emamotor.morecat.api.PublishedEntryResponse;
import org.emamotor.morecat.model.Entry;
import org.emamotor.morecat.service.EntryService;

import javax.inject.Inject;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tanabe
 */
public class EntryResourceImpl implements EntryResource {

    @Inject
    private EntryService entryService;

    @Override
    public Response findPublishedByYear(int year) {
        return Response.ok(
                entityList2Response(entryService.findPublishedByYear(year)))
                .build();
    }

    @Override
    public Response findPublishedByYearMonth(int year, int month) {
        return Response.ok(
                entityList2Response(entryService.findPublishedByYearMonth(year, month)))
                .build();
    }

    @Override
    public Response findPublishedByYearMonthDay(int year, int month, int day) {
        return Response.ok(
                entityList2Response(entryService.findPublishedByYearMonthDay(year, month, day)))
                .build();
    }

    @Override
    public Response findPublishedByYearMonthDayPermalink(int year, int month, int day, String permalink) {
        Entry anEntry = entryService.findPublishedByYearMonthDayPermalink(year, month, day, permalink);
        if (anEntry == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(null).build();
        }
        return Response.ok(entity2Response(anEntry), MediaType.APPLICATION_JSON).build();
    }

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

    private static PublishedEntryResponse entity2Response(Entry entity) {
        PublishedEntryResponse response = new PublishedEntryResponse();
        response.setAuthorName(entity.getAuthor().getName());
        response.setContent(entity.getContent());
        response.setCreatedDate(entity.getCreatedDate());
        response.setCreatedTime(entity.getCreatedTime());
        response.setPermalink(entity.getPermalink());
        response.setTags(entity.getTags());
        response.setTitle(entity.getTitle());
        return response;
    }

    private static List<PublishedEntryResponse> entityList2Response(List<Entry> entities) {
        return entities
                .parallelStream()
                .map(EntryResourceImpl::entity2Response)
                .collect(Collectors.toList());
    }

}
