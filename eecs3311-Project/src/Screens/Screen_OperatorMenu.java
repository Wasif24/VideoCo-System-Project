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
import System.User;

public class Screen_OperatorMenu {
	private JFrame frame;
    private JPanel panel;
	private JButton cartButton;
	private JLabel welcomeLabel;
	private JButton closeButton;
	private JButton movieSearchButton;
	public JTextField orderIdInput;
	private JButton orderStatusButton;
	
    public Screen_OperatorMenu(MovieSystem Sys, User user) {
        frame = new JFrame();        
        
        welcomeLabel = new JLabel("Welcome Operator!");
        
        orderIdInput = new JTextField("Enter ID");
        
        closeButton = new JButton("Log Out");
        closeButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		frame.dispose();
        	}
        });
        
        movieSearchButton = new JButton("Shop Movies (Ontario only)");
        movieSearchButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		movieSearchButtonPressed(Sys, user);
        	}
        });
        
        orderStatusButton = new JButton("Check Order Status");
        orderStatusButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		orderStatusButtonPressed(Sys);
        	}
        });
        
        cartButton = new JButton("View Cart");
        cartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cartButtonPressed(Sys, user);
			}
		});
        
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
        panel.setLayout(new GridLayout(3, 2));
        
        panel.add(welcomeLabel);
        panel.add(closeButton);
        panel.add(movieSearchButton);
        panel.add(cartButton);
        panel.add(orderIdInput);
        panel.add(orderStatusButton);
        
        
        
        
        
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("");
        frame.pack();
        frame.setVisible(true);
                
    }
    public void movieSearchButtonPressed(MovieSystem Sys, User user) {
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
    public String orderStatusButtonPressed(MovieSystem Sys) {
    	//return order status.
		boolean check = false;
		boolean threw = false;
		Order temp = new Order();
		Integer entry = -1;
		try {
			entry = Integer.decode(orderIdInput.getText());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			threw = true;
			new Screen_PopupNotice(Sys, "Error: Invalid Order ID");
			//e1.printStackTrace();
		}
		for(int i = 0; i<Sys.getOrders().size();i++) {
			temp = Sys.getOrders().get(i);
			if(temp.getID() == entry) {
				new Screen_PopupNotice(Sys, "Your order status: " + temp.getStatus());
				check = true;
				break;
			}
		}
		if(!check && !threw) {new Screen_PopupNotice(Sys, "Error: Invalid Order ID");}
		return temp.getStatus();
    }
    public void cartButtonPressed(MovieSystem Sys, User user) {
    	new Screen_ViewCart(Sys, user, user.order);
		new Screen_PopupText(Sys, "Enter Address");
    }
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
