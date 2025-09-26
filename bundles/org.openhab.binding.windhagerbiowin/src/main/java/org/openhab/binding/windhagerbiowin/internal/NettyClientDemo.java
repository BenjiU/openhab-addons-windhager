package org.openhab.binding.windhagerbiowin.internal;

import java.net.URI;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import me.vzhilin.auth.DigestAuthenticator;
import me.vzhilin.auth.netty.DigestNettyHttpAuthenticator;

public class NettyClientDemo {

    private final DigestAuthenticator digestAuthenticator;

    // Configure the client.
    private EventLoopGroup group;
    private Bootstrap b;

    // runtime
    AtomicBoolean requestRunning = new AtomicBoolean(false);
    LinkedList<URI> requests = new LinkedList<>();
    WindhagerCallback callback;

    NettyClientDemo(String username, String password) {
        digestAuthenticator = new DigestAuthenticator(username, password);

        // Configure the client.
        group = new NioEventLoopGroup(1); // todo: avoid possible nonceCount race
        b = new Bootstrap();

        NettyClientDemo ncd = this;

        b.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline p = ch.pipeline();
                p.addLast(new HttpClientCodec());
                p.addLast(new HttpContentDecompressor());
                // p.addLast(new HttpObjectAggregator(1048576)); // NB! works only with aggregated request/response
                p.addLast(new DigestNettyHttpAuthenticator(digestAuthenticator));
                p.addLast(new NettyClientDemoHandler(ncd));
            }
        });
    }

    public void request(URI uri, WindhagerCallback cb) {
        // System.err.println("__request__"+uri.getRawPath());
        requests.add(uri);
        callback = cb;

        if (requestRunning.compareAndSet(false, true)) {
            // code goes here.
            try {
                connectAndSend(uri);
            } catch (InterruptedException e) {
                System.err.println("omg");
                Thread.currentThread().interrupt();
            }
        }
    }

    void connectAndSend(URI uri) throws InterruptedException {
        // System.err.println("__connectAndSend__"+uri.getRawPath());

        // Make the connection attempt.
        Channel ch = b.connect(uri.getHost(), uri.getPort()).sync().channel();

        // Prepare the HTTP request.
        HttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, uri.getRawPath());
        request.headers().set(HttpHeaderNames.HOST, uri.getHost());
        request.headers().set(HttpHeaderNames.ACCEPT_ENCODING, HttpHeaderValues.GZIP);

        // Send the HTTP request.
        ch.writeAndFlush(request);

        // Wait for the server to close the connection.
        ch.closeFuture().sync();
    }

    void shutdownGracefully() {
        group.shutdownGracefully();
    }
}
