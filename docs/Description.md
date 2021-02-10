# Description

# Created
Using https://start.spring.io/

# Intro

Purpose:
- One writer posts a message to a topic on the clipboard
- Several readers can determin interest in a post categorie on the topic
- Once the message is posted to the topic it is distributed to all interested readers

# RabbitMQ
## Docker
Using a docker image to start rabbitmq
```shell
# docker run -p 9090:9090 prom/prometheus
# docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management
```

## Docker compose
Goto <root>/docs directory if first time
```shell
# docker-compose up
```

if not first time
```shell
# docker-compose start
```

## References
- see [Rabbit MQ Tutorial](https://www.rabbitmq.com/tutorials/tutorial-three-java.html)
- see [Article link from Tony Sloos](http://itsystemengineer.blogspot.com/2018/02/java-ee-7-startup-singleton-rabbitmq.html)
- see [API spec inspiration](https://cloud.google.com/pubsub?hl=nl)
