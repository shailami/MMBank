package com.moneymoney.account.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.moneymoney.account.SavingsAccount;
import com.moneymoney.account.util.DBUtil;
import com.moneymoney.exception.AccountNotFoundException;

public class SavingsAccountDAOImpl implements SavingsAccountDAO {

	public SavingsAccount createNewAccount(SavingsAccount account)
			throws ClassNotFoundException, SQLException {
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection
				.prepareStatement("INSERT INTO ACCOUNT VALUES(?,?,?,?,?,?)");
		preparedStatement
				.setInt(1, account.getBankAccount().getAccountNumber());
		preparedStatement.setString(2, account.getBankAccount()
				.getAccountHolderName());
		preparedStatement.setDouble(3, account.getBankAccount()
				.getAccountBalance());
		preparedStatement.setBoolean(4, account.isSalary());
		preparedStatement.setObject(5, null);
		preparedStatement.setString(6, "SA");
		preparedStatement.executeUpdate();
		preparedStatement.close();
		DBUtil.commit();
		return account;
	}

	public List<SavingsAccount> getAllSavingsAccount()
			throws ClassNotFoundException, SQLException {
		List<SavingsAccount> savingsAccounts = new ArrayList<>();
		Connection connection = DBUtil.getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM ACCOUNT");
		while (resultSet.next()) {// Check if row(s) is present in table
			int accountNumber = resultSet.getInt(1);
			String accountHolderName = resultSet.getString(2);
			double accountBalance = resultSet.getDouble(3);
			boolean salary = resultSet.getBoolean(4);
			SavingsAccount savingsAccount = new SavingsAccount(accountNumber,
					accountHolderName, accountBalance, salary);
			savingsAccounts.add(savingsAccount);
		}
		DBUtil.commit();
		return savingsAccounts;
	}

	@Override
	public void updateBalance(int accountNumber, double currentBalance)
			throws ClassNotFoundException, SQLException {
		Connection connection = DBUtil.getConnection();
		connection.setAutoCommit(false);
		PreparedStatement preparedStatement = connection
				.prepareStatement("UPDATE ACCOUNT SET accountBalance=? where accountNumber=?");
		preparedStatement.setDouble(1, currentBalance);
		preparedStatement.setInt(2, accountNumber);
		preparedStatement.executeUpdate();
		
	}

	@Override
	public SavingsAccount getAccountById(int accountNumber)
			throws ClassNotFoundException, SQLException,
			AccountNotFoundException {
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection
				.prepareStatement("SELECT * FROM account where accountNumber=?");
		preparedStatement.setInt(1, accountNumber);
		ResultSet resultSet = preparedStatement.executeQuery();
		SavingsAccount savingsAccount = null;
		if (resultSet.next()) {
			String accountHolderName = resultSet.getString(2);
			double accountBalance = resultSet.getDouble(3);
			boolean salary = resultSet.getBoolean(4);
			savingsAccount = new SavingsAccount(accountNumber,
					accountHolderName, accountBalance, salary);
			return savingsAccount;
		}
		throw new AccountNotFoundException("Account with account number "
				+ accountNumber + " does not exist.");
	}

	@Override
	public boolean deleteAccount(int accountNumber) throws SQLException,
			ClassNotFoundException, AccountNotFoundException {
		if ((getAccountById(accountNumber).getBankAccount().getAccountNumber()) == accountNumber) {
			Connection connection = DBUtil.getConnection();
			PreparedStatement preparedStatement = connection
					.prepareStatement("DELETE FROM account WHERE accountNumber=?");
			preparedStatement.setInt(1, accountNumber);
			preparedStatement.execute();
			DBUtil.commit();
			return true;
		}
		return false;
	}

	@Override
	public void commit() throws SQLException {
		DBUtil.commit();
	}

	@Override
	public SavingsAccount updateAccount(int accountNumber, String string,
			String updatedArgument) throws ClassNotFoundException,
			SQLException, AccountNotFoundException {
		Connection connection = DBUtil.getConnection();
		switch (string) {
		case "accountHolderName":
			PreparedStatement preparedStatement = connection
					.prepareStatement("UPDATE account SET  accountHolderName= ?	WHERE accountNumber=?");
			preparedStatement.setString(1, updatedArgument);
			preparedStatement.setInt(2, accountNumber);
			preparedStatement.execute();
			preparedStatement.close();
			DBUtil.commit();
			break;
		case "isSalary":
			PreparedStatement preparedStatement1 = connection
					.prepareStatement("UPDATE account SET  isSalary= ?	WHERE accountNumber=?");
			preparedStatement1.setBoolean(1,
					Boolean.parseBoolean(updatedArgument));
			preparedStatement1.setInt(2, accountNumber);
			preparedStatement1.execute();
			preparedStatement1.close();
			DBUtil.commit();
			break;
		}
		return getAccountById(accountNumber);
	}

	@Override
	public List<SavingsAccount> searchAccountBy(String string)
			throws ClassNotFoundException, SQLException,
			AccountNotFoundException {
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection
				.prepareStatement("SELECT * FROM ACCOUNT  WHERE accountHolderName= ?");
		preparedStatement.setString(1, string);
		ResultSet resultSet = preparedStatement.executeQuery();

		List<SavingsAccount> savingsAccounts = new ArrayList<>();
		while (resultSet.next()) {// Check if row(s) is present in table
			int accountNumber = resultSet.getInt(1);
			String accountHolderName = resultSet.getString(2);
			double accountBalance = resultSet.getDouble(3);
			boolean salary = resultSet.getBoolean(4);
			SavingsAccount savingsAccount = new SavingsAccount(accountNumber,
					accountHolderName, accountBalance, salary);
			savingsAccounts.add(savingsAccount);
		}
		DBUtil.commit();
		return savingsAccounts;

	}

	@Override
	public List<SavingsAccount> searchAccountBy(int min, int max)
			throws ClassNotFoundException, SQLException {
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection
				.prepareStatement("SELECT * FROM ACCOUNT  WHERE accountBalance BETWEEN ? AND ?");
		preparedStatement.setInt(1, min);
		preparedStatement.setInt(2, max);
		ResultSet resultSet = preparedStatement.executeQuery();

		List<SavingsAccount> savingsAccounts = new ArrayList<>();
		while (resultSet.next()) {// Check if row(s) is present in table
			int accountNumber = resultSet.getInt(1);
			String accountHolderName = resultSet.getString(2);
			double accountBalance = resultSet.getDouble(3);
			boolean salary = resultSet.getBoolean(4);
			SavingsAccount savingsAccount = new SavingsAccount(accountNumber,
					accountHolderName, accountBalance, salary);
			savingsAccounts.add(savingsAccount);
		}
		DBUtil.commit();
		return savingsAccounts;
	}
}
