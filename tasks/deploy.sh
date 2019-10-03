#!/bin/bash

# setup ssh key
eval sshKey='$'"sshKey"
mkdir -p ~/.ssh
echo "$sshKey" | tr -d '\r' > ~/.ssh/id_rsa
chmod 700 ~/.ssh/id_rsa
eval $(ssh-agent -s)
ssh-add ~/.ssh/id_rsa

eval jdkCommandLine='$'"jdkCommandLine"
eval deployDirectory='$'"deployDirectory"

#deploy
eval user='$'"user"
eval host='$'"host"
scp -oStrictHostKeyChecking=no ./target/biostudies.war "${user}@${host}:${deployDirectory}"
ssh -oStrictHostKeyChecking=no "${user}@${host}" << ENDSSH
cd $deployDirectory
pkill -9 -f biostudies
jdkCommandLine -Dtomcat.hostname=\$(hostname -s) -Xmx12G -jar ./biostudies.war > /dev/null 2>&1 &
ENDSSH