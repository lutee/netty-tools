package netty.websocket.server;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.timeout.IdleStateHandler;
import netty.websocket.handler.DatagramPacketHandler;
import netty.websocket.handler.HeartbeatHandler;
import netty.websocket.handler.WebSocketHandler;

public class WebSocketInitializer extends ChannelInitializer<SocketChannel> {

	private String uri;

	public WebSocketInitializer(String uri) {
		super();
		this.uri = uri;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new IdleStateHandler(0, 0, 60, TimeUnit.SECONDS));
		pipeline.addLast(new HeartbeatHandler());
		pipeline.addLast(new DatagramPacketHandler());
		pipeline.addLast(new HttpServerCodec());
		pipeline.addLast(new HttpObjectAggregator(8192));
		pipeline.addLast(new WebSocketServerCompressionHandler());
		pipeline.addLast(new WebSocketServerProtocolHandler(uri, null, true));
		pipeline.addLast(new WebSocketHandler(new HashMap<>()));
	}

}
