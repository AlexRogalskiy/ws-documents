package com.sensiblemetrics.api.ws.document.uploader.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.UtilityClass;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.util.Objects;

import static com.sensiblemetrics.api.ws.document.uploader.model.entity.BaseAuditEntity.Info.ID_FIELD_NAME;
import static com.sensiblemetrics.api.ws.document.uploader.model.entity.BaseAuditEntity.Info.ROW_VERSION_FIELD_NAME;

/**
 * Base {@link AuditEntity} model
 *
 * @param <ID> type of {@link AuditEntity} identifier {@link Serializable}
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseAuditEntity<ID extends Serializable> extends AuditEntity<ID> {
    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 6444143028591284804L;

    @Id
    @Basic(optional = false)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "uuid2")
    @NotNull(message = "{model.entity.base-audit.id.notNull}")
    @Column(name = ID_FIELD_NAME, unique = true, nullable = false)
    private ID id;

    @Version
    @PositiveOrZero(message = "{model.entity.base-audit.row-version.positiveOrZero}")
    @Column(name = ROW_VERSION_FIELD_NAME, insertable = false, updatable = false, nullable = false)
    private long rowVersion;

    @Override
    public boolean isNew() {
        return Objects.isNull(this.getId());
    }

    @UtilityClass
    public static final class Info {
        /**
         * Default field names
         */
        static final String ID_FIELD_NAME = "id";
        static final String ROW_VERSION_FIELD_NAME = "row_version";
    }
}
