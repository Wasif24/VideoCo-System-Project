package Screens;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import System.MovieSystem;
import System.User;

public class Screen_UserInfo {
	private JFrame frame;
    private JPanel panel;
	private JLabel addressText;
	public JTextField addressEntry;
	public JTextField paymentEntry;
	private JButton saveButton;
	private JButton exitButton;
	private JLabel paymentText;
	private JLabel userText;
	public JTextField userEntry;
	private JLabel emailText;
	public JTextField emailEntry;
	private JLabel passwordText;
	public JTextField passwordEntry;
	
    public Screen_UserInfo(MovieSystem Sys, User user) {
        frame = new JFrame();        
                
        addressText = new JLabel("Address:");
        String A = user.getAddress();
        addressEntry = new JTextField(A);
        
        paymentText = new JLabel("Payment (enter card info or 'Points'):   ");
        String P = user.getPayment();
        paymentEntry = new JTextField(P);
        
        userText = new JLabel("Username:   ");
        String U = user.getName();
        userEntry = new JTextField(U);
        
        emailText = new JLabel("E-mail:   ");
        String E = user.getEmail();
        emailEntry = new JTextField(E);
        
        passwordText = new JLabel("Password:   ");
        String Pass = user.getPassword();
        passwordEntry = new JTextField(Pass);
        
        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveButtonPressed(Sys, user);
			}
		});
                
        exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
        
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
        panel.setLayout(new GridLayout(6, 2));
        
        panel.add(addressText);
        panel.add(addressEntry);
        panel.add(paymentText);
        panel.add(paymentEntry);
        panel.add(userText);
        panel.add(userEntry);
        panel.add(emailText);
        panel.add(emailEntry);
        panel.add(passwordText);
        panel.add(passwordEntry);
        panel.add(saveButton);
        panel.add(exitButton);
        
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
                
    }
    public int saveButtonPressed(MovieSystem Sys, User user) {
    	//save text in payment and address
		if(Contains(addressEntry.getText().toCharArray(), ',') ||
				Contains(paymentEntry.getText().toCharArray(), ',') ||
				Contains(userEntry.getText().toCharArray(), ',') ||
				Contains(emailEntry.getText().toCharArray(), ',') ||
				Contains(passwordEntry.getText().toCharArray(), ',')) {
			new Screen_PopupNotice(Sys, "Error: Entries must not contain any commas(,)!");
			return -1;
		}
		else {
			if(addressEntry.getText().length() == 0) {user.setAddress("N/A");}
			else {
				if(addressEntry.getText().toUpperCase().equals("ONTARIO")) {
					user.setAddress("Ontario");
				}
				else {
					user.setAddress(addressEntry.getText());
				}
			}
			user.setWarehouse(Sys.associateWarehouse(user));
			
			if(paymentEntry.getText().length() == 0) {user.setPayment("N/A");}
			else {user.setPayment(paymentEntry.getText());}
			
			if(userEntry.getText().length() == 0) {/*do nothing, dont change*/}
			else {user.setName(userEntry.getText());}
			
			if(emailEntry.getText().length() == 0) {/*do nothing, dont change*/}
			else {user.setEmail(emailEntry.getText());}
			
			if(passwordEntry.getText().length() == 0) {/*do nothing, dont change*/}
			else {user.setPassword(passwordEntry.getText());}
			
			try {
				Sys.updateCustomerCSV("user.csv");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			new Screen_PopupNotice(Sys, "Info updated");
			return 1;
		}
    }
    public boolean Contains(char[] C, char c) {for(int i = 0; i<C.length;i++) {if(C[i] == c) {return true;}}return false;}
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
