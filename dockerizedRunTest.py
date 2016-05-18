#!/bin/python 

import requests
import time
import os
import requests
from requests.packages.urllib3.exceptions import InsecureRequestWarning

requests.packages.urllib3.disable_warnings(InsecureRequestWarning)


host = os.environ['host_ip']
port = os.environ['host_port']

app_url = "https://" + host + ":" + str(port) + "/ibm/api/batch/jobinstances/"

# db prpoerties
cloudant_username = os.environ['cloudant-username']
cloudant_database = os.environ['cloudant-database']
apiKey = os.environ['apiKey']
password = os.environ['password']

# Job Properties
job_parameters_env = os.environ['jobParameters']


# Requests /ibm/api/batch/jobinstances/{instanceid}
# Returns the response object on getting jobinstance
def get_job_instance( id ) :
        return requests.get(app_url +  str(id), auth=('bob','bobpwd'), verify=False)


# creates a job_parameters dict from jobParameters env variable supplied by the submitter
def get_job_parameters( job_parameters ) :
        job_params = {}
    		
      	job_params['cloudant.username'] = cloudant_username
      	job_params['cloudant.database'] = cloudant_database
        job_params['cloudant.apiKey'] = apiKey
        job_params['cloudant.apiKey.password'] = password
        
        for prop in job_parameters.split(":"):
            pair = prop.split("=")
            job_params[pair[0]] = pair[1]

        return job_params


# Submit Job Payload
submit_data = {"applicationName" : "BonusPayout",
  "moduleName" : "BonusPayout.war",
  "jobXMLName" : "BonusPayoutJob"
}

submit_data["jobParameters"] = get_job_parameters(job_parameters_env)

print "############################################################"
print "Submitting the Job"

job_instance = requests.post(app_url, auth=('bob','bobpwd'), verify=False, json=submit_data)

print job_instance.text


instance_id =  job_instance.json().get("instanceId")

print "############################################################"
print "Waiting for the job to complete"
print

while True:
        time.sleep(2)
        job_instance = get_job_instance(instance_id)
        batchStatus = job_instance.json().get("batchStatus")
        if (batchStatus == "COMPLETED"):
                print "JOB COMPLETED"
                print job_instance.text
                break
        elif (batchStatus == "FAILED"):
                print "JOB FAILED"
            	print job_instance.text
                break


print "############################################################"
print "FETCHING RESULTS"
print 

results_url = "http://" + host + ":7080/BonusPayoutResultsApiService/results/jobinstances/"

accounts_list_before = requests.get(results_url + str(instance_id)).json().get("accounts")


cloudant_query = {
  "selector": {
    "instanceId": {
      "$eq": instance_id
    },
    "accountNumber":{
      "$gt": -1
    }
  },
  "fields": [
    "instanceId",
    "accountNumber",
    "balance",
    "accountCode"
  ],
  "sort": [
    {
      "accountNumber": "asc"
    }
  ]
}





cloudant_url = "https://" + cloudant_username + ".cloudant.com/" + cloudant_database + "/_find"

accounts_list_after = requests.post(cloudant_url, auth=(apiKey,password) , json=cloudant_query).json().get("docs")



account_num = 0
for entry_before in accounts_list_before:
        entry_after = accounts_list_after[account_num]
        account_num = account_num + 1
        if ( str(entry_before.get("accountNumber")) == str(entry_after.get("accountNumber")) ):
            print "instanceId : " + str(instance_id) + " || accountNumber : " + str(entry_before.get("accountNumber")) + " || Original Balance : "+ str(entry_before.get("amount")) + " || New Balance : " + str(entry_after.get("balance"))

print
print "Done testing"
print
