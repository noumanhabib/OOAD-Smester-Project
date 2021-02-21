package SalePurchase;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JTable;

import javax.swing.JPanel;

import Store.Item;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;

public class Sale {
	private Connection conn;
	private JFrame frame;
	private JTextField textField;
	private JTable table;
	private ArrayList<Item> items = new ArrayList<Item>();
	private String[] columns = {"Id", "Title", "Price"};
	private String[][] data = new String[20][20];
	
	private Statement stmt = null;
	private ResultSet rs = null;
	
	private int totalItems = 0;
	
	public Sale(Connection conn) {
		this.conn = conn;
		
		this.importItems();
		this.view();
	}
	
	private void view() {
		frame = new JFrame("New Sale");
    	frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 13));
		frame.setSize(700, 531);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Search");
		lblNewLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		lblNewLabel.setBounds(10, 34, 59, 22);
		frame.getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(81, 34, 121, 27);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(225, 36, 295, 122);
		frame.getContentPane().add(scrollPane);
		
		JList<Item> list = new JList<Item>();
		list.setAlignmentX(Component.LEFT_ALIGNMENT);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setModel(new AbstractListModel<Item>() {
			private static final long serialVersionUID = 1L;
			public int getSize() {
				return items.size();
			}
			public Item getElementAt(int index) {
				return items.get(index);
			}
		});
		
		list.setCellRenderer(new ListCellRenderer<Item>(){

			@Override
			public Component getListCellRendererComponent(JList<? extends Item> list, Item value, int index,
					boolean isSelected, boolean cellHasFocus) {
				// TODO Auto-generated method stub
				JLabel lblNewLabel_1 = new JLabel(value.getTitle() + "     " + value.getPrice() + " .Rs");
				lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
				lblNewLabel_1.setOpaque(true);
				lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
				lblNewLabel_1.setVerticalAlignment(SwingConstants.CENTER);
				if(isSelected) {
					lblNewLabel_1.setBackground(Color.CYAN);
				}
				
				return lblNewLabel_1;
			}
			
		});
		
		scrollPane.setViewportView(list);
		
		JButton btnNewButton = new JButton("Add");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item selectedItem = list.getSelectedValue();
				for(int i = 0; i < 3; i++) {
					if(i == 0) {
						data[totalItems][i] = Integer.toString(selectedItem.getId());
					}
					else if(i == 1) {
						data[totalItems][i] = selectedItem.getTitle();
					}
					else if(i == 2) {
						data[totalItems][i] = Integer.toString(selectedItem.getPrice());
					}
				}
				totalItems++;
				table.setModel(new DefaultTableModel(
					data,
					columns
				));
					
			}
		});
		btnNewButton.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		btnNewButton.setBounds(559, 35, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		JPanel panel = new JPanel();
		panel.setBounds(81, 169, 421, 257);
		panel.setLayout(new BorderLayout());
		frame.getContentPane().add(panel);
		
		
		table = new JTable(this.data, this.columns);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setCellEditor(null);
		table.enableInputMethods(false);
		
		JScrollPane scrollPane_1 = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		panel.add(scrollPane_1);
		
		JButton btnNewButton_1 = new JButton("Create Sale");
		btnNewButton_1.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(totalItems <= 0) {
					System.out.println("Nothing in cart to create sale");
					return;
				}
				createSale();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_1.setBounds(261, 464, 121, 27);
		frame.getContentPane().add(btnNewButton_1);
		frame.setResizable(false);		
		frame.setVisible(true);
	}
	
	
	private void importItems() {
		try {
			stmt = this.conn.createStatement();
			rs = this.stmt.executeQuery("select * from items");
			while(rs.next()) {
				Item item = new Item(rs.getInt("id"), rs.getString("title"), rs.getInt("price"));
				items.add(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	private void createSale() {
		CreateSale th = new CreateSale(frame, conn, totalItems, data);
		th.start();
	}
	
}

class CreateSale extends Thread{
	private JFrame frame;
	private Connection conn;
	private int totalItems;
	private String[][] data;
	public CreateSale(JFrame frame, Connection conn, int totalItems, String[][] data) {
		this.frame = frame;
		this.conn = conn;
		this.totalItems = totalItems;
		this.data = data;
	}
	public void run() {
		PreparedStatement prstmt;
		int saleId;
		int totalPrice = 0;
		Statement stmt;
		ResultSet rs;
		for(int i = 0; i<this.totalItems; i++) {
			totalPrice += Integer.parseInt(this.data[i][2]);
		}
		try {
		    prstmt = this.conn.prepareStatement("insert into sale(totalPrice) values(?);");
			prstmt.setInt(1, totalPrice);
			prstmt.executeUpdate();
			
			stmt = this.conn.createStatement();
			rs = stmt.executeQuery("select * from sale order by id desc limit 1");
			if(rs.next()) {
				saleId = rs.getInt("id");
			}
			else {
				System.out.println("Some worst error occured!");
				return;
			}
			for(int i = 0; i<totalItems; i++) {
				int itemId = Integer.parseInt(data[i][0]);
				prstmt = this.conn.prepareStatement("insert into saleitem(sale_id, item_id) values(?,?);");
				prstmt.setInt(1, saleId);
				prstmt.setInt(2, itemId);
				prstmt.executeUpdate();
			}
			
			System.out.println("New sale created succesfuly1.1");
			frame.setVisible(false);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}











