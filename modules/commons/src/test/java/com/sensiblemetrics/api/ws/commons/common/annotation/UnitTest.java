package com.sensiblemetrics.api.ws.commons.common.annotation;

import com.sensiblemetrics.api.ws.commons.common.flow.TestFlow;
import io.qameta.allure.Epic;
import org.junit.jupiter.api.Tag;

import java.lang.annotation.*;

@Documented
@Inherited
@Tag(TestFlow.FlowUnit.UNIT_TEST)
@Epic(TestFlow.FlowUnit.UNIT_TEST)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface UnitTest {
}
