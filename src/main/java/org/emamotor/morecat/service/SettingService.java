package org.emamotor.morecat.service;

import org.emamotor.morecat.model.Setting;
import org.emamotor.morecat.repository.SettingRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author Yoshimasa Tanabe
 */
@Stateless
public class SettingService {

    @Inject
    private SettingRepository settingRepository;

    public Setting find() {
        return settingRepository.findAll().get(0);
    }

}
