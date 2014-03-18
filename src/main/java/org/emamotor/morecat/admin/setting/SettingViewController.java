package org.emamotor.morecat.admin.setting;

import lombok.Getter;
import org.emamotor.morecat.model.Setting;
import org.emamotor.morecat.service.SettingService;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

/**
 * @author Yoshimasa Tanabe
 */
@Model
public class SettingViewController {

  @Inject
  private SettingService settingService;

  @Getter
  private Setting setting;

  @PostConstruct
  public void init() {
    setting = settingService.find();
  }

}
