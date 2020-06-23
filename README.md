# MediaPoolMalBridge
This project is middle ware application between old and new system.

#### Requirements
 - Requirement for this project is to install Java version 8, Maven version 3.6.3 and MySQL server version 5.7

#### Build
Maven build tool is use for building the project.

The next command without parameters is building the project.

>Note: The profiles should be set before build and deploy

    mvn clean install
    
#### Profiles
In application.properties we set these profiles
- For development


    dev, standalone, enable controllers 
    
    
- For production 


    production, standalone, enable controllers


#### Database
- Database schema and user and password should be created
> Note: You can find the database schema and user/password in the application.properties file 


#### Deployment
There are two options
   - Deploy on WildFly (Client use this server WildFly version 10.1.0.Final)
        - For deploying on Wildfly just execute deploy or undeploy commands
   - Deploy on Tomcat (Dependency  in the pom.xml file is pre-required)
        - This is the dependency that should be added for Tomcat deployment
   
   
        <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-tomcat</artifactId>
           <version>2.0.5.RELEASE</version>
        </dependency>
