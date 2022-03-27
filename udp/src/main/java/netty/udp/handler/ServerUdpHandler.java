package netty.udp.handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

public class ServerUdpHandler extends SimpleChannelInboundHandler<DatagramPacket> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
		String content = "receive: ".concat(msg.content().toString(CharsetUtil.UTF_8));
		DatagramPacket data = new DatagramPacket(Unpooled.copiedBuffer(content.getBytes()), msg.sender());
	}

}
