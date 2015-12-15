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
package com.ibm.websphere.samples.batch.util;

import java.util.Iterator;

import com.ibm.websphere.samples.batch.cloudant.AccountModel;

/**
 * Helper class to provide more organized access to the
 * StepContext transient data.
 */
public class TransientDataHolder {

    private Iterator<AccountModel> accountsListIterator;
    private Integer recordNumber;

    /**
     * @return the resultSet
     */
    public Iterator<AccountModel>  getAccountsListIterator() {
        return accountsListIterator;
    }

    /**
     * @param resultSet the resultSet to set
     */
    public void setAccountsListIterator(Iterator<AccountModel> accountsList ) {
        this.accountsListIterator = accountsList;
    }

    /**
     * @return the recordNumber
     */
    public Integer getRecordNumber() {
        return recordNumber;
    }

    /**
     * @param recordNumber the recordNumber to set
     */
    public void setRecordNumber(Integer recordNumber) {
        this.recordNumber = recordNumber;
    }

}
