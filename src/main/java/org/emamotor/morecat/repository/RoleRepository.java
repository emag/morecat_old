package org.emamotor.morecat.repository;

import org.emamotor.morecat.model.Role;

import javax.ejb.Stateless;

/**
 * @author tanabe
 */
@Stateless
public class RoleRepository extends GenericRepository<Role> {

  public RoleRepository() {
    super(Role.class);
  }

}
