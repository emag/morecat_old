package org.emamotor.morecat.util;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * @author tanabe
 */
public class SecurityUtilTest {

    private static final String SCHEMA = "http";
    private static final String DOMAIN = "localhost";
    private static final int PORT = 8080;
    private static final String PATH = "/morecat-api/mc-admin/";

    @Test
    public void should_return_true_for_valid_referer() throws Exception {
        // Verify
        assertThat(SecurityUtil.validateReferer(
                SCHEMA, DOMAIN, PORT, PATH,
                "http://localhost:8080/morecat-api/mc-admin/entries/view.xhtml"), is(true));
    }

    @Test
    public void should_return_false_for_invalid_referer() throws Exception {
        // Verify
        assertThat(SecurityUtil.validateReferer(
                SCHEMA, DOMAIN, PORT, PATH,
                "https://twitter.com/HiromitsuTakagi/status/350971098248118272"), is(false));
    }

    @Test
    public void should_return_false_for_invalid_scheme_referer() throws Exception {
        // Verify
        assertThat(SecurityUtil.validateReferer(
                SCHEMA, DOMAIN, PORT, PATH,
                "https://localhost:8080/morecat-api/mc-admin/entries/view.xhtml"), is(false));
    }

    @Test
    public void should_return_false_for_invalid_domain_referer() throws Exception {
        // Verify
        assertThat(SecurityUtil.validateReferer(
                SCHEMA, DOMAIN, PORT, PATH,
                "http://invalid.com:8080/morecat-api/mc-admin/entries/view.xhtml"), is(false));
    }

    @Test
    public void should_return_false_for_invalid_port_referer() throws Exception {
        // Verify
        assertThat(SecurityUtil.validateReferer(
                SCHEMA, DOMAIN, PORT, PATH,
                "http://localhost:8000/morecat-api/mc-admin/entries/view.xhtml"), is(false));
    }

    @Test
    public void should_return_false_for_invalid_path_referer() throws Exception {
        // Verify
        assertThat(SecurityUtil.validateReferer(
                SCHEMA, DOMAIN, PORT, PATH,
                "http://localhost:8080/morecat-api/invalid/entries/view.xhtml"), is(false));
    }

}
