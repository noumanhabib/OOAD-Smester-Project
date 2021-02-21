package Acc;

import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login {
	private JTextField userLabel;
	private JTextField passwordLabel;
	private Statement stmt = null;
	private ResultSet rs = null;
	
	public Login(Connection conn){
		JFrame frame = new JFrame("RMS");
    	frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 13));
		frame.setSize(700, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("LogIn");
		lblNewLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 38));
		lblNewLabel.setBounds(264, 101, 141, 45);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Username");
		lblNewLabel_1.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(177, 175, 79, 20);
		frame.getContentPane().add(lblNewLabel_1);
		
		userLabel = new JTextField();
		userLabel.setBounds(296, 172, 141, 29);
		frame.getContentPane().add(userLabel);
		userLabel.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Password");
		lblNewLabel_2.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		lblNewLabel_2.setBounds(177, 244, 86, 18);
		frame.getContentPane().add(lblNewLabel_2);
		
		passwordLabel = new JTextField();
		passwordLabel.setBounds(296, 240, 141, 29);
		frame.getContentPane().add(passwordLabel);
		passwordLabel.setColumns(10);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = userLabel.getText();
				String password = passwordLabel.getText();
				
				if(username.length() < 5 || password.length() < 6) {
					System.out.println("Not able to login");
					return;
				}
				
				try {
					stmt = conn.createStatement();
					rs = stmt.executeQuery("SELECT * FROM users where username='"+username+"' and password='" + password + "';");
					
					if(rs.next()) {
						String userType = rs.getString("type");
						System.out.println(userType);
						
						if(userType.equalsIgnoreCase("cashier")) {
							System.out.println("Cashier logged in");
							@SuppressWarnings("unused")
							Cashier cahsier = new Cashier(conn);
						}
						else if(userType.equalsIgnoreCase("manager")) {
							System.out.println("Manager logged in");
							@SuppressWarnings("unused")
							Manager manager = new Manager(conn);
						}
						else if(userType.equalsIgnoreCase("admin")) {
							System.out.println("Admin logged in");
							@SuppressWarnings("unused")
							Admin cahsier = new Admin(conn);
						}
						//for other user types
						frame.setVisible(false);
					}
					else {
						System.out.println("No record found");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			    
				
			}
		});
		btnNewButton.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		btnNewButton.setBounds(264, 317, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		frame.setResizable(false);		
		frame.setVisible(true);
	}
}
