package org.emamotor.morecat.api;

import am.ik.marked4j.Marked;
import org.emamotor.morecat.model.Entry;
import org.emamotor.morecat.service.EntryService;
import org.emamotor.morecat.util.Pageable;
import org.emamotor.morecat.util.Pager;

import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tanabe
 */
@Path("/entries")
public class EntryResource {

  @Inject
  private Marked marked;

  @Inject
  private EntryService entryService;

  @GET
  @Path("/recent")
  @Produces(MediaType.APPLICATION_JSON)
  public Response findRecentOns(@QueryParam("size") @DefaultValue("5") int size) {
    return findAllPublished(0, size);
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response findAllPublished(@QueryParam("page") int page,
                            @QueryParam("size") @DefaultValue("5") int size) {
    Pageable<Entry> internalPage = entryService.findPageableAllPublished(page, size);
    return Response.ok(toPublish(internalPage)).build();
  }

  @GET
  @Path("/{year}/{month}/{day}/{permalink}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response findPagerPublishedByYearMonthDayPermalink(@PathParam("year") int year,
                                                     @PathParam("month") int month,
                                                     @PathParam("day") int day,
                                                     @PathParam("permalink") String permalink) {
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

  @GET
  @Path("/tags")
  @Produces(MediaType.APPLICATION_JSON)
  public Response findAllPublishedTags() {
    return Response.ok(entryService.findAllPublishedTags()).build();
  }

  @GET
  @Path("/tags/{tag}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response findAllPublishedByTag(@PathParam("tag") String tag,
                                 @QueryParam("page") int page,
                                 @QueryParam("size") @DefaultValue("5") int size) {
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
