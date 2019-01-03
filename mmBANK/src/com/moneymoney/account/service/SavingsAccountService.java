package com.moneymoney.account.service;

import java.sql.SQLException;
import java.util.List;

import com.moneymoney.account.SavingsAccount;
import com.moneymoney.exception.AccountNotFoundException;

public interface SavingsAccountService {

	SavingsAccount createNewAccount(String accountHolderName, double accountBalance, boolean salary) throws ClassNotFoundException, SQLException;

	SavingsAccount updateAccount(int accountNumber, String string, String updatedArgument) throws ClassNotFoundException, SQLException, AccountNotFoundException;

	SavingsAccount getAccountById(int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException;

	List<SavingsAccount> searchAccountBy(String string) throws ClassNotFoundException, SQLException, AccountNotFoundException;
	
	boolean deleteAccount(int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException;
	
	List<SavingsAccount> getAllSavingsAccount() throws ClassNotFoundException, SQLException;

	void fundTransfer(SavingsAccount sender, SavingsAccount receiver, double amount) throws ClassNotFoundException, SQLException;
	void deposit(SavingsAccount account, double amount) throws ClassNotFoundException, SQLException;
	void withdraw(SavingsAccount account, double amount) throws ClassNotFoundException, SQLException;
	
	List<SavingsAccount>sort(int choice, int order) throws ClassNotFoundException, SQLException;

	 	List<SavingsAccount> searchAccountBy(int min, int max) throws ClassNotFoundException, SQLException;
}











