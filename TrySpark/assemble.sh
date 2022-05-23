#!/bin/bash

directory="./spark-app"
rm -f ${directory}/TrySpark-1.0-SNAPSHOT.jar
if [ ! -d ${directory} ]; then
  echo "Create ${directory}"
  mkdir -p ${directory}
fi
cp ./build/libs/TrySpark-1.0-SNAPSHOT.jar ${directory}/TrySpark-1.0-SNAPSHOT.jar
chmod ugo+rx ${directory}/app.sh