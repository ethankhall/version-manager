#!/bin/bash

NEW_VERSION=$1
URL="http://api.crom.tech/api/v1/project/ethankhall/repo/version-manager/version/$NEW_VERSION"

RESPONSE=`curl $URL -w '%{response_code}' -so /dev/null`

if [[ "200" == "$RESPONSE" ]]; then
    POST_FIX=`echo $NEW_VERSION | sed 's/\./-/g'`
    gcloud alpha compute rolling-updates start --group crom-api-beta-group --template instance-template-$POST_FIX --min-instance-update-time 300 --zone us-central1-f --project crom-1289

    gcloud alpha compute rolling-updates list --zone us-central1-f --project crom-1289
    echo "==> Use 'gcloud alpha compute rolling-updates list --zone us-central1-f' to show rollout status"
else
    echo "$NEW_VERSION is not a valid version, please try again."
    exit -1
fi
