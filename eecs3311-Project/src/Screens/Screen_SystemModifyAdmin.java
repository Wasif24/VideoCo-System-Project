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

public class Screen_SystemModifyAdmin {
	private JFrame frame;
    private JPanel panel;
	private JButton closeButton;
	public JTextField Name = new JTextField(" ");
	public JTextField Email = new JTextField(" ");
	public JTextField Password = new JTextField(" ");
	private JLabel emptyLabel = new JLabel(" ");
	private JLabel nameLabel = new JLabel("Name");
	private JLabel emailLabel = new JLabel("Email");
	private JLabel passLabel = new JLabel("Password");
	private JButton registerButton = new JButton("Register Employee");
	public JList<String> employeeList;
	public ListSelectionListener L;
	private JButton deleteButton;
	
    public Screen_SystemModifyAdmin(MovieSystem Sys) {
    	
    	frame = new JFrame();        
        ArrayList<SysAdmin> Employees = Sys.getAdmins();
        String[] data = new String[Employees.size()];
        for(int i = 0; i<data.length; i++) {
        	data[i] = Employees.get(i).getName();
        }
        employeeList = new JList<String>(data);
		//data has type Object[]
        employeeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        employeeList.setLayoutOrientation(JList.VERTICAL);
        employeeList.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(employeeList);
        
        listScroller.setPreferredSize(new Dimension(250, 80));
        
        Name.setEditable(false);
        Email.setEditable(false);
        Password.setEditable(false);
        registerButton.setEnabled(false);
        
        L = new ListSelectionListener() {
        	@Override
			public void valueChanged(ListSelectionEvent arg0) {
        		SysAdmin selAdmin = new SysAdmin("N/A","N/A","N/A");
        		try {
    				selAdmin = Sys.getAdmins().get(employeeList.getSelectedIndex());
        		}
        		catch(Exception e1) {
					// TODO Auto-generated catch block
					//System.out.println("nonfatal error");
        		}  
        		
        		Name.setText(selAdmin.getName());
        		Email.setText(selAdmin.getEmail());
        		Password.setText(selAdmin.getPassword());
        		if(selAdmin.getEmail().equals("N/A")) {
        			Email.setEditable(true);
        			registerButton.setEnabled(true);
        		}
        		else {
        			Email.setEditable(false);
        		}
        		if(selAdmin.getPassword().equals("N/A")) {
        			Password.setEditable(true);
        			registerButton.setEnabled(true);
        		}
        		else {
        			Password.setEditable(false);
        		}
        		if(selAdmin.getName().equals("N/A")) {
        			registerButton.setEnabled(false);
        		}
			}
        };
        employeeList.addListSelectionListener(L);
        
        registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				registerButtonPressed(Sys, "employee.csv");
			}
		});
        
        deleteButton = new JButton("Delete Selected User");
        deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteButtonPressed(Sys, "employee.csv");
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
        panel.setLayout(new GridLayout(6, 2));
        
        panel.add(listScroller);	panel.add(emptyLabel);
        panel.add(nameLabel);		panel.add(Name);
        panel.add(emailLabel);		panel.add(Email);
        panel.add(passLabel);		panel.add(Password);
        panel.add(registerButton);		panel.add(deleteButton);
        panel.add(closeButton);
        
        
        
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("");
        frame.pack();
        frame.setVisible(true);        
    }
    public void registerButtonPressed(MovieSystem Sys, String path) {
    	if(Email.getText().equalsIgnoreCase("N/A") || Password.getText().equalsIgnoreCase("N/A")) {
			//invalid email and password combo
			new Screen_PopupNotice(Sys,"You must change both the email and passoword!");
		}
		else {
			if(Contains(Email.getText().toCharArray(), ',') || Contains(Password.getText().toCharArray(), ',')) {
				new Screen_PopupNotice(Sys, "Error: entries cannot contain commas(,)!");
			}
			else {
				SysAdmin selAdmin = Sys.getAdmins().get(employeeList.getSelectedIndex());
				Sys.changeAdminInDB(selAdmin, Name.getText(), Email.getText(), Password.getText(), path);
				//selAdmin.setEmail(Email.getText());
				//selAdmin.setPassword(Password.getText());
				//REFRESH LIST
				String[] newData = new String[Sys.getAdmins().size()];
				int c = 0;
				for(int i = 0; i<newData.length; i++) {
					newData[c++] = Sys.getAdmins().get(i).getName();
				}
				if(c == 0) {
					//new Screen_PopupNotice(Sys, "No movies selected");
				}
				employeeList.setListData(newData);
				employeeList.removeListSelectionListener(L);
				employeeList.clearSelection();
				employeeList.addListSelectionListener(L);
			}
		}
    }
    public void deleteButtonPressed(MovieSystem Sys, String path) {
    	SysAdmin selAdmin = Sys.getAdmins().get(employeeList.getSelectedIndex());
		Sys.removeAdminInDB(selAdmin, path);
		
		//REFRESH LIST
		String[] newData = new String[Sys.getAdmins().size()];
		int c = 0;
		for(int i = 0; i<newData.length; i++) {
			newData[c++] = Sys.getAdmins().get(i).getName();
		}
		if(c == 0) {
			//new Screen_PopupNotice(Sys, "No movies selected");
		}
		employeeList.setListData(newData);
		employeeList.removeListSelectionListener(L);
		employeeList.clearSelection();
		employeeList.addListSelectionListener(L);
    }
    
    public boolean Contains(char[] C, char c) {for(int i = 0; i<C.length;i++) {if(C[i] == c) {return true;}}return false;}
    
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
