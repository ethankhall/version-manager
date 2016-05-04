#!/bin/bash

sudo apt-get remove python-virtualenv python-openssl python3-openssl
sudo apt-get update
sudo apt-get install python-openssl python3-openssl
sudo rm -rf /opt/google-cloud-sdk/
export CLOUDSDK_CORE_DISABLE_PROMPTS=1
export CLOUDSDK_PYTHON_SITEPACKAGES=1
curl https://sdk.cloud.google.com | bash
source ~/.bashrc
export CLOUDSDK_CORE_DISABLE_PROMPTS=0

gcloud --quiet components update
