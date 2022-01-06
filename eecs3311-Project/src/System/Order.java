package System;

import java.util.ArrayList;

public class Order {
	private ArrayList<Movie> cart;
	private double total;
	private Integer id;
	private String status;
	
	public Order() {
		this.cart = new ArrayList<Movie>();
		this.total = 0;
		this.id = 0;
		this.status = "N/A";
	}
	
	@SuppressWarnings("unchecked")
	public Order(Order o) {
		this((ArrayList<Movie>) o.getCart().clone(), o.getTotal(), o.getID(), "Ordered");
	}
	
	public Order(ArrayList<Movie> cart2, double total2, Integer id2, String s) {
		// TODO Auto-generated constructor stub
		this.cart = cart2;
		this.total = total2;
		this.id = id2;
		this.status = s;
	}

	public void addToCart(Movie m) {
		this.cart.add(m);
		this.total = this.total + m.getPrice();
	}
	
	public void emptyCart() {
		while(!this.cart.isEmpty()) {
			this.cart.remove(this.cart.size()-1);
		}
		this.total = 0.00;
	}
	
	public void remFromCart(Movie m) {
		if(this.cart.contains(m)) {
			this.cart.remove(m);
			this.total = this.total - m.getPrice(); 
		}
		else {
			//error, this should never be explored
			//item is not in cart
		}
	}
	
	public ArrayList<Movie> getCart() {return this.cart;}
	public double getTotal() {return this.total;}
	public Integer getID() {return this.id;}
	public String getStatus() {return this.status;}
	
	//public void setTotal(double total) {this.total = total;}
	public void setID(Integer id) {this.id = id;}
	public void setStatus(String s) {this.status = s;}
}
