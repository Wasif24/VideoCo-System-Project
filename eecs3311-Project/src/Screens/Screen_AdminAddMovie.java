package Screens;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import System.Movie;
import System.MovieSystem;
import System.SysAdmin;

public class Screen_AdminAddMovie {
	private JFrame frame;
    private JPanel panel;
	private JButton closeButton;
	private JButton addButton;
	public JTextField tEntry;
	public JTextField gEntry;
	public JTextField dEntry;
	public JTextField aEntry;
	public JTextField dirEntry;
	public JTextField pEntry;
	
    public Screen_AdminAddMovie(MovieSystem Sys, SysAdmin admin) {
        frame = new JFrame();        
        
        tEntry = new JTextField("Title");
        tEntry.addFocusListener(new FocusListener() {
			@Override
        	public void focusGained(FocusEvent arg0) {
				if(tEntry.getText().equals("Title")) {
					tEntry.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent arg0) {
				if(tEntry.getText().equals("")) {
					tEntry.setText("Title");
				}
			}
        	
        });
        gEntry = new JTextField("Genre");
        gEntry.addFocusListener(new FocusListener() {
			@Override
        	public void focusGained(FocusEvent arg0) {
				if(gEntry.getText().equals("Genre")) {
					gEntry.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent arg0) {
				if(gEntry.getText().equals("")) {
					gEntry.setText("Genre");
				}
			}
        	
        });
        dEntry = new JTextField("Description");
        dEntry.addFocusListener(new FocusListener() {
			@Override
        	public void focusGained(FocusEvent arg0) {
				if(dEntry.getText().equals("Description")) {
					dEntry.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent arg0) {
				if(dEntry.getText().equals("")) {
					dEntry.setText("Description");
				}
			}
        	
        });
        aEntry = new JTextField("Actors (semicolon separated)");
        aEntry.addFocusListener(new FocusListener() {
			@Override
        	public void focusGained(FocusEvent arg0) {
				if(aEntry.getText().equals("Actors (semicolon separated)")) {
					aEntry.setText("");
				}
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				if(aEntry.getText().equals("")) {
					aEntry.setText("Actors (semicolon separated)");
				}
			}
        	
        });
        dirEntry = new JTextField("Director");
        dirEntry.addFocusListener(new FocusListener() {
			@Override
        	public void focusGained(FocusEvent arg0) {
				if(dirEntry.getText().equals("Director")) {
					dirEntry.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent arg0) {
				if(dirEntry.getText().equals("")) {
					dirEntry.setText("Director");
				}
			}
        	
        });
        pEntry = new JTextField("Price (CAD)");
        pEntry.addFocusListener(new FocusListener() {
			@Override
        	public void focusGained(FocusEvent arg0) {
				if(pEntry.getText().equals("Price (CAD)")) {
					pEntry.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent arg0) {
				if(pEntry.getText().equals("")) {
					pEntry.setText("Price (CAD)");
				}
			}
        	
        });
        
        
        
        addButton = new JButton("Add This Movie");
        addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addButtonPressed(Sys, "movie.csv");
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
        panel.setLayout(new GridLayout(2, 12));
        
        panel.add(tEntry);
        panel.add(gEntry);
        panel.add(dEntry);
        panel.add(addButton);
        panel.add(aEntry);
        panel.add(dirEntry);
        panel.add(pEntry);
        panel.add(closeButton);
        
        
        
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("");
        frame.pack();
        frame.setVisible(true);        
    }
    public void addButtonPressed(MovieSystem Sys, String path) {
    	String title = tEntry.getText();				char[] Title = title.toCharArray();
		String genre = gEntry.getText();				char[] Genre = genre.toCharArray();
		String desc = dEntry.getText();					char[] Desc = desc.toCharArray();
		String actors = aEntry.getText();				char[] Actors = actors.toCharArray();
		String director = dirEntry.getText();			char[] Director = director.toCharArray();
		double price = Double.valueOf(pEntry.getText());
		
		if(!(Contains(Title, ',') || Contains(Genre, ',') || Contains(Desc, ',') || Contains(Actors, ',') || Contains(Director, ','))) {
			Movie m = new Movie(title, desc, actors, director, genre, price);
			Sys.addMovieInDB(m, path);
			new Screen_PopupNotice(Sys, "Successfully added movie to database!");
		}
		else {
			new Screen_PopupNotice(Sys, "Error: Entries must not contain any commas(,)!");
		}
    }
    public boolean Contains(char[] C, char c) {for(int i = 0; i<C.length;i++) {if(C[i] == c) {return true;}}return false;}
    
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
