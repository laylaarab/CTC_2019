package ca.ctc2019.backend;

public class Account {

	private final int id;
	private final String type;
	private final String username;
	private final String password;


	public Account (int id, String type, String username, String password) {
		this.id = id;
		this.type = type;
		this.username = username;
		this.password = password;
	}

	public int getId(){
		return id;
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
