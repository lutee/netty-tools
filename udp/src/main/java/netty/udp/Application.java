package netty.udp;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class Application {

	public static void main(String[] args) throws InterruptedException {

		new Application().start();

	}

	public Channel start() throws InterruptedException {
		NioEventLoopGroup group = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(group);
		bootstrap.channel(NioDatagramChannel.class);
		bootstrap.option(ChannelOption.SO_BROADCAST, true);
		bootstrap.handler(new ChannelInitializer<NioDatagramChannel>() {

			@Override
			protected void initChannel(NioDatagramChannel ch) throws Exception {
				ChannelPipeline pipeline = ch.pipeline();
			}
		});
		Channel channel = bootstrap.bind(7399).sync().channel();
		while (true) {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			channel.writeAndFlush(
					new DatagramPacket(Unpooled.copiedBuffer("everyone".getBytes()), new InetSocketAddress(8080)));

		}
	}

}
