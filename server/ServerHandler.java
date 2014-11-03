package server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public abstract class ServerHandler extends ChannelInboundHandlerAdapter {

	public abstract void channelActive(final ChannelHandlerContext ctx);
	public abstract void exceptionCaught(ChannelHandlerContext ctx, Throwable cause);
	
}
