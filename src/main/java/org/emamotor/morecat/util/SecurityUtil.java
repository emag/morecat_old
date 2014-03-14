package org.emamotor.morecat.util;

import org.apache.commons.lang3.StringUtils;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author tanabe
 */
public class SecurityUtil {

    public static boolean validateReferer(String scheme,
                                          String domain,
                                          int port,
                                          String path,
                                          String referer) {

        if (StringUtils.isBlank(referer)) return false;

        try {

            URI refererURI = new URI(referer);

            System.out.println(refererURI.toString());
            System.out.println(refererURI.getScheme());
            System.out.println(refererURI.getHost());
            System.out.println(refererURI.getPort());
            System.out.println(refererURI.getPath() + "\n");

            if (! refererURI.getScheme().equals(scheme)) return false;
            if (! refererURI.getHost().equals(domain)) return false;
            if (! (refererURI.getPort() == port)) return false;
            if (! refererURI.getPath().startsWith(path)) return false;

        } catch (URISyntaxException e) {
            return false;
        }

        return true;

    }

}
