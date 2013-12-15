package org.emamotor.morecat.model;

import lombok.Data;

import javax.persistence.*;

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

    private String name;

    private String password;

    private String email;

}
