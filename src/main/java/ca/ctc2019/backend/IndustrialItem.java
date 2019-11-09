package ca.ctc2019.backend;

public class IndustrialItem {

	private String name;
	private String desc;
	private double price;
	private Company company;
	private int quantity;
	private Type type;
	private Status status;

	public enum Type {
		WOOD("Wood"), METAL("Metal"), PAPER(
				"Paper"), OTHER("Other");

		private String name;

		Type(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	public enum Status {
		SOLD("Sold"), CONDITIONALSALE("Cond-Sale"), AVAILABLE("Available");

		private String name;

		Status(String name){
			this.name = name;
		}

		public String getName(){
			return name;
		}
	}

	public IndustrialItem(Type type, String name, String desc, Company company,
				double price, int quantity, Status status) {
		this.type = type;
		this.name = name;
		this.desc = desc;
		this.price = price;
		this.company = company;
		this.quantity = quantity;
		this.status = status;
	}

	public Type getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}

	public double getPrice() {
		return price;
	}

	public Company getCompany() {
		return company;
	}

	public int getQuantity() {
		return quantity;
	}

	public Status getStatus() {
		return status;
	}

}
