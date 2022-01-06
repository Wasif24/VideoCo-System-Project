package System;

public class Movie {
	
	private String title;
	private String description;
	private String actors;
	private String director;
	private String genre;
	/*ADD TO UML*/private double price;
	
	public Movie(String Title, String Desc, String Actors, String Director, String Genre, double Price) {
		this.title = Title;
		this.description = Desc;
		this.actors = Actors;
		this.director = Director;
		this.genre = Genre;	
		/*ADD TO UML*/this.price = Price;
	}
	public Movie() {
		
	}
	
	/*ADD TO UML*/public String getTitle() {return this.title;}
	/*ADD TO UML*/public String getDesc() {return this.description;}
	/*ADD TO UML*/public String getActors() {return this.actors;}
	/*ADD TO UML*/public String getDirector() {return this.director;}
	/*ADD TO UML*/public String getGenre() {return this.genre;}
	/*ADD TO UML*/public double getPrice() {return this.price;}
	
	/*ADD TO UML*/public void setTitle(String title) {this.title = title;}
	/*ADD TO UML*/public void setDesc(String desc) {this.description = desc;}
	/*ADD TO UML*/public void setActors(String actors) {this.actors = actors;}
	/*ADD TO UML*/public void setDirector(String dir) {this.director = dir;}
	/*ADD TO UML*/public void setGenre(String genre) {this.genre = genre;}
	/*ADD TO UML*/public void setPrice(double price) {this.price = price;}
	
	@Override
	public String toString() {
		return this.getTitle();
	}
}
