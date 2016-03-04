/*
 * Copyright 2014 International Business Machines Corp.
 * 
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership. Licensed under the Apache License, 
 * Version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ibm.websphere.samples.batch.artifacts;

import java.util.logging.Logger;

import javax.batch.api.BatchProperty;
import javax.batch.api.chunk.listener.ChunkListener;
import javax.batch.runtime.context.JobContext;
import javax.batch.runtime.context.StepContext;
import javax.inject.Inject;

import com.ibm.websphere.samples.batch.cloudant.BonusPayoutCloudantClient;
import com.ibm.websphere.samples.batch.util.BonusPayoutConstants;
import com.ibm.websphere.samples.batch.util.TransientDataHolder;

public class ValidationDBReadChunkListener implements ChunkListener, BonusPayoutConstants {

    private final static Logger logger = Logger.getLogger(BONUS_PAYOUT_LOGGER);

    @Inject
    @BatchProperty(name = "chunkSize")
    private String chunkSizeStr;
    private Integer chunkSize;

    @Inject
    @BatchProperty(name = "cloudant.database")
    private String databaseName;
    
    @Inject
    @BatchProperty(name = "cloudant.username")
    private String username;
    
    @Inject
    @BatchProperty(name = "cloudant.apiKey")
    private String apiKey;
    
    @Inject
    @BatchProperty(name = "cloudant.apiKey.password")
    private String password;


    @Inject
    private JobContext jobCtx;

    @Inject
    private StepContext stepCtx;
    
	private BonusPayoutCloudantClient client = new BonusPayoutCloudantClient() ;

	private boolean initializedClient = false;
    



    /**
     * Grab checkpoint value from StepContext, and set ResultSet
     * back into context after executing query.
     */
    @Override
    public void beforeChunk() throws Exception {
    	
    	if(!initializedClient){
    		initializedClient = true;
    		client.initializeClient(username, databaseName, apiKey, password);
    	}
    	
        // We expect this to have been initialized by the ItemReader
        TransientDataHolder data = (TransientDataHolder) stepCtx.getTransientUserData();

        Integer recordNumber = data.getRecordNumber();
        data.setAccountsListIterator(client.getAccountsList(jobCtx.getInstanceId(), recordNumber, getChunkSize()).iterator());
    }


    @Override
    public void onError(Exception ex) throws Exception {
        cleanup();
    }

    @Override
    public void afterChunk() throws Exception {
        cleanup();
    }

    private void cleanup() throws Exception {
    	//TODO close client
    }

    private int getChunkSize() {
        if (chunkSize == null) {
            chunkSize = Integer.parseInt(chunkSizeStr);
        }
        return chunkSize;
    }
}
