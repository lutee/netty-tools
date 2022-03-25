package netty.websocket.handler;

import java.util.Map;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler.HandshakeComplete;

public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

	private final Map<String, Channel> channels;

	public WebSocketHandler(Map<String, Channel> channels) {
		this.channels = channels;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
		String id = ctx.channel().id().asShortText();
		Channel channel = channels.get(msg.text());
		if (channel != null) {
			// doSomething
		}
		channels.get(id).writeAndFlush(new TextWebSocketFrame(id));
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		channels.put(channel.id().asShortText(), channel);
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		channels.remove(channel.id().asShortText());
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof HandshakeComplete) {
			HandshakeComplete event = (HandshakeComplete) evt;
			System.out.println(event.requestHeaders());
			ctx.writeAndFlush(new TextWebSocketFrame("at: " + ctx.channel()));
		}
	}

}
