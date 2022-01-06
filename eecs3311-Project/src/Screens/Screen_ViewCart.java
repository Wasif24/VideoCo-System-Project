package Screens;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import System.Movie;
import System.MovieSystem;
import System.Order;
import System.User;

public class Screen_ViewCart {
	private JFrame frame;
    private JPanel panel;
    public JList<String> movieList;
    public JLabel totalLabel; 
    public DecimalFormat d = new DecimalFormat("0.00");
	private JButton remButton;
	private JButton backButton;
	private JButton completeButton;
	public double latefee;
	
    public Screen_ViewCart(MovieSystem Sys, User user, Order order) {
        frame = new JFrame();
        latefee = 0.00;
        if(!user.getAddress().equals("Ontario")) {
        	latefee = 9.99;
        }
        //JList<Movie> movieList = new JList<Movie>(mvs);
        ArrayList<Movie> Cart = user.order.getCart();
        String[] data = new String[Cart.size()];
        for(int i = 0; i<data.length; i++) {
        	data[i] = Cart.get(i).getTitle();
        }
        movieList = new JList<String>(data);
		//data has type Object[]
        movieList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        movieList.setLayoutOrientation(JList.VERTICAL);
        movieList.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(movieList);
        
        listScroller.setPreferredSize(new Dimension(250, 80));
        
        totalLabel = new JLabel( ("Total: $" + d.format(user.order.getTotal() + latefee)));
        
        
        remButton = new JButton("Remove Selected From Cart");
        remButton.addActionListener(new ActionListener() {//when OK is pressed
			public void actionPerformed(ActionEvent e) {
				remButtonPressed(Sys, user);
			}
		});
        
        completeButton = new JButton("Checkout");
        completeButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int id = user.completeOrder(Sys);
        		if(id == -1) {
        			new Screen_PopupNotice(Sys, "Error: Insufficient Points Balance");
        		}
        		else if(id == -2) {
        			new Screen_PopupNotice(Sys, "Error: No Items in Cart");
        		}
        		else {
        			new Screen_PopupNotice(Sys, "Order complete, your order ID is: " + id);
        		}
        		String[] data = new String[1];
				movieList.setListData(data);
				double latefee = 0.00;
				if(!user.getAddress().equals("Ontario")) {
		        	latefee = 9.99;
		        }
				totalLabel.setText("Total: $" + d.format(user.order.getTotal() + latefee));
        	}
        });
        
        backButton = new JButton("Close");
        backButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		frame.dispose();
        	}
        });
        
        JLabel pointsLabel = new JLabel("Points balance: " + user.getPoints());
        
        JLabel late = new JLabel("Warning: Since you are not in Ontario, a $9.99 fee has been added to your total");
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
        panel.setLayout(new GridLayout(7, 1));
        
        panel.add(listScroller);
        panel.add(totalLabel);
        panel.add(pointsLabel);
        panel.add(remButton);
        panel.add(completeButton);
        panel.add(backButton);
        if(!user.getAddress().equals("Ontario")) {
        	panel.add(late);
        }
        else {
        	panel.add(new JLabel(""));
        }
        
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("");
        frame.pack();
        frame.setVisible(true);
                
    }
    public void remButtonPressed(MovieSystem Sys, User user) {
    	Movie m;
		int[] inds = movieList.getSelectedIndices();
		double latefee = 0.00;
		if(!user.getAddress().equals("Ontario")) {
        	latefee = 9.99;
        }
		for(int i = 0; i<inds.length; i++) {
			m = user.order.getCart().get(inds[i] - i);
			user.order.remFromCart(m);
			user.getWarehouse().getMovies().add(m);
		}
		
		//REFRESH LIST
		String[] data = new String[user.order.getCart().size()];
        for(int i = 0; i<data.length; i++) {
        	data[i] = user.order.getCart().get(i).getTitle();
        }
		movieList.setListData(data);
		
		totalLabel.setText("Total: $" + d.format(user.order.getTotal() + latefee));
		
		//suboptimal way (refresh the whole frame)
		//frame.dispose();
		//new Screen_ViewCart(Sys, order);
    }
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
