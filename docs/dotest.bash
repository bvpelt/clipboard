#!/bin/bash

x=0
maxmsg=100

if [ "$#" -eq 1 ];
then
  maxmsg=$1
  echo maxmsg=$maxmsg
fi

while [ $x -lt $maxmsg ]
do
  echo $x
  content="{ \"message\": \"Vervolg message ${x}\", \"clipTopicName\": \"news\"}"
  echo $content
  curl -v -H "x-api-key: 1310eb96-0b0d-4566-904f-f2074fb1b3b8" -H "Content-type: application/json" -d "$content" http://localhost:8080/postmessage
  let x=$x+1
done
