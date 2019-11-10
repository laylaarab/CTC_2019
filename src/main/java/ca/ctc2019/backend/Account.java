package ca.ctc2019.backend;

public class Account {

	private String type;
	private String username;
	private String password;


	public Account (String type, String username, String password) {
		this.type = type;
		this.username = username;
		this.password = password;
	}

	public String getUsername(){
		return username;
	}

	public String getPassword(){
		return password;
	}

	public String getType(){
		return type;
	}

}
