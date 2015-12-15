package com.ibm.websphere.samples.batch.cloudant;

import com.ibm.websphere.samples.batch.beans.AccountDataObject;

public class AccountModel {
	
	private String _id;
	
	private int accountNumber;
	
	private int balance; 
	
	private long instanceId;
	
	private String accountCode;
	
	public AccountModel(){}
	
	public AccountModel(AccountDataObject ado, long instanceId){
		this.accountNumber = ado.getAccountNumber();
		this.balance = ado.getBalance();
		this.instanceId = instanceId;
		this.accountCode = ado.getAccountCode();
		updateId();
	}


	public AccountModel(int accountNumber, int balance, long instanceId, String accountType) {
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.instanceId = instanceId;
		this.accountCode = accountType;
		updateId();
	}
	
	private void updateId(){
		_id = new StringBuilder().append(instanceId).append("-").append(accountNumber).toString();

	}

//	public int get_id() {
//		return _id;
//	}
//
//	public void set_id(int accountNumber) {
//		this._id = accountNumber;
//	}
	
	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
		updateId();
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public long getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(long instanceId) {
		this.instanceId = instanceId;
		updateId();
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	@Override
	public String toString() {
		return "AccountModel [accountNumber=" + accountNumber + ", balance=" + balance + ", instanceId=" + instanceId
				+ ", accountCode=" + accountCode + "]";
	}
	
	

}
