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

import static org.openhab.binding.windhagerbiowin.internal.WindhagerBioWinBindingConstants.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.core.thing.ChannelUID;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.ThingStatus;
import org.openhab.core.thing.ThingTypeUID;
import org.openhab.core.thing.binding.BaseThingHandler;
import org.openhab.core.types.Command;
import org.openhab.core.types.RefreshType;
import org.openhab.core.types.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link WindhagerBioWinHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Benjamin Utz - Initial contribution
 */
@NonNullByDefault
public class WindhagerBioWinHandler extends BaseThingHandler {

    private final Logger logger = LoggerFactory.getLogger(WindhagerBioWinHandler.class);

    private @Nullable WindhagerBioWinConfiguration config;
    private @Nullable NettyClientDemo ncd;

    private @Nullable ScheduledFuture f1;

    /**
     * Support variable for type of thing
     */
    protected ThingTypeUID type;

    /**
     * Array of registers of Studer slave to read, we store this once initialization is complete
     */
    private String[] oids = new String[0];

    /**
     * Instances of this handler
     *
     * @param thing the thing to handle
     */
    public WindhagerBioWinHandler(Thing thing) {
        super(thing);
        this.type = thing.getThingTypeUID();
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        if (CHANNEL_BIOWIN_PELLET_TOTAL.equals(channelUID.getId())) {
            if (command instanceof RefreshType) {
                // TODO: handle data refresh
            }

            // TODO: handle command

            // Note: if communication with thing fails for some reason,
            // indicate that by setting the status with detail information:
            // updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR,
            // "Could not control device at IP address x.x.x.x");
        }
    }

    @Override
    public void initialize() {
        config = getConfigAs(WindhagerBioWinConfiguration.class);

        // TODO: Initialize the handler.
        // The framework requires you to return from this method quickly, i.e. any network access must be done in
        // the background initialization below.
        // Also, before leaving this method a thing status from one of ONLINE, OFFLINE or UNKNOWN must be set. This
        // might already be the real thing status in case you can decide it directly.
        // In case you can not decide the thing status directly (e.g. for long running connection handshake using WAN
        // access or similar) you should set status UNKNOWN here and then decide the real status asynchronously in the
        // background.

        // set the thing status to UNKNOWN temporarily and let the background task decide for the real status.
        // the framework is then able to reuse the resources from the thing handler initialization.
        // we set this upfront to reliably check status updates in unit tests.
        updateStatus(ThingStatus.UNKNOWN);

        startUp();
    }

    /*
     * This method starts the operation of this handler
     * Connect to the slave bridge
     * Get registers to poll
     * Start the periodic polling
     */
    private void startUp() {
        connectEndpoint();

        createPollThread();
    }

    private void createPollThread() {
        f1 = scheduler.scheduleAtFixedRate(() -> {
            logger.warn("TODO: thread to data refresh");
            try {
                URI uri_verbrauch = new URI("http://10.10.10.22:80/api/1.0/datapoint/1/60/0/23/103/0");
                ncd.request(uri_verbrauch, (String s, State val) -> {
                    internalUpdateState(s, val);
                });

            } catch (URISyntaxException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }, config.refreshInterval, config.refreshInterval, TimeUnit.SECONDS);

        // These logging types should be primarily used by bindings
        // logger.trace("Example trace message");
        // logger.debug("Example debug message");
        logger.warn("Example warn message");
        //
        // Logging to INFO should be avoided normally.
        // See https://www.openhab.org/docs/developer/guidelines.html#f-logging

        // Note: When initialization can NOT be done set the status with more details for further
        // analysis. See also class ThingStatusDetail for all available status details.
        // Add a description to give user information to understand why thing does not work as expected. E.g.
        // updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
        // "Can not access device as username and/or password are invalid");
    }

    /**
     * Get a reference to the modbus endpoint
     */
    private void connectEndpoint() {
        if (ncd != null) {
            return;
        }

        ncd = new NettyClientDemo("Service", "6FN&4#w1Hb-7");

        updateStatus(ThingStatus.ONLINE);
    }

    protected void internalUpdateState(@Nullable String channelUID, @Nullable State state) {
        if (channelUID != null && state != null) {
            super.updateState(channelUID, state);
        }
    }
}
