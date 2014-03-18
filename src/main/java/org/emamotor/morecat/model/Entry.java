package org.emamotor.morecat.model;

import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author tanabe
 */
@Entity
@Table(
  name = "entries",
  uniqueConstraints = @UniqueConstraint(columnNames = {"permalink", "created_date"})
)
@Data
public class Entry extends BaseEntity {

  @Column(nullable = false)
  @NotEmpty(message = "Title must not be empty")
  private String title;

  @Column(nullable = false)
  @NotNull
  private String content;

  @Column(nullable = false)
  @NotEmpty(message = "Permalink must not be empty")
  private String permalink;

  @ManyToOne(optional = false)
  private User author;

  @Temporal(TemporalType.DATE)
  @Column(name = "created_date", nullable = false)
  private Date createdDate;

  @Temporal(TemporalType.TIME)
  @Column(name = "created_time", nullable = false)
  private Date createdTime;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "tags")
  @Column(name = "value")
  @OrderBy
  private Set<String> tags = new HashSet<>();

  @Column(nullable = false)
  @NotNull
  @Enumerated(EnumType.STRING)
  private EntryState state;

  @Column(nullable = false)
  @NotNull
  @Enumerated(EnumType.STRING)
  private EntryFormat format;

  @PrePersist
  private void prePersist() {
    setRandomPermalinkIfNotSet();
    setCreatedDate(new Date());
    setCreatedTime(new Date());
  }

  @PreUpdate
  private void preUpdate() {
    setRandomPermalinkIfNotSet();
  }

  private void setRandomPermalinkIfNotSet() {
    if (StringUtils.isBlank(getPermalink())) {
      setPermalink(RandomStringUtils.randomNumeric(10));
    }
  }

}
