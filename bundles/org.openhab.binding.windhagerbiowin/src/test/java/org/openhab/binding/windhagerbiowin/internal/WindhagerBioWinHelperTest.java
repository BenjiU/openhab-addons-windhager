/*
 * Copyright (c) 2010-2025 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.windhagerbiowin.internal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;
import org.openhab.binding.windhagerbiowin.helper.WindhagerBioWinHelper;

public class WindhagerBioWinHelperTest {
    @Test
    public void testGGT() {
        assertThat(WindhagerBioWinHelper.ggt(35, 45), equalTo(5));
        assertThat(WindhagerBioWinHelper.ggt(60, 3600), equalTo(60));
        assertThat(WindhagerBioWinHelper.ggt(60, 7), equalTo(1));
    }

    @Test
    public void testGGTArray() {
        int intervalls[] = { 30, 600, 3600, 9 };
        assertThat(WindhagerBioWinHelper.ggt(intervalls), equalTo(3));
        int intervalls_empty[] = {};
        assertThat(WindhagerBioWinHelper.ggt(intervalls_empty), equalTo(0));
        int intervalls_min[] = { 100, 2000 };
        assertThat(WindhagerBioWinHelper.ggt(intervalls_min), equalTo(100));
    }
}
