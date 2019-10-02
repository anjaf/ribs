#!/bin/bash
echo "Updating properties files for ${CI_ENVIRONMENT_SLUG}"

indexPropertiesFile="./src/main/resources/index.properties"
eval baseDirectory='$'"index_files_baseDirectory"
eval ftpUrl='$'"index_files_ftpUrl"
sed -i -e "s@\(files\.baseDirectory=\).*@\1${baseDirectory}@g" $indexPropertiesFile
sed -i -e "s@\(files\.ftpUrl=\).*@\1${ftpUrl}@g" $indexPropertiesFile


securityPropertiesFile="./src/main/resources/security.properties"
eval profileUrl='$'"security_auth_profileUrl"
eval loginUrl='$'"security_auth_loginUrl"
sed -i -e "s@\(auth\.profileUrl=\).*@\1${profileUrl}@g" $indexPropertiesFile
sed -i -e "s@\(auth\.loginUrl=\).*@\1${loginUrl}@g" $indexPropertiesFile
