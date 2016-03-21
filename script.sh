#!/bin/bash

cd $BONUSPAYOUT_DIR
mvn clean install package
cd $WLP_DIR
cp $BONUSPAYOUT_DIR/batch-bonuspayout-wlpcfg/* .
export WLP_USER_DIR=$WLP_DIR/usr/
cd servers/BonusPayout

../../..//bin/batchManager submit --batchManager=localhost:9443 --trustSslCertificates --user=bob --password=bobpwd --applicationName=BonusPayout --jobXMLName=BonusPayoutJob --jobParameter="numRecords=100" --wait --pollingInterval_s=1

##CURL REQUEST TO READ THE DB
curl -XPOST 'https://79cfd3d0-8e3c-416a-83ef-362ebb4745ec-bluemix.cloudant.com/cloudant-bonuspayout/_find'  -H 'Content-Type: application/json' -d '{"selector":{"_id":{"$gt":0}},"fields":["_id","accountNumber","balance","instanceId"],"sort":[{"_id":"asc"}]}'
