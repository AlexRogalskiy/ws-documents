/**
 * EXCEPT WHERE OTHERWISE STATED, THE INFORMATION AND SOURCE CODE CONTAINED
 * HEREIN AND IN RELATED FILES IS THE EXCLUSIVE PROPERTY OF PARAGON SOFTWARE
 * GROUP COMPANY AND MAY NOT BE EXAMINED, DISTRIBUTED, DISCLOSED, OR REPRODUCED
 * IN WHOLE OR IN PART WITHOUT EXPLICIT WRITTEN AUTHORIZATION FROM THE COMPANY.
 * <p>
 * Copyright (c) 1994-2019 Paragon Software Group, All rights reserved.
 * </p>
 * UNLESS OTHERWISE AGREED IN A WRITING SIGNED BY THE PARTIES, THIS SOFTWARE IS
 * PROVIDED "AS-IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE, ALL OF WHICH ARE HEREBY DISCLAIMED. IN NO EVENT SHALL THE
 * AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF NOT ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.sensiblemetrics.api.ws.commons.property;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.time.DurationMin;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationFormat;
import org.springframework.boot.convert.DurationStyle;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static com.sensiblemetrics.api.ws.commons.property.PropertySettings.PROPERTY_DELIMITER;

@Data
@Validated
@Accessors(chain = true)
@ConfigurationProperties(prefix = WsGracefulShutdownProperty.PROPERTY_PREFIX, ignoreInvalidFields = true)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics Commons Graceful Shutdown configuration properties")
public class WsGracefulShutdownProperty {
    /**
     * Default graceful shutdown property prefix
     */
    public static final String PROPERTY_PREFIX = WsApiStatusProperty.PROPERTY_PREFIX + PROPERTY_DELIMITER + "shutdown";

    /**
     * Milliseconds to wait before /internal/health is starting to respond with server errors,
     * after shutdown signal is retrieved.
     * <p> Default:
     * {@code api-status.shutdown.indicateErrorAfter = 5000}
     * </p>
     */
    @DurationUnit(ChronoUnit.MILLIS)
    @DurationFormat(DurationStyle.SIMPLE)
    @DurationMin(message = "{property.api-status.shutdown.indicate-error-after.min}")
    @NotNull(message = "{property.api-status.shutdown.indicate-error-after.notNull}")
    private Duration indicateErrorAfter = Duration.ofMillis(5000);

    /**
     * Milliseconds to respond /internal/health checks with server errors, before actually shutting down the application.
     * <p> Default:
     * api-status.shutdown.phaseOutAfter = 20000
     * </p>
     */
    @DurationUnit(ChronoUnit.MILLIS)
    @DurationFormat(DurationStyle.SIMPLE)
    @DurationMin(millis = 100, message = "{property.api-status.shutdown.phase-out-after.min}")
    @NotNull(message = "{property.api-status.shutdown.phase-out-after.notNull}")
    private Duration phaseOutAfter = Duration.ofMillis(20000);

    /**
     * Enable/disable graceful shutdown configuration ({@code false} by default)
     * <p>
     * Disabling the shutdown is especially required in test cases, where you do not want to wait for 25sec. to stop
     * a test server instance.
     * </p>
     */
    private boolean enabled = false;
}
