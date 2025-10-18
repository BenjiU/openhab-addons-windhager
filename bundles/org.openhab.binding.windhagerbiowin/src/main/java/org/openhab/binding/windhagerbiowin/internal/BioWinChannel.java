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

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.openhab.core.thing.ChannelUID;

/**
 * Subscribes to a state topic and calls {@link AbstractBrokerHandler#triggerChannel(ChannelUID, String)} if a value got
 * received.
 *
 * @author David Graeff - Initial contribution
 */
@NonNullByDefault
public class BioWinChannel {
    private final BioWinChannelConfig config;
    private final ChannelUID uid;
    private final WindhagerBioWinHandler handler;

    BioWinChannel(BioWinChannelConfig config, ChannelUID uid, WindhagerBioWinHandler handler) {
        this.config = config;
        this.uid = uid;
        this.handler = handler;
    }

    // CompletableFuture<Boolean> start() {
    // return stop().thenCompose(b -> connection.subscribe(config.stateTopic, this));
    // }

    // @Override
    // public void processMessage(String topic, byte[] payload) {
    // String value = new String(payload);
    // // Check condition
    // String expectedPayload = config.payload;
    // if (expectedPayload != null && !value.equals(expectedPayload)) {
    // return;
    // }
    // if (config.separator.isEmpty()) {
    // handler.triggerChannel(uid, value);
    // } else {
    // handler.triggerChannel(uid, topic + config.separator + value);
    // }
    // }

    // public CompletableFuture<Boolean> stop() {
    // return connection.unsubscribe(config.stateTopic, this);
    // }
}
