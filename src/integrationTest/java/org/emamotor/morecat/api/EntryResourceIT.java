package org.emamotor.morecat.api;

import org.emamotor.morecat.MoreCatDeployment;
import org.emamotor.morecat.util.Pageable;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URL;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * @author Yoshimasa Tanabe
 */
@RunWith(Arquillian.class)
@RunAsClient
public class EntryResourceIT {

  private static final String RESOURCE_PREFIX = JAXRSActivator.class.getAnnotation(ApplicationPath.class).value().substring(1);
  @ArquillianResource
  private URL deploymentUrl;

  private static Client client;
  private static Response response;

  @Before
  public void setUp() throws Exception {
    client = ClientBuilder.newClient();
  }

  @After
  public void tearDown() throws Exception {
    if (response != null) {
      response.close();
    }
    client.close();
  }

  @Deployment(testable = false)
  public static WebArchive deployment() {
    return MoreCatDeployment.deployment();
  }

  @Test
  public void should_cache_anEntry() throws Exception {
    // Setup
    WebTarget target = client.target(deploymentUrl.toString() + RESOURCE_PREFIX + "/entries/2014/1/1/permalink1");
    response = target.request().get();
    assertThat(response.getStatus(), is(Response.Status.OK.getStatusCode()));

    EntityTag eTag = response.getEntityTag();
    response.close();

    // Exercise
    response = target.request().header("If-None-Match", eTag).get();
    // Verify
    assertThat(response.getStatus(), is(Response.Status.NOT_MODIFIED.getStatusCode()));
    response.close();
  }

  @Test
  public void should_get_recent_entreis() throws Exception {
    // Setup
    WebTarget target = client.target(deploymentUrl.toString() + RESOURCE_PREFIX + "/entries/recent");

    // Exercise
    response = target.request(MediaType.APPLICATION_JSON).get();

    // Verify
    assertThat(response.getStatus(), is(Response.Status.OK.getStatusCode()));
    assertThat(response.readEntity(new GenericType<Pageable<PublishedEntryResponse>>(){}).getElements().size(), is(5));
  }

  @Test
  public void pagination_page_0_size_5_total_10() throws Exception {
    // Setup
    WebTarget target = client.target(deploymentUrl.toString() + RESOURCE_PREFIX + "/entries")
                          .queryParam("page", "0").queryParam("size", "5");

    // Exercise
    response = target.request(MediaType.APPLICATION_JSON).get();
    Pageable<PublishedEntryResponse> responseEntity =
            response.readEntity(new GenericType<Pageable<PublishedEntryResponse>>(){});

    // Verify
    assertThat(responseEntity.isFirstPage(), is(true));
    assertThat(responseEntity.isLastPage(), is(false));
    assertThat(responseEntity.getTotalNumberOfPages(), is(2L));
    assertThat(responseEntity.getCurrentPageSize(), is(5L));
  }

  @Test
  public void pagination_page_1_size_5_total_10() throws Exception {
    // Setup
    WebTarget target = client.target(deploymentUrl.toString() + RESOURCE_PREFIX + "/entries")
                        .queryParam("page", "1").queryParam("size", "5");

    // Exercise
    response = target.request(MediaType.APPLICATION_JSON).get();
    Pageable<PublishedEntryResponse> responseEntity =
      response.readEntity(new GenericType<Pageable<PublishedEntryResponse>>(){});

    // Verify
    assertThat(responseEntity.isFirstPage(), is(false));
    assertThat(responseEntity.isLastPage(), is(true));
    assertThat(responseEntity.getCurrentPageSize(), is(5L));
  }

  @Test
  public void pagination_page_0_size_4_total_10() throws Exception {
    // Setup
    WebTarget target = client.target(deploymentUrl.toString() + RESOURCE_PREFIX + "/entries")
                          .queryParam("page", "0").queryParam("size", "4");

    // Exercise
    response = target.request(MediaType.APPLICATION_JSON).get();
    Pageable<PublishedEntryResponse> responseEntity =
      response.readEntity(new GenericType<Pageable<PublishedEntryResponse>>(){});

    // Verify
    assertThat(responseEntity.isFirstPage(), is(true));
    assertThat(responseEntity.isLastPage(), is(false));
    assertThat(responseEntity.getTotalNumberOfPages(), is(3L));
    assertThat(responseEntity.getCurrentPageSize(), is(4L));
  }

  @Test
  public void pagination_page_1_size_4_total_10() throws Exception {
    // Setup
    WebTarget target = client.target(deploymentUrl.toString() + RESOURCE_PREFIX + "/entries")
                          .queryParam("page", "1").queryParam("size", "4");

    // Exercise
    response = target.request(MediaType.APPLICATION_JSON).get();
    Pageable<PublishedEntryResponse> responseEntity =
      response.readEntity(new GenericType<Pageable<PublishedEntryResponse>>(){});

    // Verify
    assertThat(responseEntity.isFirstPage(), is(false));
    assertThat(responseEntity.isLastPage(), is(false));
    assertThat(responseEntity.getCurrentPageSize(), is(4L));
  }

  @Test
  public void pagination_page_2_size_4_total_10() throws Exception {
    // Setup
    WebTarget target = client.target(deploymentUrl.toString() + RESOURCE_PREFIX + "/entries")
                          .queryParam("page", "2").queryParam("size", "4");

    // Exercise
    response = target.request(MediaType.APPLICATION_JSON).get();
    Pageable<PublishedEntryResponse> responseEntity =
      response.readEntity(new GenericType<Pageable<PublishedEntryResponse>>(){});

    // Verify
    assertThat(responseEntity.isFirstPage(), is(false));
    assertThat(responseEntity.isLastPage(), is(true));
    assertThat(responseEntity.getCurrentPageSize(), is(2L));
  }

  @Test
  public void pagination_tag_page_0_size_5_total_10() throws Exception {
    String tagName = "tag1";
    // Setup
    WebTarget target = client.target(deploymentUrl.toString() + RESOURCE_PREFIX + "/entries/tags/tag1")
      .queryParam("page", "0").queryParam("size", "5");

    // Exercise
    response = target.request(MediaType.APPLICATION_JSON).get();
    Pageable<PublishedEntryResponse> responseEntity =
      response.readEntity(new GenericType<Pageable<PublishedEntryResponse>>() {
      });

    // Verify
    assertThat(responseEntity.isFirstPage(), is(true));
    assertThat(responseEntity.isLastPage(), is(false));
    assertThat(responseEntity.getTotalNumberOfPages(), is(2L));
    assertThat(responseEntity.getCurrentPageSize(), is(5L));
  }

  @Test
  public void pagination_tag_page_1_size_5_total_10() throws Exception {
    // Setup
    WebTarget target = client.target(deploymentUrl.toString() + RESOURCE_PREFIX + "/entries/tags/tag1")
      .queryParam("page", "1").queryParam("size", "5");

    // Exercise
    response = target.request(MediaType.APPLICATION_JSON).get();
    Pageable<PublishedEntryResponse> responseEntity =
      response.readEntity(new GenericType<Pageable<PublishedEntryResponse>>(){});

    // Verify
    assertThat(responseEntity.isFirstPage(), is(false));
    assertThat(responseEntity.isLastPage(), is(true));
    assertThat(responseEntity.getCurrentPageSize(), is(5L));
  }

  @Test
  public void pagination_tag_page_0_size_4_total_10() throws Exception {
    // Setup
    WebTarget target = client.target(deploymentUrl.toString() + RESOURCE_PREFIX + "/entries/tags/tag1")
      .queryParam("page", "0").queryParam("size", "4");

    // Exercise
    response = target.request(MediaType.APPLICATION_JSON).get();
    Pageable<PublishedEntryResponse> responseEntity =
      response.readEntity(new GenericType<Pageable<PublishedEntryResponse>>(){});

    // Verify
    assertThat(responseEntity.isFirstPage(), is(true));
    assertThat(responseEntity.isLastPage(), is(false));
    assertThat(responseEntity.getTotalNumberOfPages(), is(3L));
    assertThat(responseEntity.getCurrentPageSize(), is(4L));
  }

  @Test
  public void pagination_tag_page_1_size_4_total_10() throws Exception {
    // Setup
    WebTarget target = client.target(deploymentUrl.toString() + RESOURCE_PREFIX + "/entries/tags/tag1")
      .queryParam("page", "1").queryParam("size", "4");

    // Exercise
    response = target.request(MediaType.APPLICATION_JSON).get();
    Pageable<PublishedEntryResponse> responseEntity =
      response.readEntity(new GenericType<Pageable<PublishedEntryResponse>>(){});

    // Verify
    assertThat(responseEntity.isFirstPage(), is(false));
    assertThat(responseEntity.isLastPage(), is(false));
    assertThat(responseEntity.getCurrentPageSize(), is(4L));
  }

  @Test
  public void pagination_tag_page_2_size_4_total_10() throws Exception {
    // Setup
    WebTarget target = client.target(deploymentUrl.toString() + RESOURCE_PREFIX + "/entries/tags/tag1")
      .queryParam("page", "2").queryParam("size", "4");

    // Exercise
    response = target.request(MediaType.APPLICATION_JSON).get();
    Pageable<PublishedEntryResponse> responseEntity =
      response.readEntity(new GenericType<Pageable<PublishedEntryResponse>>(){});

    // Verify
    assertThat(responseEntity.isFirstPage(), is(false));
    assertThat(responseEntity.isLastPage(), is(true));
    assertThat(responseEntity.getCurrentPageSize(), is(2L));
  }
}
