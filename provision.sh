#!/bin/bash

sudo yum -y localinstall http://yum.postgresql.org/9.4/redhat/rhel-7-x86_64/pgdg-centos94-9.4-1.noarch.rpm
sudo yum -y install postgresql94-server postgresql94-contrib

sudo /usr/pgsql-9.4/bin/postgresql94-setup initdb

cat<<EOF > /tmp/pg_hba.conf
# "local" is for Unix domain socket connections only
local   all             all                                trust
# IPv4 local connections:
host    all             all             0.0.0.0/0          trust
EOF

sudo mv /tmp/pg_hba.conf /var/lib/pgsql/9.4/data/pg_hba.conf

sudo bash -c "echo \"listen_addresses = '*'\" >> /var/lib/pgsql/9.4/data/postgresql.conf"

sudo systemctl start postgresql-9.4
sudo systemctl enable postgresql-9.4

createuser -U postgres --createdb  version_manager_test
createdb --username=version_manager_test --owner version_manager_test version_manager_test
psql --user=postgres version_manager_test --command='CREATE EXTENSION "id-ossp";'

createuser -U postgres --createdb version
createdb --username=version --owner version version_manager
psql --user=postgres version_manager --command='CREATE EXTENSION "id-ossp";'
