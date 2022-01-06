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
import System.User;

public class Screen_UserMenu {
	private JFrame frame;
    private JPanel panel;
	private JButton cartButton;
	private JLabel welcomeLabel;
	private JButton closeButton;
	private JButton movieSearchButton;
	private JButton setInfoButton;
	private JButton checkOrderButton;
	
    public Screen_UserMenu(MovieSystem Sys, User user) {
        frame = new JFrame();        
        
        welcomeLabel = new JLabel("Welcome " + user.getName() + "!");
        
        cartButton = new JButton("View Cart");
        cartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Screen_ViewCart(Sys, user, user.order);
			}
		});
        
        closeButton = new JButton("Log Out");
        closeButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		frame.dispose();
        	}
        });
        
        movieSearchButton = new JButton("Shop Movies");
        movieSearchButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(user.getAddress().equals("N/A")) {
        			new Screen_PopupNotice(Sys, "Error: Cannot locate nearest warehouse because no address is associated with your account");
        		}
        		else if (user.getWarehouse().getLocation().equals("N/A")) {
        			new Screen_PopupNotice(Sys, "Error: Sorry, there is no warehouse near your location");
        		}
        		else {
        			new Screen_MovieSelect(Sys, user, user.getWarehouse().getMovies());
        		}
        	}
        });
        
        setInfoButton = new JButton("Change Info");
        setInfoButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//new Screen_MovieSelect(Sys, user, Sys.getMovieDB());
        		new Screen_UserInfo(Sys, user);
        	}
        });
        
        checkOrderButton = new JButton("Check Order");
        checkOrderButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		new Screen_OrderStatus(Sys, user);
        	}
        });
        
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
        panel.setLayout(new GridLayout(2, 2));
        
        panel.add(welcomeLabel);
        panel.add(movieSearchButton);
        panel.add(cartButton);
        panel.add(setInfoButton);
        panel.add(checkOrderButton);
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
