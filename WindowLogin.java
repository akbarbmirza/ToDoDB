import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;

import javax.swing.*;

public class WindowLogin extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel userLogin;
	private JLabel messageLabel;
	private JLabel userName;
	private JTextField userNameField;
	private JLabel userPassword;
	private JPasswordField userPasswordField;
	private LoginButton loginButton;
	private ExitButton exitButton;
	
	public WindowLogin(){
		super ("Welcome to ToDo 0.1");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize (400, 300);
		
		userLogin = new JPanel (new GridBagLayout());
		messageLabel = new JLabel("This is an example text");
		userName = new JLabel ("Username");
		userNameField = new JTextField ("", 15);
		userPassword = new JLabel ("Password");
		userPasswordField = new JPasswordField (15);
		loginButton = new LoginButton ("Login");
		exitButton = new ExitButton ("Exit");
	
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets (10, 10, 10, 10);
		
		//Add component to the panel
		constraints.gridx = 0;
		constraints.gridy = 0;
		//constraints.gridwidth = 2;
		userLogin.add(userName, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 0;
		userLogin.add(userNameField, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		userLogin.add(userPassword, constraints);
		
		constraints.gridy = 1;
		constraints.gridx = 1;
		userLogin.add(userPasswordField, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.CENTER;
		userLogin.add(messageLabel, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.anchor = GridBagConstraints.WEST;
		userLogin.add(exitButton, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 4;
//		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.EAST;
		userLogin.add(loginButton, constraints);
				
		userLogin.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Enter your username and password"));
		
		add (userLogin);
		
		pack();
		setLocationRelativeTo(null);
		
	}
	
	class ExitButton extends JButton{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		MouseListener listen = new MouseAdapter(){
			public void mousePressed (MouseEvent E){
				System.exit(0);
			}
		};
		
		public ExitButton (String label){
			super (label);
			addMouseListener(listen);
		}
	}
	
	class LoginButton extends JButton{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		MouseListener listen = new MouseAdapter(){
			public void mousePressed (MouseEvent m){
				if (userNameField.getText().equals("jdoe00")){
					String password = userPasswordField.getPassword().toString();
					messageLabel.setText(password);
				}
			
				else{
					messageLabel.setText("You don't exist in the database");
					}
				/*String connectionString = "jdbc:hsqldb:testdb,sa,";
				Connection connection = null;
				try{
					Class.forName("org.hsqldb.jdbcdriver");
				}
				catch(ClassNotFoundException e){
					System.out.println(e);
				}
				
				try{
					connection = DriverManager.getConnection(connectionString);
				}
				catch(SQLException e){
					System.out.println(e);
				}*/
				
			}
		};
		
		public LoginButton(String label){
			super (label);
			addMouseListener(listen);
		}
	}

	public String getUsername() {
		return userNameField.getText();
	}
	
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
		
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run(){
				new WindowLogin().setVisible(true);
			}
		});

	}

}