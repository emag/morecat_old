package org.emamotor.morecat.admin;

import org.emamotor.morecat.util.Version;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 * @author Yoshimasa Tanabe
 */
@ApplicationScoped
@Named
public class VersionController {

    private static final String VERSION = Version.getInstance().getVersion();

    public String getVersion() {
        return VERSION;
    }

}
