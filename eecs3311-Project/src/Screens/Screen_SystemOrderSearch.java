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

public class Screen_SystemOrderSearch {
	private JFrame frame;
    private JPanel panel;
	private JButton returnButton;
	private JTextField inputID = new JTextField("");
	private JLabel L = new JLabel("Enter Order ID");
	private JButton okButton;
	
    public Screen_SystemOrderSearch(MovieSystem Sys) {
        frame = new JFrame();
        
        
        
        okButton = new JButton("Search ID");
        okButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		boolean noID = true;
        		for(int i = 0; i<Sys.getOrders().size(); i++) {
        			if(Sys.getOrders().get(i).getID() == Integer.valueOf(inputID.getText())) {
        				noID = false;
        				new Screen_SystemOrderReview(Sys, Sys.getOrders().get(i));
        			}
        		}
        		if(noID) {
        			new Screen_PopupNotice(Sys, "Error: No Such ID");
        		}
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
        panel.setLayout(new GridLayout(4, 5));
        
        panel.add(L);
        panel.add(inputID);
        panel.add(okButton);
        panel.add(returnButton);
        
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
