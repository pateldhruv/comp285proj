package server;

import io.netty.channel.ChannelHandlerContext;


/**
 * P2PServerHandler
 * Writes data received from a client to all other clients.
 * @author Mike
 */
public class P2PServerHandler extends ServerHandler {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String message) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
