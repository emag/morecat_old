package org.emamotor.morecat.api.impl;

import am.ik.marked4j.Marked;
import org.emamotor.morecat.api.EntryResource;
import org.emamotor.morecat.api.Next;
import org.emamotor.morecat.api.Previous;
import org.emamotor.morecat.api.PublishedEntryResponse;
import org.emamotor.morecat.api.PublishedPager;
import org.emamotor.morecat.model.Entry;
import org.emamotor.morecat.service.EntryService;
import org.emamotor.morecat.util.Pageable;
import org.emamotor.morecat.util.Pager;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tanabe
 */
public class EntryResourceImpl implements EntryResource {

  @Context
  private UriInfo uri;

  private static final String PATH_PREFIX = EntryResource.class.getAnnotation(Path.class).value().substring(1);

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
    return Response.ok(toPublish(internalPage)).build();
  }

  @Override
  public Response findPagerPublishedByYearMonthDayPermalink(int year, int month, int day, String permalink) {
    Pager<Entry> internalPager = entryService.findPagerPublishedByYearMonthDayPermalink(year, month, day, permalink);
    if (internalPager == null) {
      return Response.status(Response.Status.NOT_FOUND).entity(null).build();
    }

    PublishedPager<PublishedEntryResponse> pager = new PublishedPager<>();

    pager.setElement(entity2Response(internalPager.getElement()));

    internalPager.getNext().ifPresent((Entry next) -> {
      pager.setNext(new Next(
        next.getTitle(),
        next.getCreatedDate(),
        next.getPermalink()
      ));
    });
    internalPager.getPrevious().ifPresent((Entry previous) -> {
      pager.setPrevious(new Previous(
        previous.getTitle(),
        previous.getCreatedDate(),
        previous.getPermalink()
      ));
    });

    return Response
      .ok(pager, MediaType.APPLICATION_JSON)
      .build();
  }

  @Override
  public Response findAllPublishedTags() {
    return Response.ok(entryService.findAllPublishedTags()).build();
  }

  @Override
  public Response findAllPublishedByTag(String tag, int page, int size) {
    Pageable<Entry> internalPage = entryService.findAllPublishedByTag(tag, page ,size);
    return Response.ok(toPublish(internalPage)).build();
  }

  private PublishedEntryResponse entity2Response(Entry entity) {
    PublishedEntryResponse response = new PublishedEntryResponse();
    response.setAuthorName(entity.getAuthorName());
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

  private Pageable<PublishedEntryResponse> toPublish(Pageable<Entry> internalPage) {
    return internalPage.convertElements(entityList2Response(internalPage.getElements()));
  }

}
