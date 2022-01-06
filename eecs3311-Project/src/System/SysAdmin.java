package System;

public class SysAdmin {
	private String name;
	private String email;
	private String password;
	
	public SysAdmin(String Name, String Email, String Pass) {
		this.name = Name;
		this.email = Email;
		this.password = Pass;
	}
	public SysAdmin() {
		this.name = "N/A";
		this.email = "N/A";
		this.password = "N/A";
	}
	public String getName() {return this.name;}
	public String getEmail() {return this.email;}
	public String getPassword() {return this.password;}
	
	public void setName(String Name) {this.name = Name;}
	public void setEmail(String Email) {this.email = Email;}
	public void setPassword(String Pass) {this.password = Pass;}
}
