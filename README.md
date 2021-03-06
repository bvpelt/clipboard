# Clipboard

## Requirements
- Java JDK 11 installed and on path
- Postgresql installed 
- Postgres database with:
    - name: clipboard
    - user: testuser
    - pwd: 12345
- Docker installed
- Docker compose installed

## Startup
To startup the application and get messages 
- goto the project directory
- startup rabbitmq
- startup the application
- use the api to add data
- use the testscript to do a test

### Startup Rabbitmq
The first time the docker images are fetched and created.
```shell
# cd docs
# docker-compose up
# cd -
```

After the first time
```shell
# cd docs
# docker-compose start
# cd -
```

### Startup the application
```shell
mvn spring-boot:run
WARNING: An illegal reflective access operation has occurred
WARNING: Illegal reflective access by com.google.inject.internal.cglib.core.$ReflectUtils$1 (file:/usr/share/maven/lib/guice.jar) to method java.lang.ClassLoader.defineClass(java.lang.String,byte[],int,int,java.security.ProtectionDomain)
WARNING: Please consider reporting this to the maintainers of com.google.inject.internal.cglib.core.$ReflectUtils$1
WARNING: Use --illegal-access=warn to enable warnings of further illegal reflective access operations
WARNING: All illegal access operations will be denied in a future release
[INFO] Scanning for projects...
[INFO] 
[INFO] ------------------------< bsoft.com:clipboard >-------------------------
[INFO] Building clipboard 0.0.1-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] >>> spring-boot-maven-plugin:2.4.2:run (default-cli) > test-compile @ clipboard >>>
[INFO] 
[INFO] --- maven-resources-plugin:3.2.0:resources (default-resources) @ clipboard ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Using 'UTF-8' encoding to copy filtered properties files.
[INFO] Copying 1 resource
[INFO] Copying 5 resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.8.1:compile (default-compile) @ clipboard ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 29 source files to /home/bvpelt/Develop/clipboard/target/classes
[INFO] 
[INFO] --- maven-resources-plugin:3.2.0:testResources (default-testResources) @ clipboard ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Using 'UTF-8' encoding to copy filtered properties files.
[INFO] skip non existing resourceDirectory /home/bvpelt/Develop/clipboard/src/test/resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.8.1:testCompile (default-testCompile) @ clipboard ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 1 source file to /home/bvpelt/Develop/clipboard/target/test-classes
[INFO] 
[INFO] <<< spring-boot-maven-plugin:2.4.2:run (default-cli) < test-compile @ clipboard <<<
[INFO] 
[INFO] 
[INFO] --- spring-boot-maven-plugin:2.4.2:run (default-cli) @ clipboard ---
[INFO] Attaching agents: []

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.4.2)

2021-03-05 20:52:45.533  INFO 8706 --- [  restartedMain] b.com.clipboard.ClipboardApplication     : Starting ClipboardApplication using Java 11.0.10 on pluto with PID 8706 (/home/bvpelt/Develop/clipboard/target/classes started by bvpelt in /home/bvpelt/Develop/clipboard)
2021-03-05 20:52:45.534  INFO 8706 --- [  restartedMain] b.com.clipboard.ClipboardApplication     : No active profile set, falling back to default profiles: default
2021-03-05 20:52:45.557  INFO 8706 --- [  restartedMain] o.s.b.devtools.restart.ChangeableUrls    : The Class-Path manifest attribute in /home/bvpelt/.m2/repository/org/liquibase/liquibase-core/4.2.2/liquibase-core-4.2.2.jar referenced one or more files that do not exist: file:/home/bvpelt/.m2/repository/org/liquibase/liquibase-core/4.2.2/commons-cli-1.4.jar,file:/home/bvpelt/.m2/repository/org/liquibase/liquibase-core/4.2.2/snakeyaml-1.24.jar
2021-03-05 20:52:45.558  INFO 8706 --- [  restartedMain] .e.DevToolsPropertyDefaultsPostProcessor : Devtools property defaults active! Set 'spring.devtools.add-properties' to 'false' to disable
2021-03-05 20:52:45.558  INFO 8706 --- [  restartedMain] .e.DevToolsPropertyDefaultsPostProcessor : For additional web related logging consider setting the 'logging.level.web' property to 'DEBUG'
2021-03-05 20:52:46.053  INFO 8706 --- [  restartedMain] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data JPA repositories in DEFAULT mode.
2021-03-05 20:52:46.078  INFO 8706 --- [  restartedMain] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 22 ms. Found 4 JPA repository interfaces.
2021-03-05 20:52:46.443  INFO 8706 --- [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
2021-03-05 20:52:46.448  INFO 8706 --- [  restartedMain] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2021-03-05 20:52:46.448  INFO 8706 --- [  restartedMain] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.41]
2021-03-05 20:52:46.490  INFO 8706 --- [  restartedMain] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2021-03-05 20:52:46.490  INFO 8706 --- [  restartedMain] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 932 ms
2021-03-05 20:52:46.516 DEBUG 8706 --- [  restartedMain] i.m.c.u.i.logging.InternalLoggerFactory  : Using SLF4J as the default logging framework
2021-03-05 20:52:46.617  INFO 8706 --- [  restartedMain] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
2021-03-05 20:52:46.920  INFO 8706 --- [  restartedMain] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
2021-03-05 20:52:47.222  INFO 8706 --- [  restartedMain] liquibase.lockservice                    : Successfully acquired change log lock
2021-03-05 20:52:47.498  INFO 8706 --- [  restartedMain] liquibase.changelog                      : Reading from public.databasechangelog
2021-03-05 20:52:47.530  INFO 8706 --- [  restartedMain] liquibase.lockservice                    : Successfully released change log lock
2021-03-05 20:52:47.572 DEBUG 8706 --- [  restartedMain] o.hibernate.jpa.internal.util.LogHelper  : PersistenceUnitInfo [
	name: default
	persistence provider classname: null
	classloader: org.springframework.boot.devtools.restart.classloader.RestartClassLoader@4ce83dc9
	excludeUnlistedClasses: true
	JTA datasource: null
	Non JTA datasource: HikariDataSource (HikariPool-1)
	Transaction type: RESOURCE_LOCAL
	PU root URL: file:/home/bvpelt/Develop/clipboard/target/classes/
	Shared Cache Mode: UNSPECIFIED
	Validation Mode: AUTO
	Jar files URLs []
	Managed classes names [
		bsoft.com.clipboard.model.ClipTopic
		bsoft.com.clipboard.model.RegistrationTicket
		bsoft.com.clipboard.model.Subscription
		bsoft.com.clipboard.model.User]
	Mapping files names []
	Properties []
2021-03-05 20:52:47.590  INFO 8706 --- [  restartedMain] org.hibernate.Version                    : HHH000412: Hibernate ORM core version 5.4.27.Final
2021-03-05 20:52:47.591  INFO 8706 --- [  restartedMain] org.hibernate.cfg.Environment            : HHH000205: Loaded properties from resource hibernate.properties: {hibernate.hbm2ddl.auto=none, hibernate.dialect=org.hibernate.dialect.PostgreSQL81Dialect, hibernate.bytecode.use_reflection_optimizer=false, hibernate.show_sql=true, hibernate.generate_statistics=false, hibernate.current_session_context_class=thread}
2021-03-05 20:52:47.639  INFO 8706 --- [  restartedMain] o.hibernate.annotations.common.Version   : HCANN000001: Hibernate Commons Annotations {5.1.2.Final}
2021-03-05 20:52:47.680  INFO 8706 --- [  restartedMain] org.hibernate.dialect.Dialect            : HHH000400: Using dialect: org.hibernate.dialect.PostgreSQL81Dialect
2021-03-05 20:52:47.900  INFO 8706 --- [  restartedMain] j.LocalContainerEntityManagerFactoryBean : Initialized JPA EntityManagerFactory for persistence unit 'default'
2021-03-05 20:52:48.137  INFO 8706 --- [  restartedMain] b.c.c.config.RabbitConfiguration         : Channel created
2021-03-05 20:52:48.156  INFO 8706 --- [  restartedMain] bsoft.com.clipboard.listener.Receiver    : Receiver - Waiting for messages
2021-03-05 20:52:48.214  WARN 8706 --- [  restartedMain] JpaBaseConfiguration$JpaWebConfiguration : spring.jpa.open-in-view is enabled by default. Therefore, database queries may be performed during view rendering. Explicitly configure spring.jpa.open-in-view to disable this warning
2021-03-05 20:52:48.275  INFO 8706 --- [  restartedMain] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
2021-03-05 20:52:48.601  INFO 8706 --- [  restartedMain] o.s.b.d.a.OptionalLiveReloadServer       : LiveReload server is running on port 35729
2021-03-05 20:52:48.603  INFO 8706 --- [  restartedMain] o.s.b.a.e.web.EndpointLinksResolver      : Exposing 15 endpoint(s) beneath base path '/actuator'
2021-03-05 20:52:48.628  INFO 8706 --- [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2021-03-05 20:52:48.639  INFO 8706 --- [  restartedMain] b.com.clipboard.ClipboardApplication     : Started ClipboardApplication in 3.324 seconds (JVM running for 3.606)
```
## Use the api to add data
### Add user
```shell
% content="{\"name\": \"mark\", \"email\": \"mark@universe.org\", \"endpoint\": \"https://mark.universe.org/news\"}"
% curl -v -H "Content-Type: application/json" -d "$content" http://localhost:8080/users
*   Trying 127.0.0.1:8080...
* TCP_NODELAY set
* Connected to localhost (127.0.0.1) port 8080 (#0)
> POST /users HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.68.0
> Accept: */*
> Content-Type: application/json
> Content-Length: 92
> 
* upload completely sent off: 92 out of 92 bytes
* Mark bundle as not supporting multiuse
< HTTP/1.1 200 
< Content-Type: application/json
< Transfer-Encoding: chunked
< Date: Sat, 06 Mar 2021 19:40:45 GMT
< 
* Connection #0 to host localhost left intact
{"id":1,"name":"mark","email":"mark@universe.org","endpoint":"https://mark.universe.org/news","status":"create","registrationTicket":{"userTicket":"4989f3cd-84b4-4e0a-a4f0-3fb0b9c7ea9c","status":"created"}}
```
### Add topic
```shell
% content="{\"name\": \"news\", \"description\": \"News items\"}"
% curl -v -H "Content-Type: application/json" -d "$content" http://localhost:8080/cliptopics
*   Trying 127.0.0.1:8080...
* TCP_NODELAY set
* Connected to localhost (127.0.0.1) port 8080 (#0)
> POST /cliptopics HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.68.0
> Accept: */*
> Content-Type: application/json
> Content-Length: 45
> 
* upload completely sent off: 45 out of 45 bytes
* Mark bundle as not supporting multiuse
< HTTP/1.1 200 
< Content-Type: application/json
< Transfer-Encoding: chunked
< Date: Sat, 06 Mar 2021 19:43:42 GMT
< 
* Connection #0 to host localhost left intact
{"id":1,"name":"news","description":"News items"} 

```
### Add subscription
```shell
% curl -v -X PUT -H "Content-Type: application/json"  http://localhost:8080/users/1/subscriptions?names=news
*   Trying 127.0.0.1:8080...
* TCP_NODELAY set
* Connected to localhost (127.0.0.1) port 8080 (#0)
> PUT /users/1/subscriptions?names=news HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.68.0
> Accept: */*
> Content-Type: application/json
> 
* Mark bundle as not supporting multiuse
< HTTP/1.1 200 
< Content-Type: application/json
< Transfer-Encoding: chunked
< Date: Sat, 06 Mar 2021 19:45:56 GMT
< 
* Connection #0 to host localhost left intact
{"id":1,"name":"mark","email":"mark@universe.org","endpoint":"https://mark.universe.org/news","status":"create","registrationTicket":{"userTicket":"4989f3cd-84b4-4e0a-a4f0-3fb0b9c7ea9c","status":"created"}}
```
### Post a message
In posting a message the registration key from the user is used as api-key.
```shell
% content="{ \"message\": \"Test message\", \"clipTopicName\": \"news\"}"
% curl -v -H "x-api-key: 4989f3cd-84b4-4e0a-a4f0-3fb0b9c7ea9c" -H "Content-type: application/json" -d "$content" http://localhost:8080/postmessage
*   Trying 127.0.0.1:8080...
* TCP_NODELAY set
* Connected to localhost (127.0.0.1) port 8080 (#0)
> POST /postmessage HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.68.0
> Accept: */*
> x-api-key: 4989f3cd-84b4-4e0a-a4f0-3fb0b9c7ea9c
> Content-type: application/json
> Content-Length: 53
> 
* upload completely sent off: 53 out of 53 bytes
* Mark bundle as not supporting multiuse
< HTTP/1.1 200 
< Content-Type: application/json
< Transfer-Encoding: chunked
< Date: Sat, 06 Mar 2021 19:49:05 GMT
< 
* Connection #0 to host localhost left intact
{"message":"Test message","clipTopicName":"news"}
```
### Start test
Use the testscript to send x messages.
If no arument is specified 100 messages will be send.
```shell
# docs/dotest.bash 20
```
