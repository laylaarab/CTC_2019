package ca.ctc2019.backend;

public class IndustrialItem {

	private String name;
	private String desc;
	private double price;
	private Company company;
	private int quantity;
	private Type type;
	private Status status;
	private String url;

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

	public IndustrialItem() {

	}

	public enum Status {
		SOLD("Sold"), CONDSALE("Conditional Sale"), AVAILABLE("Available");

		private String name;

		Status(String name){
			this.name = name;
		}

		public String getName(){
			return name;
		}
	}

	public IndustrialItem(Type type, String name, String desc, Company company,
				double price, int quantity, Status status, String url) {
		this.type = type;
		this.name = name;
		this.desc = desc;
		this.price = price;
		this.company = company;
		this.quantity = quantity;
		this.status = status;
		this.url = url;
	}

	public Type getType() {
		return type;
	}

	public String getTypeStr() {
		return type.toString();
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

	public String getUrl(){
		return url;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setType(String type) {
		this.type = Enum.valueOf(Type.class, type);
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setUrl(String url){
		this.url = url;
	}
}

