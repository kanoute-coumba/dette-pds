#!/bin/bash

cd ../../../target
exec java -jar client-1.0-SNAPSHOT-jar-with-dependencies.jar $*

