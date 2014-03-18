package org.emamotor.morecat.util;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Yoshimasa Tanabe
 */
public class VersionTest {

  @Test
  public void check_version() throws Exception {

    String version = Version.getInstance().getVersion();
    assertThat(version, is("test"));

  }

}
