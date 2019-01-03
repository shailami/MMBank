package com.moneymoney.account.service;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.moneymoney.account.SavingsAccount;
import com.moneymoney.account.dao.SavingsAccountDAO;
import com.moneymoney.account.dao.SavingsAccountDAOImpl;
import com.moneymoney.account.factory.AccountFactory;
import com.moneymoney.account.util.DBUtil;
import com.moneymoney.exception.AccountNotFoundException;
import com.moneymoney.exception.InsufficientFundsException;
import com.moneymoney.exception.InvalidInputException;

public class SavingsAccountServiceImpl implements SavingsAccountService {

	private AccountFactory factory;
	private SavingsAccountDAO savingsAccountDAO;

	public SavingsAccountServiceImpl() {
		factory = AccountFactory.getInstance();
		savingsAccountDAO = new SavingsAccountDAOImpl();
	}

	@Override
	public SavingsAccount createNewAccount(String accountHolderName, double accountBalance, boolean salary)
			throws ClassNotFoundException, SQLException {
		SavingsAccount account = factory.createNewSavingsAccount(accountHolderName, accountBalance, salary);
		savingsAccountDAO.createNewAccount(account);
		return null;
	}

	@Override
	public List<SavingsAccount> getAllSavingsAccount() throws ClassNotFoundException, SQLException {
		return savingsAccountDAO.getAllSavingsAccount();
	}

	@Override
	public void deposit(SavingsAccount account, double amount) throws ClassNotFoundException, SQLException {
		if (amount > 0) {
			double currentBalance = account.getBankAccount().getAccountBalance();
			currentBalance += amount;
			savingsAccountDAO.updateBalance(account.getBankAccount().getAccountNumber(), currentBalance);
			//savingsAccountDAO.commit();
		}else {
			throw new InvalidInputException("Invalid Input Amount!");
		}
	}
	@Override
	public void withdraw(SavingsAccount account, double amount) throws ClassNotFoundException, SQLException {
		double currentBalance = account.getBankAccount().getAccountBalance();
		if (amount > 0 && currentBalance >= amount) {
			currentBalance -= amount;
			savingsAccountDAO.updateBalance(account.getBankAccount().getAccountNumber(), currentBalance);
			//savingsAccountDAO.commit();
		} else {
			throw new InsufficientFundsException("Invalid Input or Insufficient Funds!");
		}
	}

	@Override
	public void fundTransfer(SavingsAccount sender, SavingsAccount receiver, double amount)
			throws ClassNotFoundException, SQLException {
		try {
			withdraw(sender, amount);
			deposit(receiver, amount);
			DBUtil.commit();
		} catch (InvalidInputException | InsufficientFundsException e) {
			e.printStackTrace();
			DBUtil.rollback();
		} catch(Exception e) {
			e.printStackTrace();
			DBUtil.rollback();
		}
	}

	@Override
	public SavingsAccount updateAccount(int accountNumber,String string,String updatedArgument) throws ClassNotFoundException, SQLException, AccountNotFoundException {
		// TODO Auto-generated method stub
		return  savingsAccountDAO.updateAccount(accountNumber, string, updatedArgument) ;
	}

	@Override
	public SavingsAccount getAccountById(int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException {
		return savingsAccountDAO.getAccountById(accountNumber);
	}

	@Override
	public boolean deleteAccount(int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException {
		return savingsAccountDAO.deleteAccount(accountNumber);
	}

	@Override
	public List<SavingsAccount>  searchAccountBy(String string) throws ClassNotFoundException, SQLException, AccountNotFoundException {
		
		return savingsAccountDAO.searchAccountBy(string) ;
	}

	@Override
	public List<SavingsAccount> searchAccountBy(int min, int max) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return savingsAccountDAO.searchAccountBy(min,max) ;
	}
	
	@Override
	public List<SavingsAccount> sort(int choice, int order)
			throws ClassNotFoundException, SQLException {
		List<SavingsAccount> list = getAllSavingsAccount();
		switch (choice) {
		case 1:
			Collections.sort(list, new Comparator<SavingsAccount>() {

				@Override
				public int compare(SavingsAccount account1,
						SavingsAccount account2) {
					if (account1.getBankAccount().getAccountNumber() > account2
							.getBankAccount().getAccountNumber())
						if (order == 1)
							return 1;
						else
							return -1;
					else if (account1.getBankAccount().getAccountNumber() == account2
							.getBankAccount().getAccountNumber())
						return 0;
					else if (order == 1)
						return -1;
					else
						return 1;
				}
			});
			break;
		case 2:
			Collections.sort(list, new Comparator<SavingsAccount>() {

				@Override
				public int compare(SavingsAccount account1,
						SavingsAccount account2) {
					int result = (account1.getBankAccount()
							.getAccountHolderName().compareTo(account2
							.getBankAccount().getAccountHolderName()));
					result = order == 1 ? result : -result;
					return result;
				}
			});

			break;
		case 3:
			Collections.sort(list, new Comparator<SavingsAccount>() {

				@Override
				public int compare(SavingsAccount account1,
						SavingsAccount account2) {
					if (account1.getBankAccount().getAccountBalance() > account2
							.getBankAccount().getAccountBalance())
						if (order == 1)
							return 1;
						else
							return -1;
					else if (account1.getBankAccount().getAccountBalance() == account2
							.getBankAccount().getAccountBalance())
						return 0;
					else if (order == 1)
						return -1;
					else
						return 1;
				}
			});
			break;
		}
		System.out.println(list);
		return list;
	}

}
