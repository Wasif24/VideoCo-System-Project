package Screens;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import System.MovieSystem;

public class Screen_SystemMenu {
	private JFrame frame;
    private JPanel panel;    
	private JButton closeButton;
	private JButton addMinButton;
	private JButton orderButton;
    public Screen_SystemMenu(MovieSystem Sys) {
        frame = new JFrame();
        
        addMinButton = new JButton("View System Admins");
        addMinButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		new Screen_SystemModifyAdmin(Sys);
        	}
        });
        
        orderButton = new JButton("Update an Order");
        orderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new Screen_SystemOrderSearch(Sys);
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
        panel.setLayout(new GridLayout(4, 5));
        
        
        panel.add(addMinButton);
        panel.add(orderButton);
        panel.add(closeButton);
        
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Sign In");
        frame.pack();
        frame.setVisible(true);
                
    }
    
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
