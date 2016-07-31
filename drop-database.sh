#!/bin/bash

dropdb --username=version_manager_test  version_manager_test
dropdb --username=version  version_manager

createdb --username=version_manager_test --owner version_manager_test version_manager_test
psql --user=postgres version_manager_test --command='CREATE EXTENSION "uuid-ossp";'

createdb --username=version --owner version version_manager
psql --user=postgres version_manager --command='CREATE EXTENSION "uuid-ossp";'
