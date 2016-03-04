package com.ibm.websphere.samples.batch.cloudant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Logger;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.FindByIndexOptions;
import com.cloudant.client.api.model.IndexField;
import com.cloudant.client.api.model.IndexField.SortOrder;
import com.ibm.websphere.samples.batch.beans.AccountDataObject;
import com.ibm.websphere.samples.batch.util.BonusPayoutConstants;
import com.ibm.websphere.samples.batch.util.BonusPayoutUtils;


public class BonusPayoutCloudantClient  {
	
	//private static Boolean cleanupDB = true;
	
   private final static Logger logger = Logger.getLogger(BonusPayoutConstants.BONUS_PAYOUT_LOGGER);
    

	
    private String dbUsername;
    private String dbApiKey;
    private String dbApiPassword;
    private String dbName;
    private CloudantClient client ;
    private Database db;
     
	
	public void initializeClient(String username, String databaseName, String apiKey, String password ){
			this.dbName = databaseName;
			this.dbUsername = username;
			this.dbApiKey = apiKey;
			this.dbApiPassword = password;
			
			client = ClientBuilder.account(dbUsername)
	                .username(dbApiKey)
	                .password(dbApiPassword)
	                .build();
			
			 db = client.database(dbName, false);
			 
			 logger.fine("DB Details : " + this.toString());
	}
			 

	public void addAccount(AccountDataObject account, long instanceId){
		db.save(new AccountModel(account, instanceId));
		
	}
	
	public List<AccountModel> getAccountsList(long instanceId, int recordNumber, int chunkSize){
		List<AccountModel> accounts =  db.findByIndex(BonusPayoutUtils.getSelectorJson(instanceId, recordNumber, chunkSize)
				, AccountModel.class
				, new FindByIndexOptions()
						.fields("instanceId")
						.fields("balance")
						.fields("accountNumber")
						.fields("accountCode")
						.sort(new IndexField("accountNumber",SortOrder.asc)));
		logger.finer("accountsList read from the DB : " + accounts);
		
		return accounts;
		
	}
	
	public void cleanDB(){
		 
		 db.getAllDocsRequestBuilder().build();
		 try {
			for( Entry<String, String>  map : db.getAllDocsRequestBuilder().build().getResponse().getIdsAndRevs().entrySet()){
				 logger.fine("map " +  map);
				 if(map.getKey().contains("_design")){
					 logger.fine("skipping design docs");
					 continue;
				 }
				db.remove(map.getKey(), map.getValue());
			 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}
	
	public  List getInstanceIds(){
		List<String> instanceIds = new ArrayList<String>(); 
		try {
			for(Entry<String, String> entry : db.getAllDocsRequestBuilder().build().getResponse().getIdsAndRevs().entrySet()){
				instanceIds.add(entry.getKey().split("-")[0]);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return instanceIds;
	}
	

	public  List getAccountsForInstanceId(){
		List<String> instanceIds = new ArrayList<String>(); 
		try {
			for(Entry<String, String> entry : db.getAllDocsRequestBuilder().build().getResponse().getIdsAndRevs().entrySet()){
				instanceIds.add(entry.getKey().split("-")[0]);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return instanceIds;
	}


	@Override
	public String toString() {
		return "BonusPayoutCloudantClient [dbUsername=" + dbUsername + ", dbApiKey=" + dbApiKey + ", dbApiPassword="
				+ dbApiPassword + ", dbName=" + dbName + "]";
	}

}
