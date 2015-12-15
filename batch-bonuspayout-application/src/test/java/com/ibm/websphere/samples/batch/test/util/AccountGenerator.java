package com.ibm.websphere.samples.batch.test.util;

import java.util.Random;

import com.ibm.websphere.samples.batch.beans.AccountDataObject;
import com.ibm.websphere.samples.batch.cloudant.AccountModel;

public class AccountGenerator {
	
	private static final String ACCOUNT_TYPE = "ABCXYZ";
	
	public static AccountDataObject generateAccount(){
	
		Random rand = new Random();
		return new AccountDataObject(new AccountModel(rand.nextInt(100000),rand.nextInt(1000),rand.nextInt(100000),ACCOUNT_TYPE));
	}
	
	public static AccountDataObject generateAccount(long instanceId){
		
		Random rand = new Random();
		return new AccountDataObject(new AccountModel(rand.nextInt(100000),rand.nextInt(1000),instanceId,ACCOUNT_TYPE));

	}
	
	public static int getRandomInt(){
		return new Random().nextInt(100000);
	}

}
