package com.sensiblemetrics.api.ws.document.generator.model.entity;

import com.sensiblemetrics.api.ws.commons.constraint.ConstraintGroup;
import com.sensiblemetrics.api.ws.document.generator.model.constraint.ChronologicalDates;
import com.sensiblemetrics.api.ws.document.generator.model.converter.TenantGenerator;
import lombok.Data;
import lombok.experimental.UtilityClass;
import org.hibernate.annotations.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.domain.Auditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.PastOrPresent;
import java.io.Serializable;
import java.time.Instant;
import java.util.Optional;

import static com.sensiblemetrics.api.ws.document.generator.model.entity.AuditEntity.Info.*;

/**
 * {@link Auditable} entity model
 *
 * @param <ID> type of entity {@link Serializable} identifier
 */
@Data
@DynamicInsert
@DynamicUpdate
@MappedSuperclass
@ChronologicalDates
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditEntity<ID extends Serializable>
    implements Auditable<String, ID, Instant>, Serializable {
  /** Default explicit serialVersionUID for interoperability */
  private static final long serialVersionUID = 431774856692738135L;

  @PastOrPresent
  @CreationTimestamp
  @NotNull(
      groups = {ConstraintGroup.OnUpdate.class, ConstraintGroup.OnSelect.class},
      message = "{model.entity.audit.created-date.notNull}")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = DATE_TIME_PATTERN)
  @Column(name = CREATED_FIELD_NAME, nullable = false, updatable = false)
  private Instant createdDate;

  @CreatedBy
  @Basic(fetch = FetchType.LAZY)
  @NotNull(
      groups = {ConstraintGroup.OnUpdate.class, ConstraintGroup.OnSelect.class},
      message = "{model.entity.audit.created-by.notNull}")
  @Column(name = CREATED_BY_FIELD_NAME, nullable = false, updatable = false, length = 512)
  @GeneratorType(type = TenantGenerator.class, when = GenerationTime.INSERT)
  private String createdBy;

  @PastOrPresent
  @UpdateTimestamp
  @Null(
      groups = ConstraintGroup.OnCreate.class,
      message = "{model.entity.audit.last-modified-date.null}")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = DATE_TIME_PATTERN)
  @Column(name = MODIFIED_FIELD_NAME, insertable = false)
  private Instant lastModifiedDate;

  @LastModifiedBy
  @Basic(fetch = FetchType.LAZY)
  @Null(
      groups = ConstraintGroup.OnCreate.class,
      message = "{model.entity.audit.last-modified-by.null}")
  @Column(name = MODIFIED_BY_FIELD_NAME, insertable = false, length = 512)
  @GeneratorType(type = TenantGenerator.class, when = GenerationTime.ALWAYS)
  private String lastModifiedBy;

  @NonNull
  @Override
  public Optional<Instant> getCreatedDate() {
    return Optional.ofNullable(this.createdDate);
  }

  @NonNull
  @Override
  public Optional<String> getCreatedBy() {
    return Optional.ofNullable(this.createdBy);
  }

  @NonNull
  @Override
  public Optional<Instant> getLastModifiedDate() {
    return Optional.ofNullable(this.lastModifiedDate);
  }

  @NonNull
  @Override
  public Optional<String> getLastModifiedBy() {
    return Optional.ofNullable(this.lastModifiedBy);
  }

  @UtilityClass
  public static final class Info {
    /** Default date time pattern */
    static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    /** Default field names */
    static final String CREATED_FIELD_NAME = "created";

    static final String MODIFIED_FIELD_NAME = "modified";
    static final String CREATED_BY_FIELD_NAME = "created_by";
    static final String MODIFIED_BY_FIELD_NAME = "modified_by";
  }
}
