#!/bin/bash

set -e
set -v

echo $1
echo $2

eval sshKey='$'"$1_key"

mkdir -p ~/.ssh
echo "$sshKey" | tr -d '\r' > ~/.ssh/id_rsa
chmod 700 ~/.ssh/id_rsa
eval $(ssh-agent -s)
ssh-add ~/.ssh/id_rsa
ssh -oStrictHostKeyChecking=no "$3@$2" <<'ENDSSH'
cd waraddress
pkill -9 -f biostudies
jdkhome -Dbiostudies -Dtomcat.hostname=$(hostname -s) -Xmx12G -jar ./biostudies.war > /dev/null 2>&1 &
ENDSSH