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
import System.Order;

public class Screen_SystemOrderReview {
	private JFrame frame;
	private JButton confirmButton;
	private JButton returnButton;
	public JTextField entry;
	private JPanel panel;
		
	public Screen_SystemOrderReview(MovieSystem Sys, Order order) {
		frame = new JFrame();
		entry = new JTextField(""+order.getStatus());
		
        confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		confirmButtonPressed(Sys, order);
        	}
        });
        
        returnButton = new JButton("Return");
        returnButton.addActionListener(new ActionListener() {//when OK is pressed
			public void actionPerformed(ActionEvent e) {
				frame.dispose(); 
			}
		});
              
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
        panel.setLayout(new GridLayout(4, 1));
        
        panel.add(new JLabel("Enter new order status"));
        panel.add(entry);
        panel.add(confirmButton);
        panel.add(returnButton);
        
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("");
        frame.pack();
        frame.setVisible(true);
                
    }
	public void confirmButtonPressed(MovieSystem Sys, Order order) {
		order.setStatus(entry.getText());
		new Screen_PopupNotice(Sys, "Status Successfully Updated");
	}
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
	
	}

}
