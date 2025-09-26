/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package org.openhab.binding.windhagerbiowin.internal;

import static org.openhab.core.library.unit.SIUnits.KILOGRAM;

import java.net.URI;

import org.json.*;
import org.openhab.core.library.types.QuantityType;
import org.openhab.core.types.State;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

public class NettyClientDemoHandler extends SimpleChannelInboundHandler<HttpObject> {

    Boolean isJson = false;
    Boolean sendAuthAgain = false;
    NettyClientDemo ncd;

    public NettyClientDemoHandler(NettyClientDemo ncd) {
        this.ncd = ncd;
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, HttpObject msg) {
        URI uri_current;

        // System.err.println("__channelRead0__");

        if (msg instanceof HttpResponse) {
            HttpResponse response = (HttpResponse) msg;

            // System.err.println("STATUS: " + response.status());
            // System.err.println("VERSION: " + response.protocolVersion());
            // System.err.println();

            if (response.status() == HttpResponseStatus.UNAUTHORIZED) {
                System.err.println("Authentication Error - Try send it again!");
                sendAuthAgain = true;
                return;
            } else {
                sendAuthAgain = false;
            }

            if (!response.headers().isEmpty()) {
                for (String name : response.headers().names()) {
                    for (String value : response.headers().getAll(name)) {
                        // System.err.println("HEADER: " + name + " = " + value);
                    }
                }
                // System.err.println();
            }

            if (HttpUtil.isTransferEncodingChunked(response)) {
                // System.err.println("CHUNKED CONTENT {");
                isJson = true;
            } else {
                // System.err.println("CONTENT {");
            }
        }

        if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent) msg;

            if (isJson) {
                String jsonString = content.content().toString(CharsetUtil.UTF_8); // assign your JSON String here
                System.err.println(jsonString);
                if (jsonString != "") {
                    String value;
                    try {
                        value = getValue(jsonString);
                    } catch (JSONException e) {
                        JSONArray arr = new JSONArray(jsonString);
                        value = arr.get(0).toString();
                    }

                    if (value != "") {
                        Float val = Float.parseFloat(getValue(jsonString)) * 1000; // it is in tons
                        uri_current = ncd.requests.getFirst();

                        // DecimalType quantity = new DecimalType(Math.random());
                        State state_value = new QuantityType<>(val, KILOGRAM);
                        ncd.callback.call("PelletTotal", state_value);

                        System.err.println("###" + uri_current.getRawPath() + ":" + val + "###");
                    }
                }
                // String pageName = obj.getJSONObject("pageInfo").getString("pageName");

                // JSONArray arr = obj.getJSONArray("posts"); // notice that `"posts": [...]`
                // for (int i = 0; i < arr.length(); i++)
                // {
                // String post_id = arr.getJSONObject(i).getString("post_id");
                // }
            } else {
                // System.err.print(content.content().toString(CharsetUtil.UTF_8));
                // System.err.flush();
            }

            if (content instanceof LastHttpContent) {
                // System.err.println("\n} END OF CONTENT");
                isJson = false;
                ctx.close();

                if (true == sendAuthAgain) // send again for authentication
                {
                    Thread t1 = new Thread(new Runnable() {
                        public void run() {
                            try {
                                ncd.connectAndSend(ncd.requests.getFirst());
                            } catch (InterruptedException e) {
                                System.err.println("omg");
                                Thread.currentThread().interrupt();
                            }
                        }
                    });
                    t1.start();
                } else {
                    // System.out.println("__listener__ "+ncd.requests.pop().getQuery()+" -> done");
                    // remove done entry from request list:
                    ncd.requests.pop();
                    // start next request if available
                    if (!ncd.requests.isEmpty()) {
                        Thread t = new Thread(new Runnable() {
                            public void run() {
                                try {
                                    ncd.connectAndSend(ncd.requests.getFirst());
                                } catch (InterruptedException e) {
                                    System.err.println("omg");
                                    Thread.currentThread().interrupt();
                                }
                            }
                        });
                        t.start();
                    } else {
                        // System.err.println("__listener__ no more requests");
                        ncd.requestRunning.compareAndSet(true, false);
                    }
                }
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    public String getValue(String jsonString) throws JSONException {
        JSONObject json = new JSONObject(jsonString);

        if (!json.isNull("value")) {
            // Note, not `getJSONArray` or any of that.
            // This will give us whatever's at "URL", regardless of its type.
            Object item = json.get("value");

            // `instanceof` tells us whether the object can be cast to a specific type
            if (item instanceof JSONArray) {
                // it's an array
                // JSONArray urlArray = (JSONArray) item;
                // do all kinds of JSONArray'ish things with urlArray
                return "JSONArray";
            } else if (item instanceof String) {
                return (String) item;
            } else {
                // if you know it's either an array or an object, then it's an object
                return "can't cast";
                // JSONObject urlObject = (JSONObject) item;
                // do objecty stuff with urlObject
            }
        } else {
            // URL is null/undefined
            // oh noes
        }

        return "";
    }
}
