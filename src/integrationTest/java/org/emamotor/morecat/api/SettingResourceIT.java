package org.emamotor.morecat.api;

import org.emamotor.morecat.MoreCatDeployment;
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
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URL;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@RunWith(Arquillian.class)
@RunAsClient
public class SettingResourceIT {

  private static final String RESOURCE_PREFIX = JAXRSActivator.class.getAnnotation(ApplicationPath.class).value().substring(1);

  @Deployment(testable = false)
  public static WebArchive deployment() {
    return MoreCatDeployment.deployment();
  }

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

  @Test
  public void get_blog_name() throws Exception {
    // Setup
    WebTarget target = client.target(deploymentUrl.toString() + RESOURCE_PREFIX + "/settings/blog-name");

    // Exercise
    response = target.request(MediaType.APPLICATION_JSON).get();

    // Verify
    assertThat(response.getStatus(), is(Response.Status.OK.getStatusCode()));
    assertThat(response.readEntity(new GenericType<String>(){}), is("default_name"));
  }

  @Test
  public void get_blog_description() throws Exception {
    // Setup
    WebTarget target = client.target(deploymentUrl.toString() + RESOURCE_PREFIX + "/settings/blog-description");

    // Exercise
    response = target.request(MediaType.APPLICATION_JSON).get();

    // Verify
    assertThat(response.getStatus(), is(Response.Status.OK.getStatusCode()));
    assertThat(response.readEntity(new GenericType<String>(){}), is("default_description"));
  }

}