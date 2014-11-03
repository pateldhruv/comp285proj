package base;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public abstract class ClientHandler extends ChannelInboundHandlerAdapter {

	public abstract void channelRead(ChannelHandlerContext ctx, Object msg);
	public abstract void exceptionCaught(ChannelHandlerContext ctx, Throwable cause);
	
}
