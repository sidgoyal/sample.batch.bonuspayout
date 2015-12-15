package com.ibm.websphere.samples.batch.test.junit;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.logging.Logger;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cloudant.client.org.lightcouch.DocumentConflictException;
import com.ibm.websphere.samples.batch.beans.AccountDataObject;
import com.ibm.websphere.samples.batch.cloudant.AccountModel;
import com.ibm.websphere.samples.batch.cloudant.BonusPayoutCloudantClient;
import com.ibm.websphere.samples.batch.test.util.AccountGenerator;

public class BonusPayoutCloudantClientTest {
	
	private static Logger logger = Logger.getLogger(BonusPayoutCloudantClientTest.class.getName());
	
	private static BonusPayoutCloudantClient client  = new BonusPayoutCloudantClient("cloudant-bonuspayout");
	

	
	@BeforeClass
	public static void setup(){
	client.cleanDB();
	}
	
	@AfterClass
	public static void breakDown(){
		//TODO method to close all db resources
	}
	
//	@Ignore
	@Test
	public void testAddMultipleAccountNumberSameInstanceId(){
		
		AccountDataObject account = AccountGenerator.generateAccount();
		logger.info("Account Generated : " + account.toString());
		
		client.addAccount(account, 1L);
		
		//Getting a new account objectr with same instanceId
		account = AccountGenerator.generateAccount(1L);
		logger.info("new account being added : " + account.toString());
		client.addAccount(account, 1L);
		
		

	}
	
	@Test
	public void testAddMultipleInstanceIdSameAccountNumber() {
		AccountDataObject account = AccountGenerator.generateAccount();
		logger.info("Account Generated : " + account.toString());
		client.addAccount(account, AccountGenerator.getRandomInt());
		
		logger.info("Account Generated : " + account.toString());
		client.addAccount(account, AccountGenerator.getRandomInt());
		
	}
	
//	@Ignore
	@Test(expected = DocumentConflictException.class)
	public void testAddDuplicateRecord() throws Exception{
		AccountDataObject account = AccountGenerator.generateAccount();
		logger.info("Account Generated : " + account.toString());
		client.addAccount(account, 3L);;
		
		account.setBalance(500);
		client.addAccount(account, 3L);;

	}
	
	@Test
	public void getAccountsForInstanceId(){
		AccountDataObject account = AccountGenerator.generateAccount();
		logger.info("Account Generated : " + account.toString());
		
		int startingAccountNumber = account.getAccountNumber();
		long instanceId = AccountGenerator.getRandomInt();
		

		for(int i = 0; i < 6; i++){
			client.addAccount(account, instanceId);
			account.setAccountNumber(account.getAccountNumber()+ 1);
		}
		
		logger.info("startingAccountNumber " + startingAccountNumber + " instanceId " + instanceId);
		
		
		
		int i = 0;
		while(true){
			List<AccountModel> accounts = client.getAccountsList(instanceId, startingAccountNumber + i*3, 3);
			System.out.println("[DEBUG] accounts = " + accounts);
			i++;
			assertEquals(3,accounts.size());
			if(i == 2 ){
				break;
			}
			
			
		}
		
		
		
	}

}
