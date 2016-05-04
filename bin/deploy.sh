#!/bin/bash

NEW_VERSION=$1
URL="http://api.crom.tech/api/v1/project/ethankhall/repo/version-manager/version/$NEW_VERSION"

RESPONSE=`curl $URL -w '%{response_code}' -so /dev/null`

if [[ "200" == "$RESPONSE" ]]; then
    POST_FIX=`echo $NEW_VERSION | sed 's/\./-/g'`
    gcloud alpha compute rolling-updates start --group crom-main-api-group --template instance-template-$POST_FIX --min-instance-update-time 300

    gcloud alpha compute rolling-updates list
    echo "==> Use `gcloud alpha compute rolling-updates list` to show rollout status"
else
    echo "$NEW_VERSION is not a valid version, please try again."
    exit -1
fi
