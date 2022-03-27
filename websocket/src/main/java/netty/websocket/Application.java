/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package netty.websocket;

import javax.net.ssl.SSLException;

import io.netty.channel.nio.NioEventLoopGroup;
import netty.websocket.server.WebSocketServer;

public class Application {

	public static void main(String[] args) throws SSLException {
		NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
		NioEventLoopGroup workerGroup = new NioEventLoopGroup();
		WebSocketServer server = new WebSocketServer(bossGroup, workerGroup);
		server.start("/", 8080);

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			server.destroy();
		}));

	}

}