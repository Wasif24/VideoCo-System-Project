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


public class Screen_PopupNotice {
	private JFrame frame;
    private JPanel panel;
	private JButton okButton;
	
    public Screen_PopupNotice(MovieSystem Sys, String popMessage) {
        frame = new JFrame();
        
        JLabel errLabel = new JLabel(popMessage);
        
        okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {//when OK is pressed
			public void actionPerformed(ActionEvent e) {
				frame.dispose(); 
			}
		});
              
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
        panel.setLayout(new GridLayout(4, 5));
        
        panel.add(errLabel);
        panel.add(okButton);
        
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
