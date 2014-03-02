package org.emamotor.morecat.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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

    @Column(name = "blog_name", nullable = false)
    @NotEmpty
    private String blogName;

    @Column(name = "blog_description", nullable = false)
    @NotNull
    private String blogDescription;

    @Column(nullable = false)
    @NotNull
    private boolean secret;

}
