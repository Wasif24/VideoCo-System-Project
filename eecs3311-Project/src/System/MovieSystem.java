package System;

import java.util.ArrayList;

import Screens.Screen_PopupNotice;

public class MovieSystem {
	public ArrayList<User> customerDB;
	private ArrayList<Movie> movieDB;
	private ArrayList<Order> orders;
	private ArrayList<Warehouse> warehouses;
	private ArrayList<SysAdmin> admins;
	private Integer idGenerator;	
	
	public MovieSystem() throws Exception {
		String uPath = "user.csv";
		String mPath = "movie.csv";
		String ePath = "employee.csv";
		MaintainUser maintainU = new MaintainUser();
		MaintainMovie maintainM = new MaintainMovie();
		MaintainEmployee maintainE = new MaintainEmployee();
		maintainU.load(uPath);
		maintainM.load(mPath);
		maintainE.load(ePath);
		this.customerDB = maintainU.users;
		this.movieDB = maintainM.movies;
		this.admins = maintainE.employees;
		
		this.warehouses = new ArrayList<Warehouse>();
		this.warehouses.add(new Warehouse("Ontario"));
		this.warehouses.get(0).SetMoviesToStock(movieDB);
		this.associateWarehouses();//associate warehouses for all users
		
		this.orders = new ArrayList<Order>();
		
		this.idGenerator = 0;
	}
	
	
	public int[] signIn(String name, String email, String pass, String path) throws Exception {
		int RegisterUser = 1;
		int isAdmin = 0;
		if(name.equals("System") && email.equalsIgnoreCase("sys@vidco.ca") && pass.equals("System")) {
			if(pass.equals("System")) {
				int[] ret = {0, 0};
				return ret;
			}
			else {
				new Screen_PopupNotice(this, "Incorrect Login Credentials!");
			}
		}
		for(int i = 0; i<this.admins.size();i++) {//first try and login as an admin
			SysAdmin curr = this.admins.get(i);
			if(curr.getName().equals(name) && curr.getEmail().equals(email)) {
				RegisterUser = 0;
				isAdmin = 1;
				if(curr.getPassword().equals(pass)) {
					int[] ret = {1,i}; //ret[0] -> 0 = Sys, 1 = Admin, 2 = User, 
					return ret;
				}
				else {
					new Screen_PopupNotice(this, "Incorrect Login Credentials!");
				}
			}
		}
		if(isAdmin == 0) {
			for(int i = 0; i < this.customerDB.size(); i++) {//if login is unsuccessful, then do it 
				User curr = this.customerDB.get(i);
				if(curr.getName().equals(name) && curr.getEmail().equals(email)) {
					RegisterUser = 0;
					if(curr.getPassword().equals(pass) && curr.getEmail().equals(email) ) {
						//take user to user menu
						int[] ret = {2,i};
						return ret;
					}
					else {
						new Screen_PopupNotice(this, "Incorrect Login Credentials!");
						break;
					}
				}
			}
		}
		if(RegisterUser == 1) {
			if(Contains(name.toCharArray(), ',') || Contains(email.toCharArray(), ',') 
					|| Contains(pass.toCharArray(), ',')) {
				//invalid registration
				new Screen_PopupNotice(this, "Invalid Registration Credentials (no commas(,) allowed!)");
			}
			else {
				int newID;
				if(this.customerDB.size()>0) {
					newID = this.customerDB.get(this.customerDB.size()-1).getId() + 1;
				}
				else {
					newID = 1;
				}
				User temp = new User(name, newID, email, pass);
				temp.warehouse = associateWarehouse(temp);
				this.addCustomer(temp, path);					
				new Screen_PopupNotice(this, "User " + name + " is now registered");
			}
		}
		int[] ret = {-1,-1};
		return ret;
	}
		
	public void addCustomer(User c, String path) throws Exception {
		this.customerDB.add(c);
		updateCustomerCSV(c, path);
	}
	
	public void associateWarehouses() {
		for(int j = 0; j < this.customerDB.size(); j++) {
			this.customerDB.get(j).warehouse = associateWarehouse(this.customerDB.get(j)); 
		}
	}
	public Warehouse associateWarehouse(User user) {
		for(int i = 0; i < this.warehouses.size(); i++) {
			if(user.getAddress().equals(warehouses.get(i).getLocation())) {
				return warehouses.get(i);
			}
		}
		return new Warehouse("N/A");
	}
	
	private void updateCustomerCSV(User newUser, String path) throws Exception {
		MaintainUser maintain = new MaintainUser();
		maintain.load(path);
		maintain.users.add(newUser);
		maintain.update(path);	
	}
	public void updateCustomerCSV(String path) throws Exception{
		MaintainUser maintain = new MaintainUser();
		maintain.load(path);
		maintain.users = this.customerDB;
		maintain.update(path);
	}
	public void updateMovieCSV(String path) throws Exception{
		MaintainMovie maintain = new MaintainMovie();
		maintain.load(path);
		maintain.movies = this.movieDB;
		maintain.update(path);
	}
	public void updateEmployeeCSV(String path) throws Exception{
		MaintainEmployee maintain = new MaintainEmployee();
		maintain.load(path);
		maintain.employees = this.admins;
		maintain.update(path);
	}
	
	public Integer generateOrderID() {
		++this.idGenerator;
		return idGenerator;
	}
	
	public void addOrder(Order o) {
		this.orders.add(o);
	}
	
	public void removeMoviefromDB(Movie m, String path) {
		for(int i = 0; i<this.warehouses.size();i++) {
			Warehouse tempHouse = this.warehouses.get(i);
			for(int j = 0; j<tempHouse.getMovies().size();j++) {
				if(tempHouse.getMovies().get(j).getTitle().equals(m.getTitle())) {//remove movie from all warehouses
					tempHouse.removeMovie(tempHouse.getMovies().get(j));
					break;
				}
			}
		}
		for(int i = 0; i<this.movieDB.size();i++) {
			if(this.movieDB.get(i).getTitle().equals(m.getTitle())) {
				this.movieDB.remove(i);
				break;
			}
		}
		try {
			this.updateMovieCSV(path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void changeMovieInDB(Movie m, String Title, String Desc, String Actors, String Director, String Genre, double Price, String path) {
		for(int i = 0; i<this.warehouses.size();i++) {
			Warehouse tempHouse = this.warehouses.get(i);
			for(int j = 0; j<tempHouse.getMovies().size();j++) {
				if(tempHouse.getMovies().get(j).getTitle().equals(m.getTitle())) {
					tempHouse.getMovies().get(j).setTitle(Title);
					tempHouse.getMovies().get(j).setDesc(Desc);
					tempHouse.getMovies().get(j).setActors(Actors);
					tempHouse.getMovies().get(j).setDirector(Director);
					tempHouse.getMovies().get(j).setGenre(Genre);
					tempHouse.getMovies().get(j).setPrice(Price);
					//break;//dont break because all movies with this title must be changed
				}
			}
		}
		for(int i = 0;i<this.movieDB.size();i++) {
			if(this.movieDB.get(i).getTitle().equals(m.getTitle())) {
				this.movieDB.get(i).setTitle(Title);
				this.movieDB.get(i).setDesc(Desc);
				this.movieDB.get(i).setActors(Actors);
				this.movieDB.get(i).setDirector(Director);
				this.movieDB.get(i).setGenre(Genre);
				this.movieDB.get(i).setPrice(Price);
			}
		}
		try {
			this.updateMovieCSV(path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void changeUserInDB(User u, String Name, String Email, String Password, int OrderID, String orderStatus, String path) {
		for(int i = 0; i<this.customerDB.size();i++) {
			if(this.getCustomer(i).getName().equals(u.getName()) && this.getCustomer(i).getEmail().equals(u.getEmail())) {
				//if this customer meets the criteria for this user:
				this.getCustomer(i).setName(Name);
				this.getCustomer(i).setEmail(Email);
				this.getCustomer(i).setPassword(Password);
				for(int j = 0; j<this.orders.size();j++) {
					if(this.orders.get(j).getID() == OrderID) {
						if(orderStatus.equalsIgnoreCase("delivered")) {
							//this.orders.remove(this.orders.get(j));//dont remove cuz they need to know its been delivered
							this.getCustomer(i).getOrderIDs().remove(this.getCustomer(i).getOrderIDs().indexOf(OrderID));//this might not work
							this.orders.get(j).setStatus(orderStatus);
						}
						else if(orderStatus.equalsIgnoreCase("cancelled")) {
							this.getCustomer(i).cancelOrder(this, this.orders.get(j).getID());
						}
						else {//delayed, ordered
							this.orders.get(j).setStatus(orderStatus);
						}
					}
				}
			}
		}
		try {
			this.updateCustomerCSV(path);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void changeUserInDB(User u, String Name, String Email, String Password, String path) {
		for(int i = 0; i<this.customerDB.size();i++) {
			if(this.getCustomer(i).getName().equals(u.getName()) && this.getCustomer(i).getEmail().equals(u.getEmail())) {
				//if this customer meets the criteria for this user:
				this.getCustomer(i).setName(Name);
				this.getCustomer(i).setEmail(Email);
				this.getCustomer(i).setPassword(Password);
			}
		}
		try {
			this.updateCustomerCSV(path);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
		
	public void removeUserInDB(User u, String path) {
		for(int i = 0; i<this.customerDB.size();i++) {
			if(this.customerDB.get(i).getName().equalsIgnoreCase(u.getName()) &&
					this.customerDB.get(i).getEmail().equalsIgnoreCase(u.getEmail())){
				this.customerDB.remove(i);
				break;
			}
		}
		try {
			this.updateCustomerCSV(path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void removeAdminInDB(SysAdmin s, String path) {
		for(int i = 0; i < this.admins.size(); i++) {
			if(this.admins.get(i).getName().equalsIgnoreCase(s.getName())&&
					this.admins.get(i).getEmail().equalsIgnoreCase(s.getEmail())) {
				this.admins.remove(i);
				break;
			}
		}
		try {
			this.updateEmployeeCSV(path);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void changeAdminInDB(SysAdmin s, String Name, String Email, String Password, String path) {
		for(int i = 0; i<this.admins.size();i++) {
			if(this.admins.get(i).getName().equals(s.getName()) &&
					this.admins.get(i).getEmail().equals(s.getEmail())) {
				//if this customer meets the criteria for this user:
				this.admins.get(i).setName(Name);
				this.admins.get(i).setEmail(Email);
				this.admins.get(i).setPassword(Password);
			}
		}
		try {
			this.updateEmployeeCSV(path);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public ArrayList<Movie> getMovieDB()		{return this.movieDB;}
	public ArrayList<User> getCustomerDB()		{return this.customerDB;}
	public ArrayList<Order> getOrders()			{return this.orders;}
	public ArrayList<SysAdmin> getAdmins()		{return this.admins;}
	/*
	public void setMovieDB(ArrayList<Movie> ms)			{this.movieDB = ms;}
	public void setCustomerDB(ArrayList<User> us)		{this.customerDB = us;}
	public void setOrders(ArrayList<Order> os)			{this.orders = os;}
	public void setWarehouses(ArrayList<Warehouse> ws)	{this.warehouses = ws;}
	public void setEmployees(ArrayList<String> Is)		{this.employees = Is;}
	public void setAdmins(ArrayList<SysAdmin> ss)		{this.admins = ss;}
	*/
	public void addMovieInDB(Movie m, String path) {
		this.movieDB.add(m);
		try {this.updateMovieCSV(path);} 
		catch (Exception e1) {e1.printStackTrace();}
	}

	//public void addAdmin(SysAdmin a) {this.admins.add(a);}
	public void removeOrder(int index) {this.orders.remove(index);}
	public User getCustomer(int i) {return this.customerDB.get(i);}
	
    public boolean Contains(char[] C, char c) {for(int i = 0; i<C.length;i++) {if(C[i] == c) {return true;}}return false;}

}
