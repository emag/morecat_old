package org.emamotor.morecat.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author tanabe
 */
@Entity
@Table(name = "settings")
@Data
public class Setting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "blog_name")
    private String blogName;

}
