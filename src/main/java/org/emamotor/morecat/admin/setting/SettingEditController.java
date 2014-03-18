package org.emamotor.morecat.admin.setting;

import lombok.Getter;
import lombok.Setter;
import org.emamotor.morecat.model.Setting;
import org.emamotor.morecat.service.SettingService;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Yoshimasa Tanabe
 */
@Model
public class SettingEditController {

  @Inject
  private SettingService settingService;

  @Getter
  @Setter
  private Setting setting;

  @PostConstruct
  public void init() {
    setting = settingService.find();
  }

  public String doSave() {
    settingService.update(this.setting);
    return "view?faces-redirect=true";
  }

}
