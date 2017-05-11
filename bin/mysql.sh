#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
docker run --detach --env-file $DIR/mysql.env --publish 3306:3306 mysql:latest
