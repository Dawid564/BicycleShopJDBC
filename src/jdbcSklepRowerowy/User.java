package jdbcSklepRowerowy;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class User {
	
	private jdbcConnection jdbcConn = new jdbcConnection();
	private Operator operator = new Operator();
	
	private String accountName = null;
	
	public User(){}
	public void menu(){
		System.out.println("");
		System.out.println("1. List Products");
		System.out.println("2. Change password");
		System.out.println("3. Log out");
		System.out.println("4. Buy something");
		Scanner scan = new Scanner(System.in);
		int var = scan.nextInt();
		realMenu(var);
	}
	
	
	public void menu(String accountName){
		this.accountName = accountName;
		System.out.println("1. List Products");
		System.out.println("2. Change password");
		System.out.println("3. Log out");
		System.out.println("4. Buy something");
		Scanner scan = new Scanner(System.in);
		int var = scan.nextInt();
		realMenu(var);
	}
	
	protected void realMenu(int var){
		switch(var){
		case 1:
			listProducts();
			break;
		case 2:
			changePassword();
			break;
		case 3:
			//logOut();
			break;
		case 4:
			buyProduct();
			break;
		default:
			break;	
		}
	}
	
	public void logOut(){
		//do not working
		operator.run();
	}
	
	public void changePassword(){
		System.out.println("What your old Password?");
		Scanner scanPass = new Scanner(System.in);
		String oldPass = scanPass.nextLine();

		System.out.println("set new password");
		Scanner scanFreshPass = new Scanner(System.in);
		String oldFreshPass= scanFreshPass.nextLine();
		
		if(jdbcConn.changePassword(accountName, oldPass, oldFreshPass)){
			System.out.println("password has changed");
		}else{
			System.out.println("incorrect old password");
		}
		menu();
	}
	
	public void listProducts(){
		List<String> listOfProducts = new ArrayList<>();
		listOfProducts = jdbcConn.listProducts();
		for(int i=0; i<listOfProducts.size(); i++){
			System.out.println(listOfProducts.get(i));
		}
		menu();
	}
	
	public void buyProduct(){
		System.out.println("What do you want to buy?");
		Scanner scanProductName = new Scanner(System.in);
		String productName = scanProductName.nextLine();
		
		if(jdbcConn.buyProduct(productName)){
			System.out.println("product is coming to you");
			menu();
		}else{
			System.out.println("out of stock");
		}
	}
}
