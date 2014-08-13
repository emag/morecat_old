package org.emamotor.morecat.service;

import org.emamotor.morecat.model.User;
import org.emamotor.morecat.repository.UserRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * @author Yoshimasa Tanabe
 */
@Stateless
public class UserService {

  @Inject
  private UserRepository userRepository;

  public List<User> findAll() {
    return userRepository.findAll();
  }

  public User findById(Integer id) {
    return userRepository.findById(id);
  }

  public User findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  public User update(User user) {
    return userRepository.update(user);
  }

}
