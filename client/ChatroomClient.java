package client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatroomClient extends Client {
	
	private String host;
	private int port;
	private boolean exit = false;
	
	Channel channel;
	ChannelFuture future;
	
	public ChatroomClient(String host, int port) {
		this.host = host;
		this.port = port;
		createGUI();
	}
	
    public static void main(String[] args) {
    	try {
    		new ChatroomClient("127.0.0.1", 8080).setUp();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public void setUp() throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            final ClientHandler c = new ClientHandler();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                	//max size 8192, all input delimited by line endings
                	ch.pipeline().addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
                	ch.pipeline().addLast("Decoder", new StringDecoder());
                	ch.pipeline().addLast("Encoder", new StringEncoder());
                	
                    ch.pipeline().addLast(c);
                }
            });

            // Start the client.
            channel = b.connect(host, port).sync().channel();
            future = null;
            c.resetMessage();
            while(true) {
            	if(!c.getMessage().equals("")) {
	            	output.append(c.getMessage());
	            	output.append("\n");
	            	c.resetMessage();
            	}
            	if(exit) {
            		System.exit(0);
            	}
            }
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

	@Override
	public void createGUI() {
		output = new JTextArea(20,40);
		output.setEditable(false);
		message = new JTextField(20);
		sendButton = new JButton("Send");
		userList = new JList();
		String[] userListData = {"test", "test1", "test3"};
		userList.setListData(userListData);
		
		frame = new JFrame("MAD Chat");
		JPanel panel = new JPanel();
		
		/*
		 * Anonymous class for the button action listener.
		 * Writes the message text to the server on click events.
		 * -Mike
		 */
		sendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(channel != null) {
					if (message.getText().toLowerCase().equals("/bye")) {
            			try {
							channel.closeFuture().sync();
							if(future != null) {
								future.sync();
							}
							exit = true;
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} else {
						channel.writeAndFlush(message.getText() + "\r\n");
					}
					message.setText("");
				}
			}
			
		});
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 400);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);
		layout.setAutoCreateContainerGaps(true);
		
		/*
		 * This was awful to make :(
		 * -Mike
		 */
		layout.setHorizontalGroup(
				layout.createSequentialGroup()
					.addGroup(
						layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
							.addComponent(output, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(message, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(10)
					.addGroup(
						layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
							.addComponent(userList, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(sendButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		
		layout.setVerticalGroup(
				layout.createSequentialGroup()
					.addGroup(
						layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
							.addComponent(output, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(userList, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(10)
					.addGroup(
						layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
							.addComponent(message, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(sendButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}
}
