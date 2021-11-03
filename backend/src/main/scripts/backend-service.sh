#!/bin/bash

cd ../../../target
exec java -jar backend-1.0-SNAPSHOT-jar-with-dependencies.jar $*

