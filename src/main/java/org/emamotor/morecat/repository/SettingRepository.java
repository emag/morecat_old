package org.emamotor.morecat.repository;

import org.emamotor.morecat.model.Setting;

/**
 * @author Yoshimasa Tanabe
 */
public class SettingRepository extends GenericRepository<Setting> {

    public SettingRepository() {
        super(Setting.class);
    }

}
