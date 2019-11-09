package ca.ctc2019.backend;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseController {
	private Connection dataCon;
	private Statement statement;
	private ResultSet resultSet;

	public DatabaseController () {
		try {
			dataCon = DriverManager.getConnection("jdbc:mysql://dump-sump.cotysnks4blq.us-west-2.rds.amazonaws.com:3306/sys", "admin", "rootroot");
		}catch (java.sql.SQLException e){
			System.err.println("Error connecting to the database");
			System.err.println(e.getMessage());
		}
	}

	protected synchronized ArrayList<Account> accountListFromDataBase (){
		ArrayList<Account> temp = new ArrayList<Account>();
		try {
			statement = dataCon.createStatement();
			ResultSet rs =  statement.executeQuery("SELECT * FROM dump-sump.account");
			while (rs.next()){
				Account tempItem = new Account(rs.getInt("id"), rs.getString("type"), rs.getString("username"),
						rs.getString("password"));
				temp.add(tempItem);
			}
		}catch (java.sql.SQLException e){
			System.err.println("Error while trying to get the list of items from the server");
			e.printStackTrace();
		}
		return temp;
	}

	protected synchronized void addItem (Account temp){
		try {
			String overrideQuerry = "SELECT * FROM account WHERE id =" + temp.getId();
			statement = dataCon.createStatement();
			resultSet = statement.executeQuery(overrideQuerry);
			if (resultSet.next()) {
				String updateQuerry = "UPDATE account SET id = ?, type = ?, username = ?, password = ? WHERE id = ? ";
				PreparedStatement pStat = dataCon.prepareStatement(updateQuerry);
				pStat.setInt(1, temp.getId());
				pStat.setString(2, temp.getType());
				pStat.setString(3, temp.getUsername());     //The description of the GIVEN item
				pStat.setString(4, temp.getPassword());    //The quantity of the GIVEN item
				pStat.executeUpdate();
			} else {
				insertAccount(temp);
			}

		}catch (java.sql.SQLException e){
			System.err.println("Error adding items into the database");
			e.printStackTrace();
		}

	}

	/**
	 * Inserts an item to the database.
	 * @param temp the item to insert.
	 */
	private synchronized void insertAccount(Account temp ){
		try {
			String insertQuery = "INSERT items (id, type, username, password) VALUES (?,?,?,?)";
			PreparedStatement pStat = dataCon.prepareStatement(insertQuery);
			pStat.setInt(1, temp.getId());
			pStat.setString(2, temp.getType());
			pStat.setString(3, temp.getUsername());
			pStat.setString(4, temp.getPassword());
			pStat.executeUpdate();
		}catch (java.sql.SQLException e){
			System.err.println("Error inserting an item to the table");
			e.printStackTrace();
		}
	}

	/**
	 * Removes an item from the database.
	 * @param item item to be removed.
	 */
	protected  synchronized void removeAccount (Account item){
		try {
			String querry = "DELETE FROM account WHERE id = ?";
			PreparedStatement pStat = dataCon.prepareStatement(querry);
			pStat.setInt(1, item.getId());
			pStat.execute();
		}catch (java.sql.SQLException e){
			System.err.println("Error removing an item from the database, Already removed?");
			e.printStackTrace();
		}
	}
	
	protected Account authenticateAccount(String username, String password) throws SQLException {
		String query = "SELECT * FROM ACCOUNT WHERE ACCOUNT.username = ? AND ACCOUNT.password = ?";
		PreparedStatement pStat = dataCon.prepareStatement(query);
		pStat.setString(1, username);
		pStat.setString(2, password);
		ResultSet result = pStat.executeQuery();
		return new Account(result.getInt("account_ID"), result.getString("type"), result.getString("username"), result.getString("password"));
	}


}


