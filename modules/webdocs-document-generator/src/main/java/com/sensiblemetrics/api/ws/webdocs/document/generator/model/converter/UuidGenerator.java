package com.sensiblemetrics.api.ws.webdocs.document.generator.model.converter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.util.ReflectionUtils;

import javax.persistence.Id;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.UUID;

@Slf4j
public class UuidGenerator implements IdentifierGenerator {

  @Override
  public Serializable generate(
      final SharedSessionContractImplementor session, final Object object) {
    try {
      Serializable id = getId(object);
      if (id != null) {
        return id;
      }
    } catch (Exception e) {
      log.error("Qasl generator exception: {}", e.getMessage());
      return UUID.randomUUID();
    }
    return UUID.randomUUID();
  }

  private static Serializable getId(final Object entity) {
    if (entity instanceof HibernateProxy) {
      HibernateProxy proxy = (HibernateProxy) entity;
      return getValue(getIdField(proxy.getHibernateLazyInitializer().getPersistentClass()), entity);
    }
    return getValue(getIdField(entity.getClass()), entity);
  }

  private static Field getIdField(final Class<?> entityClass) {
    return ReflectionUtils.findField(
        entityClass, new ReflectionUtils.AnnotationFieldFilter(Id.class), true);
  }

  private static Serializable getValue(final Field field, final Object entity) {
    if (entity instanceof HibernateProxy) {
      return (Serializable) ((HibernateProxy) entity).getHibernateLazyInitializer().getIdentifier();
    }
    try {
      return (Serializable) FieldUtils.readField(field, entity, true);
    } catch (IllegalAccessException ignored) {
      return null;
    }
  }
}
