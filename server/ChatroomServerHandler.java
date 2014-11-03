package server;

import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Handles a server-side channel.
 */
public class ChatroomServerHandler extends ServerHandler {

	 	@Override
	    public void channelActive(final ChannelHandlerContext ctx) {
	        final ByteBuf time = ctx.alloc().buffer(4);
	        time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));

	        final ChannelFuture f = ctx.writeAndFlush(time);
	        f.addListener(new ChannelFutureListener() {
	            @Override
	            public void operationComplete(ChannelFuture future) {
	                assert f == future;
	                ctx.close();
	            }
	        });
	    }

	    @Override
	    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
	        cause.printStackTrace();
	        ctx.close();
	    }
	    
	    public void channelRead(ChannelHandlerContext ctx, Object msg) {
			ByteBuf m = (ByteBuf) msg;
	        try {
	            System.out.println(m);
	            ctx.close();
	        } finally {
	            m.release();
	        }
		}
}