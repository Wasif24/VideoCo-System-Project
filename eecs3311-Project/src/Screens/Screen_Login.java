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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import System.MovieSystem;
import System.SysAdmin;
import System.User;

public class Screen_Login implements ActionListener{
    private JFrame frame;
    private JButton loginButton;
    private JLabel userLabel;
    private JPanel panel;
    private JTextField userText;
    private JLabel passwordLabel;
    private JPasswordField passwordText;
	private JLabel emailLabel;
	private JTextField emailText;
	private JButton closeButton;
    public Screen_Login(MovieSystem Sys) {
        frame = new JFrame();
        
        userLabel = new JLabel("Username");
        userText = new JTextField(20);
        
        emailLabel = new JLabel("Email");
        emailText = new JTextField(20);
        
        passwordLabel = new JLabel("Password");
        passwordText = new JPasswordField(20);
        
        
        loginButton = new JButton("Login/Register");
        loginButton.addActionListener(new ActionListener() {//when login is pressed
			public void actionPerformed(ActionEvent e) {
				int[] s = {-1,-1};
				if(userText.getText().isEmpty() || emailText.getText().isEmpty() || new String(passwordText.getPassword()).isEmpty()) {
					new Screen_PopupNotice(Sys, "One or more fields are empty!");
					//System.out.println("One or more fields are empty!");
				}
				else {
					try {
						s = Sys.signIn(userText.getText(), emailText.getText(), new String(passwordText.getPassword()), "user.csv");
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if(s[0] != -1) {
						if(s[0] == 0) {//System account detected
							new Screen_SystemMenu(Sys);
						}
						else if(s[0] == 1) {//admin account detected
							SysAdmin Ad = Sys.getAdmins().get(s[1]);
							new Screen_AdminMenu(Sys, Ad);
						}
						else if(s[0] == 2) {//user or operator account detected
							User cust = Sys.getCustomer(s[1]);	//get the user that signIn returns
							if(cust.getName().equals("Operator")) {
								if(cust.getEmail().equals("op@vidco.ca") && cust.getPassword().equals("Operator123")) {
									cust.setAddress("Ontario");
				        			cust.setWarehouse(Sys.associateWarehouse(cust));
									new Screen_OperatorMenu(Sys, cust);
								}
							}
							else {
								new Screen_UserMenu(Sys, cust);
							}
						}
					}				
				}	
			}
		});
        
        closeButton = new JButton("Exit");
        closeButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		frame.dispose();
        	}
        });
        
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
        panel.setLayout(new GridLayout(4, 5));
        
        panel.add(userLabel);
        panel.add(userText);
        panel.add(emailLabel);
        panel.add(emailText);
        panel.add(passwordLabel);
        panel.add(passwordText);
        panel.add(loginButton);
        panel.add(closeButton);
        
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Sign In");
        frame.pack();
        frame.setVisible(true);
                
    }
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}