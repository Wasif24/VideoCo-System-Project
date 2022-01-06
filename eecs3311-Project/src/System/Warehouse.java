package System;

import java.util.ArrayList;

public class Warehouse {
	 private ArrayList<Movie> MovieStock;
	 private ArrayList<Movie> toBeShipped;
	 private String Location;
	 
	 public Warehouse() {
		 this.MovieStock = new ArrayList<Movie>();
		 this.toBeShipped = new ArrayList<Movie>();
		 this.Location = "N/A";
	 }
	 public Warehouse(String location) {
		 this.MovieStock = new ArrayList<Movie>();
		 this.toBeShipped = new ArrayList<Movie>();
		 this.Location = location;
	 }
	 	 
	 @SuppressWarnings("unchecked")
	public void SetMoviesToStock(ArrayList<Movie> mvs) {
		 this.MovieStock = (ArrayList<Movie>) mvs.clone();
	 }
	 public void removeMovie(Movie m) {
		 this.MovieStock.remove(m);
	 }
	 public void addMovieToBeShipped(Movie m) {
		 this.toBeShipped.add(m);	
	 }
	 public void addMovieToStock(Movie m) {
		 this.MovieStock.add(m);
	 }
	 
	 public ArrayList<Movie> getMovies(){return this.MovieStock;}	//REQ-3 
	 /*ADD TO UML*/public ArrayList<Movie> getPendingShipment() {return this.toBeShipped;}
	 /*ADD TO UML*/public String getLocation() {return this.Location;}	 
}
