#!/bin/bash

pkill -9 -f biostudies
jdkhome -Dbiostudies -Dtomcat.hostname=$(hostname -s) -Xmx12G -jar ./biostudies.war > /dev/null 2>&1 &
exit 0