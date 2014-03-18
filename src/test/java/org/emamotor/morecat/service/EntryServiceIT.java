package org.emamotor.morecat.service;

import org.emamotor.morecat.MoreCatDeployment;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

/**
 * @author Yoshimasa Tanabe
 */
@RunWith(Arquillian.class)
public class EntryServiceIT {

  @Deployment
  public static WebArchive deployment() {
    return MoreCatDeployment.deployment();
  }

  @Inject
  private EntryService sut;

  @Test
  public void test() throws Exception {
    // Setup
    // Exercise
    // Verify
    assertThat(sut.findAll().size(), is(1));
  }

}
