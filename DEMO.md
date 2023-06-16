### DEMO SCRIPT

- [ ] Create Quarkus project from the starter - https://code.quarkus.io/
    - [ ] quarkus-resteasy-reactive
    - [ ] quarkus-resteasy-reactive-jackson
    - [ ] quarkus-smallrye-openapi
    - [ ] quarkus-smallrye-health

- [ ] Unzip the downloaded archive and open in IDE
    - [ ] Walk through generated source

### Developer Experience

- [ ] Talk about "Dev Mode" and start the application using:
    ```
    ./mvnw quarkus:dev
    ```

- [ ] Show auto restart 
    - [ ] Defaults to restart on next invocation 
    - [ ] Force restart (s)
    - [ ] w Instrumentation (i)
- [ ] Show auto testing
    - [ ] Re-run all (r)
    - [ ] Re-run failed tests (f)
- [ ] Show dev mode menu (h)
- [ ] Show log level toggling (j)
- [ ] Open the DEV UI (d)
- [ ] Walk thru Dev UI
    - [ ] Extensions 
        - [ ] Health
        - [ ] OpenAPI / Swagger
    - [ ] Configuration
        - [ ] All properties (Filterable)
        - [ ] "Show only my properties"

### Dev Services
- [ ] Add OIDC extension / Copy mvn cmd from starter for quarkus-oidc
    ```
    ./mvnw quarkus:add-extension -Dextensions="io.quarkus:quarkus-oidc"
    ```

- [ ] Discuss/show how the app auto restarts and start of Dev Service Testcontainer
- [ ] Show new Keycloak container running in Docker
- [ ] Show Extension now in DEV UI
    - [ ] Show link to documentation

- [ ] Implement OIDC
    - [ ] Add annotation to GreetingResource
        ```
        @RolesAllowed("admin")
        ```

    - [ ] Show 401 (in Postman)

    - [ ] Demonstrate Keycloak 'dev mode'
        - [ ] Show link to Keycloak dev mode interface
        - [ ] Login as bob
        - [ ] Show 403
        - [ ] Logout / in as alice
        - [ ] Show 200

### Move to fully baked app
    
> This will expand on:
>  *Quarkus Extensions*
>  *Dev Services capabilties*

- [ ] Stop dev mode and close vs code
- [ ] Open VSCode on now-playing-quarkus
    - [ ] Talk about the app features and extensions used
        - [ ] ReST API for movie data
        - [ ] Postgresql / Hibernate / Flyway
        - [ ] MicroProfile
            - [ ] OpenAPI
            - [ ] Health
            - [ ] Metrics
            - [ ] Fault Tolerance
            - [ ] ReST Client
            - [ ] JWT Auth
        - [ ] Redis cache
    - [ ] Run in dev mode
        ```
        ./mvnw quarkus:dev
        ```

    - [ ] Show dev services containers
    - [ ] Demo running app in Postman
    

### Packaging
    
- [ ] Stop dev mode
- [ ] Show no running containers
    ```
    docker ps
    ```

- [ ] Start k8s cluster & k9s

   ```
   k3d cluster start quarkus
   ```

    ```
    k9s
    ```

- [ ] Show running "prod" containers
- [ ] Port forward containers so we can run locally

#### JVM Mode
- [ ] Package the app

    ```
    ./mvnw clean package -DskipTests
    ```

- [ ] Show produced package layers in target/quarkus-app dir  (discuss docker filesystem layers)
    ```
    tree ./target/quarkus-app
    ```

- [ ] Run in JVM mode 
    ```
    java -jar -Dquarkus.profile=local target/quarkus-app/quarkus-run.jar
    ```

- [ ] Run JVM Mode in Docker (created during package)
    ```
    docker run -i --rm -p 8080:8080 -equarkus.datasource.jdbc.url=jdbc:postgresql://host.docker.internal:5432/now-playing -equarkus.oidc.auth-server-url=http://host.docker.internal:8180/realms/quarkus-demo -equarkus.redis.hosts=redis://:TCtNvljreU@host.docker.internal/ keyholesoftware/now-playing-quarkus:1.0.0-SNAPSHOT
    ```

#### Native Mode

- [ ] Build native image
    ```
    ./mvnw clean package -DskipTests -Pnative -Dquarkus.native.container-build=true
    ```


#### Kubernetes

- [ ] Deploy to k8s
    ```
    ./mvnw clean package -DskipTests -Dquarkus.kubernetes.deploy=true
    ```

##### CALL API in app deployed to k8s 

- [ ] Port forward the quarkus app service (8080)
- [ ] Get JWT pbcopy
    ```
    curl --insecure -X POST http://localhost:8180/realms/quarkus-demo/protocol/openid-connect/token \
        --user now-playing:secret \
        -H 'content-type: application/x-www-form-urlencoded' \
        -H 'host: keycloak' \
        -d 'username=bob&password=bob&grant_type=password' | jq --raw-output '.access_token' | tr -d '\n' | pbcopy
    ```

- [ ] Hit with Postman