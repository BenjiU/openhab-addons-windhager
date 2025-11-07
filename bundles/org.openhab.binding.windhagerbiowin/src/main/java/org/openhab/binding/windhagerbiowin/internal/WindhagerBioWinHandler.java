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

import static org.openhab.binding.windhagerbiowin.internal.WindhagerBioWinBindingConstants.*;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.core.thing.Channel;
import org.openhab.core.thing.ChannelUID;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.ThingStatus;
import org.openhab.core.thing.binding.BaseThingHandler;
import org.openhab.core.types.Command;
import org.openhab.core.types.RefreshType;
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

    public WindhagerBioWinHandler(Thing thing) {
        super(thing);
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        // if (CHANNEL_1.equals(channelUID.getId())) {
        if (command instanceof RefreshType) {
            // TODO: handle data refresh
            logger.warn("command '" + command + "' instanceof RefreshType");
        }
        logger.warn("command '" + command + "'");

        // TODO: handle command

        // Note: if communication with thing fails for some reason,
        // indicate that by setting the status with detail information:
        // updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR,
        // "Could not control device at IP address x.x.x.x");
        // }
        logger.warn("handle Command '" + command + "' for '" + channelUID + "'");
    }

    @Override
    public void initialize() {
        config = getConfigAs(WindhagerBioWinConfiguration.class);
        logger.warn("initialize");

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
        logger.warn("startUp");
        connectEndpoint();

        createPollThread();
    }

    private void createPollThread() {
        /*
         * f1 = scheduler.scheduleAtFixedRate(() -> {
         * logger.warn("TODO: thread to data refresh");
         * try {
         * URI uri_verbrauch = new URI("http://10.10.10.22:80/api/1.0/datapoint/1/60/0/23/103/0");
         * ncd.request(uri_verbrauch, (String s, State val) -> {
         * internalUpdateState(s, val);
         * });
         * 
         * } catch (URISyntaxException e) {
         * // TODO Auto-generated catch block
         * e.printStackTrace();
         * }
         * }, config.refreshInterval, config.refreshInterval, TimeUnit.SECONDS);
         */

        // These logging types should be primarily used by bindings
        // logger.trace("Example trace message");
        // logger.debug("Example debug message");
        logger.warn("Example warn message");

        // TODO: Initialize the handler.
        // The framework requires you to return from this method quickly, i.e. any network access must be done in
        // the background initialization below.
        // Also, before leaving this method a thing status from one of ONLINE, OFFLINE or UNKNOWN must be set. This
        // might already be the real thing status in case you can decide it directly.
        // In case you can not decide the thing status directly (e.g. for long running connection handshake using WAN
        // access or similar) you should set status UNKNOWN here and then decide the real status asynchronously in the
        // background.
        for (Channel channel : thing.getChannels()) {
            final BioWinChannelConfig channelConfig = channel.getConfiguration().as(BioWinChannelConfig.class);
            BioWinChannel c = new BioWinChannel(channelConfig, channel.getUID(), this);
            // channelStateByChannelUID.put(channel.getUID(), c);
            logger.warn("initialize channel '" + channelConfig.OID + "'");
        }
    }

    /**
     * Get a reference to the modbus endpoint
     */
    private void connectEndpoint() {
        // if (ncd != null) {
        // return;
        // }

        // Example for background initialization:
        scheduler.execute(() -> {
            boolean thingReachable = true; // <background task with long running initialization here>
            // when done do:
            if (thingReachable) {
                updateStatus(ThingStatus.ONLINE);
            } else {
                updateStatus(ThingStatus.OFFLINE);
            }
        });
        // ncd = new NettyClientDemo("Service", "6FN&4#w1Hb-7");

        updateStatus(ThingStatus.ONLINE);
    }

    @Override
    public void dispose() {
        logger.warn("dispose");
    }
}
