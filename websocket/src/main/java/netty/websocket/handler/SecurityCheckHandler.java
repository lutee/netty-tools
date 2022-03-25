package netty.websocket.handler;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.util.internal.StringUtil;

public class SecurityCheckHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof FullHttpRequest) {
			HttpHeaders headers = ((FullHttpRequest) msg).headers();
			String uuid = headers.get("Authorization");
			if (StringUtil.isNullOrEmpty(uuid)) {
				ctx.writeAndFlush("failure").addListener(ChannelFutureListener.CLOSE);
				return;
			}
		}
		super.channelRead(ctx, msg);
	}
}
