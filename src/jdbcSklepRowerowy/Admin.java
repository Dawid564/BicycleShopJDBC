package jdbcSklepRowerowy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Admin extends User{
	public Admin(){}
	
	private jdbcConnection jdbcConn = new jdbcConnection();
	
	@Override
	public void menu(){
		System.out.println("");
		System.out.println("********** MENU **********");
		System.out.println("1. List Products");
		System.out.println("2. Change Password");
		System.out.println("3. Log out");
		System.out.println("4. List Users");
		System.out.println("5. Add product");
		System.out.println("6. Remove product");
		System.out.println("7. ");
		System.out.println("8. ");
		System.out.println("********** MENU **********");
		System.out.println("");
		Scanner scan = new Scanner(System.in);
		int var = scan.nextInt();
		realMenu(var);
	}
	
	@Override
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
			listUsers();
			break;
		case 5:
			addProduct();
			break;
		case 6:
			removeProduct();
			break;
		case 7:
			break;
		case 8:
			break;
		default:
			break;	
		}
	}
	
	private void listUsers(){
		List<String> listOfUsers = new ArrayList<>();
		listOfUsers = jdbcConn.listUsers();
		for(String user : listOfUsers){
			System.out.println(user);
		}
		menu();
	}
	
	private void addProduct(){
		Scanner scanName = new Scanner(System.in);
		Scanner scanQuantity = new Scanner(System.in);
		System.out.println("Set product name");
		String productName = scanName.nextLine();
		
		System.out.println("Set product quantity");
		String productQuantity = scanQuantity.nextLine();
		
		if(jdbcConn.addProduct(productName, productQuantity)){
			System.out.println("product has added");
			menu();
		}else{
			System.out.println("ERROR");
		}
	}
	
	private void removeProduct(){
		Scanner scanName = new Scanner(System.in);
		System.out.println("which product do you want to delete?");
		String productName = scanName.nextLine();
		
		if(jdbcConn.removeProduct(productName)){
			System.out.println("product has removed");
			menu();
		}else{
			System.out.println("ERROR");
		}
	}
}
