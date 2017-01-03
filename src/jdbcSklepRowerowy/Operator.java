package jdbcSklepRowerowy;

import java.util.Scanner;

public class Operator {
	
	private jdbcConnection jdbcConn = new jdbcConnection();
	
	public Operator(){}
	
	public void run(){
		createDatabase();
		createTable();
		
		switch(menu()){
		case 1:
			singIn();
			break;
		case 2:
			signUp();
			break;
		case 3:
			jdbcConn.listProducts();
			break;
		default:
			System.out.println("select current number");
			run();
		}
	}
	
	public boolean signUp(){
		Scanner scanLog = new Scanner(System.in);
		Scanner scanPass = new Scanner(System.in);
		Scanner scanEmail = new Scanner(System.in);
		
		System.out.println("Login");
		String login = scanLog.nextLine();
		
		System.out.println("Password");
		String pass = scanPass.nextLine();
		
		System.out.println("email");
		String email = scanPass.nextLine();
		
		
		if(jdbcConn.addUser(login, pass, email)){
			System.out.println("accound has created");
			run();
			return true;
		}else{
			System.out.println("ERROR");
			return false;
		}
	}
	
	public void singIn(){
		Scanner scanLog = new Scanner(System.in);
		Scanner scanPass = new Scanner(System.in);
		System.out.println("Login");
		String login = scanLog.nextLine();
		System.out.println("Password");
		String pass = scanPass.nextLine();
		jdbcConn.login(login, pass);
	}
	
	public void createDatabase(){
		if(jdbcConn.createDatabase()){
			System.out.println("Database create");
		}else{
			System.out.println("Database already exists");
		}
	}
	
	public void createTable(){
		if(!jdbcConn.createTable() && !jdbcConn.createTableOfProducts()){
			System.out.println("Table already exists");
		}
	}
	
	public int menu(){
		Scanner scan = new Scanner(System.in);
		System.out.println("1. Sign in");
		System.out.println("2. Sign up");
		int var = scan.nextInt();
		return var;
	}
}
