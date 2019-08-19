#!/bin/bash

set -e
set -v


echo 'Copying properties files for ribs'
echo "$INDEX_PROPERTIES"
cat "$INDEX_PROPERTIES"
mv "$INDEX_PROPERTIES" /tmp/index.properties
mv "$SECURITY_PROPERTIES" /tmp/security.properties
mv -fv /tmp/index.properties ../src/main/resources
mv -fv /tmp/security.properties ../src/main/resources

mkdir -p ~/.ssh
echo "$RIBS_KEY" | tr -d '\r' > ~/.ssh/id_rsa
chmod 700 ~/.ssh/id_rsa
 eval $(ssh-agent -s)
ssh-add ~/.ssh/id_rsa
echo 'copy to ribs'
mvn clean install spring-boot:repackage
scp -oStrictHostKeyChecking=no -v ../target/biostudies.war ma-svc@ribs:/tmp
