### POC-R2DBC
spring webflux + r2dbc + postgresql

#### Run database and pgadmin
/docker
```bash
docker-compose up
```

#### Build package
```bash
mvn clean install
```

#### Run service
/service/application 
```bash
mvn spring-boot:run
```

#### Health check
```bash
curl localhost:8080/actuator/health
```

#### List users
```bash
curl localhost:8080/users
```