package com.Bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class BankingApp {
	private static final String url = "jdbc:mysql://localhost:3306/banking_system";
	private static final String userName = "root";
	private static final String password = "Dibya@2002";
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
//		Register the Driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
//		Create Connection
		try {
			Connection connection = DriverManager.getConnection(url, userName, password);
			Scanner scanner = new Scanner(System.in);
			User user = new User(connection, scanner);
			Account account = new Account(connection, scanner);
			AccountManager accountManager = new AccountManager(connection, scanner);
			
			String email;
			long account_number;
			
			while(true) {
				System.out.println("*** WELCOME TO BANKING SYSTEM ***");
				System.out.println();
				System.out.println("1. Register");
				System.out.println("2. Login");
				System.out.println("3. Exit");
				System.out.println("Enter your choice : ");
				int choice1 = scanner.nextInt();
				switch (choice1) {
					case 1:
						user.register();
						System.out.print("\033[H\033[2J");
						System.out.flush();
						break;
					case 2:
						email = user.login();
						if(email != null) {
							System.out.println();
							System.out.println("User Logged In!");
							if(!account.account_exist(email)) {
								System.out.println();
								System.out.println("1. Open New Bank Account");
								System.out.println("2. Exit");
								if(scanner.nextInt() == 1) {
									account_number = account.open_account(email);
									System.out.println("Account Created Successfully");
									System.out.println("Your Account Number is : "+account_number);
								}
								else {
									break;
								}
							}
							account_number = account.getAccount_number(email);
							int choice2 = 0;
							while(choice2 != 5) {
								System.out.println();
								System.out.println("1. Debit Money");
								System.out.println("2. Credit Money");
								System.out.println("3. Transfer Money");
								System.out.println("4. Check Balance");
								System.out.println("5. Log Out");
								System.out.println("Enter your choice : ");
								choice2 = scanner.nextInt();
								switch (choice2) {
									case 1:
										accountManager.debit_money(account_number);
										break;
									case 2:
										accountManager.credit_money(account_number);
										break;
									case 3:
										accountManager.transfer_money(account_number);
										break;
									case 4:
										accountManager.getBalance(account_number);
										break;
									case 5:
										break;
									default:
										System.out.println("Enter Valid choice!");
										break;
								}
							}
						}
						else {
							System.out.println("Incorrect Email or Password!");
						}
					case 3:
						System.out.println("THANK YOU FOR USING BANKING SYSTEM!!!");
						System.out.println("Exiting System!");
						return;
					default :
						System.out.println("Enter valid Choice");
						break;
				}
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
