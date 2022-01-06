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

import System.MovieSystem;
import System.SysAdmin;

public class Screen_AdminMenu {
	private JFrame frame;
    private JPanel panel;
	private JLabel welcomeLabel;
	private JButton closeButton;
	private JButton addButton;
	private JButton removeButton;
	private JButton modifyButton;
	private JButton UsersButton;
	
    public Screen_AdminMenu(MovieSystem Sys, SysAdmin admin) {
        frame = new JFrame();        
        
        welcomeLabel = new JLabel("Welcome " + admin.getName() + "!");
        JLabel emptyLabel = new JLabel(" ");
        addButton = new JButton("Add a Movie");
        addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Screen_AdminAddMovie(Sys, admin);
			}
		});
        
        removeButton = new JButton("Remove a Movie");
        removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Screen_AdminRemoveMovie(Sys, admin);
			}
		});
        
        modifyButton = new JButton("Modify Movies");
        modifyButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		new Screen_AdminModifyMovie(Sys, admin);
        	}
        });
        
        UsersButton = new JButton("Review Users");
        UsersButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		new Screen_AdminModifyUsers(Sys, admin);
        	}
        });
        
        
        closeButton = new JButton("Log Out");
        closeButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		frame.dispose();
        	}
        });
        
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
        panel.setLayout(new GridLayout(4, 2));
        
        panel.add(welcomeLabel);
        panel.add(emptyLabel);
        panel.add(addButton);
        panel.add(removeButton);
        panel.add(modifyButton);
        panel.add(UsersButton);
        panel.add(closeButton);
        
        
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("");
        frame.pack();
        frame.setVisible(true);
                
    }
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
