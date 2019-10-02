#!/bin/bash
eval user='$'"user"
eval host='$'"host"
echo "Copying ./target/biostudies.war to $dest"
scp -oStrictHostKeyChecking=no ./target/biostudies.war "$dest"
ssh -oStrictHostKeyChecking=no "${user}@${host}" <<'ENDSSH'
cd waraddress
pkill -9 -f biostudies
jdkhome -Dbiostudies -Dtomcat.hostname=$(hostname -s) -Xmx12G -jar ./biostudies.war > /dev/null 2>&1 &
ENDSSH