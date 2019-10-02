#!/bin/bash

# setup ssh key
eval sshKey='$'"sshKey"
mkdir -p ~/.ssh
echo "$sshKey" | tr -d '\r' > ~/.ssh/id_rsa
chmod 700 ~/.ssh/id_rsa
eval $(ssh-agent -s)
ssh-add ~/.ssh/id_rsa

eval jdkHome='$'"jdkHome"
eval deployDirectory='$'"deployDirectory"

#deploy
eval user='$'"user"
eval host='$'"host"
echo "Copying ./target/biostudies.war to ${user}@${host}:${deployDirectory}"
scp -oStrictHostKeyChecking=no ./target/biostudies.war "${user}@${host}:${deployDirectory}"
ssh -oStrictHostKeyChecking=no "${user}@${host}" <<'ENDSSH'
cd waraddress
pkill -9 -f biostudies
"${jdkhome}" -Dbiostudies -Dtomcat.hostname=$(hostname -s) -Xmx12G -jar ./biostudies.war > /dev/null 2>&1 &
ENDSSH