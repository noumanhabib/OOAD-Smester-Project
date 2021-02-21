package Acc;

import java.awt.Font;
import java.sql.Connection;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import SalePurchase.Sale;

public class Cashier {
	public Cashier(Connection conn){
		JFrame frame = new JFrame("Hospital Sales");
    	frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 13));
		frame.setSize(700, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Cashier");
		lblNewLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 30));
		lblNewLabel.setBounds(284, 67, 130, 47);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Create Sale");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("New sale starts");
				@SuppressWarnings("unused")
				Sale sale = new Sale(conn);
				
			}
		});
		btnNewButton.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		btnNewButton.setBounds(258, 153, 156, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Show Sales");
		btnNewButton_1.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		btnNewButton_1.setBounds(258, 210, 156, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Exit");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNewButton_2.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		btnNewButton_2.setBounds(258, 269, 156, 23);
		frame.getContentPane().add(btnNewButton_2);
		
		frame.setResizable(false);		
		frame.setVisible(true);
	}
}
