package netty.websocket.server;

import javax.net.ssl.SSLException;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class WebSocketServer {

	private NioEventLoopGroup bossGroup;

	private NioEventLoopGroup workerGroup;

	public WebSocketServer(NioEventLoopGroup bossGroup, NioEventLoopGroup workerGroup) {
		super();
		this.bossGroup = bossGroup;
		this.workerGroup = workerGroup;
	}

	public void start(String uri, int port) throws SSLException {
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(bossGroup, workerGroup);
		bootstrap.channel(NioServerSocketChannel.class);
		bootstrap.option(ChannelOption.SO_BACKLOG, 128);
		bootstrap.handler(new LoggingHandler(LogLevel.INFO));
		bootstrap.childHandler(new WebSocketInitializer(uri));
		ChannelFuture future = bootstrap.bind(port).syncUninterruptibly();
		future.channel().closeFuture().syncUninterruptibly();
	}

	public void destroy() {
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
	}

}
