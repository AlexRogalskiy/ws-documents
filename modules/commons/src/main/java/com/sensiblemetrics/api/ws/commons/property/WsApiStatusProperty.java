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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sensiblemetrics.api.ws.commons.utils.ServiceUtils;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.net.InetAddress;
import java.util.Optional;

@Data
@Validated
@Accessors(chain = true)
@ConfigurationProperties(prefix = WsApiStatusProperty.PROPERTY_PREFIX, ignoreInvalidFields = true)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics Commons Api status configuration properties")
public class WsApiStatusProperty {
    /**
     * Default Api status property prefix
     */
    public static final String PROPERTY_PREFIX = "api-status";

    /**
     * API status info parameters
     */
    @Valid
    @NestedConfigurationProperty
    @NotNull(message = "{property.api-status.node.notNull}")
    private InfoParameters node = new InfoParameters();

    /**
     * API status build parameters
     */
    @Valid
    @NestedConfigurationProperty
    @NotNull(message = "{property.api-status.build.notNull}")
    private BuildParameters build = new BuildParameters();

    /**
     * Enable/disable api status configuration ({@code true} by default)
     */
    private boolean enabled = true;

    @Data
    @Validated
    @Accessors(chain = true)
    public static class InfoParameters {
        /**
         * Default info name parameter
         */
        @NotBlank(message = "{property.api-status.node.name.notBlank}")
        private String name;
    }

    @Data
    @Validated
    @Accessors(chain = true)
    public static class BuildParameters {
        /**
         * Default groupId artifact parameter
         */
        @NotBlank(message = "{property.api-status.build.group-id.notBlank}")
        private String groupId;

        /**
         * Default build artifact parameter
         */
        @NotBlank(message = "{property.api-status.build.artifact-id.notBlank}")
        private String artifactId;

        /**
         * Default build version parameter
         */
        @NotBlank(message = "{property.api-status.build.version.notBlank}")
        private String version;

        /**
         * Default build name parameter
         */
        @NotBlank(message = "{property.api-status.build.name.notBlank}")
        private String name;

        /**
         * Default build timestamp parameter
         */
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", locale = "ENGLISH")
        @DateTimeFormat(pattern = "dd/MM/yyyy")
        @NotBlank(message = "{property.api-status.build.timestamp.notBlank}")
        private String timestamp;
    }

    /**
     * Returns IPPattern {@link InetAddress} by current host value
     *
     * @return IPPattern {@link InetAddress}
     */
    @Nullable
    public byte[] getIpAddress() {
        return Optional.ofNullable(this.node)
                .map(InfoParameters::getName)
                .map(ServiceUtils::getIpAddress)
                .orElse(null);
    }

    /**
     * Returns {@link InetAddress} by current host value
     *
     * @return {@link InetAddress}
     */
    @Nullable
    public InetAddress getHost() {
        return Optional.ofNullable(this.node)
                .map(InfoParameters::getName)
                .map(ServiceUtils::getHost)
                .orElse(null);
    }
}
