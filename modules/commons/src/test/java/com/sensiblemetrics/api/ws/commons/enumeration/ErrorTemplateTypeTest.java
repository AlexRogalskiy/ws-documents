package com.sensiblemetrics.api.ws.commons.enumeration;

import com.sensiblemetrics.api.ws.commons.common.flow.TestTag;
import com.sensiblemetrics.api.ws.commons.common.annotation.UnitTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@UnitTest
@Tag(TestTag.ENUMERATION)
class ErrorTemplateTypeTest {

    @Test
    void test_check_ErrorTemplateType_By_Name() {
        assertThat(ErrorTemplateType.SERVICE_UNAVAILABLE.toString(), equalTo("error-30"));
        assertThat(ErrorTemplateType.SERVICE_OPERATION_ERROR.toString(), equalTo("error-40"));
        assertThat(ErrorTemplateType.BAD_REQUEST.toString(), equalTo("error-50"));
        assertThat(ErrorTemplateType.DOCUMENT_PROCESSING_ERROR.toString(), equalTo("error-50"));
        assertThat(ErrorTemplateType.INVALID_ENDPOINT_CONFIGURATION.toString(), equalTo("error-60"));
    }

    @Test
    void test_check_ErrorTemplateType_By_Value() {
        assertThat(ErrorTemplateType.valueOf("SERVICE_UNAVAILABLE"), equalTo(ErrorTemplateType.SERVICE_UNAVAILABLE));
        assertThat(ErrorTemplateType.valueOf("SERVICE_OPERATION_ERROR"), equalTo(ErrorTemplateType.SERVICE_OPERATION_ERROR));
        assertThat(ErrorTemplateType.valueOf("BAD_REQUEST"), equalTo(ErrorTemplateType.BAD_REQUEST));
        assertThat(ErrorTemplateType.valueOf("DOCUMENT_PROCESSING_ERROR"), equalTo(ErrorTemplateType.DOCUMENT_PROCESSING_ERROR));
        assertThat(ErrorTemplateType.valueOf("INVALID_ENDPOINT_CONFIGURATION"), equalTo(ErrorTemplateType.INVALID_ENDPOINT_CONFIGURATION));
    }

    @Test
    void test_check_ErrorTemplateType_By_ValueOf_with_Class() {
        // given
        final String value = "SERVICE_UNAVAILABLE";

        // when
        final ErrorTemplateType actual = ErrorTemplateType.valueOf(ErrorTemplateType.class, value);

        // then
        assertThat(actual, equalTo(ErrorTemplateType.SERVICE_UNAVAILABLE));
    }

    @Test
    void test_check_ErrorTemplateType_whenPassed_KEY() {
        // given
        final String value = "error-30";
        final String description = "error.service.unavailable";

        // when
        final ErrorTemplateType actual = ErrorTemplateType.findByCode(value);

        // then
        assertNotNull(actual, "Error template not exist");
        assertThat(actual.getErrorCode(), equalTo(value));
        assertThat(actual.getErrorMessage(), equalTo(description));
    }

    @Test
    void test_get_ErrorTemplateType_whenPassed_NON_EXISTED_Value() {
        // given
        final String errorMessage = "No enum constant";

        // when
        final IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> ErrorTemplateType.valueOf("NON_EXISTED"));

        // then
        assertThat(thrown.getMessage(), startsWith(errorMessage));
    }
}
