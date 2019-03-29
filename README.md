# README #

Sample project to learn Outside-in TDD, 
writing end2end tests from the very beginning. Backend is in Java with SpringBoot and frontend in vanilla JavaScript.

### Setup ###

- Configure Selenium:

 Download latest version of ChromeDriver for your operating system

    https://chromedriver.storage.googleapis.com/index.html
    
Then copy the binary to `./src/test/chromedriver`    
   
- Configure the database:

  - move to `./src/main/docker`
  - run `docker-compose up --build`, it will start a postgresql container with a table *user* and a demo user. 
  - Verify that postgresql is running: `docker ps`
  - Verify that the environment variables in **Dockerfile** are the same in your **application.properties** file 
      - spring.datasource.url= jdbc:postgresql://localhost:5432/barbershop
      - spring.datasource.username=barbershop
      - spring.datasource.password=12345

- Build the project:
  - `mvn clean install`
  
- Run the server:

     `export JDBC_DATABASE_URL="jdbc:postgresql://host:5432/barbershop?user=barbershop&password=12345"`
     
    `java -jar -Dspring.profiles.active=prod target/*.jar`
    
The server can also be ran and debugged from IntelliJ     
    
The app will run on port 8080, open your browser to open 

    http://localhost:8080

You should be able to login with "barbershop@leanmind.es" and password "12345" and then, be able to hit the API with this:
    
    http://localhost:8080/api/establishments
  
  
  ### Troubleshooting
  
  - Sometimes, there are zombie containers, run to remove them:
  
      `docker rm $(docker ps -a -q)`
      
  - It's also possible that your docker is attached to a zombie volume. To delete them run:
     
       `docker volume prune`
      
### Database ###
Passwords are hashed with BCrypt. An online tool for this could be this one:      
      
     https://www.dailycred.com/article/bcrypt-calculator
      
### Production ###

#### Requirements ####
- Maven 3.5.0
- Java 8
- Docker
