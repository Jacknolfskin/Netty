package com.personal.hu;

import com.personal.hu.handler.HttpRequestHandler;
import com.personal.hu.handler.TextWebSocketFrameHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * 服务端 ChannelInitializer
 * @author Jacknolfskin
 * @date 2017-01-01
 */
public class WebsocketChatServerInitializer extends
        ChannelInitializer<SocketChannel> {	//1

	@Override
    public void initChannel(SocketChannel ch) throws Exception {//2
		 ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new HttpServerCodec());
		pipeline.addLast(new HttpObjectAggregator(64*1024));
		pipeline.addLast(new ChunkedWriteHandler());
		pipeline.addLast(new HttpRequestHandler("/ws"));
		pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
		pipeline.addLast(new TextWebSocketFrameHandler());

    }
}
