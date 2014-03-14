package org.emamotor.morecat.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author tanabe
 */
public class SecurityUtil {

    public static boolean validateReferer(String scheme,
                                          String domain,
                                          int port,
                                          String contextPath,
                                          String apiPath,
                                          String referer) {

        if (StringUtils.isBlank(referer)) return false;
        return true;

    }

}
