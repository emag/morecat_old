package org.emamotor.morecat.model;

import javax.persistence.*;

/**
 * @author tanabe
 */
@Entity
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "blog_name")
    private String blogName;

}
