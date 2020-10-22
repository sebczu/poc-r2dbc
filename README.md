### **POC-R2DBC**
**spring boot webflux + r2dbc + postgresql**

#### 1. Run database and pgadmin
/docker
```bash
docker-compose up
```

#### 2. Build package
```bash
mvn clean install
```

#### 3. Run service
```bash
mvn clean install -Prun
```

#### 4. Health check
```bash
curl localhost:8080/actuator/health
```

#### 5. List users
```bash
curl localhost:8080/users
```