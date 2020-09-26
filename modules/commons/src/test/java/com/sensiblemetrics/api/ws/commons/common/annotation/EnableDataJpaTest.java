package com.sensiblemetrics.api.ws.commons.common.annotation;

import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.core.annotation.AliasFor;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
@Transactional
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
public @interface EnableDataJpaTest {
    /**
     * Default {@link AutoConfigureTestDatabase.Replace} value
     *
     * @return {@link AutoConfigureTestDatabase.Replace} value
     */
    @AliasFor(annotation = AutoConfigureTestDatabase.class, attribute = "replace")
    AutoConfigureTestDatabase.Replace databaseReplace() default AutoConfigureTestDatabase.Replace.NONE;

    /**
     * Default {@link EmbeddedDatabaseConnection} provider
     *
     * @return {@link EmbeddedDatabaseConnection} provider
     */
    @AliasFor(annotation = AutoConfigureTestDatabase.class, attribute = "connection")
    EmbeddedDatabaseConnection databaseProvider() default EmbeddedDatabaseConnection.H2;
}
