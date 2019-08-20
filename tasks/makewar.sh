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

indexDir="./src/main/resources/index.properties"
securityDir="./src/main/resources/security.properties"

eval studiesJsonAddress='$'"studies_json_address_$1"
eval sshKey='$'"$1_key"
eval submissionsFilePath='$'"submissions_file_path_$1"
eval ftpUrl='$'"ftp_url_$1"

eval securityHost='$'"security_host_$1"
eval securityPath='$'"security_path_$1"

sed -i -e "s@studies.json@${studiesJsonAddress}@g" $indexDir
sed -i -e "s@subfilepath@${submissionsFilePath}@g" $indexDir
sed -i -e "s@ftpurl@${ftpUrl}@g" $indexDir

sed -i -e "s@biostudy-dev@${securityHost}@g" $securityDir
sed -i -e "s@subfilepath@${securityPath}@g" $securityDir


mkdir -p ~/.ssh
echo "$sshKey" | tr -d '\r' > ~/.ssh/id_rsa
chmod 700 ~/.ssh/id_rsa
eval $(ssh-agent -s)
ssh-add ~/.ssh/id_rsa
echo 'copy to ribs'
mvn clean install spring-boot:repackage
scp -oStrictHostKeyChecking=no ./target/biostudies.war "ma-svc@$1:$2"





