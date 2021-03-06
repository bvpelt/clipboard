#!/bin/bash

content="{\"name\": \"mark\", \"email\": \"mark@universe.org\", \"endpoint\": \"https://mark.universe.org/news\"}"
curl -v -H "Content-Type: application/json" -d "$content" http://localhost:8080/users

#
# You need the registration key at other calls
#

content="{\"name\": \"news\", \"description\": \"News items\"}"
curl -v -H "Content-Type: application/json" -d "$content" http://localhost:8080/cliptopics

content="{\"name\": \"sport\", \"description\": \"Sport items\"}"
curl -v -H "Content-Type: application/json" -d "$content" http://localhost:8080/cliptopics

content="{\"name\": \"finance\", \"description\": \"Finance items\"}"
curl -v -H "Content-Type: application/json" -d "$content" http://localhost:8080/cliptopics

curl -v -X PUT -H "Content-Type: application/json"  http://localhost:8080/users/1/subscriptions?names=news


content="{ \"message\": \"Test message\", \"clipTopicName\": \"news\"}"
curl -v -H "x-api-key: 4989f3cd-84b4-4e0a-a4f0-3fb0b9c7ea9c" -H "Content-type: application/json" -d "$content" http://localhost:8080/postmessage
