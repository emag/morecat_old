package org.emamotor.morecat.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author Yoshimasa Tanabe
 */
@Entity
@Table(name = "media")
@Data
@EqualsAndHashCode(callSuper = false)
public class Media extends BaseEntity {

  @Column(nullable = false)
  @NotNull
  private String uuid;

  @Column(nullable = false)
  @NotNull
  private String name;

  @Lob
  @Column(nullable = false)
  @NotNull
  private byte[] content;

  @ManyToOne
  @JoinColumn(name = "author_id", nullable = false)
  @NotNull
  private User author;

  @PrePersist
  private void prePersist() {
    setUuid(UUID.randomUUID().toString());
  }

}
