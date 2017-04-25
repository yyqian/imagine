## Imagine

A Hacker News clone in Java.

### Build and Run

1. Make sure JDK 8 and Maven are installed
2. Build: `mvn clean package`
3. Run: `java -jar target/imagine-0.0.1-SNAPSHOT.jar`
4. Visit the homepage: `http://localhost:8080`
5. By default, in-memory database is used and random data is initialized. 

### Based on:

- Spring Boot
- Spring Web MVC
- Spring Data JPA (Hibernate)
- Spring Security
- Thymeleaf 3
- Java 8 (Lambda, Stream, Optional etc)
- Maven
- Redis
- MySQL
- H2 Database (in development environment)
- Docker

### Changelog:

2016-10-12:

- Code refraction
- Add Docker deployment scripts

2016-10-09:

- Added CSRF protection
- Fixed several bugs

2016-09-26:

- Replaced Velocity with Thymeleaf
- Replaced Gradle with Maven

### TODO:

- Unit testing
- Handle invalid inputs more properly
- Add delete, update function for post and comment

### URI design

Post Controller:

- GET /post: get post list, return HTML, permitAll
- GET /post/{id}: get post with id and its comment tree, return HTML, permitAll
- GET /post/new/editor, get the editor to create a new post, return HTML, authenticated
- GET /post/{id}/editor, get the editor to update the post with id, return HTML, authenticated, IN THE FUTURE
- POST /post: create a post, request FORM or JSON, return {redirect to /post}, authenticated

Comment Controller:

- GET /comment: read comment list, return HTML, permitAll
- GET /comment/{id}: get comment with id, return HTML, permitAll
- POST /comment: create a comment, request FORM or JSON, return {redirect to /post/{id}}, authenticated

User Controller:

- GET /user: read user list, return HTML, permitAll but hidden
- GET /user/{id}: get user with id, return HTML, permitAll
- GET /user/self: get self, return HTML, authenticated
- POST /user: sign up, request FORM or JSON, return {redirect to /login}, permitAll
- POST /user/self: update user self, return HTML, authenticated

Vote Controller:

- POST /post/{id}/point: upvote a post with id, return JSON, authenticated
- POST /comment/{id}/point: upvote a comment with id, return JSON, authenticated
- DELETE /post/{id}/point: downvote a post with id, return JSON, authenticated, IN THE FUTURE
- DELETE /comment/{id}/point: downvote a comment with id, return JSON, authenticated, IN THE FUTURE

Other:

- GET /: get post list, same as /post, return HTML, permitAll
- GET /about: website introduction, return HTML, permitAll
- GET /logout: return {redirect to /post}, permitAll
- GET /login: login page, return HTML, permitAll
- POST /login: login, request FORM or JSON, return {redirect to /post}, permitAll
- GET /resetpw: reset password page, return HTML, authenticated
- POST /resetpw: reset password, request FORM or JSON, return {redirect to /user/self}, authenticated
- GET /forgot: forgot password page, return HTML, permitAll
- POST /forgot: forgot password, request FORM or JSON, return {redirect to /}, permitAll

### License

GPLv3 Licensed.

Happy hacking!
