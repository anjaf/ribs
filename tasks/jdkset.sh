#!/bin/bash
sed -i -e "s@jdkhome@${1}@g" ./tasks/runwar.sh
sed -i -e "s@waraddress@${2}@g" ./tasks/runwar.sh