package ca.ctc2019.backend;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseController {
	private Connection dataCon;
	private Statement statement;
	private ResultSet resultSet;
	private static DatabaseController instance;

	private DatabaseController () {
		try {
			//dataCon = DriverManager.getConnection("jdbc:mysql://dump-sump.cotysnks4blq.us-west-2.rds.amazonaws.com:3306", "admin", "rootroot");
			dataCon = DriverManager.getConnection("jdbc:mysql://dump-sump.cotysnks4blq.us-west-2.rds.amazonaws.com:3306/sys?user=admin&password=rootroot");
		}catch (java.sql.SQLException e){
			System.err.println("Error connecting to the database");
			System.err.println(e.getMessage());
		}
	}

	public static DatabaseController getInstance()
	{
		if (instance == null)
			instance = new DatabaseController();
		return instance;
	}

	public synchronized ArrayList<IndustrialItem> itemListFromDataBase (){
		ArrayList<IndustrialItem> temp = new ArrayList<IndustrialItem>();
		try {
			statement = dataCon.createStatement();
			ResultSet rs =  statement.executeQuery("SELECT * FROM Item");
			while (rs.next()){
				Statement newSt = dataCon.createStatement();
				ResultSet cs =  newSt.executeQuery("SELECT * FROM Company WHERE company_ID =" + rs.getInt("company_ID"));
				Company tempComp = null;
				Address tempAddress = null;
				while (cs.next()){
					Statement newSt2 = dataCon.createStatement();
					ResultSet as =  newSt2.executeQuery("SELECT * FROM Address WHERE address_ID =" + cs.getInt("address_ID"));
					while (as.next()) {
						tempAddress = new Address(as.getString("streetno"), as.getString("city"), as.getString("state"), as.getString("postalcode"));
					}
					tempComp = new Company(cs.getString("name"), tempAddress, cs.getString("email"), null);
				}
				IndustrialItem tempItem = new IndustrialItem(IndustrialItem.Type.valueOf(rs.getString("type")), rs.getString("name"), rs.getString("description"),
						tempComp, rs.getDouble("price"), rs.getInt("quantity"), IndustrialItem.Status.valueOf(rs.getString("status")));
				temp.add(tempItem);
			}
		}catch (java.sql.SQLException e){
			System.err.println("Error while trying to get the list of items from the server");
			e.printStackTrace();
		}
		return temp;
	}

	public synchronized void addAccount (Account temp){
		try {
			String overrideQuerry = "SELECT * FROM account WHERE username =" + temp.getUsername();
			statement = dataCon.createStatement();
			resultSet = statement.executeQuery(overrideQuerry);
			if (resultSet.next()) {
				String updateQuerry = "UPDATE account SET type = ?, username = ?, password = ? WHERE username = ? ";
				PreparedStatement pStat = dataCon.prepareStatement(updateQuerry);
				pStat.setString(1, temp.getType());
				pStat.setString(2, temp.getUsername());
				pStat.setString(3, temp.getPassword());
				pStat.executeUpdate();
			} else {
				insertAccount(temp);
			}

		}catch (java.sql.SQLException e){
			System.err.println("Error adding items into the database");
			e.printStackTrace();
		}
	}

	public synchronized void insertAccount(Account temp){
		try {
			String insertQuery = "INSERT INTO Account (type, username, password) VALUES (?,?,?)";
			PreparedStatement pStat = dataCon.prepareStatement(insertQuery);
			pStat.setString(1, temp.getType());
			pStat.setString(2, temp.getUsername());
			pStat.setString(3, temp.getPassword());
			pStat.executeUpdate();
		}catch (java.sql.SQLException e){
			System.err.println("Error inserting an item to the table");
			e.printStackTrace();
		}
	}


	public synchronized int findCompanyId(Company temp){
		try {
			statement = dataCon.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM Company WHERE name =" + temp.getName());
			while (rs.next()) {
				return rs.getInt("company_ID");
			}
		}
		catch(java.sql.SQLException e){
			System.err.println("Error finding the Company ID");
			e.printStackTrace();
		}
		return -1;
	}

	public synchronized void insertItem(IndustrialItem temp){
		try {
			String insertQuery = "INSERT INTO Item (type, quantity, company_ID, description, price, name, status) VALUES (?,?,?,?,?,?,?)";
			PreparedStatement pStat = dataCon.prepareStatement(insertQuery);
			pStat.setString(1, temp.getType().getName());
			pStat.setInt(2, temp.getQuantity());
			pStat.setInt(3, findCompanyId(temp.getCompany()));
			pStat.setString(4, temp.getDesc());
			pStat.setDouble(5, temp.getPrice());
			pStat.setString(6, temp.getName());
			pStat.setString(7, temp.getStatus().getName());
			pStat.executeUpdate();
		}catch (java.sql.SQLException e){
			System.err.println("Error inserting an item to the table");
			e.printStackTrace();
		}
	}

	public synchronized void removeAccount (Account temp){
		try {
			String querry = "DELETE FROM account WHERE username = ?";
			PreparedStatement pStat = dataCon.prepareStatement(querry);
			pStat.setString(1, temp.getUsername());
			pStat.execute();
		}catch (java.sql.SQLException e){
			System.err.println("Error removing an item from the database, Already removed?");
			e.printStackTrace();
		}
	}

	protected Account authenticateAccount(String username, String password) {
		String query = "SELECT * FROM Account WHERE Account.username = ? AND Account.password = ?";
		try {
			PreparedStatement pStat = dataCon.prepareStatement(query);
			pStat.setString(1, username);
			pStat.setString(2, password);
			ResultSet result = pStat.executeQuery();
			result.beforeFirst();
			result.next();
			return new Account(result.getString("type"), result.getString("username"), result.getString("password"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}

