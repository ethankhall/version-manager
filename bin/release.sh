#!/bin/bash

export BASE_URL=http://api.crom.tech/api/v1/project/ethankhall/repo/version-manager/version
export CURRENT_VERSION=`curl $BASE_URL/$(git rev-parse HEAD) | jq -r .version`
export CURRENT_POSTFIX=`echo $CURRENT_VERSION | sed 's/\./-/g'`

gcloud auth activate-service-account --key-file ${HOME}/client-secret.json
gcloud config set project $GCLOUD_PROJECT

gsutil cp ./build/web-service/webapp/libs/webapp-$CURRENT_VERSION.jar $GCLOUD_BUCKET
gsutil cp ./web-ui/build/distributions/web-ui-$CURRENT_VERSION.tar $GCLOUD_BUCKET

gcloud compute --project "$GCLOUD_PROJECT" instance-templates create "instance-template-$CURRENT_POSTFIX" \
    --machine-type "g1-small" --network "default" \
    --metadata "version=$CURRENT_VERSION,startup-script-url=gs://crom-vm-data/vm-packages/webapp-bootstrap.sh" \
    --maintenance-policy "MIGRATE" \
    --scopes default="https://www.googleapis.com/auth/cloud.useraccounts.readonly","https://www.googleapis.com/auth/devstorage.read_write","https://www.googleapis.com/auth/logging.write","https://www.googleapis.com/auth/monitoring.write" \
    --image "/centos-cloud/centos-7-v20160418" --boot-disk-size "10" --boot-disk-type "pd-ssd" \
    --boot-disk-device-name "instance-template-$CURRENT_POSTFIX"
