#!/bin/bash

IFS_backup=$IFS

# initially, DATABASE_URL is postgres://user:password@hostname:port/db

# split into 'postgres://user:password' and 'hostname:port/db'
IFS="@"
read -ra parts <<< "$DATABASE_URL"
export DB_URL="jdbc:postgresql://${parts[1]}"

# split into 'postgres', '', and 'user:password'
IFS="/"
read -ra parts <<< "${parts[0]}"

# split into 'user' and 'password'
IFS=":"
read -ra parts <<< "${parts[2]}"

export DB_USER="${parts[0]}"
export DB_PASS="${parts[1]}"

IFS=$IFS_backup
