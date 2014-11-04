package server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * Handles a server-side channel.
 */
public class ChatroomServerHandler extends ServerHandler {

	static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		System.out.println(ctx.channel().remoteAddress() + " has joined MAD Chat!");
		for(Channel channel : channels) {
			channel.write("[SERVER] : " + ctx.channel().remoteAddress() + " has joined MAD Chat!\r\n");
		}
		channels.add(ctx.channel());
	}
	
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		System.out.println(ctx.channel().remoteAddress() + " has left MAD Chat!");
		for(Channel channel : channels) {
			channel.write("[SERVER] : " + ctx.channel().remoteAddress() + " has left MAD Chat!\r\n");
		}
		channels.remove(ctx.channel());
	}

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