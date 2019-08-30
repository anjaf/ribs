#!/bin/bash

set -e
set -v

pkill -9 -f war
jdkhome -Dtomcat.hostname=$(hostname -s) -Xmx12G -jar ./biostudies.war > /dev/null 2>&1 &
disown -h %1