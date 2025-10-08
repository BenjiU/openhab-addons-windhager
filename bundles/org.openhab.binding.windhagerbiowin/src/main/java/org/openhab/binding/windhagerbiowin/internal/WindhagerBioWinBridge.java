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

import java.util.concurrent.Future;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.core.library.types.RawType;
import org.openhab.core.thing.Bridge;
import org.openhab.core.thing.ChannelUID;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.binding.BaseBridgeHandler;
import org.openhab.core.thing.binding.ThingHandler;
import org.openhab.core.types.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link WindhagerBioWinBridge} represents the connection to the BioWin Webserver.
 *
 * @author Benjamin Utz - Initial contribution
 */
@NonNullByDefault
public class WindhagerBioWinBridge extends BaseBridgeHandler {

    private static final String LOGIN_PATH = "/api/host/login.json";

    private final Logger logger = LoggerFactory.getLogger(WindhagerBioWinBridge.class);

    private @Nullable Future<?> refreshMonitorsJob;

    public WindhagerBioWinBridge(Bridge thing) { // }, HttpClient httpClient) {
        super(thing);
        // this.httpClient = httpClient;
    }

    @Override
    public void initialize() {
        // ZmBridgeConfig config = getConfigAs(ZmBridgeConfig.class);

        // Integer value;
        // value = config.refreshInterval;
        // monitorRefreshInterval = value == null ? MONITOR_REFRESH_INTERVAL_SECONDS : value;

        // value = config.defaultAlarmDuration;
        // defaultAlarmDuration = value == null ? DEFAULT_ALARM_DURATION_SECONDS : value;

        // defaultImageRefreshInterval = config.defaultImageRefreshInterval;

        // backgroundDiscoveryEnabled = config.discoveryEnabled;
        // logger.debug("Bridge: Background discovery is {}", backgroundDiscoveryEnabled ? "ENABLED" : "DISABLED");

        // host = config.host;
        // useSSL = config.useSSL.booleanValue();
        // portNumber = config.portNumber != null ? Integer.toString(config.portNumber) : null;
        // urlPath = "/".equals(config.urlPath) ? "" : config.urlPath;

        // // If user and password are configured, then use Zoneminder authentication
        // if (config.user != null && config.pass != null) {
        // zmAuth = new ZmAuth(this, config.user, config.pass);
        // }
        // if (isHostValid()) {
        // updateStatus(ThingStatus.ONLINE);
        // scheduleRefreshJob();
        // }
    }

    @Override
    public void dispose() {
        // cancelRefreshJob();
    }

    @Override
    public void childHandlerInitialized(ThingHandler childHandler, Thing childThing) {
        // String monitorId = (String) childThing.getConfiguration().get(CONFIG_MONITOR_ID);
        // monitorHandlers.put(monitorId, (ZmMonitorHandler) childHandler);
        // logger.debug("Bridge: Monitor handler was initialized for {} with id {}", childThing.getUID(), monitorId);
    }

    @Override
    public void childHandlerDisposed(ThingHandler childHandler, Thing childThing) {
        // String monitorId = (String) childThing.getConfiguration().get(CONFIG_MONITOR_ID);
        // monitorHandlers.remove(monitorId);
        // logger.debug("Bridge: Monitor handler was disposed for {} with id {}", childThing.getUID(), monitorId);
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        // switch (channelUID.getId()) {
        // case CHANNEL_IMAGE_MONITOR_ID:
        // handleMonitorIdCommand(command, CHANNEL_IMAGE_MONITOR_ID, CHANNEL_IMAGE_URL, STREAM_IMAGE);
        // break;
        // case CHANNEL_VIDEO_MONITOR_ID:
        // handleMonitorIdCommand(command, CHANNEL_VIDEO_MONITOR_ID, CHANNEL_VIDEO_URL, STREAM_VIDEO);
        // break;
        // case CHANNEL_RUN_STATE:
        // if (command instanceof StringType) {
        // changeRunState(command);
        // }
        // break;
        // }
    }

    private void handleMonitorIdCommand(Command command, String monitorIdChannelId, String urlChannelId, String type) {
        // if (command instanceof RefreshType || command == OnOffType.OFF) {
        // updateState(monitorIdChannelId, UnDefType.UNDEF);
        // updateState(urlChannelId, UnDefType.UNDEF);
        // } else if (command instanceof StringType) {
        // String id = command.toString();
        // if (isMonitorIdValid(id)) {
        // updateState(urlChannelId, new StringType(buildStreamUrl(id, type)));
        // } else {
        // updateState(monitorIdChannelId, UnDefType.UNDEF);
        // updateState(urlChannelId, UnDefType.UNDEF);
        // }
        // }
    }

    private void changeRunState(Command command) {
        // logger.debug("Bridge: Change run state to {}", command);
        // executeGet(buildUrl(String.format("/api/states/change/%s.json",
        // URLEncoder.encode(command.toString(), Charset.defaultCharset()))));
    }

    public @Nullable RawType getImage(String id, @Nullable Integer imageRefreshIntervalSeconds) {
        // Integer localRefreshInterval = imageRefreshIntervalSeconds;
        // if (localRefreshInterval == null || localRefreshInterval.intValue() < 1 || !zmAuth.isAuthorized()) {
        // return null;
        // }
        // // Call should timeout just before the refresh interval
        // int timeout = Math.min((localRefreshInterval * 1000) - 500, API_TIMEOUT_MSEC);
        // Request request = httpClient.newRequest(buildStreamUrl(id, STREAM_IMAGE));
        // request.method(HttpMethod.GET);
        // request.timeout(timeout, TimeUnit.MILLISECONDS);

        // String errorMsg;
        // try {
        // ContentResponse response = request.send();
        // if (response.getStatus() == HttpStatus.OK_200) {
        // return new RawType(response.getContent(), response.getHeaders().get(HttpHeader.CONTENT_TYPE));
        // } else {
        // errorMsg = String.format("HTTP GET failed: %d, %s", response.getStatus(), response.getReason());
        // }
        // } catch (TimeoutException e) {
        // errorMsg = String.format("TimeoutException: Call to Zoneminder API timed out after {} msec", timeout);
        // } catch (ExecutionException e) {
        // errorMsg = String.format("ExecutionException: %s", e.getMessage());
        // } catch (InterruptedException e) {
        // errorMsg = String.format("InterruptedException: %s", e.getMessage());
        // Thread.currentThread().interrupt();
        // }
        // logger.debug("{}", errorMsg);
        return null;
    }

    private void updateRunStates() {
        // if (!zmAuth.isAuthorized() || !isLinked(CHANNEL_RUN_STATE)) {
        // return;
        // }
        // try {
        // String response = executeGet(buildUrl("/api/states.json"));
        // RunStatesDTO runStates = GSON.fromJson(response, RunStatesDTO.class);
        // if (runStates != null) {
        // List<StateOption> options = new ArrayList<>();
        // for (RunStateDTO runState : runStates.runStatesList) {
        // RunState state = runState.runState;
        // logger.debug("Found runstate: id={}, name={}, desc={}, isActive={}", state.id, state.name,
        // state.definition, state.isActive);
        // options.add(new StateOption(state.name, state.name));
        // if ("1".equals(state.isActive)) {
        // updateState(CHANNEL_RUN_STATE, new StringType(state.name));
        // }
        // }
        // stateDescriptionProvider.setStateOptions(new ChannelUID(getThing().getUID(), CHANNEL_RUN_STATE),
        // options);
        // }
        // } catch (JsonSyntaxException e) {
        // logger.debug("Bridge: JsonSyntaxException: {}", e.getMessage(), e);
        // }
    }

    // private void scheduleRefreshJob() {
    // logger.debug("Bridge: Scheduling monitors refresh job");
    // cancelRefreshJob();
    // refreshMonitorsJob = scheduler.scheduleWithFixedDelay(this::refreshMonitors,
    // MONITOR_REFRESH_STARTUP_DELAY_SECONDS, monitorRefreshInterval, TimeUnit.SECONDS);
    // }

    // private void cancelRefreshJob() {
    // Future<?> localRefreshThermostatsJob = refreshMonitorsJob;
    // if (localRefreshThermostatsJob != null) {
    // localRefreshThermostatsJob.cancel(true);
    // logger.debug("Bridge: Canceling monitors refresh job");
    // refreshMonitorsJob = null;
    // }
    // }
}
