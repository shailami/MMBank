package com.moneymoney.account.ui;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.moneymoney.account.SavingsAccount;
import com.moneymoney.account.service.SavingsAccountService;
import com.moneymoney.account.service.SavingsAccountServiceImpl;
import com.moneymoney.account.util.DBUtil;
import com.moneymoney.exception.AccountNotFoundException;

public class AccountCUI {
	private static Scanner scanner = new Scanner(System.in);
	private static SavingsAccountService savingsAccountService = new SavingsAccountServiceImpl();

	public static void start() {

		do {
			System.out.println("****** Welcome to Money Money Bank********");
			System.out.println("1. Open New Savings Account");
			System.out.println("2. Update Account");
			System.out.println("3. Close Account");
			System.out.println("4. Search Account");
			System.out.println("5. Withdraw");
			System.out.println("6. Deposit");
			System.out.println("7. FundTransfer");
			System.out.println("8. Check Current Balance");
			System.out.println("9. Get All Savings Account Details");
			System.out.println("10. Sort Accounts");
			System.out.println("11. Exit");
			System.out.println();
			System.out.println("Make your choice: ");

			int choice = scanner.nextInt();

			performOperation(choice);

		} while (true);
	}

	private static void performOperation(int choice) {
		switch (choice) {
		case 1:
			acceptInput("SA");
			break;
		case 2:
			updateAccount();
			break;
		case 3:
			closeAccount();
			break;
		case 4:
			searchAccount();
			break;
		case 5:
			withdraw();
			break;
		case 6:
			deposit();
			break;
		case 7:
			fundTransfer();
			break;
		case 8:
			getCurrentBalance();
			break;
		case 9:
			showAllAccounts();
			break;
		case 10:
			sortMenu();
			break;
		case 11:
			try {
				DBUtil.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.exit(0);
			break;
		default:
			System.err.println("Invalid Choice!");
			break;
		}

	}

	private static void getCurrentBalance() {
		System.out.println("Enter accountNumber");
		int accountNumber = scanner.nextInt();
		try {
			SavingsAccount savingsAccount = savingsAccountService
					.getAccountById(accountNumber);
			System.out.println(savingsAccount);
		} catch (ClassNotFoundException | SQLException
				| AccountNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void searchAccount() {
		System.out.println("Select parameter for searched ");
		System.out.println("1.AccountNumber");
		System.out.println("2.AccountHoldersName");
		System.out.println("3.By salary Range");
		int choice = scanner.nextInt();
		switch (choice) {
		case 1:
			System.out.println("enter account number");
			int accountNumber = scanner.nextInt();
			try {
				SavingsAccount savingsAccount = savingsAccountService
						.getAccountById(accountNumber);
				System.out.println(savingsAccount);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AccountNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 2:
			System.out.println("Enter Account Holder Name");
			String string = scanner.nextLine();
			string = scanner.nextLine();
			try {
				List<SavingsAccount> savingsAccountList = savingsAccountService
						.searchAccountBy(string);
				for (SavingsAccount savingsAccount : savingsAccountList) {
					System.out.println(savingsAccount);
				}
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AccountNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case 3:
			System.out.println("Enter Salary Range");
			System.out.println("Enter minimum amount");
			int min = scanner.nextInt();
			System.out.println("Enter maximum amount");
			int max = scanner.nextInt();
			try {
				List<SavingsAccount> savingsAccountList = savingsAccountService
						.searchAccountBy(min, max);
				for (SavingsAccount savingsAccount : savingsAccountList) {
					System.out.println(savingsAccount);
				}
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static void updateAccount() {
		System.out.println("Enter Account Number of Account to be updated");
		int accountNumber = scanner.nextInt();
		System.out.println("Select what you want to update");
		System.out.println("1.Account Holders name");
		System.out.println("2.IS SALARY ");
		System.out.println("3.BOTH ACCOUNT HOLDER NAME AND IS SALARY ");
		int choice = scanner.nextInt();
		String updatedArgument = "";
		SavingsAccount savingAccount = null;
		switch (choice) {
		case 1:
			System.out.println("Enter name");
			updatedArgument = scanner.nextLine();
			updatedArgument = scanner.nextLine();
			try {
				savingAccount = savingsAccountService.updateAccount(
						accountNumber, "accountHolderName", updatedArgument);
				System.out.println(savingAccount.toString());
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AccountNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 2:
			System.out.println("Enter isSalary type(y/n)");
			updatedArgument = scanner.nextLine().equalsIgnoreCase("y") ? "true"
					: "false";
			updatedArgument = scanner.nextLine().equalsIgnoreCase("y") ? "true"
					: "false";
			try {
				savingAccount = savingsAccountService.updateAccount(
						accountNumber, "isSalary", updatedArgument);
				System.out.println(savingAccount);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AccountNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 3:
			System.out.println("Enter name");
			updatedArgument = scanner.nextLine();
			updatedArgument = scanner.nextLine();
			try {
				savingsAccountService.updateAccount(accountNumber,
						"accountHolderName", updatedArgument);
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Enter isSalary type(y/n)");
			updatedArgument = scanner.nextLine().equalsIgnoreCase("y") ? "true"
					: "false";
			try {
				savingAccount =savingsAccountService.updateAccount(
						accountNumber, "isSalary", updatedArgument);
				System.out.println(savingAccount);
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		System.out.println();

	}

	private static void closeAccount() {
		System.out.println("Enter Account Number");
		int accountNumber = scanner.nextInt();
		try {
			boolean result = savingsAccountService.deleteAccount(accountNumber);
			if (result == true) {
				System.out.println("Successfully deleted");
			} else
				System.out.println("Deletion Unsuccessfull");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AccountNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void fundTransfer() {
		System.out.println("Enter Account Sender's Number: ");
		int senderAccountNumber = scanner.nextInt();
		System.out.println("Enter Account Receiver's Number: ");
		int receiverAccountNumber = scanner.nextInt();
		System.out.println("Enter Amount: ");
		double amount = scanner.nextDouble();
		try {
			SavingsAccount senderSavingsAccount = savingsAccountService
					.getAccountById(senderAccountNumber);
			SavingsAccount receiverSavingsAccount = savingsAccountService
					.getAccountById(receiverAccountNumber);
			savingsAccountService.fundTransfer(senderSavingsAccount,
					receiverSavingsAccount, amount);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void deposit() {
		System.out.println("Enter Account Number: ");
		int accountNumber = scanner.nextInt();
		System.out.println("Enter Amount: ");
		double amount = scanner.nextDouble();
		SavingsAccount savingsAccount = null;
		try {
			savingsAccount = savingsAccountService
					.getAccountById(accountNumber);
			savingsAccountService.deposit(savingsAccount, amount);
			DBUtil.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			try {
				DBUtil.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			try {
				DBUtil.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	private static void withdraw() {
		System.out.println("Enter Account Number: ");
		int accountNumber = scanner.nextInt();
		System.out.println("Enter Amount: ");
		double amount = scanner.nextDouble();
		SavingsAccount savingsAccount = null;
		try {
			savingsAccount = savingsAccountService
					.getAccountById(accountNumber);
			savingsAccountService.withdraw(savingsAccount, amount);
			DBUtil.commit();
		} catch (ClassNotFoundException | SQLException
				| AccountNotFoundException e) {
			try {
				DBUtil.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (Exception e) {
			try {
				DBUtil.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	private static void sortMenu() {
		int choice = 0, order = 0;
		do {
			System.out.println("+++++Ways of Sorting+++++++");
			System.out.println("1. Account Number");
			System.out.println("2. Account Holder Name");
			System.out.println("3. Account Balance");
			System.out.println("4. Exit from Sorting");
			choice = scanner.nextInt();
			if(choice==4){
				AccountCUI.start();
			}
			if(choice!=4){
			System.out.println("Enter Choice");
			System.out.println("1.Ascending Order");
			System.out.println("2.Descending Order");
			order = scanner.nextInt();
			try {
				savingsAccountService.sort(choice, order);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		} while (true);		
	}

	private static void showAllAccounts() {
		List<SavingsAccount> savingsAccounts;
		try {
			savingsAccounts = savingsAccountService.getAllSavingsAccount();
			for (SavingsAccount savingsAccount : savingsAccounts) {
				System.out.println(savingsAccount);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

	private static void acceptInput(String type) {
		if (type.equalsIgnoreCase("SA")) {
			System.out.println("Enter your Full Name: ");
			String accountHolderName = scanner.nextLine();
			accountHolderName = scanner.nextLine();
			System.out
					.println("Enter Initial Balance(type na for Zero Balance): ");
			String accountBalanceStr = scanner.next();
			double accountBalance = 0.0;
			if (!accountBalanceStr.equalsIgnoreCase("na")) {
				accountBalance = Double.parseDouble(accountBalanceStr);
			}
			System.out.println("Salaried?(y/n): ");
			boolean salary = scanner.next().equalsIgnoreCase("n") ? false
					: true;
			createSavingsAccount(accountHolderName, accountBalance, salary);
		}
	}

	private static void createSavingsAccount(String accountHolderName,
			double accountBalance, boolean salary) {
		try {
			savingsAccountService.createNewAccount(accountHolderName,
					accountBalance, salary);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
