#!/bin/bash

java \
    -server \
    -Xms6g -Xmx6g \
    -XX:MaxMetaspaceSize=2g \
    -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled \
    -XX:+UseCMSInitiatingOccupancyOnly \
    -XX:+ScavengeBeforeFullGC -XX:+CMSScavengeBeforeRemark \
    -XX:+PrintGCDateStamps -verbose:gc -XX:+PrintGCDetails -Xloggc:"/home/vagrant/web-server/gc.log" \
    -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=10 -XX:GCLogFileSize=100M \
    -Dsun.net.inetaddr.ttl=10 \
    -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath="/home/vagrant/web-server/`date`.hprof" \
    -jar webapp-1.0.1-SNAPSHOT.jar \
    --spring.profiles.active=dev
