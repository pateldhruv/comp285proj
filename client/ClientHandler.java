package client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<String> {

	protected String message;
	
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
    
    public String getMessage() {
    	return message;
    }
    
    /*
     * Set the message string back to empty
     */
    public void resetMessage() {
    	message = "";
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String message) throws Exception {
		this.message = message;
	}

	
}
