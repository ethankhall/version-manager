#!/bin/bash

sudo yum -y install postgresql-server
sudo postgresql-setup initdb

cat<<EOF > /tmp/pg_hba.conf
# "local" is for Unix domain socket connections only
local   all             all                                trust
# IPv4 local connections:
host    all             all             0.0.0.0/0          trust
EOF

sudo mv /tmp/pg_hba.conf /var/lib/pgsql/data/pg_hba.conf

sudo bash -c "echo \"listen_addresses = '*'\" >> /var/lib/pgsql/data/postgresql.conf"

sudo systemctl start postgresql
sudo systemctl enable postgresql

createuser -U postgres --createdb  version_manager_test
createdb --username=version_manager_test --owner version_manager_test version_manager_test
