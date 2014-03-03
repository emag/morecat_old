package org.emamotor.morecat.repository;

import org.emamotor.morecat.model.Setting;

import javax.ejb.Stateless;

/**
 * @author Yoshimasa Tanabe
 */
@Stateless
public class SettingRepository extends GenericRepository<Setting> {

    public SettingRepository() {
        super(Setting.class);
    }

}
