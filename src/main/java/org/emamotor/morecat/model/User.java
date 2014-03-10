package org.emamotor.morecat.model;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author tanabe
 */
@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    @NotEmpty
    private String name;

    @Basic(optional = false)
    @NotEmpty
    private String password;

    @Column(nullable = false, unique = true)
    @NotEmpty
    @Email
    private String email;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Role> role = new HashSet<>();

}
