#!/bin/bash

set -e
set -v

echo $1
echo $2

eval sshKey='$'"$1_key"

sed -i -e "s@jdkhome@${5}@g" ./tasks/runscript.sh

mkdir -p ~/.ssh
echo "$sshKey" | tr -d '\r' > ~/.ssh/id_rsa
chmod 700 ~/.ssh/id_rsa
eval $(ssh-agent -s)
ssh-add ~/.ssh/id_rsa
ssh -o StrictHostKeyChecking=no "$4@$3" "cd $2; bash -s" < ./tasks/runscript.sh
exit 0