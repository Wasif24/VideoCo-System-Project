package Screens;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import System.Movie;
import System.MovieSystem;
import System.SysAdmin;

public class Screen_AdminRemoveMovie {
	private JFrame frame;
    private JPanel panel;
	private JButton closeButton;
	private JButton removeButton;
	private JLabel Title = new JLabel(" ");
	private JLabel Desc = new JLabel(" ");
	private JLabel Genre = new JLabel(" ");
	private JLabel Actors = new JLabel(" ");
	private JLabel Director = new JLabel(" ");
	private JLabel Price = new JLabel(" ");
	public JList<String> movieList;
	public ListSelectionListener L;
	
    public Screen_AdminRemoveMovie(MovieSystem Sys, SysAdmin admin) {
    	
    	frame = new JFrame();        
        ArrayList<Movie> mvs = Sys.getMovieDB();
        String[] data = new String[mvs.size()];
        for(int i = 0; i<data.length; i++) {
        	data[i] = mvs.get(i).getTitle();
        }
        movieList = new JList<String>(data);
		//data has type Object[]
        movieList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        movieList.setLayoutOrientation(JList.VERTICAL);
        movieList.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(movieList);
        
        listScroller.setPreferredSize(new Dimension(250, 80));
        
        L = new ListSelectionListener() {
        	@Override
			public void valueChanged(ListSelectionEvent arg0) {
				// TODO Auto-generated method stub
        		Movie selMovie = new Movie("N/A","N/A","N/A","N/A","N/A",0.00);
        		try {
    				selMovie = Sys.getMovieDB().get(movieList.getSelectedIndex());
        		}
        		catch(Exception e1) {
					// TODO Auto-generated catch block
					//System.out.println("nonfatal error with removing movie from db");
        		}      
				Title.setText(""+selMovie.getTitle());
				Desc.setText(""+selMovie.getDesc());
				Genre.setText(""+selMovie.getGenre());
				Actors.setText(""+selMovie.getActors());
				Director.setText(""+selMovie.getDirector());
				Price.setText(""+selMovie.getPrice());
			}
        };
        movieList.addListSelectionListener(L);
        
        removeButton = new JButton("Remove Selected Movie");
        removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeButtonPressed(Sys, "movie.csv");
			}
		});
                
        
        
        closeButton = new JButton("Return");
        closeButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		frame.dispose();
        	}
        });
        
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
        panel.setLayout(new GridLayout(9, 1));
        
        panel.add(listScroller);
        panel.add(Title);
        panel.add(Genre);
        panel.add(Desc);
        panel.add(Actors);
        panel.add(Director);
        panel.add(Price);
        panel.add(removeButton);
        panel.add(closeButton);
        
        
        
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("");
        frame.pack();
        frame.setVisible(true);        
    }
    
    public void removeButtonPressed(MovieSystem Sys, String path) {
    	int ind = movieList.getSelectedIndex();
		new Screen_PopupNotice(Sys, "Movie successfully removed: \n"+Sys.getMovieDB().get(ind));
		Sys.removeMoviefromDB(Sys.getMovieDB().get(ind), path);
		
		try {
			Sys.updateMovieCSV(path);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		}
		
		String[] newData = new String[Sys.getMovieDB().size()];
		int c = 0;
		for(int i = 0; i<newData.length; i++) {
			newData[c++] = Sys.getMovieDB().get(i).getTitle();
		}
		if(c == 0) {
			//new Screen_PopupNotice(Sys, "No movies selected");
		}
		movieList.setListData(newData);
		movieList.removeListSelectionListener(L);
		movieList.clearSelection();
		movieList.addListSelectionListener(L);
    }
    
    //public boolean Contains(char[] C, char c) {for(int i = 0; i<C.length;i++) {if(C[i] == c) {return true;}}return false;}
    
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
