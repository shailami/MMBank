package com.moneymoney.account.dao;

import java.sql.SQLException;
import java.util.List;

import com.moneymoney.account.SavingsAccount;
import com.moneymoney.exception.AccountNotFoundException;

public interface SavingsAccountDAO {
	
	SavingsAccount createNewAccount(SavingsAccount account) throws ClassNotFoundException, SQLException;
	List<SavingsAccount> searchAccountBy(String string) throws ClassNotFoundException, SQLException, AccountNotFoundException;
	SavingsAccount updateAccount(int accountNumber,String string,String updatedArgument) throws ClassNotFoundException, SQLException, AccountNotFoundException;
	SavingsAccount getAccountById(int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException;
	boolean deleteAccount(int accountNumber) throws SQLException, ClassNotFoundException, AccountNotFoundException;
	List<SavingsAccount> getAllSavingsAccount() throws ClassNotFoundException, SQLException;
	void updateBalance(int accountNumber, double currentBalance) throws ClassNotFoundException, SQLException;
	void commit() throws SQLException;
	List<SavingsAccount> searchAccountBy(int min, int max) throws ClassNotFoundException, SQLException;
	
	
}
