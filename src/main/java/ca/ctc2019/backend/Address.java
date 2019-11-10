package ca.ctc2019.backend;

public class Address {

	private String street;
	private String city;
	private String province;
	private String postalCode;

	public Address(String street, String city, String province, String postalCode) {
		this.street = street;
		this.city = city;
		this.province = province;
		this.postalCode = postalCode;
	}

	public String getStreet() {
		return street;
	}

	public String getCity() {
		return city;
	}

	public String getProvince() {
		return province;
	}

	public String getPostalCode() {
		return postalCode;
	}

}
