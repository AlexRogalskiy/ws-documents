package com.sensiblemetrics.api.ws.document.generator.model.entity;

import com.sensiblemetrics.api.ws.document.generator.enumeration.StatusType;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.EnumType;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Optional;

/**
 * Status {@link EnumType} implementation
 */
public class StatusEnumType extends EnumType<StatusType> {

    @Override
    public void nullSafeSet(final PreparedStatement st,
                            final Object value,
                            final int index,
                            final SharedSessionContractImplementor session) throws HibernateException, SQLException {
        final String target = Optional.ofNullable(value).map(v -> ((Enum<?>) v).name()).orElse(StatusType.NEW.name());
        st.setObject(index, target, Types.OTHER);
    }
}
