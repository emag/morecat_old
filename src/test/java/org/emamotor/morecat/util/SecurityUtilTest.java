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
    private static final String CONTEXT_PATH = "mc-admin";
    private static final String API_PATH = "mc-admin";

    @Test
    public void should_return_true_for_valid_referer() throws Exception {
        // Verify
        assertThat(SecurityUtil.validateReferer(
                SCHEMA, DOMAIN, PORT, CONTEXT_PATH, API_PATH,
                "http://localhost:8080/morecat-api/mc-admin/entries/view.xhtml"), is(true));

    }

    @Test
    public void should_return_false_for_invalid_referer() throws Exception {
        // Verify
        assertThat(SecurityUtil.validateReferer(
                SCHEMA, DOMAIN, PORT, CONTEXT_PATH, API_PATH,
                "https://twitter.com/HiromitsuTakagi/status/350971098248118272"), is(false));

    }

}
