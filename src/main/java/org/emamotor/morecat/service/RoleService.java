package org.emamotor.morecat.service;

import org.emamotor.morecat.model.Role;
import org.emamotor.morecat.repository.RoleRepository;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Yoshimasa Tanabe
 */
public class RoleService {

  @Inject
  private RoleRepository roleRepository;

  public List<Role> findAll() {
    return roleRepository.findAll();
  }

}
