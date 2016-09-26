## Imagine

A Hacker News clone in Java.

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

### TODO:

- add delete, update function
- handle invalid inputs more properly
- convert po to vo before sending to view
- add CSRF protection
- add admin page
- unit testing

### Changelog:

2016-09-26:

1. replaced Velocity with Thymeleaf
2. replaced Gradle with Maven 

### URI design

Post Controller:

- GET /post: get post list, return {HTML}, permitAll
- GET /post/{id}: get post with id and its comment tree, return {HTML}, permitAll
- GET /post/new/editor, get the editor to create a new post, return {HTML}, authenticated
- GET /post/{id}/editor, get the editor to update the post with id, return {HTML}, authenticated, IN THE FUTURE
- POST /post: create a post, request {form} or {json}, return {redirect to /post}, authenticated

Comment Controller:

- GET /comment: read comment list, return {HTML}, permitAll
- GET /comment/{id}: get comment with id, return {HTML}, permitAll
- POST /comment: create a comment, request {form} or {json}, return {redirect to /post/{id}}, authenticated

User Controller:

- GET /user: read user list, return {HTML}, permitAll but hidden
- GET /user/{id}: get user with id, return {HTML}, permitAll
- GET /user/self: get self, return {HTML}, authenticated
- POST /user: sign up, request {form} or {json}, return {redirect to /login}, permitAll
- POST /user/self: update user self, return {HTML}, authenticated

Vote Controller:

- POST /post/{id}/point: upvote a post with id, return {json}, authenticated
- POST /comment/{id}/point: upvote a comment with id, return {json}, authenticated
- DELETE /post/{id}/point: downvote a post with id, return {json}, authenticated, IN THE FUTURE
- DELETE /comment/{id}/point: downvote a comment with id, return {json}, authenticated, IN THE FUTURE

Other:

- GET /: get post list, same as /post, return {HTML}, permitAll
- GET /about: website introduction, return {HTML}, permitAll
- GET /admin: admin's page, return {HTML}, ADMIN
- GET /logout: return {redirect to /post}, permitAll
- GET /login: login page, return {HTML}, permitAll
- POST /login: login, request {form} or {json}, return {redirect to /post}, permitAll
- GET /resetpw: reset password page, return {HTML}, authenticated
- POST /resetpw: reset password, request {form} or {json}, return {redirect to /user/self}, authenticated
- GET /forgot: forgot password page, return {HTML}, permitAll
- POST /forgot: forgot password, request {form} or {json}, return {redirect to /}, permitAll

### License

GPLv3 Licensed.

Happy hacking!
