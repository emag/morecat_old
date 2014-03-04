package org.emamotor.morecat.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author tanabe
 */
@Entity
@Table(name = "entries")
@Data
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @NotEmpty
    private String title;

    @Column(nullable = false)
    @NotNull
    private String content;

    @ManyToOne(optional = false)
    private User author;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified_at", nullable = false)
    private Date lastModifiedAt;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "tags")
    @Column(name = "value")
    private Set<String> tags = new HashSet<>();

    @Column(nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    private EntryState state;

    @Column(nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    private EntryFormat format;

}
