package org.emamotor.morecat.api.impl;

import am.ik.marked4j.Marked;
import org.emamotor.morecat.api.EntryResource;
import org.emamotor.morecat.api.PublishedEntryResponse;
import org.emamotor.morecat.model.Entry;
import org.emamotor.morecat.service.EntryService;
import org.emamotor.morecat.util.Pageable;

import javax.inject.Inject;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tanabe
 */
public class EntryResourceImpl implements EntryResource {

  @Inject
  private Marked marked;

  @Inject
  private EntryService entryService;

  @Override
  public Response findRecentOnes(int size) {
    return findAllPublished(0, size);
  }

  @Override
  public Response findAllPublished(int page, int size) {
    Pageable<Entry> internalPage = entryService.findPageableAllPublished(page, size);

    Pageable<PublishedEntryResponse> publishedPage =
      new Pageable<>(
        entityList2Response(internalPage.getElements()),
        internalPage.getTotalNumberOfElements(),
        internalPage.getTotalNumberOfPages(),
        internalPage.getSize(),
        internalPage.getPage(),
        internalPage.getCurrentPageSize(),
        internalPage.isFirstPage(),
        internalPage.isLastPage()
      );

    return Response.ok(publishedPage).build();
  }

  @Override
  public Response findPublishedByYearMonthDayPermalink(int year, int month, int day, String permalink,
                                                       String sent, Request request) {
    Entry anEntry = entryService.findPublishedByYearMonthDayPermalink(year, month, day, permalink);
    if (anEntry == null) {
      return Response.status(Response.Status.NOT_FOUND).entity(null).build();
    }

    EntityTag eTag = new EntityTag(Integer.toString(anEntry.hashCode()));
    CacheControl cc = new CacheControl();
    cc.setMaxAge(5);

    Response.ResponseBuilder builder = request.evaluatePreconditions(eTag);
    if (builder != null) {
      builder.cacheControl(cc);
      return builder.build();
    }

    return Response
      .ok(entity2Response(anEntry), MediaType.APPLICATION_JSON)
      .cacheControl(cc)
      .tag(eTag)
      .build();
  }

  @Override
  public Response findAllTags() {
    return Response.ok(entryService.findAllTags()).build();
  }

  @Override
  public Response findAllPublishedByTag(String tag) {
    return Response.ok(entityList2Response(entryService.findAllPublishedByTag(tag))).build();
  }

  private PublishedEntryResponse entity2Response(Entry entity) {
    PublishedEntryResponse response = new PublishedEntryResponse();
    response.setAuthorName(entity.getAuthor().getName());
    switch (entity.getFormat()) {
      case MARKDOWN:
        response.setContent(marked.marked(entity.getContent()));
        break;
      case HTML:
        response.setContent(entity.getContent());
        break;
      default:
        throw new IllegalStateException("Invalid format");
    }
    response.setCreatedDate(new SimpleDateFormat("yyyy-MM-dd").format(entity.getCreatedDate()));
    response.setCreatedTime(new SimpleDateFormat("HH:mm:ss").format(entity.getCreatedTime()));
    response.setPermalink(entity.getPermalink());
    response.setTags(entity.getTags());
    response.setTitle(entity.getTitle());
    return response;
  }

  private List<PublishedEntryResponse> entityList2Response(List<Entry> entities) {
    return entities.stream()
      .map(this::entity2Response)
      .collect(Collectors.toList());
  }

}
