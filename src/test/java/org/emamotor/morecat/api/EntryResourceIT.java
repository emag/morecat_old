package org.emamotor.morecat.api;

import org.emamotor.morecat.MoreCatDeployment;
import org.emamotor.morecat.model.Entry;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.EntityTag;
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
    WebTarget target = client.target(deploymentUrl.toString() + RESOURCE_PREFIX + "/entries/2013/1/1/permalink1");
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

}
