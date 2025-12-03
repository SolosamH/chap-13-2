# Chapter 13 Exercise 2 - User Admin Application

This is a User Admin web application that uses JPA (Java Persistence API) to manage users in a MySQL database.

## Features
- Display all users
- Update user information
- Delete users
- Uses JPA with EclipseLink
- MySQL database backend

## Technologies Used
- Java Servlets
- JSP with JSTL
- JPA (Java Persistence API)
- EclipseLink
- MySQL
- Maven
- Tomcat

## Database Setup
The application connects to MySQL database `murach_jpa` with:
- Username: root
- Password: Solosamv1:)

## How to Run
1. Make sure MySQL is running
2. Navigate to project directory
3. Run: `mvn clean tomcat7:run`
4. Access: http://localhost:8080/admin

## Project Structure
- `src/main/java/murach/business/User.java` - User entity
- `src/main/java/murach/data/UserDB.java` - Database operations
- `src/main/java/murach/data/DBUtil.java` - JPA utility
- `src/main/java/murach/admin/UsersServlet.java` - Main servlet
- `src/main/webapp/index.jsp` - User list page
- `src/main/webapp/user.jsp` - User edit page
