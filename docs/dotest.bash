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
  curl -v -H "x-api-key: a726cd6b-cfb0-41bf-975c-897324d43be8" -H "Content-type: application/json" -d "$content" http://localhost:8080/postmessage
  let x=$x+1
done
