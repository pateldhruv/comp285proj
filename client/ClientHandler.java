package client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * ClientHandler implementation
 * Processes incoming messages.
 * @author Mike
 *
 */
public class ClientHandler extends SimpleChannelInboundHandler<String> {

	private String message;
	
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
    
    public String getMessage() {
    	return message;
    }
    
    /**
     * resetMessage()
     * Set the message string back to empty.
     * @author Mike
     */
    public void resetMessage() {
    	message = "";
    }

    /**
     * channelRead0(ChannelHandlerContext ctx, String message)
     * Sets stores the message to be used later by the Client.
     * @author Mike
     */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String message) throws Exception {
		this.message = message;
	}

	
}
