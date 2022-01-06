package Screens;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import System.MovieSystem;
import System.SysAdmin;
import System.User;

public class Screen_AdminModifyUsers {
	private JFrame frame;
    private JPanel panel;
	private JButton closeButton;
	public JTextField Name = new JTextField(" ");
	public JTextField Email = new JTextField(" ");
	public JTextField Password = new JTextField(" ");
	public JTextField UserID = new JTextField(" ");
	public JTextField OrderID = new JTextField(" ");
	private JLabel nameLabel = new JLabel("Name: ");
	private JLabel emailLabel = new JLabel("Email: ");
	private JLabel passLabel = new JLabel("Password: ");
	private JLabel idLabel = new JLabel("User ID: ");
	private JLabel statusLabel = new JLabel("Most Recent Order Status: ");
	private JLabel emptyLabel = new JLabel(" ");
	public ListSelectionListener L;
	public JList<String> userList;
	private JButton deleteButton;
	private JButton saveButton;
	
    public Screen_AdminModifyUsers(MovieSystem Sys, SysAdmin admin) {
    	
    	frame = new JFrame();        
        ArrayList<User> users = Sys.getCustomerDB();
        String[] data = new String[users.size()];
        for(int i = 0; i<data.length; i++) {
        	data[i] = users.get(i).getName();
        }
        userList = new JList<String>(data);
		//data has type Object[]
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userList.setLayoutOrientation(JList.VERTICAL);
        userList.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(userList);
        
        listScroller.setPreferredSize(new Dimension(250, 80));
        
        UserID.setEditable(false);
        
        L = new ListSelectionListener() {
        	@Override
			public void valueChanged(ListSelectionEvent arg0) {
				// TODO Auto-generated method stub
        		User selUser = new User("N/A", -1, "N/A", "N/A");
        		try {
    				selUser = Sys.getCustomer(userList.getSelectedIndex());
        		}
        		catch(Exception e1) {
					// TODO Auto-generated catch block
					//System.out.println("nonfatal error with removing movie from db");
        		}
        		
				Name.setText(""+selUser.getName());
				Email.setText(""+selUser.getEmail());
				Password.setText(""+selUser.getPassword());
				UserID.setText(""+selUser.getId());//cant change this
				if(selUser.getOrderIDs().size() > 0) {
					for(int i = 0; i<Sys.getOrders().size();i++) {
						if(Sys.getOrders().get(i).getID() == selUser.getOrderIDs().get(0)) {
							OrderID.setText(""+Sys.getOrders().get(i).getStatus());
							break;
						}
					}
				}
				else {
					OrderID.setText("user has no orders");
				}
				
			}
        };
        userList.addListSelectionListener(L);
        
        saveButton = new JButton("Save Selected User");
        saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveButtonPressed(Sys, "user.csv");
			}
		});
        
    	deleteButton = new JButton("Delete This User");
    	deleteButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		deleteButtonPressed(Sys, "user.csv");
        	}
        });

                
        closeButton = new JButton("Return");
        closeButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		frame.dispose();
        	}
        });
        
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
        panel.setLayout(new GridLayout(8, 2));
        
        panel.add(listScroller);	panel.add(emptyLabel);
        panel.add(nameLabel);		panel.add(Name);
        panel.add(passLabel);		panel.add(Password);
        panel.add(emailLabel);		panel.add(Email);
        panel.add(idLabel);			panel.add(UserID);
        panel.add(statusLabel); 	panel.add(OrderID);
        panel.add(saveButton);		panel.add(deleteButton);
        panel.add(closeButton);
        
        
        
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("");
        frame.pack();
        frame.setVisible(true);        
    }
    public void saveButtonPressed(MovieSystem Sys, String path) {
    	if(!(Contains(Name.getText().toCharArray(), ',') || 
				Contains(Password.getText().toCharArray(), ',') || 
				Contains(Email.getText().toCharArray(), ',') ||  
				Contains(UserID.getText().toCharArray(), ','))) {
			int ind = userList.getSelectedIndex();
			if(Sys.getCustomer(ind).getOrderIDs().isEmpty()){
				Sys.changeUserInDB(Sys.getCustomer(ind), Name.getText(),
						Email.getText(),Password.getText(), path);
			}
			else {
				Sys.changeUserInDB(Sys.getCustomer(ind), Name.getText(),Email.getText(), 
						Password.getText(), Sys.getCustomer(ind).getOrderIDs().get(0), OrderID.getText(), path);
			}
			
			//REFRESH LIST
			String[] newData = new String[Sys.getCustomerDB().size()];
			int c = 0;
			for(int i = 0; i<newData.length; i++) {
				newData[c++] = Sys.getCustomerDB().get(i).getName();
			}
			if(c == 0) {
				//new Screen_PopupNotice(Sys, "No movies selected");
			}
			userList.setListData(newData);
			userList.removeListSelectionListener(L);
			userList.clearSelection();
			userList.addListSelectionListener(L);
		}
		else {
			new Screen_PopupNotice(Sys, "Error: User Info must not contain any commas(,)!");
		}
    }
    public void deleteButtonPressed(MovieSystem Sys, String path) {
    	int ind = userList.getSelectedIndex();
		if(Sys.getCustomer(ind).getName().equalsIgnoreCase("Operator")
				&& Sys.getCustomer(ind).getEmail().equalsIgnoreCase("op@vidco.ca")) {
			new Screen_PopupNotice(Sys, "Error: cannot delete this user!");
		}
		else {
			new Screen_PopupNotice(Sys, "Successfully removed User: " + Sys.getCustomer(ind).getName());
			Sys.removeUserInDB(Sys.getCustomer(ind), path);
		}
		//REFRESH LIST
		String[] newData = new String[Sys.getCustomerDB().size()];
		int c = 0;
		for(int i = 0; i<newData.length; i++) {
			newData[c++] = Sys.getCustomerDB().get(i).getName();
		}
		if(c == 0) {
			//new Screen_PopupNotice(Sys, "No movies selected");
		}
		userList.setListData(newData);
		userList.removeListSelectionListener(L);
		userList.clearSelection();
		userList.addListSelectionListener(L);
    }
    public boolean Contains(char[] C, char c) {for(int i = 0; i<C.length;i++) {if(C[i] == c) {return true;}}return false;}
    
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
