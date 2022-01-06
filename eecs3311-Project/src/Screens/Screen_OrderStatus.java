package Screens;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import System.MovieSystem;
import System.Order;
import System.User;

public class Screen_OrderStatus {
	private JFrame frame;
    private JPanel panel;
	private JButton closeButton;
	private JTextField orderIdInput;
	private JButton cancelOrderButton;
	private AbstractButton orderStatusButton;
	
    public Screen_OrderStatus(MovieSystem Sys, User user) {
        frame = new JFrame();        
        
        orderIdInput = new JTextField("Enter ID");
        
        cancelOrderButton = new JButton("Cancel This Order");
        cancelOrderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean check = false;
				boolean threw = false;
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
					Order temp = Sys.getOrders().get(i);
					if(entry == temp.getID()) {
						if(user.cancelOrder(Sys, entry) == -1) {
							new Screen_PopupNotice(Sys, "Cannot cancel order. It's been delivered.");
							break;
						}
						check = true;
						break;
					}
				}
				if(check) {
					new Screen_PopupNotice(Sys, "Order successfully cancelled");
				}
				else if(!check && !threw) {
					new Screen_PopupNotice(Sys, "Error: Invalid Order ID");
				}
			}
		});
        
        orderStatusButton = new JButton("Check Order Status");
        orderStatusButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//return order status.
				boolean check = false;
				boolean threw = false;
        		Order temp;
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
        	}
        });
        
        closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		frame.dispose();
        	}
        });
                
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
        panel.setLayout(new GridLayout(4, 2));
        
        panel.add(orderIdInput);
        panel.add(orderStatusButton);
        panel.add(cancelOrderButton);
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
