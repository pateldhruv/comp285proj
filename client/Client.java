package client;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public abstract class Client {

	protected JFrame frame;
	protected JTextArea output;
	protected JTextField message;
	protected JButton sendButton;
	protected JList userList;
	
	public abstract void createGUI();
	public abstract void setUp() throws Exception;
	
}
