#!/bin/bash

cd $BONUSPAYOUT_DIR
mvn clean install package
cd $WLP_DIR
cp $BONUSPAYOUT_DIR/batch-bonuspayout-wlpcfg/* .
export WLP_USER_DIR=$WLP_DIR/usr/
cd servers/BonusPayout


cp $BONUSPAYOUT_DIR/batch-bonuspayout-application/target/batch-bonuspayout-application-1.0-SNAPSHOT.war dropins/BonusPayout.war

rm -r logs

../../../bin/server start BonusPayout

../../..//bin/batchManager submit --batchManager=localhost:9443 --trustSslCertificates --user=bob --password=bobpwd --applicationName=BonusPayout --jobXMLName=BonusPayoutJob --jobPropertiesFile=$WLP_USER_DIR/shared/resources/runToCompletionParms.txt --wait --pollingInterval_s=1

../../../bin/server stop BonusPayout

##CURL REQUEST TO READ THE DB
curl -XPOST 'https://79cfd3d0-8e3c-416a-83ef-362ebb4745ec-bluemix.cloudant.com/cloudant-bonuspayout/_find'  -H 'Content-Type: application/json' -d '{"selector":{"_id":{"$gt":0}},"fields":["_id","accountNumber","balance","instanceId"],"sort":[{"_id":"asc"}]}'
