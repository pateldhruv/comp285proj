package server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * ChatroomServerHandler
 * Handles incoming, and outgoing client connections
 * Writes data received from a client to all other clients.
 * @author Mike
 */
public class ChatroomServerHandler extends ServerHandler {

	static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
	/**
	 * handlerAdded(ChannelHandlerContext ctx)
	 * Handles incoming connections.
	 * Tells all clients on the server who joined.
	 * @author Mike
	 */
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		System.out.println(ctx.channel().remoteAddress() + " has joined MAD Chat!");
		for(Channel channel : channels) {
			channel.write("[SERVER] : " + ctx.channel().remoteAddress() + " has joined MAD Chat!\r\n");
		}
		channels.add(ctx.channel());
	}
	
	/**
	 * handlerRemoved(ChannelHandlerContext ctx)
	 * Handles outgoing connections.
	 * Tells all clients on the server who left.
	 * @author Mike
	 */
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		System.out.println(ctx.channel().remoteAddress() + " has left MAD Chat!");
		for(Channel channel : channels) {
			channel.write("[SERVER] : " + ctx.channel().remoteAddress() + " has left MAD Chat!\r\n");
		}
		channels.remove(ctx.channel());
	}

	/**
	 * channelRead0(ChannelHandlerContext ctx, String message)
	 * Handles incoming messages from Clients.
	 * Tells all clients what the Client sending the message said.
	 * @author Mike
	 */
	@Override
	public void channelRead0(ChannelHandlerContext ctx, String message) throws Exception {
		System.out.println("[" + ctx.channel().remoteAddress() + "] : " + message + "\r\n");
		for(Channel c: channels) {
			if(c != ctx.channel()) {
				c.writeAndFlush("[" + ctx.channel().remoteAddress() + "] : " + message + "\r\n");
	 		} else {
	 			c.writeAndFlush("[you] : " + message + "\r\n");
	 		}
	 	}
		
	}
	
}