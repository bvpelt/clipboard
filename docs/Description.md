# Description

# Created
Using https://start.spring.io/

# Intro

Purpose:
- One writer posts a message to a topic on the clipboard
- Several readers can determin interest in a post categorie on the topic
- Once the message is posted to the topic it is distributed to all interested readers

# RabbitMQ
- Access at http://localhost:15672/
- user guest
- pwd guest
- See https://www.javainuse.com/messaging/rabbitmq/exchange

# Multimodule project
- https://books.sonatype.com/mvnex-book/reference/multimodule-web-spring-sect-intro.html
- https://blog.avraampiperidis.com/dependency-management-and-versioning-with-a-maven-multi-module-project/

# JPA
- See many-to-many https://www.baeldung.com/jpa-many-to-many
- See userdefined queries https://attacomsian.com/blog/spring-data-jpa-query-annotation

# Openapi
See 
- https://www.baeldung.com/spring-rest-openapi-documentation
- https://swagger.io/resources/webinars/automate-code-first-approach-swagger/
- https://springdoc.org/

API Available on:
- http://localhost:8080/swagger-ui.html
- http://localhost:8080/v3/api-docs

Generated doc visible at localhost:8080/v3/api-docs

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
- see [Threads](https://mkyong.com/spring/spring-and-java-thread-example/)
