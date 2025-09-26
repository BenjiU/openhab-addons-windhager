/**
 * Copyright (c) 2010-2023 Contributors to the openHAB project
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

import static org.openhab.core.library.unit.SIUnits.CELSIUS;
import static org.openhab.core.library.unit.SIUnits.KILOGRAM;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.measure.Unit;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.openhab.core.thing.ThingTypeUID;

/**
 * The {@link WindhagerBioWinBindingConstants} class defines common constants, which are
 * used across the whole binding.
 *
 * @author Benjamin Utz - Initial contribution
 */
@NonNullByDefault
public class WindhagerBioWinBindingConstants {

    private static final String BINDING_ID = "windhagerbiowin";

    // List of all Thing Type UIDs
    public static final ThingTypeUID THING_TYPE_BIOWIN = new ThingTypeUID(BINDING_ID, "BioWin");
    public static final ThingTypeUID THING_TYPE_PUFFER = new ThingTypeUID(BINDING_ID, "Pufferspeicher");
    public static final ThingTypeUID THING_TYPE_HEIZKREIS = new ThingTypeUID(BINDING_ID, "Heizkreis");

    public static final Set<ThingTypeUID> SUPPORTED_THING_TYPES_UIDS = new HashSet<>();
    static {
        SUPPORTED_THING_TYPES_UIDS.add(THING_TYPE_BIOWIN);
        SUPPORTED_THING_TYPES_UIDS.add(THING_TYPE_PUFFER);
        SUPPORTED_THING_TYPES_UIDS.add(THING_TYPE_HEIZKREIS);
    }

    // List of all Channel ids
    public static final String CHANNEL_BIOWIN_PELLET_TOTAL = "PelletTotal";
    public static final String CHANNEL_BIOWIN_KESSEL_TEMP_IST = "Kesseltemperatur-Ist";

    /**
     * Map of the supported BSP channel with their registers
     */
    public static final Map<String, String> CHANNELS_BIOWIN = new HashMap<>();
    static {
        CHANNELS_BIOWIN.put("1/60/0/23/103/0", CHANNEL_BIOWIN_PELLET_TOTAL);
        CHANNELS_BIOWIN.put("1/60/0/0/7/0", CHANNEL_BIOWIN_KESSEL_TEMP_IST);
    }

    /**
     * Map of the supported BSP channel with their unit
     */
    public static final Map<String, Unit<?>> UNIT_CHANNELS_BIOWIN = new HashMap<>();
    static {
        UNIT_CHANNELS_BIOWIN.put("1/60/0/23/103/0", KILOGRAM);
        UNIT_CHANNELS_BIOWIN.put("1/60/0/0/7/0", CELSIUS);
    }
}
