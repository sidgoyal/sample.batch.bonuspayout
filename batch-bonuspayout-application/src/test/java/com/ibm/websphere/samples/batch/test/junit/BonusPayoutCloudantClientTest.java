package com.ibm.websphere.samples.batch.test.junit;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cloudant.client.org.lightcouch.DocumentConflictException;
import com.ibm.websphere.samples.batch.beans.AccountDataObject;
import com.ibm.websphere.samples.batch.cloudant.AccountModel;
import com.ibm.websphere.samples.batch.cloudant.BonusPayoutCloudantClient;
import com.ibm.websphere.samples.batch.test.util.AccountGenerator;

public class BonusPayoutCloudantClientTest {
	
	public static Logger logger = Logger.getLogger(BonusPayoutCloudantClientTest.class.getName());
	
	public static BonusPayoutCloudantClient client ;
	

	
	@BeforeClass
	public static void setup() throws Exception{
		Configuration config = null;
		config = new PropertiesConfiguration("config.properties");

		String database = config.getString("cloudant.database");
		String username = config.getString("cloudant.username");
		String apiKey = config.getString("cloudant.apiKey");
		String password = config.getString("cloudant.apiKey.password");

		client = new BonusPayoutCloudantClient();
		client.initializeClient(username, database, apiKey, password);
		client.cleanDB();
	}
	
	@AfterClass
	public static void breakDown(){
		client.cleanDB();
	}
	
	@Test
	public void testAddMultipleAccountNumberSameInstanceId(){
		
		AccountDataObject account = AccountGenerator.generateAccount();
		logger.fine("Account Generated : " + account.toString());
		
		client.addAccount(account, 1L);
		
		//Getting a new account objectr with same instanceId
		account = AccountGenerator.generateAccount(1L);
		logger.fine("new account being added : " + account.toString());
		client.addAccount(account, 1L);
		
		

	}
	
	@Test
	public void testAddMultipleInstanceIdSameAccountNumber() {
		AccountDataObject account = AccountGenerator.generateAccount();
		logger.fine("Account Generated : " + account.toString());
		client.addAccount(account, AccountGenerator.getRandomInt());
		
		logger.fine("Account Generated : " + account.toString());
		client.addAccount(account, AccountGenerator.getRandomInt());
		
	}
	
	@Test(expected = DocumentConflictException.class)
	public void testAddDuplicateRecord() throws Exception{
		AccountDataObject account = AccountGenerator.generateAccount();
		logger.fine("Account Generated : " + account.toString());
		client.addAccount(account, 3L);;
		
		account.setBalance(500);
		client.addAccount(account, 3L);;

	}
	
	@Test
	public void getAccountsForInstanceId(){
		AccountDataObject account = AccountGenerator.generateAccount();
		logger.fine("Account Generated : " + account.toString());
		
		int startingAccountNumber = account.getAccountNumber();
		long instanceId = AccountGenerator.getRandomInt();
		

		for(int i = 0; i < 6; i++){
			client.addAccount(account, instanceId);
			account.setAccountNumber(account.getAccountNumber()+ 1);
		}
		
		logger.fine("startingAccountNumber " + startingAccountNumber + " instanceId " + instanceId);
		
		
		
		int i = 0;
		while(true){
			List<AccountModel> accounts = client.getAccountsList(instanceId, startingAccountNumber + i*3, 3);
			i++;
			assertEquals(3,accounts.size());
			if(i == 2 ){
				break;
			}
			
			
		}
		
		
		
	}

}
