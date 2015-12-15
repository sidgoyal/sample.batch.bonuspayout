package com.ibm.websphere.samples.batch.cloudant;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.FindByIndexOptions;
import com.cloudant.client.api.model.IndexField;
import com.cloudant.client.api.model.IndexField.SortOrder;
import com.ibm.websphere.samples.batch.beans.AccountDataObject;
import com.ibm.websphere.samples.batch.util.BonusPayoutConstants;
import com.ibm.websphere.samples.batch.util.BonusPayoutUtils;


public class BonusPayoutCloudantClient {
	
	//private static Boolean cleanupDB = true;
	
   private final static Logger logger = Logger.getLogger(BonusPayoutConstants.BONUS_PAYOUT_LOGGER);
    

	
    private String dbUsername;
    private String dbApiKey;
    private String dbApiPassword;
    private String dbName;
    private CloudantClient client ;
    private Database db;
     
    private boolean initialized = false;
    
	
	
	public BonusPayoutCloudantClient(){
		
	}
	
	// synchronizing just for making it safer, although might not need to be locked
	public synchronized void initializeClient(String databaseName){
		if(!initialized){
			//TODO
			System.out.println("[DEBUG] instide initializer");
			initialized = true;
			Configuration config = null;
			try {
				config = new PropertiesConfiguration("config.properties");
				config.setProperty("bc", "sutta");
			} catch (ConfigurationException e) {
				logger.log(Level.FINE,"Cannot read db configuration from database : ",e);
				throw new RuntimeException("Cannot read db configuration from config file" );
				
			}
			
			this.dbName = databaseName;

			this.dbUsername = config.getString("username");
			this.dbApiKey = config.getString("apiKey");
			this.dbApiPassword = config.getString("apiKeyPassword");
			
			client = ClientBuilder.account(dbUsername)
	                .username(dbApiKey)
	                .password(dbApiPassword)
	                .build();
			
			System.out.println("[DEBUG] databaseName " + databaseName);
			
			 db = client.database(dbName, false);
			
			 
			// 
				 }
			 
			/*
			synchronized(cleanupDB){
				if(cleanupDB == true){
					//TODO code to delete all documents in the database
				}
			}
			*/
			
			 
		}
	

	public BonusPayoutCloudantClient(String databaseName){
		initializeClient(databaseName);
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
				 System.out.println("[DEBUG] map " +  map);
				 if(map.getKey().contains("_design")){
					 System.out.println("[DEBUG] skipping design docs");
					 continue;
				 }
				db.remove(map.getKey(), map.getValue());
			 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}

}
