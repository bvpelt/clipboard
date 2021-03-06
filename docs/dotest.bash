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
  curl -v -H "x-api-key: 4989f3cd-84b4-4e0a-a4f0-3fb0b9c7ea9c" -H "Content-type: application/json" -d "$content" http://localhost:8080/postmessage
  let x=$x+1
done
