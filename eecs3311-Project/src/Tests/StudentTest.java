package Tests;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import System.*;
import Screens.*;


public class StudentTest {
	
	MovieSystem Sys;
	Screen_MovieSelect S_MS;
	Screen_ViewCart S_VC;
	Screen_OperatorMenu S_OM;
	Screen_UserInfo S_UI;
	Screen_AdminAddMovie S_AAM;
	private Screen_AdminRemoveMovie S_ARM;
	private Screen_AdminModifyMovie S_AMM;
	private Screen_AdminModifyUsers S_AMU;
	private Screen_SystemModifyAdmin S_SMA;
	private Screen_SystemOrderReview S_SOR;
	@Before
    public void setUpSystem() throws Exception {
		Sys = new MovieSystem();
    }
	
	@Test
	public void REQ_1_1() throws Exception {
		int[] res = Sys.signIn("Bobby", "bob@gmail.com", "BobIsQueen", "user-test.csv");
		int[] exp = {2, 4};
		
		Assert.assertEquals(exp[0], res[0]);
		Assert.assertEquals(exp[1], res[1]);
	}
	@Test
	public void REQ_1_2() throws Exception {
		int[] res = Sys.signIn("Bobby", "bob@gmail.com", "BobIsKing123", "user-test.csv");
		int[] exp = {-1,-1};
		
		Assert.assertEquals(exp[0], res[0]);
		Assert.assertEquals(exp[1], res[1]);
	}
	@Test
	public void REQ_1_3() throws Exception {
		int[] res = Sys.signIn("System", "sys@vidco.ca", "System", "user-test.csv");
		int[] exp = {0, 0};
		
		Assert.assertEquals(exp[0], res[0]);
		Assert.assertEquals(exp[1], res[1]);
	}
	@Test
	public void REQ_1_4() throws Exception {
		int[] res = Sys.signIn("Adam", "ad@vidco.ca", "Admin123", "user-test.csv");
		int[] exp = {1, 0};
		
		Assert.assertEquals(exp[0], res[0]);
		Assert.assertEquals(exp[1], res[1]);
	}
	
	@Test
	public void REQ_2_1() throws Exception {
		Sys.signIn("Song", "wangs@yorku.ca", "songwang123", "user-test.csv");
		User res = Sys.getCustomer(Sys.getCustomerDB().size()-1);
		User exp = new User("Song", 12, "wangs@yorku.ca", "songwang123");
		
		Assert.assertEquals(exp.getName(), res.getName());
		Assert.assertEquals(exp.getId(), res.getId());
		Assert.assertEquals(exp.getEmail(), res.getEmail());
		Assert.assertEquals(exp.getPassword(), res.getPassword());
	}
	@Test
	public void REQ_2_2() throws Exception {
		Sys.signIn("So,ng", "wangs@yorku.ca", "songwang123", "user-test.csv");
		User res = Sys.getCustomer(Sys.getCustomerDB().size()-1);
		User exp = new User("Hau", 11, "hh321@gmail.com", "DreamyPeaches4");
		
		Assert.assertEquals(exp.getName(), res.getName());
		Assert.assertEquals(exp.getId(), res.getId());
		Assert.assertEquals(exp.getEmail(), res.getEmail());
		Assert.assertEquals(exp.getPassword(), res.getPassword());
	}

	@Test
	public void REQ_3_1() {
		S_MS = new Screen_MovieSelect(Sys, Sys.getCustomer(1), Sys.getMovieDB());
		S_MS.searchBar.setText("Horror");
		String[] res = S_MS.gSearchButtonPressed(Sys, Sys.getMovieDB());
		String[] exp =  {"kill me", "kill me 2: Reawakened", "kill me 2: Reawakened"};
		
		for (int i = 0; i < exp.length; i++) {
			Assert.assertEquals(exp[i], res[i]);
		}
		
	}
	@Test
	public void REQ_3_2() {
		S_MS = new Screen_MovieSelect(Sys, Sys.getCustomer(1), Sys.getMovieDB());
		S_MS.searchBar.setText("kill me 2: Reawakened");
		String[] res = S_MS.tSearchButtonPressed(Sys, Sys.getMovieDB());
		String[] exp =  {"kill me 2: Reawakened", "kill me 2: Reawakened"};
		
		for (int i = 0; i < exp.length; i++) {
			Assert.assertEquals(exp[i], res[i]);
		}
	}

	@Test
	public void REQ_4_1() {
		S_MS = new Screen_MovieSelect(Sys, Sys.getCustomer(1), Sys.getMovieDB());
		int[] sels = {1,2};
		S_MS.movieList.setSelectedIndices(sels);
		S_MS.okButtonPressed(Sys, Sys.getCustomer(1), Sys.getMovieDB());
		ArrayList<Movie> res = Sys.getCustomer(1).order.getCart();
		String[] exp = {"kill me 2: Reawakened", "kill me 3: Unkillable"};
		for(int i = 0; i<exp.length; i++) {
			Assert.assertEquals(exp[i], res.get(i).getTitle());
		}
	}
	@Test
	public void REQ_4_2() {
		S_MS = new Screen_MovieSelect(Sys, Sys.getCustomer(1), Sys.getMovieDB());
		int[] sels = {};
		S_MS.movieList.setSelectedIndices(sels);
		S_MS.okButtonPressed(Sys, Sys.getCustomer(1), Sys.getMovieDB());
		ArrayList<Movie> res = Sys.getCustomer(1).order.getCart();
		String[] exp = {};
		Assert.assertEquals(exp.length, res.size());
	}

	@Test
	public void REQ_5_1() {
		//ADD MOVIES TO CART
		S_MS = new Screen_MovieSelect(Sys, Sys.getCustomer(1), Sys.getMovieDB());
		int[] sels = {1,2};
		S_MS.movieList.setSelectedIndices(sels);
		S_MS.okButtonPressed(Sys, Sys.getCustomer(1), Sys.getMovieDB());
		
		//SELECT ITEMS FROM CART
		S_VC = new Screen_ViewCart(Sys, Sys.getCustomer(1), Sys.getCustomer(1).getOrder());
		int[] sel = {1};
		S_VC.movieList.setSelectedIndices(sel);
		
		//REMOVE SELECTED FROM CART
		S_VC.remButtonPressed(Sys, Sys.getCustomer(1));
		
		ArrayList<Movie> res = Sys.getCustomer(1).order.getCart();
		String[] exp = {"kill me 2: Reawakened"};
		for(int i = 0; i<exp.length; i++) {
			Assert.assertEquals(exp[i], res.get(i).getTitle());
		}
	}
	@Test
	public void REQ_5_2() {
		//ADD MOVIES TO CART
		S_MS = new Screen_MovieSelect(Sys, Sys.getCustomer(1), Sys.getMovieDB());
		int[] sels = {1,2};
		S_MS.movieList.setSelectedIndices(sels);
		S_MS.okButtonPressed(Sys, Sys.getCustomer(1), Sys.getMovieDB());
		
		//SELECT ITEMS FROM CART
		S_VC = new Screen_ViewCart(Sys, Sys.getCustomer(1), Sys.getCustomer(1).getOrder());
		int[] sel = {0,1};
		S_VC.movieList.setSelectedIndices(sel);
				
		//REMOVE SELECTED FROM CART
		S_VC.remButtonPressed(Sys, Sys.getCustomer(1));
		
		ArrayList<Movie> res = Sys.getCustomer(1).order.getCart();
		String[] exp = {};
		Assert.assertEquals(exp.length, res.size());
	}

	@Test
	public void REQ_6_1() {
		//ADD MOVIES TO CART
		S_MS = new Screen_MovieSelect(Sys, Sys.getCustomer(1), Sys.getMovieDB());
		int[] sels = {0,1};
		S_MS.movieList.setSelectedIndices(sels);
		S_MS.okButtonPressed(Sys, Sys.getCustomer(1), Sys.getMovieDB());
				
		//SET PAYMENT METHOD TO POINTS
		Sys.getCustomer(1).setPayment("points");
		Sys.getCustomer(1).setPoints(11);
		
		//COMPLETE ORDER
		int res = Sys.getCustomer(1).completeOrder(Sys);
		
		int unexp = -1;
		Assert.assertNotEquals(unexp, res);	
	}
	@Test
	public void REQ_6_2() {
		//ADD MOVIES TO CART
		S_MS = new Screen_MovieSelect(Sys, Sys.getCustomer(1), Sys.getMovieDB());
		int[] sels = {};
		S_MS.movieList.setSelectedIndices(sels);
		S_MS.okButtonPressed(Sys, Sys.getCustomer(1), Sys.getMovieDB());
				
		//SET PAYMENT METHOD TO POINTS
		Sys.getCustomer(1).setPayment("points");
		Sys.getCustomer(1).setPoints(1);
		
		//COMPLETE ORDER
		int res = Sys.getCustomer(1).completeOrder(Sys);
		
		int exp = -2;
		Assert.assertEquals(exp, res);	
	}

	@Test
	public void REQ_7_1() {
		//ADD MOVIES TO CART
		S_MS = new Screen_MovieSelect(Sys, Sys.getCustomer(1), Sys.getMovieDB());
		int[] sels = {0,1};
		S_MS.movieList.setSelectedIndices(sels);
		S_MS.okButtonPressed(Sys, Sys.getCustomer(1), Sys.getMovieDB());
				
		//SET PAYMENT METHOD TO CARD
		Sys.getCustomer(1).setPayment("1098 1234 5671 6923; 04/25; 139");
		
		int exp = Sys.getCustomer(1).getPoints() + 1;
		
		//COMPLETE ORDER
		Sys.getCustomer(1).completeOrder(Sys);
		
		int res = Sys.getCustomer(1).getPoints(); 
		Assert.assertEquals(exp, res);
	}
	@Test
	public void REQ_7_2() {
		//ADD MOVIES TO CART
		S_MS = new Screen_MovieSelect(Sys, Sys.getCustomer(1), Sys.getMovieDB());
		int[] sels = {0,1};
		S_MS.movieList.setSelectedIndices(sels);
		S_MS.okButtonPressed(Sys, Sys.getCustomer(1), Sys.getMovieDB());
						
		//SET PAYMENT METHOD TO POINTS
		Sys.getCustomer(1).setPayment("points");
		Sys.getCustomer(1).setPoints(1);
					
		//COMPLETE ORDER (SHOULD FAIL AND RETURN -1)
		int res = Sys.getCustomer(1).completeOrder(Sys);
				
		int exp = -1;		
		Assert.assertEquals(exp, res);
	}

	@Test
	public void REQ_8_1() {
		//ADD MOVIES TO CART
		S_MS = new Screen_MovieSelect(Sys, Sys.getCustomer(1), Sys.getMovieDB());
		int[] sels = {0,1};
		S_MS.movieList.setSelectedIndices(sels);
		S_MS.okButtonPressed(Sys, Sys.getCustomer(1), Sys.getMovieDB());
				
		Sys.getCustomer(1).setAddress("NotOntario");
		S_VC = new Screen_ViewCart(Sys, Sys.getCustomer(1), Sys.getCustomer(1).getOrder());
		
		double res = S_VC.latefee;

		double exp = 9.99;		
		
		Assert.assertTrue((exp == res));
	}
	@Test
	public void REQ_8_2() {
		//ADD MOVIES TO CART
		S_MS = new Screen_MovieSelect(Sys, Sys.getCustomer(1), Sys.getMovieDB());
		int[] sels = {0,1};
		S_MS.movieList.setSelectedIndices(sels);
		S_MS.okButtonPressed(Sys, Sys.getCustomer(1), Sys.getMovieDB());
				
		Sys.getCustomer(1).setAddress("Ontario");
		S_VC = new Screen_ViewCart(Sys, Sys.getCustomer(1), Sys.getCustomer(1).getOrder());
		
		double res = S_VC.latefee;
		double exp = 0.00;		
		
		Assert.assertTrue((exp == res));
	}

	@Test
	public void REQ_9_1() {
		//ADD MOVIES TO CART
		S_MS = new Screen_MovieSelect(Sys, Sys.getCustomer(1), Sys.getCustomer(1).getWarehouse().getMovies());
		int[] sels = {0,1};
		S_MS.movieList.setSelectedIndices(sels);
		int exp = Sys.getCustomer(1).getWarehouse().getMovies().size() - sels.length;
		S_MS.okButtonPressed(Sys, Sys.getCustomer(1), Sys.getMovieDB());
		
		//STOCK OF MOVIES SHOULD BE REDUCED BY sels.length
		int res = Sys.getCustomer(1).getWarehouse().getMovies().size();
		//int exp = ;
		
		Assert.assertEquals(exp, res);
	}
	@Test
	public void REQ_9_2() {
		//ADD MOVIES TO CART
		S_MS = new Screen_MovieSelect(Sys, Sys.getCustomer(1), Sys.getCustomer(1).getWarehouse().getMovies());
		int[] sels = {0,1};
		S_MS.movieList.setSelectedIndices(sels);
		int exp = Sys.getCustomer(1).getWarehouse().getMovies().size();
		S_MS.okButtonPressed(Sys, Sys.getCustomer(1), Sys.getCustomer(1).getWarehouse().getMovies());
								
		//SET PAYMENT METHOD TO CARD
		Sys.getCustomer(1).setPayment("1098 1234 5671 6923; 04/25; 139");
							
		//COMPLETE ORDER
		int ID = Sys.getCustomer(1).completeOrder(Sys);
		
		//CANCEL ORDER
		Sys.getCustomer(1).cancelOrder(Sys, ID);
						
		int res = Sys.getCustomer(1).getWarehouse().getMovies().size();
		Assert.assertEquals(exp, res);
	}

	@Test
	public void REQ_10_1() {
		//ACCESS OPERATOR MENU
		S_OM = new Screen_OperatorMenu(Sys, Sys.getCustomer(0));
		S_OM.movieSearchButtonPressed(Sys, Sys.getCustomer(0));
		
		//ADD MOVIES TO CART
		S_MS = new Screen_MovieSelect(Sys, Sys.getCustomer(0), Sys.getCustomer(0).getWarehouse().getMovies());
		int[] sels = {0,1};
		S_MS.movieList.setSelectedIndices(sels);
		S_MS.okButtonPressed(Sys, Sys.getCustomer(0), Sys.getCustomer(0).getWarehouse().getMovies());
										
		//SET PAYMENT METHOD TO CARD
		Sys.getCustomer(0).setPayment("1098 1234 5671 6923; 04/25; 139");
								
		//COMPLETE ORDER
		S_OM.cartButtonPressed(Sys, Sys.getCustomer(0));
		int res = Sys.getCustomer(0).completeOrder(Sys);
						
		int exp = 1;
		Assert.assertEquals(exp, res);
	}
	@Test
	public void REQ_10_2() {
		//ACCESS OPERATOR MENU
		S_OM = new Screen_OperatorMenu(Sys, Sys.getCustomer(0));
		S_OM.movieSearchButtonPressed(Sys, Sys.getCustomer(0));
				
		//ADD MOVIES TO CART
		S_MS = new Screen_MovieSelect(Sys, Sys.getCustomer(0), Sys.getCustomer(0).getWarehouse().getMovies());
		int[] sels = {};
		S_MS.movieList.setSelectedIndices(sels);
		S_MS.okButtonPressed(Sys, Sys.getCustomer(0), Sys.getCustomer(0).getWarehouse().getMovies());
												
		//SET PAYMENT METHOD TO CARD
		Sys.getCustomer(0).setPayment("1098 1234 5671 6923; 04/25; 139");
										
		//COMPLETE ORDER (SHOULD RETURN -2 BECAUSE NO ITEMS IN CART)
		S_OM.cartButtonPressed(Sys, Sys.getCustomer(0));
		int res = Sys.getCustomer(0).completeOrder(Sys);
								
		int exp = -2;
		Assert.assertEquals(exp, res);
	}

	@Test
	public void REQ_11_1() {
		//ACCESS OPERATOR MENU
		S_OM = new Screen_OperatorMenu(Sys, Sys.getCustomer(0));
		
		//ADD MOVIES TO CART
		S_MS = new Screen_MovieSelect(Sys, Sys.getCustomer(1), Sys.getMovieDB());
		int[] sels = {0,1};
		S_MS.movieList.setSelectedIndices(sels);
		S_MS.okButtonPressed(Sys, Sys.getCustomer(1), Sys.getMovieDB());
						
		//SET PAYMENT METHOD TO CARD
		Sys.getCustomer(1).setPayment("1098 1234 5671 6923; 04/25; 139");
								
		//COMPLETE ORDER
		int ID = Sys.getCustomer(1).completeOrder(Sys);
		
		//SEARCH FOR ID
		S_OM.orderIdInput.setText("" + ID);
		String res = S_OM.orderStatusButtonPressed(Sys);
		String exp = "Ordered";
		
		Assert.assertEquals(exp, res);
	}
	@Test
	public void REQ_11_2() {
		//ACCESS OPERATOR MENU
		S_OM = new Screen_OperatorMenu(Sys, Sys.getCustomer(0));
			
		
		//SEARCH FOR ID
		S_OM.orderIdInput.setText("1");
		String res = S_OM.orderStatusButtonPressed(Sys);
		String exp = "N/A";
				
		Assert.assertEquals(exp, res);
	}

	@Test
	public void REQ_12_1() {/*SKIPPED*/}
	@Test
	public void REQ_12_2() {/*SKIPPED*/}

	@Test
	public void REQ_13_1() {
		//ADD MOVIES TO CART
		S_MS = new Screen_MovieSelect(Sys, Sys.getCustomer(1), Sys.getCustomer(1).getWarehouse().getMovies());
		int[] sels = {0,1};
		S_MS.movieList.setSelectedIndices(sels);
		S_MS.okButtonPressed(Sys, Sys.getCustomer(1), Sys.getCustomer(1).getWarehouse().getMovies());
								
		//SET PAYMENT METHOD TO CARD
		Sys.getCustomer(1).setPayment("1098 1234 5671 6923; 04/25; 139");
							
		//COMPLETE ORDER
		int ID = Sys.getCustomer(1).completeOrder(Sys);
		
		//CANCEL ORDER
		int res = Sys.getCustomer(1).cancelOrder(Sys, ID);
		
		int exp = 1;
		Assert.assertEquals(exp, res);
	}
	@Test
	public void REQ_13_2() {
		//ADD MOVIES TO CART
		S_MS = new Screen_MovieSelect(Sys, Sys.getCustomer(1), Sys.getCustomer(1).getWarehouse().getMovies());
		int[] sels = {0,1};
		S_MS.movieList.setSelectedIndices(sels);
		S_MS.okButtonPressed(Sys, Sys.getCustomer(1), Sys.getCustomer(1).getWarehouse().getMovies());
								
		//SET PAYMENT METHOD TO CARD
		Sys.getCustomer(1).setPayment("1098 1234 5671 6923; 04/25; 139");
							
		//COMPLETE ORDER
		int ID = Sys.getCustomer(1).completeOrder(Sys);
		
		//DELIVER ORDER
		for(int i = 0; i<Sys.getOrders().size();i++) {
			if(Sys.getOrders().get(i).getID() == ID) {
				Sys.getOrders().get(i).setStatus("delivered");
				break;
			}
		}
		
		//CANCEL ORDER
		int res = Sys.getCustomer(1).cancelOrder(Sys, ID);
		
		int exp = -1;
		Assert.assertEquals(exp, res);
	}

	@Test
	public void REQ_14_1() {
		S_UI = new Screen_UserInfo(Sys, Sys.getCustomer(1));
		S_UI.addressEntry.setText("Ontario");
		S_UI.paymentEntry.setText("Points");
		S_UI.userEntry.setText("newt1");
		S_UI.emailEntry.setText("newt1@gmail.com");
		S_UI.passwordEntry.setText("newt1newt1");
		int res = S_UI.saveButtonPressed(Sys, Sys.getCustomer(1));
		int exp = 1;
		Assert.assertEquals(exp, res);
	}
	@Test
	public void REQ_14_2() {
		S_UI = new Screen_UserInfo(Sys, Sys.getCustomer(1));
		S_UI.addressEntry.setText("On,tario");
		S_UI.paymentEntry.setText("Points");
		S_UI.userEntry.setText("newt1");
		S_UI.emailEntry.setText("newt1@gmail.com");
		S_UI.passwordEntry.setText("newt1newt1");
		int res = S_UI.saveButtonPressed(Sys, Sys.getCustomer(1));
		int exp = -1;
		Assert.assertEquals(exp, res);
	}

	@Test
	public void REQ_15_1() {
		/*TESTING IS INAPPLICABLE*/
		//IF IMPLEMENTATION NEEDS TO BE SEEN, 
		//THIS COMMAND CAN BE RUN
		
		//S_VC = new Screen_ViewCart(Sys, Sys.getCustomer(1), Sys.getCustomer(1).getOrder());
	}
	@Test
	public void REQ_15_2() {
		/*TESTING IS INAPPLICABLE*/
		//IF IMPLEMENTATION NEEDS TO BE SEEN, 
		//THIS COMMAND CAN BE RUN
		
		//S_VC = new Screen_ViewCart(Sys, Sys.getCustomer(1), Sys.getCustomer(1).getOrder());
	}

	@Test
	public void REQ_16_1() {
		//ACCESS ADMIN ADD A MOVIE MENU
		S_AAM = new Screen_AdminAddMovie(Sys, Sys.getAdmins().get(0));
		
		S_AAM.aEntry.setText("new actor");
		S_AAM.dirEntry.setText("new director");
		S_AAM.dEntry.setText("new description of movie");
		S_AAM.gEntry.setText("new genre");
		S_AAM.pEntry.setText("4.00");
		S_AAM.tEntry.setText("New Movie Title");
		
		//ADD THE MOVIE TO THE DATABASE
		S_AAM.addButtonPressed(Sys, "movie-test.csv");
		
		Movie res = Sys.getMovieDB().get(Sys.getMovieDB().size()-1);
		Movie exp = new Movie("New Movie Title", "new description of movie", "new actor", "new director", "new genre", 4.00);
		
		Assert.assertEquals(exp.getTitle(), res.getTitle());
		Assert.assertEquals(exp.getGenre(), res.getGenre());
		Assert.assertEquals(exp.getActors(), res.getActors());
		Assert.assertEquals(exp.getDirector(), res.getDirector());
		Assert.assertEquals(exp.getDesc(), res.getDesc());
		Assert.assertTrue(exp.getPrice() == res.getPrice());
	}
	@Test
	public void REQ_16_2() {
		//ACCESS ADMIN REMOVE A MOVIE MENU
		S_ARM = new Screen_AdminRemoveMovie(Sys, Sys.getAdmins().get(0));
				
		//REMOVE THE MOVIE FROM THE DATABASE
		S_ARM.movieList.setSelectedIndex(S_ARM.movieList.getModel().getSize() - 1);
		S_ARM.removeButtonPressed(Sys, "movie-test.csv");
		
		Movie res = Sys.getMovieDB().get(Sys.getMovieDB().size()-1);
		Movie exp = new Movie("kill me 5: Im Deceased", 
				"the finale of the critically acclaimed kill me series", 
				"chris chrissen; joe jordan", "Director", "Thriller", 5.00);
		Assert.assertEquals(exp.getTitle(), res.getTitle());
		Assert.assertEquals(exp.getGenre(), res.getGenre());
		Assert.assertEquals(exp.getActors(), res.getActors());
		Assert.assertEquals(exp.getDirector(), res.getDirector());
		Assert.assertEquals(exp.getDesc(), res.getDesc());
		Assert.assertTrue(exp.getPrice() == res.getPrice());
	}

	@Test
	public void REQ_17_1() {
		//ACCESS ADMIN MODIFY A MOVIE MENU
		S_AMM = new Screen_AdminModifyMovie(Sys, Sys.getAdmins().get(0));
		
		S_AMM.movieList.setSelectedIndex(S_AMM.movieList.getModel().getSize()-1);
		
		//MODIFY THE MOVIE
		S_AMM.Genre.setText("Mystery");
		S_AMM.saveButtonPressed(Sys, "movie-test.csv");
		
		Movie res = Sys.getMovieDB().get(Sys.getMovieDB().size()-1);
		Movie exp = new Movie("kill me 5: Im Deceased", 
				"the finale of the critically acclaimed kill me series", 
				"chris chrissen; joe jordan", "Director", "Mystery", 5.00);
			
		Assert.assertEquals(exp.getTitle(), res.getTitle());
		Assert.assertEquals(exp.getGenre(), res.getGenre());
		Assert.assertEquals(exp.getActors(), res.getActors());
		Assert.assertEquals(exp.getDirector(), res.getDirector());
		Assert.assertEquals(exp.getDesc(), res.getDesc());
		Assert.assertTrue(exp.getPrice() == res.getPrice());
		
	}
	@Test
	public void REQ_17_2() {
		//ACCESS ADMIN MODIFY A MOVIE MENU
		S_AMM = new Screen_AdminModifyMovie(Sys, Sys.getAdmins().get(0));
		
		S_AMM.movieList.setSelectedIndex(S_AMM.movieList.getModel().getSize()-1);
		
		//MODIFY THE MOVIE (MOVIE WILL BE UNCHANGED)
		S_AMM.Genre.setText("Myste,ry");
		S_AMM.saveButtonPressed(Sys, "movie-test.csv");
		
		Movie res = Sys.getMovieDB().get(Sys.getMovieDB().size()-1);
		Movie exp = new Movie("kill me 5: Im Deceased", 
				"the finale of the critically acclaimed kill me series", 
				"chris chrissen; joe jordan", "Director", "Thriller", 5.00);
			
		Assert.assertEquals(exp.getTitle(), res.getTitle());
		Assert.assertEquals(exp.getGenre(), res.getGenre());
		Assert.assertEquals(exp.getActors(), res.getActors());
		Assert.assertEquals(exp.getDirector(), res.getDirector());
		Assert.assertEquals(exp.getDesc(), res.getDesc());
		Assert.assertTrue(exp.getPrice() == res.getPrice());
		
	}

	@Test
	public void REQ_18_1() {
		//ACCESS ADMIN MODIFY A USER MENU
		S_AMU = new Screen_AdminModifyUsers(Sys, Sys.getAdmins().get(0));
		
		//MODIFY THE USER
		S_AMU.userList.setSelectedIndex(S_AMU.userList.getModel().getSize()-1);
		S_AMU.Email.setText("newFakeEmail@gmail.com");
		
		S_AMU.saveButtonPressed(Sys, "user-test.csv");
		
		User res = Sys.getCustomer(Sys.getCustomerDB().size()-1);
		User exp = new User("Hau", 11, "newFakeEmail@gmail.com", "DreamyPeaches4");
		
		Assert.assertEquals(exp.getName(), res.getName());
		Assert.assertEquals(exp.getId(), res.getId());
		Assert.assertEquals(exp.getEmail(), res.getEmail());
		Assert.assertEquals(exp.getPassword(), res.getPassword());
	}
	@Test
	public void REQ_18_2() {
		//ACCESS ADMIN MODIFY A USER MENU
		S_AMU = new Screen_AdminModifyUsers(Sys, Sys.getAdmins().get(0));
		
		//MODIFY THE USER (USER SHOULD NOT CHANGE)
		S_AMU.userList.setSelectedIndex(S_AMU.userList.getModel().getSize()-1);
		S_AMU.Email.setText("newFakeEm,ail@gmail.com");
		
		S_AMU.saveButtonPressed(Sys, "user-test.csv");
		
		User res = Sys.getCustomer(Sys.getCustomerDB().size()-1);
		User exp = new User("Hau", 11, "hh321@gmail.com", "DreamyPeaches4");
		
		Assert.assertEquals(exp.getName(), res.getName());
		Assert.assertEquals(exp.getId(), res.getId());
		Assert.assertEquals(exp.getEmail(), res.getEmail());
		Assert.assertEquals(exp.getPassword(), res.getPassword());
	}
	
	@Test
	public void REQ_18_3() {		
		//ACCESS ADMIN MODIFY A USER MENU
		S_AMU = new Screen_AdminModifyUsers(Sys, Sys.getAdmins().get(0));
		
		//ADD MOVIES TO CART
		S_MS = new Screen_MovieSelect(Sys, Sys.getCustomer(S_AMU.userList.getModel().getSize()-1), 
				Sys.getCustomer(S_AMU.userList.getModel().getSize()-1).getWarehouse().getMovies());
		int[] sels = {0,1};
		S_MS.movieList.setSelectedIndices(sels);
		S_MS.okButtonPressed(Sys, Sys.getCustomer(S_AMU.userList.getModel().getSize()-1), 
				Sys.getCustomer(S_AMU.userList.getModel().getSize()-1).getWarehouse().getMovies());
										
		//SET PAYMENT METHOD TO CARD
		Sys.getCustomer(S_AMU.userList.getModel().getSize()-1).setPayment("1098 1234 5671 6923; 04/25; 139");
					
		//COMPLETE ORDER
		Sys.getCustomer(S_AMU.userList.getModel().getSize()-1).completeOrder(Sys);
		
		//MODIFY THE USER (USER SHOULD NOT CHANGE)
		S_AMU.userList.setSelectedIndex(S_AMU.userList.getModel().getSize()-1);
		S_AMU.Email.setText("newFakeEmail@gmail.com");
		
		S_AMU.saveButtonPressed(Sys, "user-test.csv");
			
		User res = Sys.getCustomer(Sys.getCustomerDB().size()-1);
		User exp = new User("Hau", 11, "newFakeEmail@gmail.com", "DreamyPeaches4");
		
		Assert.assertEquals(exp.getName(), res.getName());
		Assert.assertEquals(exp.getId(), res.getId());
		Assert.assertEquals(exp.getEmail(), res.getEmail());
		Assert.assertEquals(exp.getPassword(), res.getPassword());
	}
	@Test
	public void REQ_18_4() {
		//ACCESS ADMIN MODIFY A USER MENU

		S_AMU = new Screen_AdminModifyUsers(Sys, Sys.getAdmins().get(0));
				
		//ADD MOVIES TO CART
		S_MS = new Screen_MovieSelect(Sys, Sys.getCustomer(S_AMU.userList.getModel().getSize()-1), 
		Sys.getCustomer(S_AMU.userList.getModel().getSize()-1).getWarehouse().getMovies());
		int[] sels = {0,1};
		S_MS.movieList.setSelectedIndices(sels);
		S_MS.okButtonPressed(Sys, Sys.getCustomer(S_AMU.userList.getModel().getSize()-1), 
				Sys.getCustomer(S_AMU.userList.getModel().getSize()-1).getWarehouse().getMovies());
										
		//SET PAYMENT METHOD TO CARD
		Sys.getCustomer(S_AMU.userList.getModel().getSize()-1).setPayment("1098 1234 5671 6923; 04/25; 139");
					
		//COMPLETE ORDER
		Sys.getCustomer(S_AMU.userList.getModel().getSize()-1).completeOrder(Sys);
		
		//MODIFY THE USER
		S_AMU.userList.setSelectedIndex(S_AMU.userList.getModel().getSize()-1);
		S_AMU.OrderID.setText("delivered");
		
		S_AMU.saveButtonPressed(Sys, "user-test.csv");
		
		User res = Sys.getCustomer(Sys.getCustomerDB().size()-1);
		User exp = new User("Hau", 11, "hh321@gmail.com", "DreamyPeaches4");
		
		Assert.assertEquals(exp.getName(), res.getName());
		Assert.assertEquals(exp.getId(), res.getId());
		Assert.assertEquals(exp.getEmail(), res.getEmail());
		Assert.assertEquals(exp.getPassword(), res.getPassword());
	}
	@Test
	public void REQ_18_5() {
		//ACCESS ADMIN MODIFY A USER MENU

		S_AMU = new Screen_AdminModifyUsers(Sys, Sys.getAdmins().get(0));
				
		//ADD MOVIES TO CART
		S_MS = new Screen_MovieSelect(Sys, Sys.getCustomer(S_AMU.userList.getModel().getSize()-1), 
		Sys.getCustomer(S_AMU.userList.getModel().getSize()-1).getWarehouse().getMovies());
		int[] sels = {0,1};
		S_MS.movieList.setSelectedIndices(sels);
		S_MS.okButtonPressed(Sys, Sys.getCustomer(S_AMU.userList.getModel().getSize()-1), 
				Sys.getCustomer(S_AMU.userList.getModel().getSize()-1).getWarehouse().getMovies());
										
		//SET PAYMENT METHOD TO CARD
		Sys.getCustomer(S_AMU.userList.getModel().getSize()-1).setPayment("1098 1234 5671 6923; 04/25; 139");
					
		//COMPLETE ORDER
		Sys.getCustomer(S_AMU.userList.getModel().getSize()-1).completeOrder(Sys);
		
		//MODIFY THE USER
		S_AMU.userList.setSelectedIndex(S_AMU.userList.getModel().getSize()-1);
		S_AMU.OrderID.setText("cancelled");
		
		S_AMU.saveButtonPressed(Sys, "user-test.csv");
		
		User res = Sys.getCustomer(Sys.getCustomerDB().size()-1);
		User exp = new User("Hau", 11, "hh321@gmail.com", "DreamyPeaches4");
		
		Assert.assertEquals(exp.getName(), res.getName());
		Assert.assertEquals(exp.getId(), res.getId());
		Assert.assertEquals(exp.getEmail(), res.getEmail());
		Assert.assertEquals(exp.getPassword(), res.getPassword());
	}

	@Test
	public void REQ_19_1() {
		//ACCESS ADMIN MODIFY A USER MENU
		S_AMU = new Screen_AdminModifyUsers(Sys, Sys.getAdmins().get(0));
		
		//DELETE USER
		S_AMU.userList.setSelectedIndex(S_AMU.userList.getModel().getSize()-1);
		S_AMU.deleteButtonPressed(Sys, "user-test.csv");
		
		User res = Sys.getCustomer(Sys.getCustomerDB().size()-1);
		User exp = new User("Alice", 10, "al@hotmail.com", "PrettyPumpkins898");
		
		Assert.assertEquals(exp.getName(), res.getName());
		Assert.assertEquals(exp.getId(), res.getId());
		Assert.assertEquals(exp.getEmail(), res.getEmail());
		Assert.assertEquals(exp.getPassword(), res.getPassword());
	}
	@Test
	public void REQ_19_2() {
		//ACCESS ADMIN MODIFY A USER MENU
		S_AMU = new Screen_AdminModifyUsers(Sys, Sys.getAdmins().get(0));
		
		//DELETE USER
		S_AMU.userList.setSelectedIndex(0);
		S_AMU.deleteButtonPressed(Sys, "user-test.csv");
		
		User res = Sys.getCustomer(0);
		User exp = new User("Operator", 0, "op@vidco.ca", "Operator123");
		
		Assert.assertEquals(exp.getName(), res.getName());
		Assert.assertEquals(exp.getId(), res.getId());
		Assert.assertEquals(exp.getEmail(), res.getEmail());
		Assert.assertEquals(exp.getPassword(), res.getPassword());
	}

	@Test
	public void REQ_20_1() {
		//LOAD SYSTEM MODIFY ADMIN SCREEN
		S_SMA = new Screen_SystemModifyAdmin(Sys);
		S_SMA.employeeList.getModel();
		
		//SELECT ADMIN
		S_SMA.employeeList.setSelectedIndex(1);
		
		//MODIFY ENTRIES
		S_SMA.Email.setText("alice@vidco.ca");
		S_SMA.Password.setText("AliceInWonderland1999");
		
		S_SMA.registerButtonPressed(Sys, "employee-test.csv");
		
		SysAdmin res = Sys.getAdmins().get(1);
		SysAdmin exp = new SysAdmin("Alice", "alice@vidco.ca", "AliceInWonderland1999");
		
		Assert.assertEquals(exp.getName(), res.getName());
		Assert.assertEquals(exp.getEmail(), res.getEmail());
		Assert.assertEquals(exp.getPassword(), res.getPassword());
	}
	@Test
	public void REQ_20_2() {
		//LOAD SYSTEM MODIFY ADMIN SCREEN
		S_SMA = new Screen_SystemModifyAdmin(Sys);
		
		//SELECT ADMIN
		S_SMA.employeeList.setSelectedIndex(1);
		
		//MODIFY ENTRIES
		S_SMA.Email.setText("alice@vidco.ca");
		
		S_SMA.registerButtonPressed(Sys, "employee-test.csv");
		
		SysAdmin res = Sys.getAdmins().get(1);
		SysAdmin exp = new SysAdmin("Alice", "N/A", "N/A");
		
		Assert.assertEquals(exp.getName(), res.getName());
		Assert.assertEquals(exp.getEmail(), res.getEmail());
		Assert.assertEquals(exp.getPassword(), res.getPassword());
	}

	@Test
	public void REQ_21_1() {
		//LOAD SYSTEM MODIFY ADMIN SCREEN
		S_SMA = new Screen_SystemModifyAdmin(Sys);
		
		//SELECT ADMIN
		S_SMA.employeeList.setSelectedIndex(1);
		
		//DELETE ADMIN
		S_SMA.deleteButtonPressed(Sys, "employee-test.csv");
		
		SysAdmin res = Sys.getAdmins().get(1);
		SysAdmin exp = new SysAdmin("Bob", "bob@vidco.ca", "BobIsKing");
		
		Assert.assertEquals(exp.getName(), res.getName());
		Assert.assertEquals(exp.getEmail(), res.getEmail());
		Assert.assertEquals(exp.getPassword(), res.getPassword());
		
	}
	@Test
	public void REQ_21_2() {
		//LOAD SYSTEM MODIFY ADMIN SCREEN
		S_SMA = new Screen_SystemModifyAdmin(Sys);
		
		//SELECT ADMIN
		S_SMA.employeeList.setSelectedIndex(0);
		
		//DELETE ADMIN
		S_SMA.deleteButtonPressed(Sys, "employee-test.csv");
		
		SysAdmin res = Sys.getAdmins().get(0);
		SysAdmin exp = new SysAdmin("Alice", "N/A", "N/A");
		
		Assert.assertEquals(exp.getName(), res.getName());
		Assert.assertEquals(exp.getEmail(), res.getEmail());
		Assert.assertEquals(exp.getPassword(), res.getPassword());
	}

	@Test
	public void REQ_22_1() {
		//ADD MOVIES TO CART
		S_MS = new Screen_MovieSelect(Sys, Sys.getCustomer(1), Sys.getCustomer(1).getWarehouse().getMovies());
		int[] sels = {0,1};
		S_MS.movieList.setSelectedIndices(sels);
		S_MS.okButtonPressed(Sys, Sys.getCustomer(1), Sys.getCustomer(1).getWarehouse().getMovies());
				
		//SET PAYMENT METHOD TO CARD
		Sys.getCustomer(1).setPayment("1098 1234 5671 6923; 04/25; 139");
				
		//COMPLETE ORDER
		int ID = Sys.getCustomer(1).completeOrder(Sys);
		int ind = -1;
		for(int i = 0; i<Sys.getOrders().size();i++) {
			if(Sys.getOrders().get(i).getID() == ID) {
				ind = i;
				break;
			}
		}	
		
		//MODIFY ORDER STATUS
		S_SOR = new Screen_SystemOrderReview(Sys, Sys.getOrders().get(ind));
		S_SOR.entry.setText("delivered");
		S_SOR.confirmButtonPressed(Sys, Sys.getOrders().get(ind));
		
		ID = Sys.getCustomer(1).completeOrder(Sys);
		int res = -1;
		for(int i = 0; i<Sys.getOrders().size();i++) {
			if(Sys.getOrders().get(i).getID() == ID) {
				res = i;
				break;
			}
		}	
		
		int exp = -1;
		Assert.assertEquals(exp, res);
		
	}
	@Test
	public void REQ_22_2() {
		//ADD MOVIES TO CART
		S_MS = new Screen_MovieSelect(Sys, Sys.getCustomer(1), Sys.getCustomer(1).getWarehouse().getMovies());
		int[] sels = {0,1};
		S_MS.movieList.setSelectedIndices(sels);
		S_MS.okButtonPressed(Sys, Sys.getCustomer(1), Sys.getCustomer(1).getWarehouse().getMovies());
				
		//SET PAYMENT METHOD TO CARD
		Sys.getCustomer(1).setPayment("1098 1234 5671 6923; 04/25; 139");
				
		//COMPLETE ORDER
		int ID = Sys.getCustomer(1).completeOrder(Sys);
		int ind = -1;
		for(int i = 0; i<Sys.getOrders().size();i++) {
			if(Sys.getOrders().get(i).getID() == ID) {
				ind = i;
				break;
			}
		}	
		
		//MODIFY ORDER STATUS
		S_SOR = new Screen_SystemOrderReview(Sys, Sys.getOrders().get(ind));
		S_SOR.entry.setText("Cancelled");
		S_SOR.confirmButtonPressed(Sys, Sys.getOrders().get(ind));
		
		String res = Sys.getOrders().get(ind).getStatus();
		String exp = "Cancelled";
		Assert.assertEquals(exp, res);
	}

	@Test
	public void REQ_23_1() {/*REQUIREMENT DOES NOT EXIST*/}
	@Test
	public void REQ_23_2() {/*REQUIREMENT DOES NOT EXIST*/}

	@Test
	public void REQ_24_1() {/*REQUIREMENT NOT IMPLEMENTED*/}
	@Test
	public void REQ_24_2() {/*REQUIREMENT NOT IMPLEMENTED*/}

	@Test
	public void REQ_25_1() {
		//ADD MOVIES TO CART
		S_MS = new Screen_MovieSelect(Sys, Sys.getCustomer(1), Sys.getCustomer(1).getWarehouse().getMovies());
		int[] sels = {0,1};
		S_MS.movieList.setSelectedIndices(sels);
		ArrayList<Movie> exp = Sys.getCustomer(1).getOrder().getCart();
		S_MS.okButtonPressed(Sys, Sys.getCustomer(1), Sys.getCustomer(1).getWarehouse().getMovies());
						
		//SET PAYMENT METHOD TO CARD
		Sys.getCustomer(1).setPayment("1098 1234 5671 6923; 04/25; 139");
			
		//COMPLETE ORDER
		Sys.getCustomer(1).completeOrder(Sys);
		
		ArrayList<Movie> res = Sys.getCustomer(1).getWarehouse().getPendingShipment();
		Assert.assertTrue(res.containsAll(exp));
		
	}
	@Test
	public void REQ_25_2() {
		//ADD MOVIES TO CART
		S_MS = new Screen_MovieSelect(Sys, Sys.getCustomer(1), Sys.getCustomer(1).getWarehouse().getMovies());
		int[] sels = {0,1};
		S_MS.movieList.setSelectedIndices(sels);
		ArrayList<Movie> exp = Sys.getCustomer(1).getOrder().getCart();
		S_MS.okButtonPressed(Sys, Sys.getCustomer(1), Sys.getCustomer(1).getWarehouse().getMovies());
						
		//SET PAYMENT METHOD TO CARD
		Sys.getCustomer(1).setPayment("1098 1234 5671 6923; 04/25; 139");
			
		//COMPLETE ORDER
		Sys.getCustomer(1).completeOrder(Sys);
		
		//ADD MOVIES TO NEW CART
		int[] sels2 = {1};
		S_MS.movieList.setSelectedIndices(sels2);
		S_MS.okButtonPressed(Sys, Sys.getCustomer(1), Sys.getCustomer(1).getWarehouse().getMovies());
			
		//COMPLETE ORDER
		Sys.getCustomer(1).completeOrder(Sys);
		
		ArrayList<Movie> res = Sys.getCustomer(1).getWarehouse().getPendingShipment();
		Assert.assertTrue(res.containsAll(exp));
	}

	@Test
	public void REQ_26_1() {/*REQUIREMENT NOT IMPLEMENTED*/}
	@Test
	public void REQ_26_2() {/*REQUIREMENT NOT IMPLEMENTED*/}
}
