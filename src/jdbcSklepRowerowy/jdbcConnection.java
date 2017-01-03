package jdbcSklepRowerowy;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class jdbcConnection {
	private String url = "jdbc:mysql://localhost/rowerek";
	private String pass = "123456";
	private String user = "root";
	
	private String sqlCreateDatabase = "CREATE DATABASE IF NOT EXISTS rowerek";
	private String sqlUseUser = "USE rowerek";
	private String sqlCreateTable = "CREATE TABLE IF NOT EXISTS user (id MEDIUMINT NOT NULL AUTO_INCREMENT, login VARCHAR(20), password VARCHAR(20), "
			+ "email VARCHAR(25), rights VARCHAR(20), PRIMARY KEY(id))";
	private String sqlCreateTableOfProducts = "CREATE TABLE IF NOT EXISTS products (id MEDIUMINT NOT NULL AUTO_INCREMENT, productName VARCHAR(25), productQuantity VARCHAR(5),"
			+ " PRIMARY KEY(id));";
	private String sqlList = "SELECT * FROM products";
	
	private Statement statement = null;
	private PreparedStatement prepareListProducts = null;
	private PreparedStatement prepareLogin = null;
	private PreparedStatement prepareCheckOldPassword = null;
	private PreparedStatement prepareUpdatePassword = null;
	private PreparedStatement prepareListUsers = null;
	private PreparedStatement prepareAddProduct = null;
	private PreparedStatement prepareAddUser = null;
	private PreparedStatement prepareRemoveProduct = null;
	private PreparedStatement prepareBuyProduct = null;
	private Connection connection = null;
	
	public jdbcConnection(){}
	
	public void conneectToDatabase(){
		try{
			connection = DriverManager.getConnection(url, user, pass);
			statement = connection.createStatement();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean createDatabase(){
		conneectToDatabase();
		try{
			if(statement.execute(sqlCreateDatabase)){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean createTable(){
		conneectToDatabase();
		try{
			statement.execute(sqlUseUser);//mean database
			if(statement.execute(sqlCreateTable)){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean createTableOfProducts(){
		conneectToDatabase();
		try{
			statement.execute(sqlUseUser);//mean database
			if(statement.execute(sqlCreateTableOfProducts)){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean addUser(String login, String pass, String email){
		String sqlCreateUser = "INSERT INTO user (login, password, email, rights) VALUES(?,?,?, 'user')";
		try{
			prepareAddUser = connection.prepareStatement(sqlCreateUser);
			prepareAddUser.setString(1, login);
			prepareAddUser.setString(2, pass);
			prepareAddUser.setString(3, email);
			prepareAddUser.execute();
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean login(String login, String password){
		String sqlLogin = "SELECT * FROM user WHERE login = ? AND password = ?";
		try{
			prepareLogin = connection.prepareStatement(sqlLogin);
			prepareLogin.setString(1, login);
			prepareLogin.setString(2, password);
			ResultSet result = prepareLogin.executeQuery();
			result.absolute(1);
			String right = result.getString("rights");
			if(right.equals("admin")){
				System.out.println("Hello Admin");
				Admin admin = new Admin();
				admin.menu();
			}else{
				System.out.println("Hello User");
				User user = new User();
				user.menu(login);
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Wrong password or login");
			return false;
		}
	}
	
	public List<String> listProducts(){
		conneectToDatabase();
		List<String> listOfProducts = new ArrayList<>();
		try{
			if(connection != null){
				System.out.println("connection is not null");
				prepareListProducts = connection.prepareStatement(sqlList);
				ResultSet productResult = prepareListProducts.executeQuery();
				while(productResult.next()){
					//listOfProducts.add(productResult.getString("productName"));
					System.out.println(productResult.getString("productName"));
				}
			}else{
				System.out.println("connection is null");
			}
		}catch(SQLException e){
			e.printStackTrace();
			System.out.println("Error with listProducts");
		}
		return listOfProducts;
	}
	
	public void statTest(){
		String slqTest = "SELECT productName FROM products WHERE id=?";
		try{
			PreparedStatement preparedStatement = connection.prepareStatement(slqTest);
			preparedStatement.setLong(1, 1);
			ResultSet result = preparedStatement.executeQuery();
			result.next();
			String txt = null;
			System.out.println(txt = result.getString(1));
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("hello programmer!!");
	}
	
	public boolean changePassword(String login, String oldPassword, String freshPassword){
		conneectToDatabase();
		String sqlCheckOldPass = "SELECT password FROM user WHERE login = ?";
		String sqlChangePassword = "UPDATE user SET password = ? WHERE login = ?";
		try{
			prepareCheckOldPassword = connection.prepareStatement(sqlCheckOldPass);
			prepareCheckOldPassword.setString(1, login);
			ResultSet resultPass = prepareCheckOldPassword.executeQuery();
			resultPass.absolute(1);
			String oldPass = resultPass.getString("password");
			if(oldPass.equals(oldPassword)){
				prepareUpdatePassword = connection.prepareStatement(sqlChangePassword);
				prepareUpdatePassword.setString(1, freshPassword);
				prepareUpdatePassword.setString(2, login);
				prepareUpdatePassword.executeUpdate();
				System.out.println("hello my friend");
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	public List<String> listUsers(){
		conneectToDatabase();
		List<String> listOfUsers = new ArrayList<>();
		String sqlListUsers = "SELECT login FROM user";
		try{
			prepareListUsers = connection.prepareStatement(sqlListUsers);
			ResultSet result = prepareListUsers.executeQuery();
			while(result.next()){
				listOfUsers.add(result.getString("login"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return listOfUsers;
	}
	
	public boolean addProduct(String productName, String productQuantity){
		String sqlAddProduct = "INSERT INTO products (productName, productQuantity) VALUES (?, ?)";
		conneectToDatabase();
		try{
			prepareAddProduct = connection.prepareStatement(sqlAddProduct);
			prepareAddProduct.setString(1, productName);
			prepareAddProduct.setString(2, productQuantity);
			prepareAddProduct.executeUpdate();
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean removeProduct(String productName){
		String sqlRemoveProduct = "DELETE FROM products WHERE productName = ?";
		conneectToDatabase();
		try{
			prepareRemoveProduct = connection.prepareStatement(sqlRemoveProduct);
			prepareRemoveProduct.setString(1, productName);
			prepareRemoveProduct.executeUpdate();
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean buyProduct(String productName){
		String sqlBuyProduct = "UPDATE products SET productQuantity = productQuantity - 1 WHERE productName = ?";
		conneectToDatabase();
		try{
			prepareBuyProduct = connection.prepareStatement(sqlBuyProduct);
			prepareBuyProduct.setString(1, productName);
			prepareBuyProduct.execute();
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
}