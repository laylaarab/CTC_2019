package ca.ctc2019.backend;

public class Company {

	private final String name;
	private final Address address;
	private final String email;
	private final Account account;

	public Company (String name, String street, String city, String province, String postalCode, String email, String type, String username, String password){
		this.name = name;
		this.address = new Address(street, city, province, postalCode);
		this.email = email;
		this.account = new Account (type, username, password);
	}

	public Company (String name, Address address, String email, Account account){
		this.name = name;
		this.address = address;
		this.email = email;
		this.account = account;
	}
}
