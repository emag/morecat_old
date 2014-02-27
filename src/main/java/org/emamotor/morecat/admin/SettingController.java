package org.emamotor.morecat.admin;

import org.emamotor.morecat.model.Setting;
import org.emamotor.morecat.service.SettingService;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

/**
 * @author Yoshimasa Tanabe
 */
@Model
public class SettingController {

    @Inject
    private SettingService settingService;

    private Setting setting;

    public Setting getSetting() {
        return settingService.find();
    }

}
