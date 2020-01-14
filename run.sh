#!/bin/bash
cp ressources/* classes/
cd classes
export CLASSPATH=`find ../lib -name "*.jar" | tr '\n' ':'`
if [ "$#" -eq "1" ]
then
	java -cp ${CLASSPATH}: $@
else
	java -cp ${CLASSPATH}: StudyParty
fi
cd ..
