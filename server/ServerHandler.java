package server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Base ServerHandler class.
 * Implements the exceptionCaught method.
 * @author Mike
 *
 */
public abstract class ServerHandler extends SimpleChannelInboundHandler<String> {
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		 cause.printStackTrace();
	     ctx.close();
	 }
	
}
