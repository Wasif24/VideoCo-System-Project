package Screens;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import System.Movie;
import System.MovieSystem;
import System.User;

public class Screen_MovieSelect {
	private JFrame frame;
    private JPanel panel;
	private JButton okButton;
	public JTextField searchBar;
	private JButton gSearchButton;
	private JButton tSearchButton;
	private JButton aSearchButton;
	public JList<String> movieList;
	private JLabel defLabel;
	private JButton backButton;
	
    public Screen_MovieSelect(MovieSystem Sys, User user, ArrayList<Movie> mvs) {
        frame = new JFrame();
        
        defLabel = new JLabel("");
        
        String[] data = new String[mvs.size()];
        for(int i = 0; i<data.length; i++) {
        	data[i] = mvs.get(i).getTitle();
        }
        movieList = new JList<String>(data);
		//data has type Object[]
        movieList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        movieList.setLayoutOrientation(JList.VERTICAL);
        movieList.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(movieList);
        
        listScroller.setPreferredSize(new Dimension(250, 80));
        
        okButton = new JButton("Add To Cart");
        okButton.addActionListener(new ActionListener() {//when OK is pressed
			public void actionPerformed(ActionEvent e) {
				okButtonPressed(Sys, user, mvs);
			}
			
		});
        
        searchBar = new JTextField("Enter Search Keyword", 3);
        searchBar.addFocusListener(new FocusListener() {
			@Override
        	public void focusGained(FocusEvent arg0) {
				if(searchBar.getText().equals("Enter Search Keyword")) {
					searchBar.setText("");
				}
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				if(searchBar.getText().equals("")) {
					searchBar.setText("Enter Search Keyword");
				}
			}
        	
        });
        
        tSearchButton = new JButton("Search by Title");
        tSearchButton.addActionListener(new ActionListener() {//when OK is pressed
			public void actionPerformed(ActionEvent e) {
				tSearchButtonPressed(Sys, mvs);
			}
		});
        
        gSearchButton = new JButton("Search by Genre"); 
        gSearchButton.addActionListener(new ActionListener() {//when OK is pressed
			public void actionPerformed(ActionEvent e) {
				gSearchButtonPressed(Sys, mvs);
			}
		});
        
        aSearchButton = new JButton("Show All Movies");
        aSearchButton.addActionListener(new ActionListener() {//when OK is pressed
			public void actionPerformed(ActionEvent e) {
				String[] newData = new String[mvs.size()];
				for(int i = 0; i<mvs.size(); i++) {
					newData[i] = mvs.get(i).getTitle();
				}
				movieList.setListData(newData);
			}
		});
        
        backButton = new JButton("Return");
        backButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		frame.dispose();
        	}
        });
        
              
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
        panel.setLayout(new GridLayout(8, 3));
        
        panel.add(defLabel);
        panel.add(searchBar);
        panel.add(tSearchButton);
        panel.add(gSearchButton);
        panel.add(aSearchButton);
        panel.add(listScroller);
        panel.add(okButton);
        panel.add(backButton);
        
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("");
        frame.pack();
        frame.setVisible(true);
                
    }
    public void okButtonPressed(MovieSystem Sys, User user, ArrayList<Movie> mvs) {
    	List<String> sel = movieList.getSelectedValuesList();
		ArrayList<String> moviesAddedNow = new ArrayList<String>();
		if(sel.size() == 0) {
			new Screen_PopupNotice(Sys, "No movies selected");
		}
		else {
			for(int i = 0; i<sel.size(); i++) {
				for(int j = 0; j<mvs.size(); j++) {
					if(mvs.get(j).getTitle().equals(sel.get(i))) {
						if(moviesAddedNow.contains(mvs.get(j).getTitle())) {
							
						}
						else {
							moviesAddedNow.add(mvs.get(j).getTitle());
							user.order.addToCart(mvs.get(j));
							user.getWarehouse().removeMovie(mvs.get(j)); //remove the movie from stock
						}
					}
				}
			}
			//remove item from the list in display
			String str="";
			if(sel.size() == 1) {
				str = "Successfully added movie to cart";
			}
			else {
				str = "Successfully added movies to cart";
			}
			
			String[] newData = new String[user.getWarehouse().getMovies().size()];
			int c = 0;
			for(int i = 0; i<newData.length; i++) {
				newData[c++] = user.getWarehouse().getMovies().get(i).getTitle();
			}
			if(c == 0) {
				//new Screen_PopupNotice(Sys, "No movies selected");
			}
			movieList.setListData(newData);
			
			new Screen_PopupNotice(Sys, str);
		}
    }
    
    public String[] tSearchButtonPressed(MovieSystem Sys, ArrayList<Movie> mvs) {
    	String[] newData = new String[mvs.size()];
		int c = 0;
		for(int i = 0; i<mvs.size(); i++) {
			if(searchBar.getText().equals(mvs.get(i).getTitle())) {
				newData[c++] = mvs.get(i).getTitle();
			}
		}
		if(c == 0) {
			new Screen_PopupNotice(Sys, "No movies match the selected criteria");
		}
		movieList.setListData(newData);
		return newData;
    }
    public String[] gSearchButtonPressed(MovieSystem Sys, ArrayList<Movie> mvs) {
    	String[] newData = new String[mvs.size()];
		int c = 0;
		for(int i = 0; i<mvs.size(); i++) {
			if(searchBar.getText().equals(mvs.get(i).getGenre())) {
				newData[c++] = mvs.get(i).getTitle();
			}
		}
		if(c == 0) {
			new Screen_PopupNotice(Sys, "No movies match the selected criteria");
		}
		movieList.setListData(newData);
		return newData;
    }
    
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
