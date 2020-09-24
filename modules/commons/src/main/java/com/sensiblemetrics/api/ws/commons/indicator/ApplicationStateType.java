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
package com.sensiblemetrics.api.ws.commons.indicator;

import org.springframework.lang.NonNull;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Application state {@link Enum} type
 */
public enum ApplicationStateType {
    /**
     * Application is up and running
     */
    OK,
    /**
     * Application is not running or suspended
     */
    DOWN;

    /**
     * Returns {@link List} of all {@link ApplicationStateType}s
     *
     * @return {@link List} of all {@link ApplicationStateType}s
     */
    @NonNull
    public static List<ApplicationStateType> valuesList() {
        return Collections.unmodifiableList(asList(ApplicationStateType.values()));
    }
}
