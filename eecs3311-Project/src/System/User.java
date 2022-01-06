package System;

import java.util.ArrayList;

public class User {
	public String name;
	public int id;
	public String email;
	public String password;
	public int points;
	public String address;
	public String payment;
	public Order order;
	public Warehouse warehouse;
	public ArrayList<Integer> orderIDs;
	
	public User(String name, int id, String email, String password) {
		super();
		this.name = name;
		this.id = id;
		this.email = email;
		this.password = password;
		this.points = 0;
		this.address = "N/A";
		this.payment = "N/A";
		this.order = new Order();
		this.orderIDs = new ArrayList<Integer>() ;
	}

	public User(){
		super();
		this.points = 0;
		this.address = "N/A";
		this.payment = "N/A";
		this.order = new Order();
		this.orderIDs = new ArrayList<Integer>() ;
	}
	
	public int completeOrder(MovieSystem Sys) {
		if(this.order.getCart().isEmpty()) {
			return -2;
		}
		if(this.getPayment().toLowerCase().equals("points")) {
			if(this.points >= 10) {
				//if payment method is points, deduct points, 
				//else; hypothetical charge to card
				this.points = this.points - 10;
			}
			else {
				return -1;
			}
		}
		for(int i = 0; i<this.order.getCart().size(); i++) {
			this.getWarehouse().addMovieToBeShipped(this.order.getCart().get(i));
		}
		this.order.setID(Sys.generateOrderID()); //generate ID for the order
		this.points++;	//users get 1 point for each order
		
		Sys.addOrder(new Order(this.order));
		this.orderIDs.add(this.order.getID());
		this.order.emptyCart();
		return this.order.getID();	//return ID of transaction
	}
	
	public int cancelOrder(MovieSystem Sys, Integer id) {
		Order temp;
		for(int i = 0; i<Sys.getOrders().size(); i++) {
			temp = Sys.getOrders().get(i);
			if(temp.getID() == id) {
				if(temp.getStatus().equalsIgnoreCase("delivered")) {
					return -1;
				}
				else {
					Sys.removeOrder(i);
					for(int j = 0; j<temp.getCart().size(); j++) {
						this.getWarehouse().addMovieToStock(temp.getCart().get(j));
					}
				}
			}
		}
		return 1;
	}
	
	public String getName() 				{return name;}
	public int getId() 						{return id;}
	public String getEmail()		 		{return email;}
	public String getPassword() 			{return password;}
	public String getAddress()				{return this.address;}
	public int getPoints()					{return this.points;}
	public String getPayment()				{return this.payment;}
	public Order getOrder()					{return this.order;}
	public Warehouse getWarehouse() 		{return this.warehouse;}
	public ArrayList<Integer> getOrderIDs() {return this.orderIDs;}
	
	public void setName(String name) 		{this.name = name;}
	public void setId(int id) 				{this.id = id;}
	public void setEmail(String email) 		{this.email = email;}
	public void setPassword(String password){this.password = password;}
	public void setAddress(String address)	{this.address = address;}
	public void setPoints(int points)		{this.points = points;}
	public void setPayment(String payment)	{this.payment = payment;}
	public void setOrder(Order order)		{this.order = order;}
	public void setWarehouse(Warehouse w) 	{this.warehouse = w;}	
}
