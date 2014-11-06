package client;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Base class for all Clients.
 * @author Mike
 */
public abstract class Client {

	/**
	 * Swing components for the GUI.
	 */
	protected JFrame frame;
	protected JTextArea output;
	protected JTextField message;
	protected JButton sendButton;
	protected JList<String> userList;
	
	protected String host;
	protected int port;
	
	/**
	 * Base Client constructor.
	 */
	public Client(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	/**
	 * Subclasses must implement createGUI().
	 * Chatroom and P2P will have different implementations so this
	 * can't be implemented here.
	 */
	public abstract void createGUI();
	/**
	 * Subclasses must implement setUp().
	 * Chatroom and P2P will have different implementations so this
	 * can't be implemented here.
	 */
	public abstract void setUp() throws Exception;
	
}
