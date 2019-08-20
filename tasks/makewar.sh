#!/bin/bash

set -e
set -v

echo $1
echo $2

echo "Copying properties files for $1"
#echo "$INDEX_PROPERTIES"
#cat "$INDEX_PROPERTIES"
#mv "$INDEX_PROPERTIES" /tmp/index.properties
#mv "$SECURITY_PROPERTIES" /tmp/security.properties
#mv -fv /tmp/index.properties ./src/main/resources
#mv -fv /tmp/security.properties ./src/main/resources

eval studiesJsonAddress='$'"studies_json_address_$1"
eval sshKey='$'"$1_key"
eval submissionsFilePath='$'"submissions_file_path_$1"
eval ftpUrl='$'"ftp_url_$1"

sed -i -v -e "s@studies.json@$studiesJsonAddress@g" ./src/main/resources/index.properties
sed -i -v -e "s/subfilepath/${submissionsFilePath}/g" ./src/main/resources/index.properties
sed -i -e "s/ftpurl/${ftpUrl}/g" ./src/main/resources/index.properties


mkdir -p ~/.ssh
echo "$sshKey" | tr -d '\r' > ~/.ssh/id_rsa
chmod 700 ~/.ssh/id_rsa
eval $(ssh-agent -s)
ssh-add ~/.ssh/id_rsa
echo 'copy to ribs'
mvn clean install spring-boot:repackage
scp -oStrictHostKeyChecking=no -v ./target/biostudies.war "$2"





